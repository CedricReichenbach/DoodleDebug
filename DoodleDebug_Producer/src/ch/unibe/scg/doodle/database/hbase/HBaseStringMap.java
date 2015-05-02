package ch.unibe.scg.doodle.database.hbase;

import static org.apache.hadoop.hbase.util.Bytes.toBytes;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import org.apache.hadoop.hbase.HColumnDescriptor;
import org.apache.hadoop.hbase.HTableDescriptor;
import org.apache.hadoop.hbase.client.Delete;
import org.apache.hadoop.hbase.client.Get;
import org.apache.hadoop.hbase.client.HBaseAdmin;
import org.apache.hadoop.hbase.client.HTable;
import org.apache.hadoop.hbase.client.Put;
import org.apache.hadoop.hbase.client.Result;
import org.apache.hadoop.hbase.client.ResultScanner;
import org.apache.hadoop.hbase.client.Scan;
import org.apache.hadoop.hbase.util.Bytes;

import ch.unibe.scg.doodle.api.Doodleable;
import ch.unibe.scg.doodle.database.DoodleDatabaseMap;
import ch.unibe.scg.doodle.database.MetaInfo;
import ch.unibe.scg.doodle.properties.DoodleDebugProperties;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.core.util.CompositeClassLoader;

public class HBaseStringMap<T> extends DoodleDatabaseMap<T> {

	private static final String ID_COL_TITLE = "id";
	private static final String OBJECT_COL_TITLE = "object";

	private static HBaseAdmin hbaseAdmin; // XXX: Why not final?
	private HTable table;

	private final XStream xstream = new XStream();

	public HBaseStringMap(String applicationName, String tableName) {
		this(applicationName + "-" + tableName);
		MetaInfo.addApplicationName(applicationName);
	}

	public HBaseStringMap(String tableFullName) {
		super(tableFullName);
		try {
			hbaseAdmin = HBaseAdminProvider.get();
			assureTableExistence(tableFullName);
			this.table = new HTable(hbaseAdmin.getConfiguration(),
					tableFullName);
		} catch (IOException e) {
			System.out.println("Connection to HBase failed!");
			throw new RuntimeException(e);
		}

		// XXX: Why is this here? This class shouldn't know about xstream stuff.
		extendClassLoader();
	}

	private void extendClassLoader() {
		CompositeClassLoader extended = new CompositeClassLoader();
		ClassLoader ddClassLoader = Doodleable.class.getClassLoader();
		try {
			URL url = DoodleDebugProperties.tempDirForClasses().toURI().toURL();
			URL[] urls = { url };
			extended.add(new URLClassLoader(urls, ddClassLoader));
		} catch (MalformedURLException e) {
			e.printStackTrace();
		}
		extended.add(xstream.getClassLoader());
		xstream.setClassLoader(extended);
	}

	private void assureTableExistence(String tableName) throws IOException {
		if (hbaseAdmin.tableExists(tableName))
			return;

		HTableDescriptor tableDescriptor = new HTableDescriptor(tableName);
		tableDescriptor.addFamily(new HColumnDescriptor(ID_COL_TITLE));
		tableDescriptor.addFamily(new HColumnDescriptor(OBJECT_COL_TITLE));

		hbaseAdmin.createTable(tableDescriptor);
	}

	@Override
	public void clear() {
		try {
			HTableDescriptor descriptor = table.getTableDescriptor();

			hbaseAdmin.disableTable(table.getTableName());
			hbaseAdmin.deleteTable(table.getTableName());

			hbaseAdmin.createTable(descriptor);

			this.table = new HTable(table.getConfiguration(), table.getTableName());
		} catch (IOException e) {
			System.out.println("Failed to reset table: " + table);
			e.printStackTrace();
		}
	}

	@Override
	public T get(Object key) {
		if (!(key instanceof String)) {
			System.err.println("WARNING: HBaseMap key needs to be a String");
			return null;
		}

		Get get = new Get(toBytes((String) key));
		Get existenceGet = new Get(toBytes((String) key)); // XXX: Why?
		try {
			if (!table.exists(existenceGet))
				return null;

			Result result = table.get(get);

			byte[] valueBytes = result.getValue(toBytes(OBJECT_COL_TITLE),
					toBytes(OBJECT_COL_TITLE));
			String valueXML = Bytes.toString(valueBytes);
			@SuppressWarnings("unchecked")
			T t = (T) xstream.fromXML(valueXML); // XXX: Is there a better way?
			return t;
		} catch (IOException e) {
			System.out.println("Failed to load object from HBase. Key: " + key);
			e.printStackTrace();
			return null;
		}
	}

	@Override
	public Set<String> keySet() {
		Set<String> set = new HashSet<>();

		Collection<Result> results = getRowResults();
		for (Result result : results) {
			String rowId = Bytes.toString(result.getRow());
			set.add(rowId);
		}

		return set;
	}

	@Override
	public T put(String key, T value) {
		T previous = this.get(key);

		Put put = new Put(toBytes(key));
		// XXX: Why do we need a "qualifier" here (second argument)?
		put.add(toBytes(ID_COL_TITLE), toBytes(ID_COL_TITLE), toBytes(key));
		put.add(toBytes(OBJECT_COL_TITLE), toBytes(OBJECT_COL_TITLE),
				toBytes(xstream.toXML(value)));
		try {
			this.table.put(put);
		} catch (IOException e) {
			System.out.println("Failed to save object to HBase: " + value);
			e.printStackTrace();
		}

		return previous;
	}

	@Override
	public T remove(Object key) {
		if (!this.containsKey(key))
			return null;

		T oldValue = this.get(key);

		Delete delete = new Delete(toBytes((String) key));
		try {
			table.delete(delete);
		} catch (IOException e) {
			System.out.println("Failed to delete object from HBase. Key: "
					+ key);
			e.printStackTrace();
			return null;
		}

		return oldValue;
	}

	@Override
	public int size() {
		return getRowResults().size();
	}

	@Override
	public Collection<T> values() {
		Collection<T> values = new ArrayList<>();

		Collection<Result> results = getRowResults();
		for (Result result : results) {
			String valueXML = Bytes.toString(result.getValue(
					toBytes(OBJECT_COL_TITLE), toBytes(OBJECT_COL_TITLE)));
			@SuppressWarnings("unchecked")
			T value = (T) xstream.fromXML(valueXML); // XXX: Better solution?
			values.add(value);
		}

		return values;
	}

	private Collection<Result> getRowResults() {
		Collection<Result> list = new ArrayList<>();
		try {
			ResultScanner scanner = table.getScanner(new Scan());

			Result result = scanner.next();
			while (result != null) {
				list.add(result);
				result = scanner.next();
			}
		} catch (IOException e) {
			System.out.println("Could not list elements of table: " + table);
			e.printStackTrace();
		}
		return list;
	}
}

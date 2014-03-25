package ch.unibe.scg.doodle.hbase;

import static org.apache.hadoop.hbase.util.Bytes.toBytes;

import java.io.IOException;
import java.io.InterruptedIOException;
import java.util.AbstractMap;
import java.util.AbstractMap.SimpleEntry;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.HBaseConfiguration;
import org.apache.hadoop.hbase.HColumnDescriptor;
import org.apache.hadoop.hbase.HTableDescriptor;
import org.apache.hadoop.hbase.TableName;
import org.apache.hadoop.hbase.client.Delete;
import org.apache.hadoop.hbase.client.Get;
import org.apache.hadoop.hbase.client.HBaseAdmin;
import org.apache.hadoop.hbase.client.HTable;
import org.apache.hadoop.hbase.client.Put;
import org.apache.hadoop.hbase.client.Result;
import org.apache.hadoop.hbase.client.ResultScanner;
import org.apache.hadoop.hbase.client.RetriesExhaustedWithDetailsException;
import org.apache.hadoop.hbase.client.Scan;
import org.apache.hadoop.hbase.util.Bytes;

import com.thoughtworks.xstream.XStream;

public class HBaseMap implements Map<Integer, Object> {

	private static final String ID_COL_TITLE = "id";
	private static final String OBJECT_COL_TITLE = "object";

	private final HBaseAdmin hbaseAdmin;
	private HTable table;

	private final XStream xstream = new XStream();

	public HBaseMap(String tableName) {
		Configuration hbaseConfiguration = HBaseConfiguration.create();
		try {
			System.out.println("Connecting to HBase...");
			hbaseAdmin = new HBaseAdmin(hbaseConfiguration);
			System.out.println("Connection to HBase established.");

			assureTableExistence(tableName);
			this.table = new HTable(hbaseConfiguration, tableName);
		} catch (IOException e) {
			System.out.println("Connection to HBase failed!");
			throw new RuntimeException(e);
		}
	}

	private void assureTableExistence(String tableName) throws IOException {
		if (hbaseAdmin.tableExists(tableName))
			return;

		HTableDescriptor tableDescriptor = new HTableDescriptor(
				TableName.valueOf(tableName)); // XXX: That's weird...
		tableDescriptor.addFamily(new HColumnDescriptor(ID_COL_TITLE));
		tableDescriptor.addFamily(new HColumnDescriptor(OBJECT_COL_TITLE));

		hbaseAdmin.createTable(tableDescriptor);
	}

	@Override
	public void clear() {
		try {
			HTableDescriptor descriptor = table.getTableDescriptor();

			hbaseAdmin.disableTable(table.getName());
			hbaseAdmin.deleteTable(table.getName());

			hbaseAdmin.createTable(descriptor);

			this.table = new HTable(table.getConfiguration(), table.getName());
		} catch (IOException e) {
			System.out.println("Failed to reset table: " + table);
			e.printStackTrace();
		}
	}

	@Override
	public boolean containsKey(Object key) {
		return this.get(key) != null;
	}

	@Override
	public boolean containsValue(Object value) {
		return values().contains(value);
	}

	@Override
	public Set<Entry<Integer, Object>> entrySet() {
		HashSet<Entry<Integer, Object>> set = new HashSet<>();

		for (Integer key : keySet()) {
			Object value = get(key);
			SimpleEntry<Integer, Object> entry = new AbstractMap.SimpleEntry<>(
					key, value);
			set.add(entry);
		}

		return set;
	}

	@Override
	public Object get(Object key) {
		if (!(key instanceof Integer)) {
			System.err.println("WARNING: HBaseMap key needs to be an integer");
			return null;
		}

		Get get = new Get(toBytes((int) key));
		Get existenceGet = new Get(toBytes((int) key)); // XXX: Why?
		try {
			if (!table.exists(existenceGet))
				return null;

			Result result = table.get(get);

			byte[] valueBytes = result.getValue(toBytes(OBJECT_COL_TITLE),
					toBytes(OBJECT_COL_TITLE));
			String valueXML = Bytes.toString(valueBytes);
			return xstream.fromXML(valueXML);
		} catch (IOException e) {
			System.out.println("Failed to load object from HBase. Key: " + key);
			e.printStackTrace();
			return null;
		}
	}

	@Override
	public boolean isEmpty() {
		return this.size() == 0;
	}

	@Override
	public Set<Integer> keySet() {
		Set<Integer> set = new HashSet<>();

		Collection<Result> results = getRowResults();
		for (Result result : results) {
			int rowId = Bytes.toInt(result.getRow());
			set.add(rowId);
		}

		return set;
	}

	@Override
	public Object put(Integer key, Object value) {
		Object previous = this.get(key);

		Put put = new Put(toBytes(key));
		// XXX: Why do we need a "qualifier" here (second argument)?
		put.add(toBytes(ID_COL_TITLE), toBytes(ID_COL_TITLE), toBytes(key));
		put.add(toBytes(OBJECT_COL_TITLE), toBytes(OBJECT_COL_TITLE),
				toBytes(xstream.toXML(value)));
		try {
			this.table.put(put);
		} catch (RetriesExhaustedWithDetailsException | InterruptedIOException e) {
			System.out.println("Failed to save object to HBase: " + value);
			e.printStackTrace();
		}

		return previous;
	}

	@Override
	public void putAll(Map<? extends Integer, ? extends Object> otherMap) {
		for (Integer key : otherMap.keySet())
			this.put(key, otherMap.get(key));
	}

	@Override
	public Object remove(Object key) {
		if (!this.containsKey(key))
			return null;

		Object oldValue = this.get(key);

		Delete delete = new Delete(toBytes((int) key));
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
	public Collection<Object> values() {
		Collection<Object> values = new ArrayList<>();

		Collection<Result> results = getRowResults();
		for (Result result : results) {
			String valueXML = Bytes.toString(result.getValue(
					toBytes(OBJECT_COL_TITLE), toBytes(OBJECT_COL_TITLE)));
			Object value = xstream.fromXML(valueXML);
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

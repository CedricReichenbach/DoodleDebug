package ch.unibe.scg.doodle.hbase;

import java.io.IOException;
import java.io.InterruptedIOException;
import java.util.Collection;
import java.util.Map;
import java.util.Set;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.HBaseConfiguration;
import org.apache.hadoop.hbase.HColumnDescriptor;
import org.apache.hadoop.hbase.HTableDescriptor;
import org.apache.hadoop.hbase.TableName;
import org.apache.hadoop.hbase.client.HBaseAdmin;
import org.apache.hadoop.hbase.client.HTable;
import org.apache.hadoop.hbase.client.Put;
import org.apache.hadoop.hbase.client.RetriesExhaustedWithDetailsException;

import static org.apache.hadoop.hbase.util.Bytes.toBytes;

import com.thoughtworks.xstream.XStream;

public class HBaseMap implements Map<Integer, Object> {

	private static final String ID_COL_TITLE = "id";
	private static final String OBJECT_COL_TITLE = "object";

	private final HBaseAdmin hbaseAdmin;
	private final HTable table;

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
		// TODO Auto-generated method stub

	}

	@Override
	public boolean containsKey(Object key) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean containsValue(Object value) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public Set<java.util.Map.Entry<Integer, Object>> entrySet() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Object get(Object key) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean isEmpty() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public Set<Integer> keySet() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Object put(Integer index, Object value) {
		Object previous = this.get(index);

		Put put = new Put(toBytes(index));
		// XXX: Why do we need a "qualifier" here (second argument)?
		put.add(toBytes(ID_COL_TITLE), toBytes(ID_COL_TITLE), toBytes(index));
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
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int size() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public Collection<Object> values() {
		// TODO Auto-generated method stub
		return null;
	}
}

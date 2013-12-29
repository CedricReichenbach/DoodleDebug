package ch.unibe.scg.doodle.hbase;

import java.io.IOException;
import java.util.Collection;
import java.util.Map;
import java.util.Set;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.HBaseConfiguration;
import org.apache.hadoop.hbase.HColumnDescriptor;
import org.apache.hadoop.hbase.HTableDescriptor;
import org.apache.hadoop.hbase.TableName;
import org.apache.hadoop.hbase.client.HBaseAdmin;

public class HBaseMap implements Map<Integer, Object> {

	private static final String TABLE_NAME = "objects";
	private static final String ID_COL_TITLE = "id";
	private static final String OBJECT_COL_TITLE = "object";

	private final HBaseAdmin hbaseAdmin;

	public HBaseMap() {
		Configuration hbaseConfiguration = HBaseConfiguration.create();
		try {
			System.out.println("Connecting to HBase...");
			hbaseAdmin = new HBaseAdmin(hbaseConfiguration);
			System.out.println("Connection to HBase established.");

			assureTableExistence();
		} catch (IOException e) {
			System.out.println("Connection to HBase failed!");
			throw new RuntimeException(e);
		}
	}

	private void assureTableExistence() throws IOException {
		if (hbaseAdmin.tableExists(TABLE_NAME))
			return;

		HTableDescriptor tableDescriptor = new HTableDescriptor(
				TableName.valueOf(TABLE_NAME)); // XXX: That's weird...
		tableDescriptor.addFamily(new HColumnDescriptor(ID_COL_TITLE));
		tableDescriptor.addFamily(new HColumnDescriptor(OBJECT_COL_TITLE));

		hbaseAdmin.createTable(tableDescriptor);
	}

	@Override
	public void clear() {
		// TODO Auto-generated method stub

	}

	@Override
	public boolean containsKey(Object arg0) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean containsValue(Object arg0) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public Set<java.util.Map.Entry<Integer, Object>> entrySet() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Object get(Object arg0) {
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
	public Object put(Integer arg0, Object arg1) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void putAll(Map<? extends Integer, ? extends Object> arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public Object remove(Object arg0) {
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

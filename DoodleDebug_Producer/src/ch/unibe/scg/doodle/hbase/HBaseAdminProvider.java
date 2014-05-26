package ch.unibe.scg.doodle.hbase;

import java.io.IOException;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.HBaseConfiguration;
import org.apache.hadoop.hbase.client.HBaseAdmin;

public class HBaseAdminProvider {

	private static Configuration hbaseConfiguration = HBaseConfiguration
			.create();
	private static HBaseAdmin hbaseAdmin;

	public static HBaseAdmin get() throws IOException {
		if (hbaseAdmin == null) {
			System.out.println("DoodleDebug is connecting to HBase...");
			hbaseAdmin = new HBaseAdmin(hbaseConfiguration);
			System.out.println("Connection to HBase established.");
		}
		return hbaseAdmin;
	}
}

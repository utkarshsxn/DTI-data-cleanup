package org.fireeye.datasource;

import java.sql.Connection;

public class HiveDBConnection extends DBConnection{

	private String drivername;
	
	public HiveDBConnection(String dburl, String database, String username, String password) {
		super(dburl, database, username, password);
		drivername = "org.apache.hive.jdbc.HiveDriver";
	}
	
	public Connection getConnection(){
		System.out.println("in Hive");
		return super.getConnection(drivername);
	}

}

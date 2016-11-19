package org.fireeye.datasource;

import java.sql.Connection;

public class PostgresDBConnection extends DBConnection{
	
	private String drivername;
	public PostgresDBConnection(String dburl, String database, String username, String password) {
		super(dburl, database, username, password);
		drivername = "org.postgresql.Driver";
	}
	
	public Connection getConnection(){
		System.out.println("in Postgres");
		return super.getConnection(drivername);
	}
	
}

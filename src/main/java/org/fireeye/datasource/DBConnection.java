package org.fireeye.datasource;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

class DBConnection{

	private Connection connection = null;
	private String dburl;
	private String username;
	private String password;
	private String database;
	
	protected DBConnection(String dburl, String database, String username, String password) {
		this.dburl = dburl;
		this.username = username;
		this.password = password;
		this.database = database;
	}

	protected Connection getConnection(String drivername){
		
		StringBuilder jdbcconnectionstring = new StringBuilder(dburl);
		jdbcconnectionstring = jdbcconnectionstring.append("/").append(database);
		
		System.out.println("**** Establishing Connection ****");	
		try{
			System.out.println("Connection String :" + jdbcconnectionstring.toString() +"  Driver Name: "+drivername);
			Class.forName(drivername);	
			connection = DriverManager.getConnection(jdbcconnectionstring.toString(), username, password);
			if(connection != null){
				System.out.println("Connection Establish");
			}
			
		}catch (ClassNotFoundException e) {
			System.out.println("Driver Not Found..!!");
			e.printStackTrace();
		}catch(SQLException sqle){
			System.out.println("*** Error while connecting db using string "+ jdbcconnectionstring +" for user "+ username + " with password "+ password);
			sqle.printStackTrace();
		}
		
		return connection;
	}
}

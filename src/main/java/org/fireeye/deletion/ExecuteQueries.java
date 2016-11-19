package org.fireeye.deletion;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class ExecuteQueries {
	Connection connection = null;
	GenerateQueries generateQueries = null;

	public ExecuteQueries(Connection connection) {
		this.connection = connection;
		generateQueries = new GenerateQueries();
	}
	
	/*public void executeGenratedQuery(String query) throws SQLException{
		Statement statement = connection.createStatement();
		statement.execute(query);
	}*/
	
	public void createTemporaryTable(String tableName) throws SQLException{
		String createTemporaryTableQuery = generateQueries.getCreateTemporaryTableQuery(tableName);
		Statement stmt = connection.createStatement();

		// drop table if it exists..!!
		if(isTableExists(tableName)){
			String dropTableQuery = generateQueries.getDropTemporaryTableQuery(tableName);
			stmt.execute(dropTableQuery);
		}
		stmt.execute(createTemporaryTableQuery);
	}
	
	public boolean isTableExists(String tableName) throws SQLException{
		Statement stmt = connection.createStatement();
		String query = generateQueries.getShowTableQuery(tableName);
		ResultSet resultSet = stmt.executeQuery(query);
		if(resultSet.next())
			return true;
		else
			return false;
	}
	
	public void setDynamicPartitioning() throws SQLException{
		Statement stmt = connection.createStatement();
		stmt.execute("SET hive.exec.dynamic.partition = true");
		stmt.execute("SET hive.exec.dynamic.partition.mode = nonstrict");
	}
	
	public void insertData(String tableName, String partitionColumn, String primaryKeyColumn, long partitionValue) throws SQLException{
		String insertQuery =  generateQueries.getInsertDataQuery(tableName, partitionColumn, primaryKeyColumn, partitionValue);
		Statement stmt = connection.createStatement();
		
		stmt.execute(insertQuery);	
	}
	
	public ResultSet getDataFromDataCleanupTable() throws SQLException{
		
		String fetchDataFromDataCleanTable = generateQueries.getFetchDataFromDataCleanTableQuery();
		Statement stmt = connection.createStatement();
		ResultSet resultSet = stmt.executeQuery(fetchDataFromDataCleanTable);	
		return resultSet;
	}
	
	public void alterTableToExchangePartitions(String tableName, String partitionColumn, long partitionValue) throws SQLException{
		String query = generateQueries.getAlterTableToExchangePartitionsQuery(tableName, partitionColumn, partitionValue);
		Statement stmt = connection.createStatement();
		stmt.execute(query);	
	}
	
	public void alterAndCreateFesysTable() throws SQLException {
		String alterTableQuery = generateQueries.getAlterFesystableQuery();
		String createTableQuery = generateQueries.getCreateFesystableQuery();
		Statement stmt = connection.createStatement();
		stmt.execute(alterTableQuery);
		stmt.executeQuery(createTableQuery);
	}
	
	
	

}

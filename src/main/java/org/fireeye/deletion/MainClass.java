package org.fireeye.deletion;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import org.fireeye.datasource.HiveDBConnection;
import org.fireeye.datasource.PostgresDBConnection;
import org.fireeye.environment.ReadArguments;
import org.fireeye.environment.ReadPropertyFile;

public class MainClass {

	public static void main(String[] args) throws Exception{
		ReadArguments readArgumentObj = new ReadArguments();
		ReadPropertyFile readPropertyFileObj = new ReadPropertyFile(readArgumentObj.getNamespace(args));
		
		HiveDBConnection hiveDBConnectionObj = new HiveDBConnection(readPropertyFileObj.getHiveUrl(), readPropertyFileObj.getHiveDbname(), 
													readPropertyFileObj.getHiveUserName(), readPropertyFileObj.getHivePassword());
		try{
			MainClass mainClass = new MainClass();
			mainClass.runQueries(hiveDBConnectionObj);
		}catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	
	public void runQueries(HiveDBConnection hiveDBConnectionObj) throws SQLException{
		Connection connection = hiveDBConnectionObj.getConnection();
		if(connection!= null){
			
			ExecuteQueries executeQueriesObj = new ExecuteQueries(connection);
			executeQueriesObj.setDynamicPartitioning();
			ResultSet resultSet = executeQueriesObj.getDataFromDataCleanupTable();
			
			while(resultSet.next()){
				String tableName = resultSet.getString("table_name");
				long partitionValue = resultSet.getInt("partition_value");
				String partitionColumn = resultSet.getString("partition_column");
				String primaryKeyColumn = resultSet.getString("primary_key_column");
				
				executeQueriesObj.createTemporaryTable(tableName);
				executeQueriesObj.insertData(tableName, partitionColumn, primaryKeyColumn, partitionValue);
				executeQueriesObj.alterTableToExchangePartitions(tableName, partitionColumn, partitionValue);	
			}
			executeQueriesObj.alterAndCreateFesysTable();
		}else{
			System.out.println("Not able to connect to DB...!!");
		}
		
	}
	


}

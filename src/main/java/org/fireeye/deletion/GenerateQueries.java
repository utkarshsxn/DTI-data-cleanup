package org.fireeye.deletion;

import org.fireeye.utilities.DateUtility;

public class GenerateQueries {

	public String getCreateTemporaryTableQuery(String tableName){
		String createTemporaryTableQuery = "create table if not exists "+ tableName +"_temp like "+tableName;
		return createTemporaryTableQuery;
	}
	
	public String getDropTemporaryTableQuery(String tableName){
		String dropTableQuery = "drop table "+ tableName +"_temp";
		return dropTableQuery;
	}
	
	public String getShowTableQuery(String tableName){
		String query = "show tables like '"+ tableName +"'";
		return query;
	}
	
	public String getInsertDataQuery(String tableName, String partitionColumn, String primaryKeyColumn, long partitionValue){
		String insertQuery =  "insert into " + tableName +"_temp "
				+ "select * from "+ tableName +" f "
						+ "left outer join fesys_data_cleanup s "
						+ "on ( s.table_name = '"+ tableName +"' "
						+ "and s.partition_value = "+ partitionValue + " "
						+ "and f.primary_key_column = s.primary_key_value ) "
						+ "where f."+ partitionColumn +" = " + partitionValue +" "
						+ "and s.primary_key is null";
		return insertQuery;
	}
	
	public String getFetchDataFromDataCleanTableQuery(){
		String fetchDataFromDataCleanTable = "select table_name, partition_value, min(partition_column) as partition_column, "
				+ "min(primary_key_column) as primary_key_column "
				+ "from fesys_data_cleanup "
				+ "group by table_name, partition_value";
		return fetchDataFromDataCleanTable;
	}
	
	public String getAlterTableToExchangePartitionsQuery(String tableName, String partitionColumn, long partitionValue){
		String query = "alter table "+tableName+" exchange partition ("+ partitionColumn +"="+ partitionValue +") with table "+tableName+"_temp";
		return query;
	}
	
	public String getAlterFesystableQuery(){
		String todayDate = new DateUtility().getTodayDate();
		String alterTableQuery = "ALTER TABLE FESYS_DATA_CLEANUP RENAME TO FESYS_DATA_CLEANUP_COMPLETED_"+todayDate;
		return alterTableQuery;
	}
	
	public String getCreateFesystableQuery(){
		String todayDate = new DateUtility().getTodayDate();
		String createTableQuery = "CREATE TABLE FESYS_DATA_CLEANUP like FESYS_DATA_CLEANUP_COMPLETED_"+todayDate;
		return createTableQuery;
	}
	
}

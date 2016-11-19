package org.fireeye.environment;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import net.sourceforge.argparse4j.inf.Namespace;


/*
 * 
 */
public class ReadPropertyFile {
	
	protected Properties prop = null;
	protected InputStream input = null;
	
	public ReadPropertyFile(Namespace ns) throws IOException{
		
		input = new FileInputStream(new File(ns.getString("cf"))); //ReadPropertyFile.class.getClassLoader().getResourceAsStream(ns.getString("cf"));
		prop = new Properties();
		prop.load(input);
	}
		
	
	// hive attributes from the config file.
	public String getHiveUrl(){
		return prop.getProperty("hive_url");
	}
	
	public String getHiveDbname(){
		return prop.getProperty("hive_dbname");
	}
	
	public String getHiveTableName(){
		return prop.getProperty("hive_tablename");
	}
	
	public String getHiveUserName(){
		return prop.getProperty("hive_username");
	}
	
	public String getHivePassword(){
		return prop.getProperty("hive_password");
	}
	
	
	
}

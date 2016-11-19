package org.fireeye.environment;

import net.sourceforge.argparse4j.ArgumentParsers;
import net.sourceforge.argparse4j.inf.ArgumentParser;
import net.sourceforge.argparse4j.inf.ArgumentParserException;
import net.sourceforge.argparse4j.inf.Namespace;

public class ReadArguments {

	public Namespace getNamespace(String[] args) throws ArgumentParserException{
		ArgumentParser parser = ArgumentParsers.newArgumentParser("Sync_Postgres_Hive_Tables")
									.defaultHelp(true)
									.description("This synchronize postgres updates/deletes to Hive Table");
		
		parser.addArgument("-cf","-file").required(true)
			.help("Specify Configuration File");
		
		Namespace ns = parser.parseArgs(args); 
		return ns;
	}
}

package eu.drseus.cb.backend.util;

import org.apache.commons.configuration.Configuration;
import org.apache.commons.configuration.ConfigurationException;
import org.apache.commons.configuration.PropertiesConfiguration;

public class Config {

	public static Forum forum;
	public static Database db;
	
	static {
		forum = new Forum();
		db = new Database();
	}
	
	public static class Forum {
		
		public String user;
		public String password;
		
		@Override
		public String toString() {
			return "Forum [user=" + user + ", password=" + password + "]";
		}
		
	}
	
	public static class Database {
		
		public String host;
		public int port;
		public String user;
		public String password;
		public String database;
		
		@Override
		public String toString() {
			return "Database [host=" + host + ", port=" + port + ", user="
					+ user + ", password=" + password + ", database="
					+ database + "]";
		}
		
	}
	
	public static String asString() {
		return "Config [forum=" + Config.forum.toString() + ", db=" + Config.db.toString() + "]";
	}

	public static void loadConfig(String file) throws ConfigurationException {
		Configuration config = new PropertiesConfiguration(file);
		
		forum.user = config.getString("forum.user", "user");
		forum.password = config.getString("forum.password", "password");

		db.host = config.getString("db.host", "localhost");
		db.port = config.getInt("db.port", 0);
		db.user = config.getString("db.user", "user");
		db.password = config.getString("db.password", "password");
		db.database = config.getString("db.database", "database");
		
	}


	
}
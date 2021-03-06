package eu.drseus.cb.backend.util;

import org.apache.commons.configuration.Configuration;
import org.apache.commons.configuration.ConfigurationException;
import org.apache.commons.configuration.PropertiesConfiguration;

public class Config {

	public static Forum forum;
	public static Database db;
	public static GCM gcm;
	public static Web web;
	
	static {
		forum = new Forum();
		db = new Database();
		gcm = new GCM();
		web = new Web();
	}
	
	public static class Forum {
		
		public String user;
		public String password;
		public Double refreshDelay;
		
		@Override
		public String toString() {
			return "Forum [user=" + user + ", password=" + password + ", refreshDelay=" + refreshDelay + "]";
		}
		
	}
	
	public static class GCM {
		
		public String key;

		@Override
		public String toString() {
			return "GCM [key=" + key + "]";
		}
		
	}
	
	public static class Web {
		
		public Integer port;

		@Override
		public String toString() {
			return "Web [port=" + port + "]";
		}
		
	}
	
	public static class Database {
		
		public String host;
		public Integer port;
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
		try {

			Configuration config = new PropertiesConfiguration(file);

			forum.user = config.getString("forum.user", "user");
			forum.password = config.getString("forum.password", "password");
			forum.refreshDelay = config.getDouble("forum.refreshDelay", 5.0);
			
			gcm.key = config.getString("gcm.key", "defaultkey");
			
			web.port = config.getInt("web.port", 8080);

			db.host = config.getString("db.host", "localhost");
			db.port = config.getInt("db.port", 0);
			db.user = config.getString("db.user", "user");
			db.password = config.getString("db.password", "password");
			db.database = config.getString("db.database", "database");

		} catch (Exception e) {
			throw new ConfigurationException(e);
		}
	}


	
}

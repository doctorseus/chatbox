package eu.drseus.cb.backend.util;

import org.apache.commons.configuration.Configuration;
import org.apache.commons.configuration.ConfigurationException;
import org.apache.commons.configuration.PropertiesConfiguration;

public class Config {

	public Forum forum = new Forum();
	public Database db = new Database();
	public GCM gcm = new GCM();
	public Web web = new Web();
	
	private Config(){}
	
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
	
	@Override
	public String toString() {
		return "Config [forum=" + forum.toString() + ", gcm=" + gcm.toString() + ", web=" + web.toString() + ", db=" + db.toString() + "]";
	}
	
	public static Config createFromFile(String file) throws ConfigurationException {
		try {

			Config obj = new Config();
			
			Configuration config = new PropertiesConfiguration(file);

			obj.forum.user = config.getString("forum.user", "user");
			obj.forum.password = config.getString("forum.password", "password");
			obj.forum.refreshDelay = config.getDouble("forum.refreshDelay", 5.0);
			
			obj.gcm.key = config.getString("gcm.key", "defaultkey");
			
			obj.web.port = config.getInt("web.port", 8080);

			obj.db.host = config.getString("db.host", "localhost");
			obj.db.port = config.getInt("db.port", 0);
			obj.db.user = config.getString("db.user", "user");
			obj.db.password = config.getString("db.password", "password");
			obj.db.database = config.getString("db.database", "database");

			return obj;
		} catch (Exception e) {
			throw new ConfigurationException(e);
		}
	}
	
}

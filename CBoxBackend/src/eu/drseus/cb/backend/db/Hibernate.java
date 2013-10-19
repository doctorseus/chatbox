package eu.drseus.cb.backend.db;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;

public class Hibernate {

	/**
	 * hibernate.cfg.xml
	 * 

<?xml version='1.0' encoding='UTF-8'?>
<!DOCTYPE hibernate-configuration PUBLIC
   "-//Hibernate/Hibernate Configuration DTD 3.0//EN"
   "http://www.hibernate.org/dtd/hibernate-configuration-3.0.dtd">
<hibernate-configuration>
	<session-factory>
		<property name="connection.url">jdbc:postgresql://192.168.0.61:5432/chatbox</property>
		<property name="connection.username">chatbox</property>
		<property name="connection.password">chatbox</property>
		<property name="connection.driver_class">org.postgresql.Driver</property>
		
		<property name="dialect">org.hibernate.dialect.PostgreSQLDialect</property>
		
		<property name="cache.provider_class">org.hibernate.cache.EhCacheProvider</property>
		
		<property name="current_session_context_class">thread</property>
		
		<property name="hibernate.transaction.factory_class">org.hibernate.transaction.JDBCTransactionFactory</property>
		
		<property name="hibernate.hbm2ddl.auto">create</property>
		
		<mapping class="eu.drseus.cb.backend.forum.chat.Message" />
	</session-factory>
</hibernate-configuration>

	 */
	
	private static org.hibernate.SessionFactory sessionFactory;

	static {
		Configuration configuration = new Configuration();

		//Default Configuration
		configuration.setProperty("hibernate.connection.url", "jdbc:postgresql://192.168.0.61:5432/chatbox");
		configuration.setProperty("hibernate.connection.username", "postgres");
		configuration.setProperty("hibernate.connection.password", "postgres");
		configuration.setProperty("hibernate.connection.driver_class", "org.postgresql.Driver");
		
		configuration.setProperty("hibernate.temp.use_jdbc_metadata_defaults", "false");
		configuration.setProperty("hibernate.dialect", "org.hibernate.dialect.PostgreSQLDialect");
		
		configuration.setProperty("hibernate.cache.provider_class", "org.hibernate.cache.EhCacheProvider");
		
		configuration.setProperty("hibernate.current_session_context_class", "thread");
		
		//configuration.setProperty("hibernate.hibernate.transaction.factory_class", "org.hibernate.transaction.JDBCTransactionFactory");
		//configuration.setProperty("hibernate.hibernate.hbm2ddl.auto", "create");
		configuration.setProperty("hibernate.transaction.factory_class", "org.hibernate.transaction.JDBCTransactionFactory");
		configuration.setProperty("hibernate.hbm2ddl.auto", "create");
		
		//Register Entity classes
		configuration.addAnnotatedClass(eu.drseus.cb.shared.forum.chat.Message.class);
		configuration.addAnnotatedClass(eu.drseus.cb.shared.forum.user.User.class);

		
		//configuration.configure(); //Only for loading the XML-File...
		
		StandardServiceRegistryBuilder builder = new StandardServiceRegistryBuilder();
		builder.applySettings(configuration.getProperties());
		
		sessionFactory = configuration.buildSessionFactory(builder.build());

	}

	public static SessionFactory getSessionFactory() {
		return sessionFactory;
	}

	public static Session getSession() {
		return sessionFactory.getCurrentSession();
	}
	
}

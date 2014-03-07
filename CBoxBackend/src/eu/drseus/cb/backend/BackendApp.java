package eu.drseus.cb.backend;

import java.util.ArrayList;

import javax.security.auth.login.LoginException;

import org.apache.commons.configuration.ConfigurationException;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.support.AbstractApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import eu.drseus.cb.backend.forum.Forum;
import eu.drseus.cb.backend.forum.exception.ForumIOException;
import eu.drseus.cb.backend.message.MessageManager;
import eu.drseus.cb.backend.util.Config;
import eu.drseus.cb.backend.web.WebServer;
import eu.drseus.cb.shared.forum.chat.Message;

public class BackendApp {

	private Log logger = LogFactory.getLog(BackendApp.class);

	public static void main(String[] args) {
		new BackendApp().run(args);
	}
	
	@SuppressWarnings("resource")
	private void run(String[] args) {
		logger.debug("DEBUG");
		logger.error("ERROR");
		logger.fatal("FATAL");
		logger.info("INFO");
		logger.trace("TRACE");
		logger.warn("WARN");

		try {			
			AbstractApplicationContext ctx = new ClassPathXmlApplicationContext(new String[] { "eu/drseus/cb/backend/context-app.xml" });
			ctx.start();

			Config config = ctx.getBean(Config.class);
			Forum forum = ctx.getBean(Forum.class);

			// Forum Login
			forum.login(config.forum.user, config.forum.password);
			logger.info("Forum login with username = " + config.forum.user + " and password = ******** successful.");

			// If successful start the chatbox-fetcher thread
			ChatboxFetcherThread thread = ctx.getBean(ChatboxFetcherThread.class);
			thread.start();

			// At the end, start the WebServer
			WebServer webServer = ctx.getBean(WebServer.class);
			webServer.startServer();
			
		} catch (LoginException e) {
			logger.fatal(e.getMessage(), e);
			System.exit(-1);
		} catch (ForumIOException e) {
			logger.fatal(e.getMessage(), e);
			System.exit(-1);
		} catch (ConfigurationException e) {
			logger.fatal("Failed to load config file. (" + e.getMessage() + ")");
			System.exit(-1);
		} catch (Exception e) {
			logger.fatal(e.getMessage(), e);
		}

	}

	public static class ChatboxFetcherThread extends Thread {
		
		private Log logger = LogFactory.getLog(ChatboxFetcherThread.class);
		
		@Autowired
		private Forum forum;
		@Autowired
		private Config config;
		@Autowired
		private MessageManager messageManager;
		
		public ChatboxFetcherThread(){}
		
		@Override
		public void run() {
			while(this.isAlive() && !this.isInterrupted()){
				try {
				
					ArrayList<Message> messageList = forum.fetchChatbox();
					messageManager.processMessages(messageList);

				} catch (Exception e) {
					logger.error(e.getMessage(), e);
				}
				try {
					Thread.sleep((long) (config.forum.refreshDelay * 1000.0));
				} catch (InterruptedException e) { }
				
			}
		}
	}

}

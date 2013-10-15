package eu.drseus.cb.backend;

import java.io.IOException;
import java.util.ArrayList;

import javax.security.auth.login.LoginException;

import org.apache.commons.configuration.ConfigurationException;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.http.client.ClientProtocolException;

import eu.drseus.cb.backend.forum.Forum;
import eu.drseus.cb.backend.forum.chat.Message;
import eu.drseus.cb.backend.forum.exception.ForumIOException;
import eu.drseus.cb.backend.message.MessageManager;
import eu.drseus.cb.backend.util.Config;

public class Backend {
	
	private Log log = LogFactory.getLog(Backend.class);

	/*
	 * Username: chatbot
	 * Password: ***********
	 */
	
	public static void main(String[] args) throws ClientProtocolException, IOException {
		new Backend().run(args);
	}

	private Forum forum = new Forum();
	
	private MessageManager messageM = new MessageManager();
	
	public void run(String[] args){
		
		log.debug("DEBUG");
		log.error("ERROR");
		log.fatal("FATAL");
		log.info("INFO");
		log.trace("TRACE");
		log.warn("WARN");
		
		try {
			//Load Config
			Config.loadConfig("config");
			//System.out.println(Config.asString());
			
			//Forum Login
			forum.login(Config.forum.user, Config.forum.password);
			log.info("Forum login with username = "+Config.forum.user+" and password = ******** successful.");
			
			//If successful start the chatbox-fetcher thread
			ChatboxFetcherThread thread = new ChatboxFetcherThread();
			thread.start();			
			
		} catch (LoginException e) {
			log.fatal(e.getMessage(), e);
			System.exit(-1);
		} catch (ForumIOException e) {
			log.fatal(e.getMessage(), e);
			System.exit(-1);
		} catch (ConfigurationException e) {
			log.fatal("Failed to load config file. ("+e.getMessage()+")");
			System.exit(-1);
		}
		
	}
	
	private class ChatboxFetcherThread extends Thread {

		@Override
		public void run() {
			while(this.isAlive() && !this.isInterrupted()){
				
				try {
					
					ArrayList<Message> messageList = forum.fetchChatbox();
					messageM.processMessages(messageList);

				} catch (Exception e) {
					log.error(e.getMessage(), e);
				}
				try {
					Thread.sleep((long) (Config.forum.refreshDelay * 1000.0));
				} catch (InterruptedException e) { }
				
			}
		}
		
	}
	
}
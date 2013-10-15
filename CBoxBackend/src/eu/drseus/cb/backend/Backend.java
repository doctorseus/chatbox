package eu.drseus.cb.backend;

import java.io.IOException;
import java.util.ArrayList;

import javax.security.auth.login.LoginException;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.http.client.ClientProtocolException;

import eu.drseus.cb.backend.forum.Forum;
import eu.drseus.cb.backend.forum.chat.Message;
import eu.drseus.cb.backend.forum.exception.ForumIOException;
import eu.drseus.cb.backend.message.MessageManager;

public class Backend {
	
	private Log log = LogFactory.getLog(Backend.class);
	
	private final String C_USER_NAME = "chatbot";
	private final String C_USER_PWD = "";
	
	private final double C_REFRESH_DELAY = 5.0; //Seconds
	
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

		try {
			//Login into Forum
			forum.login(C_USER_NAME, C_USER_PWD);
			
			//If successful start the chatbox-fetcher thread
			ChatboxFetcherThread thread = new ChatboxFetcherThread();
			thread.start();			
			
		} catch (LoginException e) {
			log.fatal(e.getMessage(), e);
			System.exit(-1);
		} catch (ForumIOException e) {
			log.fatal(e.getMessage(), e);
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
					Thread.sleep((long) (C_REFRESH_DELAY * 1000.0));
				} catch (InterruptedException e) { }
				
			}
		}
		
	}
	
}
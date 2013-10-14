package eu.drseus.cb.backend;

import java.io.IOException;
import java.util.LinkedHashMap;

import org.apache.http.client.ClientProtocolException;

import eu.drseus.cb.backend.forum.Forum;
import eu.drseus.cb.backend.forum.chat.Message;
import eu.drseus.cb.backend.util.MessageCache;

public class Backend {
	
	/*
	 * Username: chatbot
	 * Password: ce49e4a541068d49
	 */
	
	public static void main(String[] args) throws ClientProtocolException, IOException {
		new Backend().run(args);
	}

	private MessageCache cache = new MessageCache();
	
	public void run(String[] args){
		
		final Forum forum = new Forum();
		
		try {
			
			forum.login("chatbot", "ce49e4a541068d49");

			Thread thread = new Thread(new Runnable() {
				
				@Override
				public void run() {
					
					try {
						forum.fetchChatbox();
					} catch (Exception e) {
						e.printStackTrace();
					}
					
					
					try {
						Thread.sleep(2000);
					} catch (InterruptedException e) { }
				}
				
			});
			thread.start();
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	
}
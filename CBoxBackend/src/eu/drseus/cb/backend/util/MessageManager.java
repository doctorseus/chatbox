package eu.drseus.cb.backend.util;

import java.util.ArrayList;

import eu.drseus.cb.backend.forum.chat.Message;

public class MessageManager {
	
	private MessageCache cache = new MessageCache(100);
	
	public MessageManager() {
		//Load the last 100 messages into the cache

	}

	public void processMessages(ArrayList<Message> incoming) {
		
		for(Message m:incoming){
			
			Message oldMessage = cache.get(m.getId());
			
			if(oldMessage != null){
				//Old message, check for content change (That means: Message history is working, 
				//but only if the message gets changed before it leaves the first page.)
				if(oldMessage.checkChange(m)){
					//Message Changed, Notify "MESSAGE CHANGED"
					Message updatedMessage = Message.updateHistory(oldMessage, m);
					
		
					cache.put(updatedMessage);
				}
				
			}else{
				//New message, Notify "NEW MESSAGE"
				cache.put(m);
			}
			
			System.out.println(m.toString());
		}
	
	}

}

package eu.drseus.cb.backend.message;

import java.util.ArrayList;
import java.util.Collections;

import eu.drseus.cb.backend.forum.chat.Message;

public class MessageManager {
	
	private ArrayList<IMessageListener> listeners = new ArrayList<>();
	
	private MessageOrder order = new MessageOrder(100);
	private MessageCache cache = new MessageCache(300);
	
	public MessageManager() {
		//Load the last 100 messages into the cache

	}
	
	public void addListener(IMessageListener listener){
		listeners.add(listener);
	}
	
	public void removeListener(IMessageListener listener){
		listeners.remove(listener);
	}
	
	private void notifyOnMessageNew(Message newMessage){
		for(IMessageListener l:listeners)
			l.onMessageNew(newMessage);
	}
	
	private void notfiyOnMessageUpdate(Message updatedMessage){
		for(IMessageListener l:listeners)
			l.onMessageUpdate(updatedMessage);
	}
	
	private void notifyOnUpdate(ArrayList<Message> newMessages, ArrayList<Message> updatedMessages){
		for(IMessageListener l:listeners)
			l.onUpdate(newMessages, updatedMessages);
	}

	public void processMessages(ArrayList<Message> incoming) {
		
		//Reverse the order of the messages (from top->down/new->old  TO  old->new)
		Collections.reverse(incoming);
		
		ArrayList<Message> newMessages = new ArrayList<>();
		ArrayList<Message> updatedMessages = new ArrayList<>();
		
		for(Message m:incoming){
			
			if(order.contains(m.getId())){
				
				Message oldMessage = cache.get(m.getId());
				//Old message, check for content change (That means: Message history is working, 
				//but only if the message gets changed before it leaves the first page.)
				if(oldMessage.checkChange(m)){
					//Message Changed, Notify "MESSAGE CHANGED"
					Message updatedMessage = Message.updateHistory(oldMessage, m);
					cache.put(updatedMessage);
					updatedMessages.add(updatedMessage);
					notfiyOnMessageUpdate(updatedMessage);
				}
				
			}else{
				
				//New message, Notify "NEW MESSAGE"
				cache.put(m);
				order.add(m.getId());	
				newMessages.add(m);
				notifyOnMessageNew(m);
			}

		}
		
		notifyOnUpdate(newMessages, updatedMessages);
		
	}
	
	@SuppressWarnings("unused")
	private Message[] getLast(int limit){
		return null;
	}

}
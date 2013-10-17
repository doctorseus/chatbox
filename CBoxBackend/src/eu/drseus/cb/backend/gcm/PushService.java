package eu.drseus.cb.backend.gcm;

import java.util.ArrayList;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import eu.drseus.cb.backend.forum.chat.Message;
import eu.drseus.cb.backend.message.IMessageListener;

public class PushService implements IMessageListener {

	private Log log = LogFactory.getLog(PushService.class);
	
	@Override
	public void onUpdate(ArrayList<Message> newMessages, ArrayList<Message> updatedMessages) {
		if(newMessages.size() > 0 || updatedMessages.size() > 0){
			log.debug("send out stuff!!!");	
		}		
	}

	@Override
	public void onMessageNew(Message newMessage) {}

	@Override
	public void onMessageUpdate(Message updatedMessage) {}	

}
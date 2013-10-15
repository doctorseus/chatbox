package eu.drseus.cb.backend.message;

import java.util.ArrayList;

import eu.drseus.cb.backend.forum.chat.Message;

public interface IMessageListener {
	void onUpdate(ArrayList<Message> newMessages, ArrayList<Message> updatedMessages);	
	void onMessageNew(Message newMessage);
	void onMessageUpdate(Message updatedMessage);
}

package eu.drseus.cb.backend.forum.chat;

import java.util.ArrayList;
import java.util.Date;

import eu.drseus.cb.backend.forum.user.User;

public class Message {

	private long id;
	
	private User user;
	private Date time;
	
	private String message;

	private ArrayList<String> messageHistory = new ArrayList<>();
	
	@SuppressWarnings("unchecked")
	public Message(Message msg){
		this(msg.id, msg.user, msg.time, msg.message);
		messageHistory = (ArrayList<String>) msg.messageHistory.clone();
	}
	
	public Message(long id, User user, Date time, String message) {
		super();
		this.id = id;
		this.user = user;
		this.time = time;
		this.message = message;
	}

	public boolean checkChange(Message newMessage){
		if(!this.message.equals(newMessage.message)){
			//Do not modify the message here!
			//messageHistory.add(this.message);
			//this.message = newMessage.message;
			return true;
		}
		return false;
	}
	
	public long getId() {
		return id;
	}

	public User getUser() {
		return user;
	}

	public Date getTime() {
		return time;
	}

	public String getMessage() {
		return message;
	}

	public ArrayList<String> getMessageHistory() {
		return messageHistory;
	}

	@Override
	public String toString() {
		return "Message [id=" + id + ", user=" + user + ", time=" + time
				+ ", message=" + message + "]";
	}

	public static Message updateHistory(Message oldMessage, Message newMessage) {
		Message resultMessage = new Message(oldMessage);
		resultMessage.messageHistory.add(oldMessage.message);
		resultMessage.message = newMessage.message;
		return resultMessage;
	}

}

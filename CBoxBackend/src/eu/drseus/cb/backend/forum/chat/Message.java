package eu.drseus.cb.backend.forum.chat;

import java.util.ArrayList;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.json.simple.JSONObject;

import eu.drseus.cb.backend.forum.user.User;

@Entity
@Table(name = "messages")
public class Message {

	@Id
	@Column(name = "id")
	private long id;

	@ManyToOne
	@JoinColumn(name = "owner")
	private User user;

	@Column(name = "time")
	private Date time;

	@Column(name = "message")
	private String message;

	@Column(name = "history")
	private ArrayList<String> messageHistory = new ArrayList<>();

	public Message() {

	}

	@SuppressWarnings("unchecked")
	public Message(Message msg) {
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

	public boolean checkChange(Message newMessage) {
		if (!this.message.equals(newMessage.message)) {
			// Do not modify the message here!
			// messageHistory.add(this.message);
			// this.message = newMessage.message;
			return true;
		}
		return false;
	}

	public static Message updateHistory(Message oldMessage, Message newMessage) {
		Message resultMessage = new Message(oldMessage);
		resultMessage.messageHistory.add(oldMessage.message);
		resultMessage.message = newMessage.message;
		return resultMessage;
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

	@SuppressWarnings("unchecked")
	public JSONObject toJSON(){
		JSONObject result = new JSONObject();
		result.put("id", getId());
		result.put("user", getUser().toJSON());
		result.put("time", getTime().toString());
		result.put("message", getMessage());		
		return result;
	}
	
}

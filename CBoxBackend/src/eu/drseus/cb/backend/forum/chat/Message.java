package eu.drseus.cb.backend.forum.chat;

import java.util.Date;

import eu.drseus.cb.backend.forum.user.User;

public class Message {

	private long id;
	
	private User user;
	private Date time;
	
	private String message;

	public Message(long id, User user, Date time, String message) {
		super();
		this.id = id;
		this.user = user;
		this.time = time;
		this.message = message;
	}

	@Override
	public String toString() {
		return "Message [id=" + id + ", user=" + user + ", time=" + time
				+ ", message=" + message + "]";
	}

}

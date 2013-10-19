package eu.drseus.cb.backend.db.entities;

import java.util.ArrayList;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import eu.drseus.cb.shared.forum.chat.Message;


@Entity
@Table(name = "messages")
public class EMessage {

	@Id
	@Column(name = "id")
	private long id;

	@ManyToOne
	@JoinColumn(name = "owner")
	private EUser owner;

	@Column(name = "time")
	private Date time;

	@Column(name = "message")
	private String message;

	@Column(name = "history")
	private ArrayList<String> messageHistory = new ArrayList<>();

	private EMessage() {

	}
	
	public static EMessage createFromMessage(Message m){
		EMessage eM = new EMessage();
		eM.id = m.getId();
		eM.owner = EUser.createFromUser(m.getUser());
		eM.time = m.getTime();
		eM.message = m.getMessage();
		eM.messageHistory = m.getMessageHistory();
		return eM;
	}

	public long getId() {
		return id;
	}

	public EUser getUser() {
		return owner;
	}

	public Date getTime() {
		return time;
	}

	public String getMessage() {
		return message;
	}

	public ArrayList<String> getMessageHistory() {
		return new ArrayList<String>(messageHistory);
	}	
	
	public Message toMessage(){
		return Message.create(id, owner.toUser(), time, message, messageHistory);
	}

	@Override
	public String toString() {
		return "EMessage [id=" + id + ", owner=" + owner + ", time=" + time
				+ ", message=" + message + "]";
	}
	
}
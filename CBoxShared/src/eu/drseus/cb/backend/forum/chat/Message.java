package eu.drseus.cb.backend.forum.chat;

import java.util.ArrayList;
import java.util.Date;

import org.json.simple.JSONObject;
import org.json.simple.parser.ParseException;

import eu.drseus.cb.backend.forum.user.User;
import eu.drseus.cb.backend.util.IJSONable;

public class Message implements IJSONable<Message> {

	private long id;
	private User owner;
	private Date time;
	private String message;
	private ArrayList<String> messageHistory = new ArrayList<>();

	private Message() {

	}
	
	public static Message create(long id, User user, Date time, String message){
		Message m = new Message();
		m.id = id;
		m.owner = user;
		m.time = time;
		m.message = message;
		return m;
	}
	
	public static Message create(long id, User user, Date time, String message, ArrayList<String> messageHistory){
		Message m = create(id, user, time, message);
		m.messageHistory = new ArrayList<>(messageHistory);
		return m;
	}
	
	public static Message create(Message msg){
		Message m = create(msg.id, msg.owner, msg.time, msg.message);
		m.messageHistory = new ArrayList<>(msg.messageHistory);
		return m;
	}

	public boolean checkChange(Message newMessage) {
		if (!this.message.equals(newMessage.message)) {
			return true;
		}
		return false;
	}

	public static Message updateHistory(Message oldMessage, Message newMessage) {
		Message resultMessage = Message.create(oldMessage);
		resultMessage.messageHistory.add(oldMessage.message);
		resultMessage.message = newMessage.message;
		return resultMessage;
	}

	public long getId() {
		return id;
	}

	public User getUser() {
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

	@Override
	public String toString() {
		return "Message [id=" + id + ", owner=" + owner + ", time=" + time
				+ ", message=" + message + "]";
	}

	public static Message createFromJSON(JSONObject o) throws ParseException {
		try {
			Message m = new Message();
			m.id = Integer.parseInt(o.get("id").toString());
			m.owner = User.createFromJSON((JSONObject) o.get("owner"));
			m.time = new Date(Long.parseLong(o.get("time").toString()));
			m.message = o.get("id").toString();
			return m;
		} catch (Exception e) {
			throw new ParseException(ParseException.ERROR_UNEXPECTED_EXCEPTION);
		}
	}

	@Override
	public JSONObject toJSON() {
		return toJSON(false);
	}
	
	@SuppressWarnings("unchecked")
	public JSONObject toJSON(boolean withHistory){
		JSONObject result = new JSONObject();
		result.put("id", getId());
		result.put("owner", getUser().toJSON());
		result.put("time", getTime().getTime());
		result.put("message", getMessage());	
		if(withHistory)
			result.put("history", messageHistory);
		return result;
	}
	
}

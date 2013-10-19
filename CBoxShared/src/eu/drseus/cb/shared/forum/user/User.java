package eu.drseus.cb.shared.forum.user;

import org.json.simple.JSONObject;
import org.json.simple.parser.ParseException;

import eu.drseus.cb.shared.util.IJSONable;


public class User implements IJSONable<User>{

	private long id;

	private String name;

	public User() {

	}
	
	public static User create(long id, String name){
		User u = new User();
		u.id = id;
		u.name = name;
		return u;
	}

	public long getId() {
		return id;
	}

	public String getName() {
		return name;
	}
	
	@Override
	public String toString() {
		return "User [id=" + id + ", name=" + name + "]";
	}
	
	public static User createFromJSON(JSONObject o) throws ParseException {
		try {
			User u = new User();
			u.id = Integer.parseInt(o.get("id").toString());
			u.name = o.get("id").toString();
			return u;
		} catch (Exception e) {
			throw new ParseException(ParseException.ERROR_UNEXPECTED_EXCEPTION);
		}
	}
	
	@SuppressWarnings("unchecked")
	public JSONObject toJSON(){
		JSONObject result = new JSONObject();
		result.put("id", getId());
		result.put("name", getName());		
		return result;
	}
	
}

package eu.drseus.cb.backend.forum.user;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import org.json.simple.JSONObject;

@Entity
@Table(name = "users")
public class User {

	@Id
	@Column(name = "id")
	private long id;

	@Column(name = "name")
	private String name;

	public User() {

	}

	public User(long id, String name) {
		super();
		this.id = id;
		this.name = name;
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
	
	@SuppressWarnings("unchecked")
	public JSONObject toJSON(){
		JSONObject result = new JSONObject();
		result.put("id", getId());
		result.put("name", getName());		
		return result;
	}
	
}

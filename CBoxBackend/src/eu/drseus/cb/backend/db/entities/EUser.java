package eu.drseus.cb.backend.db.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import eu.drseus.cb.backend.forum.user.User;

@Entity
@Table(name = "users")
public class EUser {

	@Id
	@Column(name = "id")
	private long id;

	@Column(name = "name")
	private String name;

	private EUser() {

	}

	public static EUser createFromUser(User m){
		EUser eU = new EUser();
		
		return eU;
	}
	
	public long getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public User toUser() {
		return User.create(id, name);
	}
	
	@Override
	public String toString() {
		return "EUser [id=" + id + ", name=" + name + "]";
	}

}

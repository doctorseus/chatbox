package eu.drseus.cb.backend.forum.user;

public class User {
	
	private long id;
	
	private String name;

	public User(long id, String name) {
		super();
		this.id = id;
		this.name = name;
	}

	@Override
	public String toString() {
		return "User [id=" + id + ", name=" + name + "]";
	}
	
}

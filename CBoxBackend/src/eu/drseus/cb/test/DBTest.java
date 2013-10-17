package eu.drseus.cb.test;

import java.util.Date;

import org.hibernate.Session;

import eu.drseus.cb.backend.db.Hibernate;
import eu.drseus.cb.backend.forum.chat.Message;
import eu.drseus.cb.backend.forum.user.User;

public class DBTest {

	/**
	 * @param args
	 */
	@SuppressWarnings("deprecation")
	public static void main(String[] args) {

		Session session = Hibernate.getSession();
		session.beginTransaction();

		User user1 = new User(999001, "User01");
		User user2 = new User(999002, "User02");
		
		session.save(user1);
		session.save(user2);
		
		Message msg1 = new Message(888001, user1, new Date(2013, 10, 16, 15, 55), "This is the first test message");
		Message msg2 = new Message(888002, user2, new Date(2013, 10, 16, 15, 58), "This is the second test message");
			
		session.save(msg1);
		session.save(msg2);
		
		//
		
		session.getTransaction().commit();
		
		
	}

}

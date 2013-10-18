package eu.drseus.cb.test;

import java.util.Date;

import org.hibernate.Session;

import eu.drseus.cb.backend.db.Hibernate;
import eu.drseus.cb.backend.db.entities.EMessage;
import eu.drseus.cb.backend.db.entities.EUser;
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

		User user1 = User.create(999001, "User01");
		User user2 = User.create(999002, "User02");
		
		EUser euser1 = EUser.createFromUser(user1);
		EUser euser2 = EUser.createFromUser(user2);
		
		session.save(euser1);
		session.save(euser2);

		EMessage msg1 = EMessage.createFromMessage(Message.create(888001, user1, new Date(2013, 10, 16, 15, 55), "This is the first test message"));
		EMessage msg2 = EMessage.createFromMessage(Message.create(888001, user1, new Date(2013, 10, 16, 15, 55), "This is the first test message"));
			
		session.save(msg1);
		session.save(msg2);
		
		//
		
		session.getTransaction().commit();
		
		
	}

}

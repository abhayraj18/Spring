package com.spring.dao;

import org.hibernate.Session;
import org.hibernate.query.Query;
import org.springframework.transaction.annotation.Transactional;

import com.spring.model.User;

@Transactional
public class RegistrationDAO {

	/*@Autowired
	SessionFactory sessionFactory;

	public SessionFactory getSessionFactory() {
		return sessionFactory;
	}

	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}
	
	private Session getSession() {
		return getSessionFactory().getCurrentSession();
	}*/

	public static void createNewUser(Session session, User user) {
		session.save(user);
	}

	public static boolean doesUserExist(Session session, String userName) {
		String query = "From User where userName = ?";
		Query<?> q = session.createQuery(query);
		q.setParameter(0, userName);
		if(q.getResultList().size() > 0)
			return true;
		return false;
	}
	
}
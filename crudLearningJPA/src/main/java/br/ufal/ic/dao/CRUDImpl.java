package br.ufal.ic.dao;

import java.util.List;


import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import br.ufal.ic.model.User;

@SuppressWarnings("rawtypes")
public class CRUDImpl{

	final ThreadLocal<Session> threadLocal = new ThreadLocal<Session>();
	final SessionFactory sessionFactory = new Configuration().configure("./META-INF/hibernate.cfg.xml")
			.buildSessionFactory();
	Session session = threadLocal.get();

	public void addInstance(User instance) {
		session = sessionFactory.openSession();

		try {
			session.beginTransaction();
			session.save(instance);
			session.getTransaction().commit();
			session.close();
		} catch (HibernateException e) {
			e.printStackTrace();
			session.getTransaction().rollback();
		}
	}	

	public void updateInstance(User instance) {

		session = sessionFactory.openSession();

		try {
			session.beginTransaction();
			session.update(instance);
			session.getTransaction().commit();
			session.close();
		} catch (HibernateException e) {
			e.printStackTrace();
			session.getTransaction().rollback();
		}
	}
	
	User getUserById(int id) {
		Session session = sessionFactory.openSession();
		
		Query query = session.createQuery("from User where id = :id");
		query.setParameter("id", id);
		
		User u = (User) query.uniqueResult();
		return u;
	}
	
//	public void updateInstance(User instance) {
//
//		session = sessionFactory.openSession();
//
//		try {
//			session.beginTransaction();
//			session.delete(instance);
//			session.getTransaction().commit();
//			session.close();
//		} catch (HibernateException e) {
//			e.printStackTrace();
//			session.getTransaction().rollback();
//
//		}
//
//	}

	@SuppressWarnings("unchecked")
	public List<User> getAllInstances() {
		session = sessionFactory.openSession();
		List<User> list = null;

		try {
			session.beginTransaction();
			list = session.createCriteria(User.class).list();
			session.close();
		} catch (HibernateException e) {
			e.printStackTrace();
			session.getTransaction().rollback();
		}
		return list;
	}

	public static CRUDImpl getInstance() {
		return new CRUDImpl();
	}

}

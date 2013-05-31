package unisave2006;

import org.hibernate.*;
import org.hibernate.cfg.*;


public class HibernateUtil {
	
    private static SessionFactory sessionFactory;
    private static final ThreadLocal threadLocal = new ThreadLocal();
    private static Configuration configuration = (new Configuration()).configure();
    
    /**
     * @return
     * @throws HibernateException
     */
    public static SessionFactory buildSessionFactory() throws HibernateException {
        if (sessionFactory != null) {
            closeFactory();
        }
        return configureSessionFactory();
    }

    public static SessionFactory buildIfNeeded() throws HibernateException{
        if (sessionFactory != null) {
            return sessionFactory;
        }
            return configureSessionFactory();
    }
    
    public static SessionFactory getSessionFactory() {
        return sessionFactory;
    }
    
    public static Session getCurrentSession() {
    	return sessionFactory.getCurrentSession();
    }

    public static Session openSession() throws HibernateException {
        buildIfNeeded();
        Session session = (Session) threadLocal.get();
        if (session == null || !session.isOpen()) {
			session = sessionFactory.openSession();
			threadLocal.set(session);
		}
        return session;
    }

    public static void closeFactory() {
        if (sessionFactory != null) {
            try {
                sessionFactory.close();
            } catch (HibernateException e) {
            	System.out.println(e);
            }
        }
    }

    public static void close(Session session) {
    	session = (Session) threadLocal.get();
        threadLocal.set(null);
    	if (session != null) {
            try {
                session.close();
            } catch (HibernateException e) {
            	System.out.println(e);
            }
        }
    }

    
    public static void rollback(Transaction tx) {
        try {
            if (tx != null) {
                tx.rollback();
            }
        } catch (HibernateException e) {
        	System.out.println(e);
        }
    }
    /**
     *
     * @return
     * @throws HibernateException
     */
    private static SessionFactory configureSessionFactory() throws HibernateException {
        sessionFactory = configuration.buildSessionFactory();
        return sessionFactory;
    }
}
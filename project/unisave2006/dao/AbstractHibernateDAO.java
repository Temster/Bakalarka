package unisave2006.dao;

import org.hibernate.*;
import unisave2006.HibernateUtil;

import java.util.List;

public abstract class AbstractHibernateDAO {
    private Session session;
    private Transaction transaction;

    public AbstractHibernateDAO() {
        HibernateUtil.buildIfNeeded();
    }

    public Session getSession() {
    	return session;
    }
    public void setSession(Session session) {
    	this.session = session;
    }
    
    public void closeSession() {
    	HibernateUtil.close(session);
    }
    
    public Transaction getTransaction() {
    	return transaction;
    }
    public void setTransaction(Transaction transaction) {
    	this.transaction = transaction;
    }
    
    protected void saveOrUpdate(Object obj) {
        try {
            startOperation();
            session.saveOrUpdate(obj);
            transaction.commit();
        } catch (HibernateException e) {
            handleException(e);
        } finally {
            closeSession();
        }
    }

    protected void delete(Object obj) {
        try {
            startOperation();
            session.delete(obj);
            transaction.commit();
        } catch (HibernateException e) {
            handleException(e);
        } finally {
            closeSession();
        }
    }

    protected Object find(Class cl, Long id) {
        Object obj = null;
        try {
            startOperation();
            obj = session.load(cl, id);
            transaction.commit();
        } catch (HibernateException e) {
            handleException(e);
        } finally {
            closeSession();
        }
        return obj;
    }

    protected List findAll(Class cl) {
        List objects = null;
        try {
            startOperation();
            Query query = session.createQuery("from " + cl.getName());
            objects = query.list();
            transaction.commit();
        } catch (HibernateException e) {
            handleException(e);
        } finally {
            closeSession();
        }
        return objects;
    }

    protected void handleException(HibernateException e) throws DataAccessLayerException {
        HibernateUtil.rollback(transaction);
        throw new DataAccessLayerException(e.getMessage()); 
    }

    protected void startOperation() throws HibernateException {
        session = HibernateUtil.openSession();
        transaction = session.beginTransaction();
    }
}


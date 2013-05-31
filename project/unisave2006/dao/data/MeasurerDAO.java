package unisave2006.dao.data;

import java.util.List;

import javax.swing.JOptionPane;

import org.hibernate.HibernateException;
import org.hibernate.Query;

import unisave2006.dao.AbstractHibernateDAO;
import unisave2006.data.Measurer;

public class MeasurerDAO extends AbstractHibernateDAO {

	public MeasurerDAO() {
		super();
	}
	
	public void saveOrUpdate(Measurer me) {
		try {
            startOperation();
            getSession().saveOrUpdate(me);
            getTransaction().commit();
        } catch (HibernateException e) {
            handleException(e);
        } finally {
        	closeSession();
        }
	}
	
	public void delete(Long id) {
		Measurer me = find(id); 
		try {
            startOperation();
            getSession().delete(me);
            getTransaction().commit();
        } catch (HibernateException e) {
            handleException(e);
        }
	}
	
	public Measurer find(Long id) {
		Measurer me = null;
        try {
            startOperation();
            me = (Measurer) getSession().load(Measurer.class, id);
            getTransaction().commit();
        } catch (HibernateException e) {
            handleException(e);
        } 
        return me;
	}
	
	public List<Measurer> findAll() {
		List<Measurer> list = null;
        try {
            startOperation();
            Query query = getSession().createQuery("from " + Measurer.class.getName());
            list = (List<Measurer>) query.list();
            getTransaction().commit();
        } catch (HibernateException e) {
        	JOptionPane.showMessageDialog(null, "Otevřete znovu dialog", "Unisave 2006", JOptionPane.INFORMATION_MESSAGE);
            handleException(e);
        }
        return list;
	}
	
}

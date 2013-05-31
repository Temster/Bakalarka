package unisave2006.dao.data;

import java.util.List;

import javax.swing.JOptionPane;

import org.hibernate.Hibernate;
import org.hibernate.HibernateException;
import org.hibernate.Query;

import unisave2006.dao.AbstractHibernateDAO;
import unisave2006.data.Organization;

public class OrganizationDAO extends AbstractHibernateDAO{

	public OrganizationDAO() {
		super();
	}
	
	public void saveOrUpdate(Organization org) {
		super.saveOrUpdate(org);
	}
	
	public void delete(Long id) {
		Organization org = find(id); 
		try {
            startOperation();
            getSession().delete(org);
            getTransaction().commit();
        } catch (HibernateException e) {
            handleException(e);
        }
	}
	
	public Organization find(Long id) {
		Organization org = null;
        try {
            startOperation();
            org = (Organization) getSession().load(Organization.class, id);
            Hibernate.initialize(org.getAddress());
            getTransaction().commit();
        } catch (HibernateException e) {
            handleException(e);
        } 
        return org;
	}
	
	public List<Organization> findAll() {
		List<Organization> list = null;
        try {
            startOperation();
            Query query = getSession().createQuery("from " + Organization.class.getName());
            list = (List<Organization>) query.list();
            getTransaction().commit();
        } catch (HibernateException e) {
        	JOptionPane.showMessageDialog(null, "Otevřete znovu dialog", "Unisave 2006", JOptionPane.INFORMATION_MESSAGE);
            handleException(e);
        }
        return list;
	}
	
}

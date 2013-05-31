package unisave2006.dao.data;

import java.util.List;
import javax.swing.JOptionPane;

import org.hibernate.HibernateException;
import org.hibernate.Query;

import unisave2006.dao.AbstractHibernateDAO;
import unisave2006.data.Employee;

public class EmployeeDAO extends AbstractHibernateDAO {

	public EmployeeDAO() {
		super();
	}
	
	public void saveOrUpdate(Employee emp) {
		super.saveOrUpdate(emp);
	}
	
	public void delete(Long id) {
		Employee emp = find(id); 
		try {
            startOperation();
            getSession().delete(emp);
            getTransaction().commit();
        } catch (HibernateException e) {
            handleException(e);
        }
	}
	
	public Employee find(Long id) {
		Employee emp = null;
        try {
            startOperation();
            emp = (Employee) getSession().load(Employee.class, id);
            getTransaction().commit();
        } catch (HibernateException e) {
            handleException(e);
        } 
        return emp;
	}
	
	public List<Employee> findAll() {
		List<Employee> list = null;
        try {
            startOperation();
            Query query = getSession().createQuery("from " + Employee.class.getName());
            list = (List<Employee>) query.list();
            getTransaction().commit();
        } catch (HibernateException e) {
        	JOptionPane.showMessageDialog(null, "Otevřete znovu dialog", "Unisave 2006", JOptionPane.INFORMATION_MESSAGE);
            handleException(e);
        }
        return list;
	}
	
}

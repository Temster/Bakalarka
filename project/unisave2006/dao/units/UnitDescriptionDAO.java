package unisave2006.dao.units;

import org.hibernate.Hibernate;
import org.hibernate.HibernateException;
import org.hibernate.Query;

import unisave2006.HibernateUtil;
import unisave2006.dao.AbstractHibernateDAO;
import unisave2006.units.UnitDescription;
import unisave2006.units.UnitSet;
import java.util.List;

public class UnitDescriptionDAO extends AbstractHibernateDAO{

	public UnitDescriptionDAO() {
		super();
	}
	
	public void saveOrUpdate(UnitDescription ud) {
		super.saveOrUpdate(ud);
	}
	
	public void delete(UnitDescription ud) {
		super.delete(ud);
	}
	
	public UnitDescription find(Long id) {
		return (UnitDescription) super.find(UnitDescription.class, id);
	}
	
	public List<UnitDescription> findAll() {
		List<UnitDescription> list = null;
        try {
            startOperation();
            Query query = getSession().createQuery("from " + UnitDescription.class.getName());
            list = (List<UnitDescription>) query.list();
            getTransaction().commit();
        } catch (HibernateException e) {
            handleException(e);
        }
        return list;
	}
	
	public UnitSet getUnitSet() {
		List<UnitDescription> list = findAll();
		UnitSet units = new UnitSet();
		for (UnitDescription ud : list) {
			units.add(ud);
		}
		return units;
	}

}

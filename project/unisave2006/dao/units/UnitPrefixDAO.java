package unisave2006.dao.units;

import unisave2006.dao.AbstractHibernateDAO;
import unisave2006.units.UnitPrefix;
import unisave2006.units.PrefixSet;
import java.util.List;

public class UnitPrefixDAO extends AbstractHibernateDAO{
	
	public UnitPrefixDAO() {
		super();
	}
	
	public void saveOrUpdate(UnitPrefix up) {
		super.saveOrUpdate(up);
	}
	
	public void delete(UnitPrefix up) {
		super.delete(up);
	}
	
	public UnitPrefix find(Long id) {
		return (UnitPrefix) super.find(UnitPrefix.class, id);
	}
	
	public List<UnitPrefix> findAll() {
		return (List<UnitPrefix>) super.findAll(UnitPrefix.class);
	}
	
	public PrefixSet getPrefixSet() {
		List<UnitPrefix> list = findAll();
		PrefixSet prefixes = new PrefixSet();
		for (UnitPrefix up : list) {
			prefixes.addPrefix(up);
		}
		return prefixes;
	}
	
}

package unisave2006.dao;

import java.io.File;
import java.util.List;

import org.hibernate.Hibernate;
import org.hibernate.HibernateException;
import org.hibernate.Query;

import unisave2006.PortName;
import unisave2006.UserSetting;
import unisave2006.units.Unit;

public class UserSettingDAO extends AbstractHibernateDAO {

	public UserSettingDAO() {
		super();
	}
	
	public void saveOrUpdate(UserSetting us) {
		us.setLastUsedPortName(us.getLastUsedPort().getName());
		us.setLastUsedDirPath(us.getLastUsedDir().getAbsolutePath());
		us.setLastUsedUnit(Unit.getEmptyUnit());
		us.setLastUsedYUnit(Unit.getEmptyUnit());
		for(File f : us.getRecentFiles()) {
			us.getRecentFilesPath().add(f.getAbsolutePath());
		}
		try {
	        startOperation();
            getSession().saveOrUpdate(us);
            getTransaction().commit();
        } catch (HibernateException e) {

        }
        closeSession();
    }

	public void delete(UserSetting us) {
        super.delete(us);
    }

	public UserSetting find(Long id) {
		UserSetting us = null;
        try {
            startOperation();
            us = (UserSetting)getSession().load(UserSetting.class, id);
            Hibernate.initialize(us.getPortNames());
            Hibernate.initialize(us.getLastUsedGraphSetting());
            Hibernate.initialize(us.getLastUsedYUnit());
            getTransaction().commit();
        } catch (HibernateException e) {
            handleException(e);
        } 
        us.setLastUsedPort(new PortName(us.getLastUsedPortName()));
		us.setLastUsedDir(new File(us.getLastUsedDirPath()));
		for(String s : us.getRecentFilesPath()) {
			us.getRecentFiles().add(new File(s));
		}
        return us;
    }

	public List<UserSetting> findAll() {
		List<UserSetting> list = (List<UserSetting>)super.findAll(UserSetting.class);
		return list;
    }
	
	public long countElements() {
		startOperation();
		Query query = getSession().createQuery("select count(*) from " + UserSetting.class.getName());
		long res = (Long) query.list().iterator().next();
		closeSession();
		return res;
	}
	
}

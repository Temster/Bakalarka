package unisave2006.dao.device;

import java.util.List;

import org.hibernate.Hibernate;
import org.hibernate.HibernateException;
import org.hibernate.Query;

import unisave2006.HibernateUtil;
import unisave2006.dao.AbstractHibernateDAO;
import unisave2006.device.MeasurementDevice;
import unisave2006.device.MeasurementDeviceSet;

public class MeasurementDeviceDAO extends AbstractHibernateDAO {

	public MeasurementDeviceDAO() {
		super();
	}
	
	public void saveOrUpdate(MeasurementDevice md) {
		super.saveOrUpdate(md);
	}
	
	public void delete(MeasurementDevice md) {
		super.delete(md);
	}
	
	public MeasurementDevice find(Long id) {
		return (MeasurementDevice) super.find(MeasurementDevice.class, id);
	}
	
	public List<MeasurementDevice> findAll() {
		List<MeasurementDevice> list = null;
        try {
            startOperation();
            Query query = getSession().createQuery("from " + MeasurementDevice.class.getName());
            list = (List<MeasurementDevice>) query.list();
            for(MeasurementDevice md : list) {
            	Hibernate.initialize(md.getGrabber().getPosibleUnits());
            }
            getTransaction().commit();
        } catch (HibernateException e) {
            handleException(e);
        }
        return list;
	}
	
	public MeasurementDeviceSet getMeasurementDeviceSet() {
		List<MeasurementDevice> list = findAll();
		MeasurementDeviceSet devices = new MeasurementDeviceSet();
		for (MeasurementDevice md : list) {
			devices.addMeasurementDevice(md);
		}
		return devices;
	}
	
}

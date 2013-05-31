package unisave2006.dao.data;

import java.util.Date;
import java.util.List;
import javax.swing.JOptionPane;

import org.hibernate.HibernateException;
import org.hibernate.Query;

import unisave2006.GlobalSetting;
import unisave2006.dao.AbstractHibernateDAO;

import unisave2006.data.Employee;
import unisave2006.data.GraphSetting;
import unisave2006.data.Measurement;
import unisave2006.data.MeasurementPOJO;
import unisave2006.data.Organization;
import unisave2006.data.ProtocolSetting;
import unisave2006.data.XYMeasurement;
import unisave2006.data.XYMeasurementPOJO;
import unisave2006.data.value.MeasurementEntry;
import unisave2006.data.value.XYValue;

public class MeasurementDAO extends AbstractHibernateDAO{
	
	public MeasurementDAO() {
		super();
	}
	
	public int saveOrUpdate(Measurement measurement) {
		XYMeasurement m = (XYMeasurement)measurement;
        GlobalSetting.getUserSetting().getLastUsedProtocolSetting().updateFrom(m.getProtocolSetting());
        GlobalSetting.getUserSetting().getLastUsedGraphSetting().updateFrom(m.getGraphSetting());
        XYMeasurementPOJO savedXY = new XYMeasurementPOJO();
		savedXY.setAutoConvert(m.getAutoConvert());
		savedXY.setAutoConvertUnit(m.getAutoConvertUnit());
		savedXY.setAutoIndex(m.getAutoIndex());
		savedXY.setGraphSetting(m.getGraphSetting());
		savedXY.setIgnoreGroweXValue(m.getIgnoreGroweXValue());
		savedXY.setProtocolSetting(m.getProtocolSetting());
		savedXY.setXMarker(m.getXMarker());
		savedXY.setYMarker(m.getYMarker());
		savedXY.setXMarkerFixed(m.getXMarkerFixed());
		savedXY.setYMarkerFixed(m.getYMarkerFixed());
		savedXY.setXValueName(m.getXValueName());
		savedXY.setYValueName(m.getYValueName());
		savedXY.setYAutoconvertUnit(m.getYAutoconvertUnit());
		List<MeasurementEntry> entries = m.getElements();
		int index = 0;
		for(MeasurementEntry me : entries) {
			((XYValue)me).setMeasurement(savedXY);
			((XYValue)me).setIndexXY(index++);
		}
		savedXY.setElements(entries);
		try {
            startOperation();
            getSession().save(savedXY);
            getTransaction().commit();
        } catch (HibernateException e) {

        } finally {
            closeSession();
        }
        m.setModified(false);
        return 0;
	}
	
	public void delete(Long id) {
		MeasurementPOJO delObj = (MeasurementPOJO) super.find(MeasurementPOJO.class, id);
		super.delete(delObj);
	}
	
	public Measurement find(Long id) {
		XYMeasurementPOJO pojo = null;
        try {
            startOperation();
            pojo = (XYMeasurementPOJO) getSession().load(XYMeasurementPOJO.class, id);
            getTransaction().commit();
        } catch (HibernateException e) {
            handleException(e);
        }
		XYMeasurement retXY = new XYMeasurement();
		retXY.setAutoConvert(pojo.getAutoConvert());
		retXY.setAutoIndex(pojo.getAutoIndex());
		retXY.setIgnoreGroweXValue(pojo.getIgnoreGroweXValue());
		retXY.setProtocolSetting(new ProtocolSetting());
		retXY.getProtocolSetting().updateFrom(pojo.getProtocolSetting());
		retXY.setGraphSetting(new GraphSetting());
		retXY.getGraphSetting().updateFrom(pojo.getGraphSetting());
		retXY.getProtocolSetting().setDateOfMeasurement(new Date());
		retXY.setXMarkerFixed(pojo.getYMarkerFixed());
		retXY.setYMarkerFixed(pojo.getYMarkerFixed());
		retXY.setXValueName(pojo.getXValueName());
		retXY.setYValueName(pojo.getYValueName());
		retXY.setAutoConvertUnit(pojo.getAutoConvertUnit());
		retXY.setYAutoconvertUnit(pojo.getYAutoconvertUnit());
		for(MeasurementEntry me : pojo.getElements()) {
			XYValue val = new XYValue();
			val.updateFrom((XYValue)me);
			retXY.getElements().add(val);
		}
		return retXY;
	}
	
	public List<MeasurementPOJO> findAll() {
		List<MeasurementPOJO> entries = null;
		Query query = null;
        try {
            startOperation();
            query = getSession().createQuery("from " + XYMeasurementPOJO.class.getName());
            entries = (List<MeasurementPOJO>) query.list();
            getTransaction().commit();         
        } catch (HibernateException e) {
            handleException(e);
        }
        return entries;
	}
}

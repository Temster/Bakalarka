/*
 * Created on 15.7.2006
 *
 * Copyright (C) 2006
 * David Ježek 
 * david.jezek@vsb.cz.
 * All rights reserved.
 */
package unisave2006;

import java.awt.Image;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.net.URISyntaxException;
import java.net.URL;

import javax.help.HelpBroker;
import javax.help.HelpSet;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.hibernate.HibernateException;
import org.w3c.dom.Document;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.SAXParseException;

import unisave2006.dao.UserSettingDAO;
import unisave2006.dao.device.MeasurementDeviceDAO;
import unisave2006.dao.units.UnitDescriptionDAO;
import unisave2006.dao.units.UnitPrefixDAO;
import unisave2006.data.MeasurementFormatException;
import unisave2006.data.StructuredXMLLoader;
import unisave2006.device.DeviceXMLLoader;
import unisave2006.device.MeasurementDeviceSet;
import unisave2006.units.PrefixSet;
import unisave2006.units.Unit;
import unisave2006.units.UnitSet;
import unisave2006.units.UnitXMLLoader;

public class GlobalSetting {

    protected static UnitSet unitSet = null;
    protected static PrefixSet prefixSet = null;
    protected static MeasurementDeviceSet measurementDeviceSet = null;
    protected static File commBridgeExeFile = null;
    protected static UserSetting userSetting = null;
    protected static UserSettingDAO usDAO = new UserSettingDAO();
    protected static UnitPrefixDAO upDAO = new UnitPrefixDAO();
    protected static UnitDescriptionDAO udDAO = new UnitDescriptionDAO();
    protected static MeasurementDeviceDAO mdDAO = new MeasurementDeviceDAO();
    
    
    public static UnitSet getUnitSet(){
        if(unitSet == null)
            initializeUnits();
        return unitSet;
    }

    public static PrefixSet getPrefixSet(){
        if(prefixSet == null)
            initializeUnits();
        return prefixSet;
    }
    
    public static MeasurementDeviceSet getMeasurementDeviceSet(){
        if(measurementDeviceSet == null)
            initializeMeasurementDevice();
        return measurementDeviceSet;
    }
    
    //-- PRIDANY KOD
    // Promena bool na ovladani ukladani
    protected static Boolean savetoDB = null;
    
    public static Boolean getSavetoDB(){
    	if(savetoDB == null)
      		initalizeGlobalSetting();
    	return savetoDB;
    }
    public static void setSavetoDB(Boolean save) {
    	savetoDB = save;
    }
    
    //-- KONEC PRIDANEHO KODU
    protected static void initializeMeasurementDevice(){
    	if(GlobalSetting.getSavetoDB() == false)
    	{
        
    	DeviceXMLLoader loader = new DeviceXMLLoader();
        try {
            loader.setSource(new InputStreamReader(GlobalSetting.class.getClassLoader().getResourceAsStream("resource/mesurmentDevice.xml"), "UTF-8"));
            loader.load();
        } catch (UnsupportedEncodingException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        
    	measurementDeviceSet = loader.getMeasurementDeviceSet();
    	}
    	else
    	{
        measurementDeviceSet = mdDAO.getMeasurementDeviceSet();
    	}
    }
    
    
    protected static void initializeUnits(){
    	if(GlobalSetting.getSavetoDB() == false)
    	{
        UnitXMLLoader loader = new UnitXMLLoader();
        try {
            loader.setSource(new InputStreamReader(GlobalSetting.class.getClassLoader().getResourceAsStream("resource/units.xml"), "UTF-8"));
            loader.load();
        } catch (UnsupportedEncodingException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    	
        unitSet = loader.getUnitSet();
        prefixSet = loader.getPrefixSet();
    	}
    	else
    	{
        unitSet = udDAO.getUnitSet();
        prefixSet = upDAO.getPrefixSet();
    	}
    }

    protected static void initalizeGlobalSetting(){
    	//-- PRIDANY KOD
    		try {
    		
   		 		//** nacteni souboru do DOC
    			DocumentBuilderFactory docBuilderFactory = DocumentBuilderFactory.newInstance();
            	DocumentBuilder docBuilder = docBuilderFactory.newDocumentBuilder();
            	Document doc = docBuilder.parse (new File("resource" + File.separator + "unisave_setting_bak.xml"));

            	// normalize text representation
            	doc.getDocumentElement ().normalize ();
            
             commBridgeExeFile = new File( doc.getElementsByTagName("commBridgeExeFile").item(0).getTextContent());
             reportXYFileName = doc.getElementsByTagName("reportXYFile").item(0).getTextContent();
             recentFileMaxCount = Integer.parseInt(doc.getElementsByTagName("recentFileMaxCount").item(0).getTextContent());
             savetoDB = Boolean.parseBoolean(doc.getElementsByTagName("savetoDB").item(0).getTextContent());
             
             System.out.println("TEST"+savetoDB);
            
             
        }
            catch (SAXParseException err) {
        System.out.println ("** Parsing error" + ", line " 
             + err.getLineNumber () + ", uri " + err.getSystemId ());
        System.out.println(" " + err.getMessage ());

        }catch (SAXException e) {
        Exception x = e.getException ();
        ((x == null) ? e : x).printStackTrace ();

        }catch (Throwable t) {
        t.printStackTrace ();
        }
    	//-- KONEC PRIDANEHO KODU
    	/*
        // TODO load from XML fille
        GlobalSettingXMLLoader l = new GlobalSetting().new GlobalSettingXMLLoader();
        try {
            l.setSource(new InputStreamReader(GlobalSetting.class.getClassLoader().getResourceAsStream("resource/unisave_setting.xml"), "UTF-8"));
            l.load();
        } catch (UnsupportedEncodingException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (FileNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        */
    }

    public static File getCommBridgeExeFile() {
        if(commBridgeExeFile == null){
            URL url = GlobalSetting.class.getClassLoader().getResource("CommBridge.exe");
            try {
                commBridgeExeFile = new File(url.toURI());
            } catch (URISyntaxException e) {}
            initalizeGlobalSetting();
        }
        return commBridgeExeFile;
    }

    public static void setCommBridgeExeFile(File commBridgeExeFile) {
        GlobalSetting.commBridgeExeFile = commBridgeExeFile;
    }

    public static String getConfirSoundFileName() {
        return "resource/upozorneni.wav";
    }

    protected static int recentFileMaxCount = 4;

    public static int getRecentFileMaxCount() {
        return recentFileMaxCount;
    }

    public static void setRecentFileMaxCount(int recentFileMaxCount) {
        GlobalSetting.recentFileMaxCount = recentFileMaxCount;
    }

    protected static String reportXYFileName = null;
    public static String getReportXYFileName(){
    	if(reportXYFileName == null)
    		initalizeGlobalSetting();
    	return reportXYFileName;
    }
/*    public static InputStream getReportXYFile() {
        //if(reportXYFile == null){
            InputStream in = GlobalSetting.class.getClassLoader().getResourceAsStream(getReportXYFileName());
            //try {
                //reportXYFile = new File("C:\\Documents and Settings\\jez04\\My Documents\\projekty\\UniSave2006\\UniSave2006Implementation\\resource\\invoice.xml");
            //} catch (URISyntaxException e) {}
            //initalizeGlobalSetting();
        //}
        return in;
    }
*/    
    //protected static InputStream reportXYFile = null;
     public static InputStream getReportXYTemplate() {
    	 
    	 
        //if(reportXYFile == null){
        	InputStream reportXYFile = GlobalSetting.class.getClassLoader().getResourceAsStream(getReportXYFileName());
//            try {
//                reportXYFile = new File(url.toURI());//new File("C:\\Documents and Settings\\jez04\\My Documents\\projekty\\UniSave2006\\UniSave2006Implementation\\resource\\invoice.xml");
//            } catch (URISyntaxException e) {}
            //initalizeGlobalSetting();
        //}
        return reportXYFile;
    }

    public static UserSetting getUserSetting() {
    	if(GlobalSetting.getSavetoDB() == false)
    	{
        if(userSetting == null){
            userSetting = UserSetting.initialize();
            File setting = new File("resource" + File.separator + "UniTEST22.xml");
            if(setting.exists()){
                try {
                    UserSettingXMLLoader l = new UserSettingXMLLoader();
                    l.setSource(new InputStreamReader(new FileInputStream(setting), "UTF-8"));
                    l.load();
                    if(l.getUserSetting() != null)
                        userSetting = l.getUserSetting();
                } catch (UnsupportedEncodingException e) {
                	
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                } catch (IOException e) {
                    // TODO Auto-generated catch block
                	
                    e.printStackTrace();
                }
            }
        }
    	}
    	else
    	{
    	if(userSetting == null) {
    		
    		try {
    			long size = usDAO.countElements();
    			userSetting = usDAO.find(size);
    		}
    		catch(HibernateException e) {
    			userSetting = UserSetting.initialize();
    		}
    	}
    	}
        return userSetting;
        
    }
    
    public static void saveUserSetting(){
    	if(GlobalSetting.getSavetoDB() == false)
    	{
        File setting = new File("resource" + File.separator + "UniTEST22.xml");
        if(!setting.exists()){
            try {
                setting.createNewFile();
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
        try {
            BufferedWriter w = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(setting), "UTF-8"));
            getUserSetting().storeToXML(w);
            w.close();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    	}
    	//konec ifu
    	else
    	{
    	udDAO.closeSession();
    	mdDAO.closeSession();
    	usDAO.saveOrUpdate(userSetting);
    	usDAO.closeSession();
    	}
    }
    //-- PRIDANY KOD
    public static void saveGlobalSetting()
    {
    	// UKLADANI DO XML
    	File setting = new File("resource" + File.separator + "unisave_setting_bak.xml");
        if(!setting.exists()){
            try {
                setting.createNewFile();
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
        try {
            BufferedWriter w = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(setting), "UTF-8"));
            storeToXML(w);
            w.close();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    	
    }
    //-- KONEC PRIDANEHO KODU
    
    //-- PRIDANY KOD
    public static void storeToXML(BufferedWriter out) throws IOException{
    	GlobalSettingXMLWriter w = new GlobalSettingXMLWriter(out);
        w.store();
    }
    //-- KONEC PRIDANEHO KODU
    
    //-- PRIDANY KOD
    public static class GlobalSettingXMLWriter {
        protected int offset = 0;
        protected BufferedWriter w = null;
        public GlobalSettingXMLWriter(BufferedWriter w){
            this.w = w;
        }
        public void startFileTag() throws IOException {
            write("<?xml version=\"1.0\" encoding=\"UTF-8\"?>");
            write("<unisave_setting>");
            
            offset++;
        }

        public void endFileTag() throws IOException {
            offset--;
            write("</unisave_setting>");
        }
        public void storeUserSettingBody() throws IOException {
            
        	write("<savetoDB>"+ savetoDB +"</savetoDB>");
            write("<commBridgeExeFile>"+ "CommBridge.exe" +"</commBridgeExeFile>");
            write("<reportXYFile>"+ "resource/reportXY.xml" +"</reportXYFile>");
            write("<recentFileMaxCount>"+ "4" +"</recentFileMaxCount>");
            
            offset++;
            
        }
        protected String escape(String s){
            return XMLSupport.escape(s);
        }
        protected void write(String s) throws IOException{
            XMLSupport.write(s, w, offset);
        }
        public void store() throws IOException{
            startFileTag();
            storeUserSettingBody();
            endFileTag();
            w.flush();
        }
    }
    //-- KONEC PRIDANEHO KODU
    
    
    class GlobalSettingXMLLoader extends StructuredXMLLoader{
        
        public GlobalSettingXMLLoader(){
        }
        @Override
        public void endElement(String uri, String localName, String qName) throws SAXException {
            if(qName.equals("unisave_setting")){
            }
        }

        @Override
        public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
            if(qName.equals("commBridgeExeFile")){
                commBridgeExeFile = new File(attributes.getValue("value")); 
            }else if(qName.equals("reportXYFile")){
            	reportXYFileName = attributes.getValue("value");
            }else if(qName.equals("recentFileMaxCount")){
                try{
                    recentFileMaxCount = Integer.parseInt(attributes.getValue("value"));
                }catch(NumberFormatException ex){
                    throw new MeasurementFormatException("Cyba pøi naèítání mìøení.");
                }
            }
        }
    }
    
    protected static Image graphIcon = null;
	public static Image getGraphIcon() {
		if (graphIcon == null) {
			graphIcon = java.awt.Toolkit.getDefaultToolkit().createImage(GlobalSetting.class.getClassLoader()
					.getResource("resource/icons/graph_small.gif"));
		}
		return graphIcon;
	}
    protected static Image uniSaveIcon = null;
	public static Image getUniSaveIcon() {
		if (uniSaveIcon == null) {
			uniSaveIcon = java.awt.Toolkit.getDefaultToolkit().createImage(GlobalSetting.class.getClassLoader()
					.getResource("resource/icons/UniSave.gif"));
		}
		return uniSaveIcon;
	}
    protected static Image uniSaveDocIcon = null;
	public static Image getUniSaveDocIcon() {
		if (uniSaveDocIcon == null) {
			uniSaveDocIcon = java.awt.Toolkit.getDefaultToolkit().createImage(GlobalSetting.class.getClassLoader()
					.getResource("resource/icons/UniSaveDoc.gif"));
		}
		return uniSaveDocIcon;
	}
	
	protected static HelpSet helpSet = null;
	protected static HelpBroker helpBroker = null;
	public static HelpSet getHelpSet(){
		if(helpSet == null)
			initHelp();
		return helpSet;
	}
	public static HelpBroker getHelpBroker(){
		if(helpBroker == null)
			initHelp();
		return helpBroker;
	}
	protected static void initHelp(){
		// Find the HelpSet file and create the HelpSet object:
		String helpHS = "help/UniSave2006.hs";
		ClassLoader cl = GlobalSetting.class.getClassLoader();
		try {
			URL hsURL = HelpSet.findHelpSet(cl, helpHS);
			helpSet = new HelpSet(cl, hsURL);
			// Create a HelpBroker object:
			helpBroker = helpSet.createHelpBroker();
		} catch (Exception ee) {
			// Say what the exception really is
			//TODO dodelat
		}
	}
}

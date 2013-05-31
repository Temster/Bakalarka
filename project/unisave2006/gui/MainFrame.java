/*
 * Created on Jul 12, 2006
 *
 * Copyright (C) 2006
 * David Ježek 
 * david.jezek@vsb.cz.
 * All rights reserved.
 */
package unisave2006.gui;

import javax.swing.JFrame;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.JCheckBoxMenuItem;
import javax.swing.JFileChooser;
import javax.swing.JMenuBar;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JSeparator;
import javax.swing.JPanel;
import javax.swing.KeyStroke;
import java.awt.Dimension;
import java.awt.Frame;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.Toolkit;

import javax.swing.JToolBar;
import javax.swing.JButton;
import java.awt.BorderLayout;
import java.io.File;
import java.io.IOException;
import java.util.Vector;

import javax.swing.ImageIcon;

import unisave2006.GlobalSetting;
import unisave2006.HibernateUtil;
import unisave2006.dao.data.MeasurementDAO;
import unisave2006.data.Measurement;
import unisave2006.data.MeasurementFactory;
import unisave2006.data.MeasurementPOJO;
import unisave2006.data.MeasurementSettingListener;
import unisave2006.data.XYMeasurement;
import unisave2006.data.XYMeasurementPOJO;
import unisave2006.units.Unit;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import javax.swing.WindowConstants;
import javax.help.*;

/**
 * @author David Ježek
 * 
 * Copyright (C) 2006 David Ježek david.jezek@vsb.cz. All rights reserved. This
 * class was created as part of project UniSave2006Implementation.
 */
public class MainFrame extends JFrame implements MeasurementSettingListener, 
													LoadMeasurementListener,ActionListener {

    /**
     * 
     */
    private static final long serialVersionUID = 1L;

    protected static Measurement measurement = null;  //  @jve:decl-portIndex=0:
    
    protected MeasurementDAO mDAO;
    
    private LoadMeasurementDialog lm;

    private JMenuBar jJMenuBar = null;

    private JMenu jMenuFile = null;

    private JMenu jMenuEdit = null;

    private JMenu jMenuProtocol = null;

    private JMenu jMenuView = null;

    private JMenu jMenuSettings = null;

    private JMenu jMenuHelp = null;

    private JMenuItem jMenuItemNew = null;

    private JMenuItem jMenuItemOpen = null;

    private JMenuItem jMenuItemSave = null;

    private JMenuItem jMenuItemSaveAs = null;

    private JMenuItem jMenuItemExit = null;

    private JSeparator jSeparatorStartRecent = null;

    private Vector<JMenuItem> recentFileMenuItems = null;  //  @jve:decl-portIndex=0:
    
    private JSeparator jSeparator1 = null;

    private JPanel jPanel = null;

    private JPanel jPanelMeasurement = null;

    private JToolBar jToolBar = null;

    private JMenuItem jMenuItemProtocolCreate = null;

    private JMenuItem jMenuItemEditEdit = null;

    private JMenuItem jMenuItemEditCut = null;

    private JMenuItem jMenuItemEditCopy = null;

    private JMenuItem jMenuItemEditDeleteLast = null;

    private JMenuItem jMenuItemEditDeleteSelected = null;

    private JMenuItem jMenuItemEditUnitConversion = null;

    private JSeparator jSeparator2 = null;

    private JMenuItem jMenuItemHelpAbout = null;

    private JButton jButtonNew = null;

    private JButton jButtonFileOpen = null;

    private JMenuItem jMenuItemChart = null;

    private MeasurementPanelInterface panelInterface;  //  @jve:decl-portIndex=0:
    
    
    //-- PRIDANY KOD VLASTNI PROMENNE
    private JCheckBoxMenuItem ComboMenuItem;
    //-- KONEC KODU


    /**
     * This method initializes
     * 
     */
    public MainFrame() {
        super();
//        System.getProperties().list(System.out);
//        InputStream is = this.getClass().getClassLoader().getResourceAsStream("resource/swing.properties");
//        Properties p = new Properties();
//        try {
//            p.load(new InputStreamReader(is, "UTF-8"));
//            is.close();
//        } catch (FileNotFoundException e) {
//            e.printStackTrace();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//        for(Entry<Object, Object> e: p.entrySet()){
//            //System.out.println(e.getKey() +"="+ UIManager.get(e.getKey()));
//            UIManager.put(e.getKey(), e.getValue());
//        }
//        UIDefaults d = UIManager.getDefaults();
//        for(Entry<Object, Object> e:d.entrySet()){
//            System.out.println(e.getKey() +"="+ UIManager.get(e.getKey()));
//        }
        initialize();
        GlobalSetting.getHelpBroker().enableHelpKey(this.getRootPane(), "MainFrame", GlobalSetting.getHelpSet());
        setMeasurementPanel(new NullMeasurementPanel());
        setLocationRelativeTo(null);
        mDAO = new MeasurementDAO();
    }

    /**
     * This method initializes this
     * 
     * @return void
     */
    private void initialize() {
        this.setContentPane(getJPanel());
        this.setJMenuBar(getJJMenuBar());
        this.setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
        this.setTitle("UniSave 2006");
        this.setIconImage(GlobalSetting.getUniSaveIcon());
        // Kopirovano z createNewMeasurement(), 
        // aby zabranil chybu 'Session is closed' v MeasurementDAO
        int type = MeasurementFactory.MEASUREMENT_XY;
        Measurement m = MeasurementFactory.createMeasurement(type);
        setMeasurement(m);
        /////////////////////////////////////////////////////
        this.addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowClosing(java.awt.event.WindowEvent e) {
                exitApplication();
            }
        });
    }

    /**
     * This method initializes jJMenuBar
     * 
     * @return javax.swing.JMenuBar
     */
    private JMenuBar getJJMenuBar() {
        if (jJMenuBar == null) {
            jJMenuBar = new JMenuBar();
            jJMenuBar.add(getJMenuFile());
            jJMenuBar.add(getJMenuProtocol());
            jJMenuBar.add(getJMenuEdit());
            jJMenuBar.add(getJMenuView());
            jJMenuBar.add(getJMenuSettings());
            jJMenuBar.add(getJMenuHelp());
        }
        return jJMenuBar;
    }

    /**
     * This method initializes jMenu
     * 
     * @return javax.swing.JMenu
     */
    private JMenu getJMenuFile() {
        if (jMenuFile == null) {
            jMenuFile = new JMenu();
            jMenuFile.setText("Soubor");
            jMenuFile.add(getJMenuItemNew());
            jMenuFile.add(getJMenuItemOpen());
            jMenuFile.add(getJMenuItemSave());
            jMenuFile.add(getJMenuItemSaveAs());
            jMenuFile.add(getJSeparatorStartRecent());
            for(int i=0; i<GlobalSetting.getRecentFileMaxCount(); i++){
                jMenuFile.add(getJMenuItemRecentFile(i));
            }
            jMenuFile.add(getJSeparator1());
            jMenuFile.add(getJMenuItemExit());
            updateRecentFiles();
        }
        return jMenuFile;
    }

    /**
     * This method initializes jMenu1
     * 
     * @return javax.swing.JMenu
     */
    private JMenu getJMenuEdit() {
        if (jMenuEdit == null) {
            jMenuEdit = new JMenu();
            jMenuEdit.setText("Editace");
            jMenuEdit.add(getJMenuItemEditEdit());
            jMenuEdit.add(getJMenuItemEditCut());
            jMenuEdit.add(getJMenuItemEditCopy());
            jMenuEdit.add(getJMenuItemEditDeleteLast());
            jMenuEdit.add(getJMenuItemEditDeleteSelected());
            jMenuEdit.add(getJMenuItemEditUnitConversion());
        }
        return jMenuEdit;
    }

    /**
     * This method initializes jMenu2
     * 
     * @return javax.swing.JMenu
     */
    private JMenu getJMenuProtocol() {
        if (jMenuProtocol == null) {
            jMenuProtocol = new JMenu();
            jMenuProtocol.setName("");
            jMenuProtocol.setText("Protokol");
            jMenuProtocol.add(getJMenuItemProtocolCreate());
        }
        return jMenuProtocol;
    }

    /**
     * This method initializes jMenu3
     * 
     * @return javax.swing.JMenu
     */
    private JMenu getJMenuView() {
        if (jMenuView == null) {
            jMenuView = new JMenu();
            jMenuView.setText("Zobrazit");
            jMenuView.add(getJMenuItemChart());
        }
        return jMenuView;
    }

    /**
     * This method initializes jMenu4
     * 
     * @return javax.swing.JMenu
     */
    private JMenu getJMenuSettings() {
        if (jMenuSettings == null) {
            jMenuSettings = new JMenu();
            jMenuSettings.setText("Nastavení");
            jMenuSettings.add(getJMenuItemSetting());
            //-- PRIDANY KOD
            jMenuSettings.addSeparator();
            ComboMenuItem = new JCheckBoxMenuItem("Ukladani do Databáze");
            ComboMenuItem.setMnemonic(KeyEvent.VK_C);                        
            jMenuSettings.add(ComboMenuItem);            
            ComboMenuItem.setSelected(GlobalSetting.getSavetoDB());
            ComboMenuItem.addActionListener(this);            
            //-- KONEC PRIDANEHO KODU
        }
        return jMenuSettings;
    }

    /**
     * This method initializes jMenu5
     * 
     * @return javax.swing.JMenu
     */
    private JMenu getJMenuHelp() {
        if (jMenuHelp == null) {
            jMenuHelp = new JMenu();
            jMenuHelp.setText("Nápověda");
            jMenuHelp.add(getJMenuItemContents());
            jMenuHelp.add(getJSeparator2());
            jMenuHelp.add(getJMenuItemHelpAbout());
        }
        return jMenuHelp;
    }

    /**
     * This method initializes jMenuItem
     * 
     * @return javax.swing.JMenuItem
     */
    private JMenuItem getJMenuItemNew() {
        if (jMenuItemNew == null) {
            jMenuItemNew = new JMenuItem();
            jMenuItemNew.setAction(getActionNewFile());
/*            jMenuItemNew.setText("Nový");
            jMenuItemNew.addActionListener(new java.awt.event.ActionListener() {
                public void actionPerformed(java.awt.event.ActionEvent e) {
                    createNewMeasurement();
                }
            });
*/        }
        return jMenuItemNew;
    }

    /**
     * This method initializes jMenuItem1
     * 
     * @return javax.swing.JMenuItem
     */
    private JMenuItem getJMenuItemOpen() {
        if (jMenuItemOpen == null) {
            jMenuItemOpen = new JMenuItem();
            jMenuItemOpen.setAction(getActionOpenFile());
/*            jMenuItemOpen.setText("Otevøít...");
            jMenuItemOpen.addActionListener(new java.awt.event.ActionListener() {
                public void actionPerformed(java.awt.event.ActionEvent e) {
                    openMesurment();
                }
            });
*/        }
        return jMenuItemOpen;
    }

    public static Measurement getMeasurement() {
    	return measurement;
    }
    
    protected void openMeasurement() {
    	if(measurement != null && measurement.getModified()){
            if(askAndSave()<0)
                return;
        }
        if (GlobalSetting.getSavetoDB()== false)
        {
        JFileChooser fc = new JFileChooser();
        fc.setDialogTitle("Otevřít");
        fc.setFileFilter(new UnisaveFileFilter());
        fc.setCurrentDirectory(GlobalSetting.getUserSetting().getLastUsedDir());
        if (fc.showOpenDialog(this) == JFileChooser.APPROVE_OPTION) {
            GlobalSetting.getUserSetting().setLastUsedDir(fc.getCurrentDirectory());
            File f = fc.getSelectedFile();
            openMeasurement(f);
        }
        }
        
        if (GlobalSetting.getSavetoDB())
        {
        lm = new LoadMeasurementDialog(this);
        lm.addLoadMeasurementListener(this);
        lm.pack();
        lm.setLocationRelativeTo(this);
        lm.setVisible(true);
        }
    }

    private void openMeasurement(File f) {
        try {
            setMeasurement(MeasurementFactory.loadMeasurement(f));
        } catch (IOException e) {
            JOptionPane.showMessageDialog(this, String.format("Nelze číst soubor %s", f.getPath()), "UniSave 2006 - chyba", JOptionPane.ERROR_MESSAGE);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, new ErrorMessagePanel("Soubor neodpovídá podporovanému formátu.", e.getMessage()), "UniSave 2006 - chyba", JOptionPane.ERROR_MESSAGE);
        }
    }

    /**
     * This method initializes jMenuItem2
     * 
     * @return javax.swing.JMenuItem
     */
    private JMenuItem getJMenuItemSave() {
        if (jMenuItemSave == null) {
            jMenuItemSave = new JMenuItem();
            jMenuItemSave.setAction(getActionSaveFile());
           jMenuItemSave.setText("Uložit");
/*            jMenuItemSave.addActionListener(new java.awt.event.ActionListener() {
                public void actionPerformed(java.awt.event.ActionEvent e) {
                    saveMeasurement();
                }
            });
 */       }
        return jMenuItemSave;
    }

    /**
     * This method initializes jMenuItem3
     * 
     * @return javax.swing.JMenuItem
     */
    private JMenuItem getJMenuItemSaveAs() {
        if (jMenuItemSaveAs == null) {
            jMenuItemSaveAs = new JMenuItem();
            jMenuItemSaveAs.setAction(getActionSaveAsFile());
/*            jMenuItemSaveAs.setText("Uložit jako...");
            jMenuItemSaveAs.setMnemonic(KeyEvent.VK_UNDEFINED);
            jMenuItemSaveAs.addActionListener(new java.awt.event.ActionListener() {
                public void actionPerformed(java.awt.event.ActionEvent e) {
                    saveAsMeasurement();
                }
            });
*/        }
        return jMenuItemSaveAs;
    }

    /**
     * This method initializes jMenuItem6
     * 
     * @return javax.swing.JMenuItem
     */
    private JMenuItem getJMenuItemExit() {
        if (jMenuItemExit == null) {
            jMenuItemExit = new JMenuItem();
            jMenuItemExit.setAction(getActionExit());
/*            jMenuItemExit.setText("Konec");
            jMenuItemExit.addActionListener(new java.awt.event.ActionListener() {
                public void actionPerformed(java.awt.event.ActionEvent e) {
                    exitApplication();
                }
            });
*/        }
        return jMenuItemExit;
    }
    class RecentFileActionListener implements ActionListener{
        public RecentFileActionListener(int index){
            this.index = index;
        }
        protected int index = 0;
        public void setIndex(int index){
            this.index = index;
        }
        public void actionPerformed(java.awt.event.ActionEvent e) {
            if(measurement != null && measurement.getModified()){
                if(askAndSave()<0)
                    return;
            }
            openMeasurement(GlobalSetting.getUserSetting().getRecentFiles().elementAt(index));
        }
    }
    private JMenuItem getJMenuItemRecentFile(int i){
        if(recentFileMenuItems == null){
            recentFileMenuItems = new Vector<JMenuItem>(GlobalSetting.getRecentFileMaxCount());
            for(int j=0; j<GlobalSetting.getRecentFileMaxCount(); j++){
                JMenuItem recent = new JMenuItem();
                recent.addActionListener(new RecentFileActionListener(j));
                recentFileMenuItems.add(recent);
            }
        }
        if(i>= recentFileMenuItems.size() || 
                recentFileMenuItems.elementAt(i) == null)
            return null;
        return recentFileMenuItems.elementAt(i);
    }
    
    protected void exitApplication() {
        if(measurement != null && measurement.getModified()){
            if(askAndSave()<0)
                return;
        }
        GlobalSetting.saveUserSetting();
        //-- PRIDANY KOD
        GlobalSetting.saveGlobalSetting();
        //-- KONEC PRIDANEHO KODU
        HibernateUtil.closeFactory();
        this.setVisible(false);
        System.exit(0);
    }

    /**
     * This method initializes jSeparatorStartRecent
     * 
     * @return javax.swing.JSeparator
     */
    private JSeparator getJSeparatorStartRecent() {
        if (jSeparatorStartRecent == null) {
            jSeparatorStartRecent = new JSeparator();
        }
        return jSeparatorStartRecent;
    }

    /**
     * This method initializes jSeparator1
     * 
     * @return javax.swing.JSeparator
     */
    private JSeparator getJSeparator1() {
        if (jSeparator1 == null) {
            jSeparator1 = new JSeparator();
        }
        return jSeparator1;
    }

    /**
     * This method initializes jPanel
     * 
     * @return javax.swing.JPanel
     */
    private JPanel getJPanel() {
        if (jPanel == null) {
            jPanel = new JPanel();
            jPanel.setLayout(new BorderLayout());
            jPanel.add(getJPanelMeasurement(), java.awt.BorderLayout.CENTER);
            jPanel.add(getJToolBar(), BorderLayout.NORTH);
        }
        return jPanel;
    }

    /**
     * This method initializes jPanel1
     * 
     * @return javax.swing.JPanel
     */
    private JPanel getJPanelMeasurement() {
        if (jPanelMeasurement == null) {
            GridBagConstraints gridBagConstraints = new GridBagConstraints();
            gridBagConstraints.fill = GridBagConstraints.BOTH;
            gridBagConstraints.weighty = 2.0D;
            gridBagConstraints.weightx = 2.0D;
            jPanelMeasurement = new JPanel();
            jPanelMeasurement.setLayout(new GridBagLayout());
        }
        return jPanelMeasurement;
    }

    public void setMeasurementPanel(MeasurementPanelInterface p) {
    	if(panelInterface != null){
    		panelInterface.close();
    		panelInterface.detache();
    	}
    	panelInterface = p;
        getJPanelMeasurement().removeAll();
        GridBagConstraints gridBagConstraints = new GridBagConstraints();
        gridBagConstraints.fill = GridBagConstraints.BOTH;
        gridBagConstraints.weighty = 2.0D;
        gridBagConstraints.weightx = 2.0D;
        getJPanelMeasurement().add(p.getPanel(), gridBagConstraints);
        getJPanelMeasurement().validate();
        if(this.getExtendedState() != Frame.MAXIMIZED_BOTH)
        pack();
        initializeMenuActions();
    }

    /**
     * This method initializes jToolBar
     * 
     * @return javax.swing.JToolBar
     */
    private JToolBar getJToolBar() {
        if (jToolBar == null) {
            jToolBar = new JToolBar();
            jToolBar.add(getJButtonNew());
            jToolBar.add(getJButtonFileOpen());
            jToolBar.add(getJButtonSaveFile());
            jToolBar.add(getJToolBarSeparator());
            jToolBar.add(getJButtonCut());
            jToolBar.add(getJButtonCopy());
            jToolBar.add(getJButtonDeleteLast());
            jToolBar.add(getJButtonDelete());
            jToolBar.add(getJToolBarSeparator2());
            jToolBar.add(getJButtonUnitConversion());
            jToolBar.add(getJButtonProtocol());
            jToolBar.add(getJButtonGraph());
        }
        return jToolBar;
    }

    /**
     * This method initializes jMenuItem
     * 
     * @return javax.swing.JMenuItem
     */
    private JMenuItem getJMenuItemProtocolCreate() {
        if (jMenuItemProtocolCreate == null) {
            jMenuItemProtocolCreate = new JMenuItem();
            jMenuItemProtocolCreate.setText("Vytvořit...");
        }
        return jMenuItemProtocolCreate;
    }

    /**
     * This method initializes jMenuItem
     * 
     * @return javax.swing.JMenuItem
     */
    private JMenuItem getJMenuItemEditEdit() {
        if (jMenuItemEditEdit == null) {
            jMenuItemEditEdit = new JMenuItem();
            jMenuItemEditEdit.setText("Editovat...");
        }
        return jMenuItemEditEdit;
    }

    /**
     * This method initializes jMenuItem
     * 
     * @return javax.swing.JMenuItem
     */
    private JMenuItem getJMenuItemEditCut() {
        if (jMenuItemEditCut == null) {
            jMenuItemEditCut = new JMenuItem();
            jMenuItemEditCut.setText("Vyjmout");
        }
        return jMenuItemEditCut;
    }

    /**
     * This method initializes jMenuItem
     * 
     * @return javax.swing.JMenuItem
     */
    private JMenuItem getJMenuItemEditCopy() {
        if (jMenuItemEditCopy == null) {
            jMenuItemEditCopy = new JMenuItem();
            jMenuItemEditCopy.setText("Kopírovat");
        }
        return jMenuItemEditCopy;
    }

    /**
     * This method initializes jMenuItem
     * 
     * @return javax.swing.JMenuItem
     */
    private JMenuItem getJMenuItemEditDeleteLast() {
        if (jMenuItemEditDeleteLast == null) {
            jMenuItemEditDeleteLast = new JMenuItem();
            jMenuItemEditDeleteLast.setText("Smazat poslední");
        }
        return jMenuItemEditDeleteLast;
    }

    /**
     * This method initializes jMenuItem
     * 
     * @return javax.swing.JMenuItem
     */
    private JMenuItem getJMenuItemEditDeleteSelected() {
        if (jMenuItemEditDeleteSelected == null) {
            jMenuItemEditDeleteSelected = new JMenuItem();
            jMenuItemEditDeleteSelected.setText("Smazat vybrané");
        }
        return jMenuItemEditDeleteSelected;
    }

    /**
     * This method initializes jMenuItem
     * 
     * @return javax.swing.JMenuItem
     */
    private JMenuItem getJMenuItemEditUnitConversion() {
        if (jMenuItemEditUnitConversion == null) {
            jMenuItemEditUnitConversion = new JMenuItem();
            jMenuItemEditUnitConversion.setText("Převod jednotek");
        }
        return jMenuItemEditUnitConversion;
    }

    /**
     * This method initializes jSeparator2
     * 
     * @return javax.swing.JSeparator
     */
    private JSeparator getJSeparator2() {
        if (jSeparator2 == null) {
            jSeparator2 = new JSeparator();
        }
        return jSeparator2;
    }

    /**
     * This method initializes jMenuItem
     * 
     * @return javax.swing.JMenuItem
     */
    private JMenuItem getJMenuItemHelpAbout() {
        if (jMenuItemHelpAbout == null) {
            jMenuItemHelpAbout = new JMenuItem();
            jMenuItemHelpAbout.setText("O programu...");
            jMenuItemHelpAbout.addActionListener(new java.awt.event.ActionListener() {
                public void actionPerformed(java.awt.event.ActionEvent e) {
                    About a = new About(MainFrame.this, false);
                    a.pack();
                    Dimension screen = Toolkit.getDefaultToolkit().getScreenSize();
                    a.setLocation(screen.width/2-a.getWidth()/2, screen.height/2-a.getHeight()/2);
                    a.setVisible(true);
                }
            });
        }
        return jMenuItemHelpAbout;
    }

    /**
     * This method initializes jButtonNew
     * 
     * @return javax.swing.JButton
     */
    private JButton getJButtonNew() {
        if (jButtonNew == null) {
            jButtonNew = new JButton();
            jButtonNew.setAction(getActionNewFile());
            jButtonNew.setHideActionText(true);
/*            jButtonNew.setIcon(new ImageIcon(getClass().getResource(
                    "/resource/icons/newfile_wiz.gif")));
            jButtonNew.setDisabledIcon(new ImageIcon(getClass().getResource(
                    "/resource/icons/newfile_wiz_disable.gif")));
            jButtonNew.setEnabled(true);
            jButtonNew.addActionListener(new java.awt.event.ActionListener() {
                public void actionPerformed(java.awt.event.ActionEvent e) {
                    createNewMesurment();
                }
            });
*/        }
        return jButtonNew;
    }

    /**
     * This method initializes jButtonFileOpen
     * 
     * @return javax.swing.JButton
     */
    private JButton getJButtonFileOpen() {
        if (jButtonFileOpen == null) {
            jButtonFileOpen = new JButton();
            jButtonFileOpen.setAction(getActionOpenFile());
            jButtonFileOpen.setHideActionText(true);
        }
        return jButtonFileOpen;
    }


    /**
     * This method initializes jMenuItemChart	
     * 	
     * @return javax.swing.JMenuItem	
     */
    private JMenuItem getJMenuItemChart() {
        if (jMenuItemChart == null) {
            jMenuItemChart = new JMenuItem();
            jMenuItemChart.setText("Graf");
        }
        return jMenuItemChart;
    }

    /**
     * This method initializes jButtonSaveFile	
     * 	
     * @return javax.swing.JButton	
     */
    private JButton getJButtonSaveFile() {
        if (jButtonSaveFile == null) {
            jButtonSaveFile = new JButton();
            jButtonSaveFile.setAction(getActionSaveFile());
            jButtonSaveFile.setHideActionText(true);
        }
        return jButtonSaveFile;
    }

    /**
     * This method initializes jMenuItemContents	
     * 	
     * @return javax.swing.JMenuItem	
     */
    private JMenuItem getJMenuItemContents() {
        if (jMenuItemContents == null) {
            jMenuItemContents = new JMenuItem();
			jMenuItemContents.setText("Obsah nápovědy...");
			// Create a "Help" menu item to trigger the help viewer:
			jMenuItemContents.addActionListener(
					new CSH.DisplayHelpFromSource(GlobalSetting.getHelpBroker()));
		}
		return jMenuItemContents;
	}

    /**
	 * This method initializes jToolBarSeparator
	 * 
	 * @return javax.swing.JToolBar.Separator
	 */
    private JToolBar.Separator getJToolBarSeparator() {
        if (jToolBarSeparator == null) {
            jToolBarSeparator = new JToolBar.Separator();
        }
        return jToolBarSeparator;
    }

    /**
     * This method initializes jButtonCut	
     * 	
     * @return javax.swing.JButton	
     */
    private JButton getJButtonCut() {
        if (jButtonCut == null) {
            jButtonCut = new JButton();
            jButtonCut.setHideActionText(true);
        }
        return jButtonCut;
    }

    /**
     * This method initializes jButtonCopy	
     * 	
     * @return javax.swing.JButton	
     */
    private JButton getJButtonCopy() {
        if (jButtonCopy == null) {
            jButtonCopy = new JButton();
            jButtonCopy.setHideActionText(true);
        }
        return jButtonCopy;
    }

    /**
     * This method initializes jButtonDeleteLast	
     * 	
     * @return javax.swing.JButton	
     */
    private JButton getJButtonDeleteLast() {
        if (jButtonDeleteLast == null) {
            jButtonDeleteLast = new JButton();
            jButtonDeleteLast.setHideActionText(true);
        }
        return jButtonDeleteLast;
    }

    /**
     * This method initializes jButtonDelete	
     * 	
     * @return javax.swing.JButton	
     */
    private JButton getJButtonDelete() {
        if (jButtonDelete == null) {
            jButtonDelete = new JButton();
            jButtonDelete.setHideActionText(true);
        }
        return jButtonDelete;
    }

    /**
     * This method initializes jToolBarSeparator2	
     * 	
     * @return javax.swing.JToolBar.Separator	
     */
    private JToolBar.Separator getJToolBarSeparator2() {
        if (jToolBarSeparator2 == null) {
            jToolBarSeparator2 = new JToolBar.Separator();
        }
        return jToolBarSeparator2;
    }

    /**
     * This method initializes jButtonUnitConversion	
     * 	
     * @return javax.swing.JButton	
     */
    private JButton getJButtonUnitConversion() {
        if (jButtonUnitConversion == null) {
            jButtonUnitConversion = new JButton();
            jButtonUnitConversion.setHideActionText(true);
        }
        return jButtonUnitConversion;
    }

    /**
     * This method initializes jButtonProtocol	
     * 	
     * @return javax.swing.JButton	
     */
    private JButton getJButtonProtocol() {
        if (jButtonProtocol == null) {
            jButtonProtocol = new JButton();
            jButtonProtocol.setHideActionText(true);
        }
        return jButtonProtocol;
    }

    /**
     * This method initializes jButtonGraph	
     * 	
     * @return javax.swing.JButton	
     */
    private JButton getJButtonGraph() {
        if (jButtonGraph == null) {
            jButtonGraph = new JButton();
            jButtonGraph.setHideActionText(true);
        }
        return jButtonGraph;
    }

    /**
	 * This method initializes jMenuItemSetting	
	 * 	
	 * @return javax.swing.JMenuItem	
	 */
	private JMenuItem getJMenuItemSetting() {
		if (jMenuItemSetting == null) {
			jMenuItemSetting = new JMenuItem();
			jMenuItemSetting.setAction(getActionProperties());
		}
		return jMenuItemSetting;
	}

	public static void main(String[] args) {
        MainFrame mf = new MainFrame();
        mf.pack();
        mf.setVisible(true);
    }

    private void createNewMeasurement() {
        if (measurement != null) {
            if (measurement.getModified()) {
                if(askAndSave()<0)
                    return;
            }
        }
        //TODO vyber ruznych typu mereni
        int type = MeasurementFactory.MEASUREMENT_XY;
        Measurement m = MeasurementFactory.createMeasurement(type);
        setMeasurement(m);
    }

    private int askAndSave() {
        int result = JOptionPane.showConfirmDialog(this,
                "Chcete uložit změny v aktuálním měření?",
                "Unisave 2006",
                JOptionPane.YES_NO_CANCEL_OPTION,
                JOptionPane.QUESTION_MESSAGE);
        switch (result) {
        case JOptionPane.CANCEL_OPTION:
            return -1;
        case JOptionPane.YES_OPTION:
            if (saveMeasurement() < 0)
                return -1;
            break;
        case JOptionPane.NO_OPTION:
            break;
        }
        return 0;
    }

    protected void setMeasurement(Measurement m) {
        if(measurement != null){
        	measurement.detache();
        }
    	measurement = m;
        //GlobalSetting.getUserSetting().addRecentFile(measurement.getFile());
        //updateRecentFiles();
        measurement.addMeasurementSettingListener(this);
        MeasurementPanelInterface panel = MeasurementPanelFactory.createMeasurementPanel(measurement.getType());
        setMeasurementPanel(panel);
        panel.setMeasurement(m);
        m.setModified(false);
        //Toolkit.getDefaultToolkit()
        initializeMeasurement();
    }

    protected void initializeMenuActions() {
        getJButtonCut().setAction(panelInterface.getActionCut());
        getJMenuItemEditCut().setAction(panelInterface.getActionCut());
        
        getJButtonCopy().setAction(panelInterface.getActionCopy());
        getJMenuItemEditCopy().setAction(panelInterface.getActionCopy());
        
        getJButtonDeleteLast().setAction(panelInterface.getActionDeleteLast());
        getJMenuItemEditDeleteLast().setAction(panelInterface.getActionDeleteLast());

        getJButtonDelete().setAction(panelInterface.getActionDeleteSelected());
        getJMenuItemEditDeleteSelected().setAction(panelInterface.getActionDeleteSelected());

        getJButtonUnitConversion().setAction(panelInterface.getActionUnitConversion());
        getJMenuItemEditUnitConversion().setAction(panelInterface.getActionUnitConversion());

        getJButtonProtocol().setAction(panelInterface.getActionCreateProtocol());
        getJMenuItemProtocolCreate().setAction(panelInterface.getActionCreateProtocol());

        getJButtonGraph().setAction(panelInterface.getActionViewGraph());
        getJMenuItemChart().setAction(panelInterface.getActionViewGraph());

        getJMenuItemEditEdit().setAction(panelInterface.getActionEdit());
    }

    private void updateRecentFiles() {
        Vector<File> files = GlobalSetting.getUserSetting().getRecentFiles();
        for(int i=0; i<recentFileMenuItems.size(); i++){
            if(i >= files.size() || files.elementAt(i) == null)
                recentFileMenuItems.elementAt(i).setVisible(false);
            else{
                recentFileMenuItems.elementAt(i).setVisible(true);
                recentFileMenuItems.elementAt(i).setText(files.elementAt(i).getName());
            }
        }
        
    }

    protected void initializeMeasurement() {
        // TODO Auto-generated method stub
        modifiedChanged();
    }

    protected int saveMeasurement() {
    	if (measurement == null)
            return 0;
        //if (!measurement.hasAssignedFile()) {
        //    return saveAsMeasurement();
        //}
        if(GlobalSetting.getSavetoDB())
        {
        
       
        int ret = mDAO.saveOrUpdate(measurement);
        
        //int ret = measurement.saveToXML();
        if (ret < 0) {
            JOptionPane.showMessageDialog(this, "Nezdařilo se uložit aktuální měření!", "Unisave 2006",
                    JOptionPane.ERROR_MESSAGE);
        }
        else if (ret == 0) {
        	JOptionPane.showMessageDialog(this, "Úspěšně se uložil aktuální měření!", "Unisave 2006",
                    JOptionPane.INFORMATION_MESSAGE);
        }
        return ret;
        }
        else 
        {
        	if (!measurement.hasAssignedFile()) {
                    return saveAsMeasurement();
                }
        	
        	int ret = measurement.saveToXML();
        	
        	if (ret < 0) {
                JOptionPane.showMessageDialog(this, "Nezdařilo se uložit aktuální měření!", "Unisave 2006",
                        JOptionPane.ERROR_MESSAGE);
            }
            else if (ret == 0) {
            	JOptionPane.showMessageDialog(this, "Úspěšně se uložil aktuální měření!", "Unisave 2006",
                        JOptionPane.INFORMATION_MESSAGE);
            }
        	
        return ret;
        }
    }
    
    protected int saveAsMeasurement() {
    	
    	
        if(measurement == null)
            return 0;
        
        JFileChooser fc = new JFileChooser();
        fc.setDialogTitle("Uložit jako XML");
        fc.setFileFilter(new UnisaveFileFilter());
        if (measurement.getFileName() == null)
            fc.setCurrentDirectory(GlobalSetting.getUserSetting().getLastUsedDir());
        else
            fc.setCurrentDirectory(new File(measurement.getFileName()));
        boolean fileSelectedSuccesful = true;
        File selected = null;
        do {
            fileSelectedSuccesful = true;
            if (fc.showSaveDialog(this) == JFileChooser.APPROVE_OPTION) {
                selected = fc.getSelectedFile();
                GlobalSetting.getUserSetting().setLastUsedDir(fc.getCurrentDirectory());
                if(!selected.getName().endsWith(".unisave")){
                    selected = new File(selected.getPath()+".unisave");
                }
                if (selected.exists()) {
                    // String[] buttons = { "Ano", "Ne", "Storno" };
                    int ret = JOptionPane.showConfirmDialog(this,
                            "Soubor již existuje. Pøepsat?", "Unisave 2006",
                            JOptionPane.YES_NO_CANCEL_OPTION,
                            JOptionPane.QUESTION_MESSAGE);
                    switch (ret) {
                    case JOptionPane.YES_OPTION:
                        break;
                    case JOptionPane.NO_OPTION:
                        fileSelectedSuccesful = false;
                        break;
                    case JOptionPane.CANCEL_OPTION:
                        return -1;
                    }
                }
            }
            else
                return -1;
        } while (!fileSelectedSuccesful);
        measurement.setFile(selected);
        int ret = saveMeasurement();
        if(ret >=0){
            GlobalSetting.getUserSetting().addRecentFile(measurement.getFile());
            updateRecentFiles();
        }
        return ret;
    }

    public void autoConvertChanged() {}

    public void autoConvertUnitChanged() {}

    public void autoindexingChanged() {}

    public void modifiedChanged() {
        getActionSaveFile().setEnabled(measurement.getModified());
        getActionSaveAsFile().setEnabled(measurement.getModified());
        //String fileName = "bez názvu";
        //if(measurement.getFile() != null)
            //fileName = measurement.getFile().getName();
        if(measurement.getModified()){
            //setTitle("UniSave 2006 - *" + fileName);
        	setTitle("Unisave 2006 - neuložené");
        }
        else{
            //setTitle("UniSave 2006 - " + fileName);
        	setTitle("Unisave 2006");
        }
    }
    
    public void loadedRecordChanged() {
    	if(lm.getLoadedRecord() != null) {
        	Measurement m = mDAO.find(lm.getLoadedRecord());
        	setMeasurement(m);	
        }
    }
    
    public void deleteRecord() {
    	if(lm.getLoadedRecord() != null) {
    		mDAO.delete(lm.getLoadedRecord());
    	}
    }

    protected ActionNewFile actionNewFile = null;  //  @jve:decl-portIndex=0:
    protected Action getActionNewFile(){
        if(actionNewFile == null)
            actionNewFile = new ActionNewFile();
        return actionNewFile;
    }
    class ActionNewFile extends AbstractAction{
        private static final long serialVersionUID = 384080799761293184L;
        public ActionNewFile(){
            super("Nový");
            setEnabled(true);
            putValue(Action.SHORT_DESCRIPTION, "Vytvoří nové měření.");
            putValue(Action.MNEMONIC_KEY, KeyEvent.VK_N);
            putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke("control N"));
            putValue(Action.ACTION_COMMAND_KEY, "NewMeasurement");
            putValue(Action.DISPLAYED_MNEMONIC_INDEX_KEY, 0);
            putValue(Action.LARGE_ICON_KEY,
                    new ImageIcon(getClass().getResource("/resource/icons/newfile_wiz.gif")));
            putValue(Action.LONG_DESCRIPTION, "Vytvoří nové měření.");
            putValue(Action.SMALL_ICON, new ImageIcon(getClass().getResource("/resource/icons/newfile_wiz_small.gif")));
        }
        public void actionPerformed(ActionEvent e) {
            createNewMeasurement();
        }
        
    }
    
    protected ActionOpenFile actionOpenFile = null;
    protected Action getActionOpenFile(){
        if(actionOpenFile == null)
            actionOpenFile = new ActionOpenFile();
        return actionOpenFile;
    }
    class ActionOpenFile extends AbstractAction{
        private static final long serialVersionUID = 7311125740789913447L;
        public ActionOpenFile(){
            super("Otevřít...");
            setEnabled(true);
            putValue(Action.SHORT_DESCRIPTION, "Otevře záznam s měření.");
            putValue(Action.MNEMONIC_KEY, KeyEvent.VK_O);
            putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke("control O"));
            putValue(Action.ACTION_COMMAND_KEY, "OpenMeasurement");
            putValue(Action.DISPLAYED_MNEMONIC_INDEX_KEY, 0);
            putValue(Action.LARGE_ICON_KEY,
                    new ImageIcon(getClass().getResource("/resource/icons/openfile.gif")));
            putValue(Action.LONG_DESCRIPTION, "Otevře záznam s měřením a zobrazí naměřené hodnoty.");
            putValue(Action.SMALL_ICON, new ImageIcon(getClass().getResource("/resource/icons/openfile_small.gif")));
        }
        public void actionPerformed(ActionEvent e) {
            openMeasurement();
        }
    }

    protected ActionSaveFile actionSaveFile = null;
    private JButton jButtonSaveFile = null;
    protected Action getActionSaveFile(){
        if(actionSaveFile == null)
            actionSaveFile = new ActionSaveFile();
        return actionSaveFile;
    }
    class ActionSaveFile extends AbstractAction{
        private static final long serialVersionUID = -8910873754973625563L;
        public ActionSaveFile(){
            super("Uložit");
            setEnabled(false);
            putValue(Action.SHORT_DESCRIPTION, "Uloží soubor s měření.");
            putValue(Action.MNEMONIC_KEY, KeyEvent.VK_U);
            putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke("control S"));
            putValue(Action.ACTION_COMMAND_KEY, "SaveMeasurement");
            putValue(Action.DISPLAYED_MNEMONIC_INDEX_KEY, 0);
            putValue(Action.LARGE_ICON_KEY,
                    new ImageIcon(getClass().getResource("/resource/icons/save_edit.gif")));
            putValue(Action.LONG_DESCRIPTION, "Uloží naměřené hodnoty do databaze.");
            putValue(Action.SMALL_ICON,
                    new ImageIcon(getClass().getResource("/resource/icons/save_edit_small.gif")));
        }
        public void actionPerformed(ActionEvent e) {
            saveMeasurement();
        }
    }

    protected ActionSaveAsFile actionSaveAsFile = null;  //  @jve:decl-portIndex=0:
    protected Action getActionSaveAsFile(){
        if(actionSaveAsFile == null)
            actionSaveAsFile = new ActionSaveAsFile();
        return actionSaveAsFile;
    }
    class ActionSaveAsFile extends AbstractAction{
        private static final long serialVersionUID = 148069220298279127L;
        public ActionSaveAsFile(){
            super("Uložit jako XML");
            setEnabled(false);
            putValue(Action.SHORT_DESCRIPTION, "Uloží měření pod novým názvem.");
            putValue(Action.MNEMONIC_KEY, KeyEvent.VK_J);
            //putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke("control N"));
            putValue(Action.ACTION_COMMAND_KEY, "SaveAsMeasurement");
            putValue(Action.DISPLAYED_MNEMONIC_INDEX_KEY, 7);
            putValue(Action.LONG_DESCRIPTION, "Uloží naměřené hodnoty na disk do souboru s novým názvem.");
        }
        public void actionPerformed(ActionEvent e) {
            saveAsMeasurement();
        }
        
    }

    protected ActionExit actionExit = null;  //  @jve:decl-portIndex=0:

    private JMenuItem jMenuItemContents = null;

    private JToolBar.Separator jToolBarSeparator = null;

    private JButton jButtonCut = null;

    private JButton jButtonCopy = null;

    private JButton jButtonDeleteLast = null;

    private JButton jButtonDelete = null;

    private JToolBar.Separator jToolBarSeparator2 = null;

    private JButton jButtonUnitConversion = null;

    private JButton jButtonProtocol = null;

    private JButton jButtonGraph = null;

	private JMenuItem jMenuItemSetting = null;
    protected Action getActionExit(){
        if(actionExit == null)
            actionExit = new ActionExit();
        return actionExit;
    }
    class ActionExit extends AbstractAction{
        private static final long serialVersionUID = 112083540855764110L;
        public ActionExit(){
            super("Konec");
            setEnabled(true);
            putValue(Action.SHORT_DESCRIPTION, "Ukončí program.");
            putValue(Action.MNEMONIC_KEY, KeyEvent.VK_K);
            putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke("control X"));
            putValue(Action.ACTION_COMMAND_KEY, "Exit");
            putValue(Action.DISPLAYED_MNEMONIC_INDEX_KEY, 0);
            putValue(Action.LONG_DESCRIPTION, "Ukončí běh programu UniSave 2006.");
        }
        public void actionPerformed(ActionEvent e) {
            exitApplication();
        }
        
    }
	public void mostRecentUnitChanged(Unit u) {
		
	}
    protected ActionProperties actionProperties = null;  //  @jve:decl-portIndex=0:
    protected Action getActionProperties(){
        if(actionProperties == null)
        	actionProperties = new ActionProperties();
        return actionProperties;
    }
    class ActionProperties extends AbstractAction{
		private static final long serialVersionUID = -3443226332459349941L;
		public ActionProperties(){
            super("Vlastnosti...");
            setEnabled(true);
            putValue(Action.SHORT_DESCRIPTION, "Nastavení vlastností chování programu UniSave 2006.");
            putValue(Action.ACTION_COMMAND_KEY, "Properties");
            putValue(Action.DISPLAYED_MNEMONIC_INDEX_KEY, 0);
            putValue(Action.LONG_DESCRIPTION, "Nastavení vlastností chování programu UniSave 2006.");
        }
        public void actionPerformed(ActionEvent e) {
            showProperties();
        }
        
    }
	protected void showProperties() {
		SettingDialog sd = new SettingDialog(this);
		sd.pack();
		sd.setLocationRelativeTo(this);
		sd.setVisible(true);

		
	}
	
	//-- PRIDANY KOD
	@Override
	public void actionPerformed(ActionEvent e) {
	
		GlobalSetting.setSavetoDB(ComboMenuItem.isSelected());
		//System.out.println("TEST"+GlobalSetting.getSavetoDB());
	
	}
	//-- KONEC PRIDANEHO KODU

}

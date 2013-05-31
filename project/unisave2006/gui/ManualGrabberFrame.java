/*
 * Created on 2.9.2006
 *
 * Copyright (C) 2006
 * David Je�ek 
 * david.jezek@vsb.cz.
 * All rights reserved.
 */
package unisave2006.gui;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.swing.SwingUtilities;
import java.awt.BorderLayout;

import javax.swing.AbstractAction;
import javax.swing.JComponent;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JFrame;
import javax.swing.KeyStroke;

import java.awt.GridBagLayout;
import javax.swing.JButton;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.io.IOException;
import java.util.Collection;
import java.util.concurrent.ConcurrentLinkedQueue;

import javax.swing.JComboBox;

import unisave2006.GlobalSetting;
import unisave2006.data.Measurement;
import unisave2006.data.value.MeasurementEntry;
import unisave2006.data.value.MeasurementEntryFactory;
import unisave2006.grabber.ManualGrabber;
import unisave2006.gui.value.MeasurementEntityConstructionException;
import unisave2006.gui.value.MeasurementEntityEditPanelFactory;
import unisave2006.gui.value.MeasurementEntityEditor;

import javax.swing.WindowConstants;

public class ManualGrabberFrame extends JFrame {


    /**
	 * 
	 */
	private static final long serialVersionUID = -5487366838933542482L;
	protected MeasurementEntityEditor currentEntryEditor;
    protected Measurement measurement = null;  //  @jve:decl-portIndex=0:
    protected int curentValueType = 0;
    
    private JPanel jContentPane = null;

    private JPanel jPanelMeasurementEntryEditor = null;

    private JButton jButtonOK = null;

    private JButton jButtonStop = null;

    private JPanel jPanelButtons = null;

    private JComboBox jComboBoxValueType = null;
    
    protected boolean nessted = false;
    
    protected AudioInputStream audio = null;
    protected Clip clip = null;
    
    protected ManualGrabber grabber = null;
    protected ConcurrentLinkedQueue<MeasurementEntry> queue = new ConcurrentLinkedQueue<MeasurementEntry>();

    public void setGrabber(ManualGrabber grabber) {
        this.grabber = grabber;
    }

    /**
     * This method initializes jPanelMeasurementEntryEditor	
     * 	
     * @return javax.swing.JPanel	
     */
    private JPanel getJPanelMeasurementEntryEditor() {
        if (jPanelMeasurementEntryEditor == null) {
            jPanelMeasurementEntryEditor = new JPanel();
            jPanelMeasurementEntryEditor.setLayout(new BorderLayout());
        }
        return jPanelMeasurementEntryEditor;
    }

    /**
     * This method initializes jButtonOK	
     * 	
     * @return javax.swing.JButton	
     */
    private JButton getJButtonOK() {
        if (jButtonOK == null) {
            jButtonOK = new JButton();
            jButtonOK.setText("Vložit");
            jButtonOK.addActionListener(new java.awt.event.ActionListener() {
                public void actionPerformed(java.awt.event.ActionEvent e) {
                    try {
                        MeasurementEntry entry = currentEntryEditor.getEntity().cloneInstance();
                        if(nessted){
                            queue.add(entry);
                            playSound();
                            currentEntryEditor.eraseValue();
                        }
                        else{
                            measurement.addEntry(entry);
                            currentEntryEditor.eraseValue();
                            currentEntryEditor.gainFocus();
                        }
                    } catch (MeasurementEntityConstructionException ex) {
                        JOptionPane.showMessageDialog(ManualGrabberFrame.this, ex.getMessage(), "Unisave 2006 - chyba", JOptionPane.ERROR_MESSAGE);
                        ex.getSource().requestFocus();
                    }                    
                    
                }

            });
        }
        return jButtonOK;
    }
    private void playSound() {
        if(clip != null){
            clip.stop();
            clip.setFramePosition(0);
            clip.start();
        }
    }

    /**
     * This method initializes jButtonStop	
     * 	
     * @return javax.swing.JButton	
     */
    private JButton getJButtonStop() {
        if (jButtonStop == null) {
            jButtonStop = new JButton();
            jButtonStop.setMnemonic(KeyEvent.VK_UNDEFINED);
            jButtonStop.setText("Stop");
            jButtonStop.setName("Stop");
            jButtonStop.addActionListener(new java.awt.event.ActionListener() {
                public void actionPerformed(java.awt.event.ActionEvent e) {
                    if(grabber != null)
                        grabber.stopGrabbing();
                    else {
                        setVisible(false);
                        dispose();
                    }
                }
            });
            jButtonStop.getInputMap(JComponent.WHEN_FOCUSED).put(KeyStroke.getKeyStroke("ENTER"), "Stop");
            jButtonStop.getActionMap().put("Stop", new AbstractAction(){
            	private static final long serialVersionUID = 1L;
                public void actionPerformed(ActionEvent e) {
                    getJButtonStop().doClick();
                }
                
            });
        }
        return jButtonStop;
    }

    /**
     * This method initializes jPanelButtons	
     * 	
     * @return javax.swing.JPanel	
     */
    private JPanel getJPanelButtons() {
        if (jPanelButtons == null) {
            GridBagConstraints gridBagConstraints2 = new GridBagConstraints();
            gridBagConstraints2.insets = new Insets(2, 2, 2, 2);
            gridBagConstraints2.gridy = -1;
            gridBagConstraints2.ipady = 0;
            gridBagConstraints2.gridx = -1;
            GridBagConstraints gridBagConstraints1 = new GridBagConstraints();
            gridBagConstraints1.insets = new Insets(2, 2, 2, 2);
            gridBagConstraints1.gridy = -1;
            gridBagConstraints1.ipady = 0;
            gridBagConstraints1.gridx = -1;
            jPanelButtons = new JPanel();
            jPanelButtons.setLayout(new GridBagLayout());
            jPanelButtons.add(getJButtonOK(), gridBagConstraints1);
            jPanelButtons.add(getJButtonStop(), gridBagConstraints2);
        }
        return jPanelButtons;
    }

    /**
     * This method initializes jComboBoxValueType	
     * 	
     * @return javax.swing.JComboBox	
     */
    private JComboBox getJComboBoxValueType() {
        if (jComboBoxValueType == null) {
            jComboBoxValueType = new JComboBox();
            jComboBoxValueType.addActionListener(new java.awt.event.ActionListener() {

                public void actionPerformed(java.awt.event.ActionEvent e) {
                    if(getJComboBoxValueType().getSelectedItem() instanceof MyComboBoxItem){
                        MyComboBoxItem item = (MyComboBoxItem)(getJComboBoxValueType().getSelectedItem());
                        MeasurementEntityEditor ed = item.editor;
                        if(ed == null){
                            ed = MeasurementEntityEditPanelFactory.createEditPanel(item.type, nessted);
                            item.editor = ed;
                        }
                        currentEntryEditor = ed;
                        getJPanelMeasurementEntryEditor().removeAll();
                        getJPanelMeasurementEntryEditor().add(ed.getPanel(), BorderLayout.CENTER);
                        
                        curentValueType = item.type;
                        pack();
                    }
                }
            });
        }
        return jComboBoxValueType;
    }

    /**
     * @param args
     */
    public static void main(String[] args) {
        // TODO Auto-generated method stub

        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                ManualGrabberFrame thisClass = new ManualGrabberFrame(false);
                thisClass.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                thisClass.setVisible(true);
            }
        });
    }

    /**
     * This is the default constructor
     */
    public ManualGrabberFrame(boolean nessted) {
        super();
        this.nessted = nessted;
        initialize();
        try {
            audio = AudioSystem.getAudioInputStream(getClass().getClassLoader().getResourceAsStream(GlobalSetting.getConfirSoundFileName()));
                clip = AudioSystem.getClip();
            clip.open(audio);
        } catch (LineUnavailableException e) {
        } catch (UnsupportedAudioFileException e) {
        } catch (IOException e) {
        }
    }

    /**
     * This method initializes this
     * 
     * @return void
     */
    private void initialize() {
        this.setSize(300, 200);
        this.setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
        this.setContentPane(getJContentPane());
        this.setTitle("UniSave 2006 - Manuální vkládání");
        this.addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowClosing(java.awt.event.WindowEvent e) {
                grabber.stopGrabbing();
            }
        });
    }

    /**
     * This method initializes jContentPane
     * 
     * @return javax.swing.JPanel
     */
	private JPanel getJContentPane() {
        if (jContentPane == null) {
            GridBagConstraints gridBagConstraints4 = new GridBagConstraints();
            gridBagConstraints4.fill = GridBagConstraints.VERTICAL;
            gridBagConstraints4.gridwidth = 0;
            gridBagConstraints4.anchor = GridBagConstraints.WEST;
            gridBagConstraints4.weightx = 1.0;
            GridBagConstraints gridBagConstraints3 = new GridBagConstraints();
            gridBagConstraints3.anchor = GridBagConstraints.EAST;
            GridBagConstraints gridBagConstraints = new GridBagConstraints();
            gridBagConstraints.gridheight = 1;
            gridBagConstraints.gridx = -1;
            gridBagConstraints.gridy = -1;
            gridBagConstraints.ipadx = 0;
            gridBagConstraints.insets = new Insets(2, 2, 2, 2);
            gridBagConstraints.weightx = 1.0D;
            gridBagConstraints.weighty = 1.0D;
            gridBagConstraints.fill = GridBagConstraints.BOTH;
            gridBagConstraints.gridwidth = 0;
            jContentPane = new JPanel();
            jContentPane.setLayout(new GridBagLayout());
            jContentPane.add(getJComboBoxValueType(), gridBagConstraints4);
            jContentPane.add(getJPanelMeasurementEntryEditor(), gridBagConstraints);
            jContentPane.add(getJPanelButtons(), gridBagConstraints3);
            jContentPane.getInputMap(JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT).put(KeyStroke.getKeyStroke("ENTER"), "Insert");
            jContentPane.getActionMap().put("Insert", 
            		new AbstractAction(){
            	private static final long serialVersionUID = 1L;
                public void actionPerformed(ActionEvent e) {
                    getJButtonOK().doClick();
                }
                
            });
        }
        return jContentPane;
    }

    class MyComboBoxItem{
        public int type;
        public String name;
        public MeasurementEntityEditor editor = null;
        public String toString(){
            return name;
        }
        public MyComboBoxItem(int type, String name) {
            this.type = type;
            this.name = name;
        }
    }
    
    public void setAllowedValueTypes(Collection<Integer> c){
        getJComboBoxValueType().removeAllItems();
        for(int t: c){
            if(t == MeasurementEntryFactory.XY_VALUE)
                t = MeasurementEntryFactory.VALUE;
            getJComboBoxValueType().addItem(new MyComboBoxItem(t, MeasurementEntryFactory.getName(t)));
        }
    }

    public Measurement getMeasurement() {
        return measurement;
    }

    public void setMeasurement(Measurement measurement) {
        this.measurement = measurement;
    }
    
    public void setEnableInserting(boolean b){
        getJComboBoxValueType().setEnabled(b);
        currentEntryEditor.setEnableInserting(b);
        currentEntryEditor.gainFocus();
        getJButtonOK().setEnabled(b);
    }

    public MeasurementEntry pollValue() {
        return queue.poll();
    }

    
}

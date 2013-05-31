/*
 * Created on 11.10.2006
 *
 * Copyright (C) 2006
 * David Ježek 
 * david.jezek@vsb.cz.
 * All rights reserved.
 */
package unisave2006.gui;

import java.awt.GridBagLayout;

import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextField;
import java.awt.GridBagConstraints;
import javax.swing.JButton;
import javax.swing.ImageIcon;

import unisave2006.EncapsulatedObservable;
import unisave2006.MonthCalendar;

import java.awt.Dimension;
import java.awt.Insets;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.DateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Observer;
import java.awt.Color;

public class DateSelector extends JPanel {

    private static final long serialVersionUID = 1L;
    private JTextField jTextFieldDate = null;
    private JButton jButtonSelectDate = null;
    protected MonthCalendar cal = new MonthCalendar();
    protected JDialog popup = new JDialog();
    protected EncapsulatedObservable obs = new EncapsulatedObservable();
    
    public void addObserver(Observer o) {
        obs.addObserver(o);
    }

    public void deleteObserver(Observer o) {
        obs.deleteObserver(o);
    }

    /**
     * This method initializes jTextFieldDate	
     * 	
     * @return javax.swing.JTextField	
     */
    private JTextField getJTextFieldDate() {
        if (jTextFieldDate == null) {
            jTextFieldDate = new JTextField();
            jTextFieldDate.setPreferredSize(new Dimension(100, 20));
            jTextFieldDate.setText("");
            jTextFieldDate.setBackground(Color.white);
            jTextFieldDate.setEditable(false);
        }
        return jTextFieldDate;
    }

    /**
     * This method initializes jButtonSelectDate	
     * 	
     * @return javax.swing.JButton	
     */
    private JButton getJButtonSelectDate() {
        if (jButtonSelectDate == null) {
            jButtonSelectDate = new JButton();
            jButtonSelectDate.setIcon(new ImageIcon(getClass().getResource("/resource/icons/down.gif")));
            jButtonSelectDate.setPreferredSize(new Dimension(20, 20));
            jButtonSelectDate.addActionListener(new java.awt.event.ActionListener() {
                public void actionPerformed(java.awt.event.ActionEvent e) {
                    Point p = getJButtonSelectDate().getLocationOnScreen();
                    Dimension downDim = getJButtonSelectDate().getSize();
                    Dimension d = popup.getSize();
                    
                    popup.setLocation(p.x+downDim.width-d.width, p.y+downDim.height);
                    popup.setVisible(true);
                }
            });
        }
        return jButtonSelectDate;
    }

    /**
     * @param args
     */
    public static void main(String[] args) {
        JFrame f = new JFrame();
        f.getContentPane().add(new DateSelector());
        f.pack();
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        f.setVisible(true);

    }

    /**
     * This is the default constructor
     */
    public DateSelector() {
        super();
        initialize();
        popup.setModal(true);
        popup.setResizable(false);
        popup.setUndecorated(true);
        popup.getContentPane().add(cal);
        popup.pack();
        updateDate();
        cal.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e) {
                if(cal.isOk()){
                    updateDate();
                    obs.setChanged();
                    obs.notifyObservers();
                } else{
                    
                }
                popup.setVisible(false);
            }
        });
    }

    protected void updateDate() {
        String d = DateFormat.getDateInstance().format(cal.getSelectedDate().getTime());
        getJTextFieldDate().setText(d);
    }

    /**
     * This method initializes this
     * 
     * @return void
     */
    private void initialize() {
        GridBagConstraints gridBagConstraints1 = new GridBagConstraints();
        gridBagConstraints1.insets = new Insets(2, 0, 2, 2);
        GridBagConstraints gridBagConstraints = new GridBagConstraints();
        gridBagConstraints.fill = GridBagConstraints.VERTICAL;
        gridBagConstraints.insets = new Insets(2, 2, 2, 0);
        gridBagConstraints.weightx = 0.0D;
        this.setSize(152, 44);
        this.setLayout(new GridBagLayout());
        this.add(getJTextFieldDate(), gridBagConstraints);
        this.add(getJButtonSelectDate(), gridBagConstraints1);
    }
    public Date getSelectedDate(){
        return cal.getSelectedDate().getTime();
    }
    public void setSelectedDate(Date d){
        Calendar c = Calendar.getInstance();
        c.setTime(d);
        cal.setSelectedDate(c);
        updateDate();
    }
    
}  //  @jve:decl-portIndex=0:visual-constraint="4,-8"

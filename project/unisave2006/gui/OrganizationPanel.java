/*
 * Created on 2.10.2006
 *
 * Copyright (C) 2006
 * David Je�ek 
 * david.jezek@vsb.cz.
 * All rights reserved.
 */
package unisave2006.gui;

import java.awt.GridBagLayout;
import javax.swing.JPanel;
import javax.swing.JLabel;
import java.awt.GridBagConstraints;
import javax.swing.JTextField;
import java.awt.Insets;
import javax.swing.BorderFactory;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;

import unisave2006.data.Organization;

import java.awt.Font;
import java.awt.Color;

public class OrganizationPanel extends JPanel {

    private static final long serialVersionUID = 1L;
    private JLabel jLabeltitle = null;
    private JTextField jTextFieldtitle = null;
    private JLabel jLabelStreet = null;
    private JTextField jTextFieldStreet = null;
    private JLabel jLabelPsc = null;
    private JTextField jTextFieldPsc = null;
    private JLabel jLabelCity = null;
    private JTextField jTextFieldCity = null;
    private JLabel jLabelTel = null;
    private JTextField jTextFieldTel = null;
    
    protected Organization org = new Organization();

    /**
     * This is the default constructor
     */
    public OrganizationPanel() {
        super();
        initialize();
        upadateValues();
    }

    /**
     * This method initializes this
     * 
     * @return void
     */
    private void initialize() {
        GridBagConstraints gridBagConstraints21 = new GridBagConstraints();
        gridBagConstraints21.fill = GridBagConstraints.HORIZONTAL;
        gridBagConstraints21.gridwidth = 0;
        gridBagConstraints21.weightx = 1.0;
        GridBagConstraints gridBagConstraints11 = new GridBagConstraints();
        gridBagConstraints11.insets = new Insets(2, 2, 2, 2);
        jLabelTel = new JLabel();
        jLabelTel.setText("Tel./Fax.:");
        GridBagConstraints gridBagConstraints7 = new GridBagConstraints();
        gridBagConstraints7.insets = new Insets(2, 2, 2, 2);
        GridBagConstraints gridBagConstraints6 = new GridBagConstraints();
        gridBagConstraints6.insets = new Insets(2, 2, 2, 2);
        GridBagConstraints gridBagConstraints5 = new GridBagConstraints();
        gridBagConstraints5.insets = new Insets(2, 2, 2, 2);
        GridBagConstraints gridBagConstraints4 = new GridBagConstraints();
        gridBagConstraints4.fill = GridBagConstraints.HORIZONTAL;
        gridBagConstraints4.insets = new Insets(2, 2, 2, 2);
        gridBagConstraints4.gridwidth = 0;
        gridBagConstraints4.weightx = 2.0D;
        jLabelCity = new JLabel();
        jLabelCity.setText("Město:");
        GridBagConstraints gridBagConstraints3 = new GridBagConstraints();
        gridBagConstraints3.fill = GridBagConstraints.HORIZONTAL;
        gridBagConstraints3.insets = new Insets(2, 2, 2, 2);
        gridBagConstraints3.weightx = 1.0;
        jLabelPsc = new JLabel();
        jLabelPsc.setText("PSČ:");
        GridBagConstraints gridBagConstraints2 = new GridBagConstraints();
        gridBagConstraints2.fill = GridBagConstraints.HORIZONTAL;
        gridBagConstraints2.gridwidth = 0;
        gridBagConstraints2.insets = new Insets(2, 2, 2, 2);
        gridBagConstraints2.weightx = 1.0;
        jLabelStreet = new JLabel();
        jLabelStreet.setText("Ulice:");
        GridBagConstraints gridBagConstraints1 = new GridBagConstraints();
        gridBagConstraints1.ipadx = 0;
        gridBagConstraints1.insets = new Insets(2, 2, 2, 2);
        GridBagConstraints gridBagConstraints = new GridBagConstraints();
        gridBagConstraints.fill = GridBagConstraints.HORIZONTAL;
        gridBagConstraints.gridwidth = 0;
        gridBagConstraints.insets = new Insets(2, 2, 2, 2);
        gridBagConstraints.weightx = 1.0;
        jLabeltitle = new JLabel();
        jLabeltitle.setText("Název:");
        this.setSize(300, 200);
        this.setLayout(new GridBagLayout());
        this.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(EtchedBorder.LOWERED), "Organizace", TitledBorder.DEFAULT_JUSTIFICATION, TitledBorder.DEFAULT_POSITION, new Font("Dialog", Font.BOLD, 12), new Color(51, 51, 51)));
        this.add(jLabeltitle, gridBagConstraints1);
        this.add(getJTextFieldtitle(), gridBagConstraints);
        this.add(jLabelStreet, gridBagConstraints5);
        this.add(getJTextFieldStreet(), gridBagConstraints2);
        this.add(jLabelPsc, gridBagConstraints6);
        this.add(getJTextFieldPsc(), gridBagConstraints3);
        this.add(jLabelCity, gridBagConstraints7);
        this.add(getJTextFieldCity(), gridBagConstraints4);
        this.add(jLabelTel, gridBagConstraints11);
        this.add(getJTextFieldTel(), gridBagConstraints21);
    }

    /**
     * This method initializes jTextFieldtitle	
     * 	
     * @return javax.swing.JTextField	
     */
    private JTextField getJTextFieldtitle() {
        if (jTextFieldtitle == null) {
            jTextFieldtitle = new JTextField();
            jTextFieldtitle.addFocusListener(new java.awt.event.FocusAdapter() {
                public void focusLost(java.awt.event.FocusEvent e) {
                    org.setName(getJTextFieldtitle().getText());
                }
            });
        }
        return jTextFieldtitle;
    }

    /**
     * This method initializes jTextFieldStreet	
     * 	
     * @return javax.swing.JTextField	
     */
    private JTextField getJTextFieldStreet() {
        if (jTextFieldStreet == null) {
            jTextFieldStreet = new JTextField();
            jTextFieldStreet.addFocusListener(new java.awt.event.FocusAdapter() {
                public void focusLost(java.awt.event.FocusEvent e) {
                    org.getAddress().setStreet(getJTextFieldStreet().getText());
                }
            });
        }
        return jTextFieldStreet;
    }

    /**
     * This method initializes jTextFieldPsc	
     * 	
     * @return javax.swing.JTextField	
     */
    private JTextField getJTextFieldPsc() {
        if (jTextFieldPsc == null) {
            jTextFieldPsc = new JTextField();
            jTextFieldPsc.addFocusListener(new java.awt.event.FocusAdapter() {
                public void focusLost(java.awt.event.FocusEvent e) {
                    org.getAddress().setPsc(getJTextFieldPsc().getText());
                }
            });
        }
        return jTextFieldPsc;
    }

    /**
     * This method initializes jTextFieldCity	
     * 	
     * @return javax.swing.JTextField	
     */
    private JTextField getJTextFieldCity() {
        if (jTextFieldCity == null) {
            jTextFieldCity = new JTextField();
            jTextFieldCity.addFocusListener(new java.awt.event.FocusAdapter() {
                public void focusLost(java.awt.event.FocusEvent e) {
                    org.getAddress().setCity(getJTextFieldCity().getText());
                }
            });
        }
        return jTextFieldCity;
    }
    
    public void setTitle(String title){
        if(this.getBorder() instanceof TitledBorder){
            ((TitledBorder)this.getBorder()).setTitle(title);
        }
    }

    /**
     * This method initializes jTextFieldTel	
     * 	
     * @return javax.swing.JTextField	
     */
    private JTextField getJTextFieldTel() {
        if (jTextFieldTel == null) {
            jTextFieldTel = new JTextField();
            jTextFieldTel.addFocusListener(new java.awt.event.FocusAdapter() {
                public void focusLost(java.awt.event.FocusEvent e) {
                    org.setTel(getJTextFieldTel().getText());
                }
            });
        }
        return jTextFieldTel;
    }

    public Organization getOrg() {
        return org;
    }

    public void setOrg(Organization org) {
        this.org = org;
        upadateValues();
    }

    protected void upadateValues() {
        getJTextFieldtitle().setText(org.getName());
        getJTextFieldStreet().setText(org.getAddress().getStreet());
        getJTextFieldPsc().setText(org.getAddress().getPsc());
        getJTextFieldCity().setText(org.getAddress().getCity());
        getJTextFieldTel().setText(org.getTel());
    }
    
}

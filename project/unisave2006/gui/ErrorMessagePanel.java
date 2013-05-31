/*
 * Created on 1.9.2006
 *
 * Copyright (C) 2006
 * David Je�ek 
 * david.jezek@vsb.cz.
 * All rights reserved.
 */
package unisave2006.gui;

import java.awt.Component;
import java.awt.GridBagLayout;
import java.awt.Window;

import javax.swing.JPanel;
import java.awt.GridBagConstraints;

import javax.swing.JLabel;
import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JTextArea;
import javax.swing.JScrollPane;
import javax.swing.BorderFactory;
import javax.swing.border.EtchedBorder;
import java.awt.Dimension;

public class ErrorMessagePanel extends JPanel {

    private static final long serialVersionUID = 1L;
    private JButton jButtonDetails = null;
    private JTextArea jTextAreaDetails = null;
    protected boolean expanded;
    private JScrollPane jScrollPane = null;
    private JPanel jPanelMessage = null;
    private JLabel jLabelMessage = null;
    private JPanel jPanel = null;
    /**
     * This method initializes jButtonDetails	
     * 	
     * @return javax.swing.JButton	
     */
    private JButton getJButtonDetails() {
        if (jButtonDetails == null) {
            jButtonDetails = new JButton();
            jButtonDetails.setText("Podrobnosti");
            jButtonDetails.addActionListener(new java.awt.event.ActionListener() {
                public void actionPerformed(java.awt.event.ActionEvent e) {
                    setExpanded(!isExpanded());
                }
            });
        }
        return jButtonDetails;
    }

    /**
     * This method initializes jTextAreaDetails	
     * 	
     * @return javax.swing.JTextArea	
     */
    private JTextArea getJTextAreaDetails() {
        if (jTextAreaDetails == null) {
            jTextAreaDetails = new JTextArea();
            jTextAreaDetails.setEditable(false);
            jTextAreaDetails.setSize(new Dimension(100, 50));
            jTextAreaDetails.setEnabled(true);
        }
        return jTextAreaDetails;
    }

    /**
     * This method initializes jScrollPane	
     * 	
     * @return javax.swing.JScrollPane	
     */
    private JScrollPane getJScrollPane() {
        if (jScrollPane == null) {
            jScrollPane = new JScrollPane();
            jScrollPane.setBorder(BorderFactory.createEtchedBorder(EtchedBorder.LOWERED));
            jScrollPane.setPreferredSize(new Dimension(250, 100));
            jScrollPane.setViewportView(getJTextAreaDetails());
        }
        return jScrollPane;
    }

    /**
     * This method initializes jPanelMessage	
     * 	
     * @return javax.swing.JPanel	
     */
    private JPanel getJPanelMessage() {
        if (jPanelMessage == null) {
            GridBagConstraints gridBagConstraints = new GridBagConstraints();
            gridBagConstraints.anchor = GridBagConstraints.WEST;
            gridBagConstraints.weightx = 1.0D;
            jPanelMessage = new JPanel();
            jPanelMessage.setLayout(new GridBagLayout());
            jPanelMessage.add(getJLabelMessage(), gridBagConstraints);
        }
        return jPanelMessage;
    }

    protected JLabel getJLabelMessage(){
        if(jLabelMessage == null){
            jLabelMessage = new JLabel();
            jLabelMessage.setText("Zpr�va");
        }
        return jLabelMessage;
    }
    /**
     * This method initializes jPanel	
     * 	
     * @return javax.swing.JPanel	
     */
    private JPanel getJPanel() {
        if (jPanel == null) {
            GridBagConstraints gridBagConstraints2 = new GridBagConstraints();
            gridBagConstraints2.anchor = GridBagConstraints.NORTHEAST;
            gridBagConstraints2.gridx = -1;
            gridBagConstraints2.gridy = -1;
            gridBagConstraints2.weightx = 1.0D;
            gridBagConstraints2.gridwidth = 0;
            jPanel = new JPanel();
            jPanel.setLayout(new GridBagLayout());
            jPanel.setPreferredSize(new Dimension(250, 26));
            jPanel.add(getJButtonDetails(), gridBagConstraints2);
        }
        return jPanel;
    }

    /**
     * @param args
     */
    public static void main(String[] args) {
        JOptionPane.showMessageDialog(null,
                new ErrorMessagePanel("Todel je chyba.", "podrobnosti o chybě.\n in adal39m 58dku.\n in adal39m 58dku.\n in adal39m 58dku.\n in adal39m 58dku.\n in adal39m 58dku.\n in adal39m 58dku.\n in adal39m 58dku.\n in adal39m 58dku.\n in adal39m 58dku.\n in adal39m 58dku.\n in adal39m 58dku.\n in adal39m 58dku.\n in adal39m 58dku."),
                "Chyba", JOptionPane.ERROR_MESSAGE);

    }

    /**
     * This is the default constructor
     */
    public ErrorMessagePanel() {
        super();
        initialize();
    }

    /**
     * This method initializes this
     * 
     * @return void
     */
    private void initialize() {
        GridBagConstraints gridBagConstraints5 = new GridBagConstraints();
        gridBagConstraints5.fill = GridBagConstraints.HORIZONTAL;
        gridBagConstraints5.weightx = 1.0D;
        gridBagConstraints5.gridwidth = 0;
        GridBagConstraints gridBagConstraints4 = new GridBagConstraints();
        gridBagConstraints4.gridwidth = 0;
        gridBagConstraints4.weighty = 0.0D;
        gridBagConstraints4.weightx = 1.0D;
        gridBagConstraints4.fill = GridBagConstraints.HORIZONTAL;
        GridBagConstraints gridBagConstraints3 = new GridBagConstraints();
        gridBagConstraints3.fill = GridBagConstraints.BOTH;
        gridBagConstraints3.weighty = 1.0;
        gridBagConstraints3.weightx = 1.0;
        GridBagConstraints gridBagConstraints1 = new GridBagConstraints();
        gridBagConstraints1.fill = GridBagConstraints.BOTH;
        gridBagConstraints1.weighty = 1.0;
        gridBagConstraints1.weightx = 1.0;
        this.setSize(300, 200);
        this.setLayout(new GridBagLayout());
        this.add(getJPanelMessage(), gridBagConstraints4);
        this.add(getJPanel(), gridBagConstraints5);
        this.add(getJScrollPane(), gridBagConstraints3);
    }
    
    public void setExpanded(boolean b){
        expanded = b;
        getJScrollPane().setVisible(b);
        Component p = this;
        do{
            p = p.getParent();
            if(p instanceof Window){
                ((Window)p).pack();
                break;
            }
        }while(p != null);
    }
    
    public ErrorMessagePanel(String message, String details){
        this();
        getJLabelMessage().setText(message);
        //getJLabelMessage().
        getJTextAreaDetails().setText(details);
        setExpanded(false);
    }

    public boolean isExpanded() {
        return expanded;
    }

}

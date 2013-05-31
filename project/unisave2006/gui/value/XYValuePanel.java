/*
 * Created on 2.9.2006
 *
 * Copyright (C) 2006
 * David Je�ek 
 * david.jezek@vsb.cz.
 * All rights reserved.
 */
package unisave2006.gui.value;

import java.awt.GridBagLayout;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JLabel;
import java.awt.GridBagConstraints;
import javax.swing.JTextField;
import java.awt.Dimension;
import java.awt.Insets;
import javax.swing.BorderFactory;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;

import unisave2006.data.value.MeasurementEntry;
import unisave2006.data.value.Value;
import unisave2006.data.value.XYValue;

import java.awt.Font;
import java.awt.Color;

public class XYValuePanel extends JPanel  implements MeasurementEntityEditor{

    private static final long serialVersionUID = 1L;
    private JLabel jLabelIndex = null;
    private JTextField jTextFieldIndex = null;
    private ValuePanel xValuePanel = null;
    private ValuePanel yValuePanel = null;
    private JPanel jPanelX = null;
    private JPanel jPanelY = null;
    private JPanel jPanelIndex = null;
    private XYValue editedValue;

    /**
     * This method initializes jTextFieldIndex	
     * 	
     * @return javax.swing.JTextField	
     */
    private JTextField getJTextFieldIndex() {
        if (jTextFieldIndex == null) {
            jTextFieldIndex = new JTextField();
            jTextFieldIndex.setPreferredSize(new Dimension(50, 20));
        }
        return jTextFieldIndex;
    }

    /**
     * This method initializes xValuePanel	
     * 	
     * @return unisave2006.gui.value.ValuePanel	
     */
    private ValuePanel getXValuePanel() {
        if (xValuePanel == null) {
            xValuePanel = new ValuePanel();
            xValuePanel.setNoIndex();
        }
        return xValuePanel;
    }

    /**
     * This method initializes yValuePanel	
     * 	
     * @return unisave2006.gui.value.ValuePanel	
     */
    private ValuePanel getYValuePanel() {
        if (yValuePanel == null) {
            yValuePanel = new ValuePanel();
            yValuePanel.setNoIndex();
        }
        return yValuePanel;
    }

    /**
     * This method initializes jPanelX	
     * 	
     * @return javax.swing.JPanel	
     */
    private JPanel getJPanelX() {
        if (jPanelX == null) {
            GridBagConstraints gridBagConstraints1 = new GridBagConstraints();
            gridBagConstraints1.insets = new Insets(2, 2, 2, 2);
            gridBagConstraints1.gridx = -1;
            gridBagConstraints1.gridy = -1;
            gridBagConstraints1.weightx = 1.0D;
            gridBagConstraints1.fill = GridBagConstraints.BOTH;
            gridBagConstraints1.gridwidth = 0;
            jPanelX = new JPanel();
            jPanelX.setLayout(new GridBagLayout());
            jPanelX.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(EtchedBorder.RAISED), "Hodnota X", TitledBorder.DEFAULT_JUSTIFICATION, TitledBorder.DEFAULT_POSITION, new Font("Dialog", Font.BOLD, 12), new Color(51, 51, 51)));
            jPanelX.add(getXValuePanel(), gridBagConstraints1);
        }
        return jPanelX;
    }

    /**
     * This method initializes jPanelY	
     * 	
     * @return javax.swing.JPanel	
     */
    private JPanel getJPanelY() {
        if (jPanelY == null) {
            GridBagConstraints gridBagConstraints4 = new GridBagConstraints();
            gridBagConstraints4.insets = new Insets(2, 2, 2, 2);
            gridBagConstraints4.gridx = -1;
            gridBagConstraints4.gridy = -1;
            gridBagConstraints4.fill = GridBagConstraints.BOTH;
            gridBagConstraints4.weightx = 1.0D;
            gridBagConstraints4.gridwidth = 0;
            jPanelY = new JPanel();
            jPanelY.setLayout(new GridBagLayout());
            jPanelY.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(EtchedBorder.RAISED), "Hodnota Y", TitledBorder.DEFAULT_JUSTIFICATION, TitledBorder.DEFAULT_POSITION, new Font("Dialog", Font.BOLD, 12), new Color(51, 51, 51)));
            jPanelY.add(getYValuePanel(), gridBagConstraints4);
        }
        return jPanelY;
    }

    /**
     * This method initializes jPanelIndex	
     * 	
     * @return javax.swing.JPanel	
     */
    private JPanel getJPanelIndex() {
        if (jPanelIndex == null) {
            GridBagConstraints gridBagConstraints = new GridBagConstraints();
            gridBagConstraints.fill = GridBagConstraints.HORIZONTAL;
            gridBagConstraints.gridwidth = 0;
            gridBagConstraints.gridx = -1;
            gridBagConstraints.gridy = -1;
            gridBagConstraints.weightx = 1.0;
            gridBagConstraints.insets = new Insets(2, 2, 2, 2);
            GridBagConstraints gridBagConstraints5 = new GridBagConstraints();
            gridBagConstraints5.insets = new Insets(2, 2, 2, 2);
            gridBagConstraints5.gridy = -1;
            gridBagConstraints5.gridx = -1;
            jPanelIndex = new JPanel();
            jPanelIndex.setLayout(new GridBagLayout());
            jPanelIndex.add(jLabelIndex, gridBagConstraints5);
            jPanelIndex.add(getJTextFieldIndex(), gridBagConstraints);
        }
        return jPanelIndex;
    }

    /**
     * @param args
     */
    public static void main(String[] args) {
        // TODO Auto-generated method stub
        JFrame f = new JFrame();
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        f.getContentPane().add(new XYValuePanel());
        f.pack();
        f.setVisible(true);
    }

    /**
     * This is the default constructor
     */
    public XYValuePanel() {
        super();
        initialize();
    }

    /**
     * This method initializes this
     * 
     * @return void
     */
    private void initialize() {
        GridBagConstraints gridBagConstraints19 = new GridBagConstraints();
        gridBagConstraints19.weightx = 1.0D;
        gridBagConstraints19.fill = GridBagConstraints.BOTH;
        gridBagConstraints19.insets = new Insets(2, 2, 2, 2);
        GridBagConstraints gridBagConstraints18 = new GridBagConstraints();
        gridBagConstraints18.fill = GridBagConstraints.BOTH;
        gridBagConstraints18.insets = new Insets(2, 2, 2, 2);
        gridBagConstraints18.gridwidth = 0;
        gridBagConstraints18.weightx = 1.0D;
        GridBagConstraints gridBagConstraints17 = new GridBagConstraints();
        gridBagConstraints17.gridwidth = 0;
        gridBagConstraints17.weightx = 1.0D;
        gridBagConstraints17.insets = new Insets(2, 2, 2, 2);
        gridBagConstraints17.fill = GridBagConstraints.HORIZONTAL;
        jLabelIndex = new JLabel();
        jLabelIndex.setText("Index:");
        this.setSize(329, 288);
        this.setLayout(new GridBagLayout());
        this.add(getJPanelIndex(), gridBagConstraints17);
        this.add(getJPanelX(), gridBagConstraints18);
        this.add(getJPanelY(), gridBagConstraints19);
    }
    
    public void setXYValue(XYValue v){
        editedValue = v;
        getJTextFieldIndex().setText(Integer.toString(editedValue.getIndexXY()));
        getXValuePanel().setValue(editedValue.getXValue());
        getYValuePanel().setValue(editedValue.getYValue());
    }
    
    public XYValue getXYValue() throws MeasurementEntityConstructionException{
        int index = 0;
        if(editedValue == null)
            editedValue = new XYValue();
        try {
            index = Integer.parseInt(getJTextFieldIndex().getText());
        } catch (NumberFormatException e) {
            throw new MeasurementEntityConstructionException(String.format("Řetězec \"%s\" v poli indexu není celé číslo.", getJTextFieldIndex().getText()), getJTextFieldIndex());
        }
        Value x = getXValuePanel().getValue();
        Value y = getYValuePanel().getValue();
        
        editedValue.setXValue(x);
        editedValue.setYValue(y);
        editedValue.setIndexXY(index);
        return editedValue;
    }

    public MeasurementEntry getEntity() throws MeasurementEntityConstructionException{
        return getXYValue();
    }

    public JPanel getPanel() {
        return this;
    }

    public void setMeasurementEntity(MeasurementEntry e) {
        if(e instanceof XYValue)
            setXYValue((XYValue)e);
    }

    public void setEnableInserting(boolean b) {
        getJTextFieldIndex().setEnabled(b);
        getXValuePanel().setEnableInserting(b);
        getYValuePanel().setEnableInserting(b);
    }

    public void eraseValue() {
        getXValuePanel().eraseValue();
        getYValuePanel().eraseValue();
        if(editedValue != null){
            getJTextFieldIndex().setText(Integer.toString(editedValue.getIndexXY()+1));
        }
    }

    public void gainFocus() {
        getXValuePanel().gainFocus();
    }

}  //  @jve:decl-portIndex=0:visual-constraint="10,10"

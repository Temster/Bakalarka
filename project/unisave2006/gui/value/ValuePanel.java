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
import javax.swing.JPanel;
import javax.swing.JLabel;
import java.awt.GridBagConstraints;
import javax.swing.JTextField;
import java.awt.Dimension;

import unisave2006.data.value.MeasurementEntry;
import unisave2006.data.value.Value;
import unisave2006.gui.UnitSelectionPanel;
import java.awt.Insets;

public class ValuePanel extends JPanel  implements MeasurementEntityEditor{

    private static final long serialVersionUID = 1L;
    private JLabel jLabelIndex = null;
    private JTextField jTextFieldIndex = null;
    private JLabel jLabeValue = null;
    private JTextField jTextFieldValue = null;
    private JLabel jLabelunit = null;
    private UnitSelectionPanel unitSelectionPanel = null;
    private Value editedValue;  //  @jve:decl-portIndex=0:

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
     * This method initializes jTextFieldValue	
     * 	
     * @return javax.swing.JTextField	
     */
    private JTextField getJTextFieldValue() {
        if (jTextFieldValue == null) {
            jTextFieldValue = new JTextField();
            jTextFieldValue.setPreferredSize(new Dimension(100, 20));
        }
        return jTextFieldValue;
    }

    /**
     * This method initializes unitSelectionPanel	
     * 	
     * @return unisave2006.gui.UnitSelectionPanel	
     */
    private UnitSelectionPanel getUnitSelectionPanel() {
        if (unitSelectionPanel == null) {
            unitSelectionPanel = new UnitSelectionPanel();
        }
        return unitSelectionPanel;
    }

    /**
     * @param args
     */
    public static void main(String[] args) {
        // TODO Auto-generated method stub

    }

    /**
     * This is the default constructor
     */
    public ValuePanel() {
        super();
        initialize();
    }

    /**
     * This method initializes this
     * 
     * @return void
     */
    private void initialize() {
        GridBagConstraints gridBagConstraints11 = new GridBagConstraints();
        gridBagConstraints11.anchor = GridBagConstraints.EAST;
        gridBagConstraints11.insets = new Insets(2, 2, 2, 2);
        GridBagConstraints gridBagConstraints10 = new GridBagConstraints();
        gridBagConstraints10.anchor = GridBagConstraints.EAST;
        gridBagConstraints10.insets = new Insets(2, 2, 2, 2);
        GridBagConstraints gridBagConstraints9 = new GridBagConstraints();
        gridBagConstraints9.anchor = GridBagConstraints.EAST;
        gridBagConstraints9.insets = new Insets(2, 2, 2, 2);
        GridBagConstraints gridBagConstraints8 = new GridBagConstraints();
        gridBagConstraints8.gridwidth = 0;
        gridBagConstraints8.insets = new Insets(2, 2, 2, 2);
        jLabelunit = new JLabel();
        jLabelunit.setText("Jednotka:");
        GridBagConstraints gridBagConstraints7 = new GridBagConstraints();
        gridBagConstraints7.fill = GridBagConstraints.HORIZONTAL;
        gridBagConstraints7.gridwidth = 0;
        gridBagConstraints7.insets = new Insets(2, 2, 2, 2);
        gridBagConstraints7.weightx = 1.0;
        jLabeValue = new JLabel();
        jLabeValue.setText("Hodnota:");
        GridBagConstraints gridBagConstraints = new GridBagConstraints();
        gridBagConstraints.fill = GridBagConstraints.HORIZONTAL;
        gridBagConstraints.gridwidth = 0;
        gridBagConstraints.insets = new Insets(2, 2, 2, 2);
        gridBagConstraints.weightx = 1.0;
        jLabelIndex = new JLabel();
        jLabelIndex.setText("Index:");
        this.setSize(300, 200);
        this.setLayout(new GridBagLayout());
        this.add(jLabelIndex, gridBagConstraints9);
        this.add(getJTextFieldIndex(), gridBagConstraints);
        this.add(jLabeValue, gridBagConstraints10);
        this.add(getJTextFieldValue(), gridBagConstraints7);
        this.add(jLabelunit, gridBagConstraints11);
        this.add(getUnitSelectionPanel(), gridBagConstraints8);
    }

    public void setValue(Value v){
        editedValue = v;
        if(editedValue == null){
            getJTextFieldIndex().setText("");
            getJTextFieldValue().setText("");
        }
        else{
            getJTextFieldIndex().setText(Integer.toString(editedValue.getIndexVal()));
            //tady se do dialogu vklada opravdova hodnota ne formatovana treba na 2 desetinna mista
            getJTextFieldValue().setText(Double.toString(editedValue.getValueVal()));
            getUnitSelectionPanel().setSelectedUnit(editedValue.getUnit());
        }
    }
    
    public Value getValue() throws MeasurementEntityConstructionException{
        int index = 0;
        double value = 0;
        
        if(editedValue == null){
            editedValue = new Value();
        }
        if(getJTextFieldIndex().isVisible()){
            try {
                index = Integer.parseInt(getJTextFieldIndex().getText());
            } catch (NumberFormatException e) {
                throw new MeasurementEntityConstructionException(String.format("Řetězec \"%s\" v poli indexu není celé číslo.", getJTextFieldIndex().getText()), getJTextFieldIndex());
            }
        }
        try {
            String text = getJTextFieldValue().getText();
            text = text.replace(',', '.');
            value = Double.parseDouble(text);
        } catch (NumberFormatException e) {
            throw new MeasurementEntityConstructionException(String.format("Řetězec \"%s\" v poli hodnota není desetinné číslo.", getJTextFieldValue().getText()), getJTextFieldValue());
        }
        editedValue.setUnit(getUnitSelectionPanel().getSelectedUnit());
        editedValue.setIndexVal(index);
        editedValue.setValueVal(value);
        return editedValue;
    }
    
    public void setNoIndex(){
        getJTextFieldIndex().setVisible(false);
        if(jLabelIndex != null)
            jLabelIndex.setVisible(false);
    }

    public MeasurementEntry getEntity() throws MeasurementEntityConstructionException{
        return getValue();
    }

    public JPanel getPanel() {
        return this;
    }

    public void setMeasurementEntity(MeasurementEntry e) {
        if(e instanceof Value)
            setValue((Value)e);
    }

    public void setEnableInserting(boolean b) {
        getJTextFieldIndex().setEnabled(b);
        getJTextFieldValue().setEnabled(b);
        getUnitSelectionPanel().setEnableInserting(b);
        
    }

    public void eraseValue() {
        getJTextFieldValue().setText("");
        if(editedValue != null)
            getJTextFieldIndex().setText(Integer.toString(editedValue.getIndexVal()+1));
    }

    public void gainFocus() {
        getJTextFieldValue().requestFocus();
    }
}

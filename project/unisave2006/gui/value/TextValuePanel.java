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
import javax.swing.JComboBox;
import javax.swing.JTextField;

import unisave2006.data.value.MeasurementEntry;
import unisave2006.data.value.Text;

import java.awt.Dimension;
import java.awt.Insets;

public class TextValuePanel extends JPanel implements MeasurementEntityEditor{

    private static final long serialVersionUID = 1L;
    private JLabel jLabelType = null;
    private JComboBox jComboBoxType = null;
    private JLabel jLabelText = null;
    private JTextField jTextFieldText = null;
    private Text valueText;  //  @jve:decl-portIndex=0:

    /**
     * This is the default constructor
     */
    public TextValuePanel(){
        super();
        initialize();
    }

    /**
     * This method initializes this
     * 
     * @return void
     */
    private void initialize() {
        GridBagConstraints gridBagConstraints13 = new GridBagConstraints();
        gridBagConstraints13.insets = new Insets(2, 2, 2, 2);
        gridBagConstraints13.anchor = GridBagConstraints.EAST;
        GridBagConstraints gridBagConstraints12 = new GridBagConstraints();
        gridBagConstraints12.insets = new Insets(2, 2, 2, 2);
        gridBagConstraints12.anchor = GridBagConstraints.EAST;
        GridBagConstraints gridBagConstraints6 = new GridBagConstraints();
        gridBagConstraints6.fill = GridBagConstraints.HORIZONTAL;
        gridBagConstraints6.insets = new Insets(2, 2, 2, 2);
        gridBagConstraints6.weightx = 1.0;
        jLabelText = new JLabel();
        jLabelText.setText("Text:");
        GridBagConstraints gridBagConstraints = new GridBagConstraints();
        gridBagConstraints.fill = GridBagConstraints.HORIZONTAL;
        gridBagConstraints.gridwidth = 0;
        gridBagConstraints.insets = new Insets(2, 2, 2, 2);
        gridBagConstraints.weightx = 1.0;
        jLabelType = new JLabel();
        jLabelType.setText("Typ:");
        this.setSize(300, 200);
        this.setLayout(new GridBagLayout());
        this.add(jLabelType, gridBagConstraints13);
        this.add(getJComboBoxType(), gridBagConstraints);
        this.add(jLabelText, gridBagConstraints12);
        this.add(getJTextFieldText(), gridBagConstraints6);
    }

    /**
     * This method initializes jComboBoxType	
     * 	
     * @return javax.swing.JComboBox	
     */
    private JComboBox getJComboBoxType() {
        if (jComboBoxType == null) {
            jComboBoxType = new JComboBox();
            jComboBoxType.addItem("TEXT");
            jComboBoxType.addItem("INFRMACE");
            jComboBoxType.addItem("VÝSTRAHA");
            jComboBoxType.addItem("CHYBA");
        }
        return jComboBoxType;
    }

    /**
     * This method initializes jTextFieldText	
     * 	
     * @return javax.swing.JTextField	
     */
    private JTextField getJTextFieldText() {
        if (jTextFieldText == null) {
            jTextFieldText = new JTextField();
            jTextFieldText.setPreferredSize(new Dimension(200, 20));
        }
        return jTextFieldText;
    }
    
    public void setTextValue(Text t){
        valueText = t;
        getJComboBoxType().setSelectedIndex(valueText.getType());
        getJTextFieldText().setText(valueText.getTextTxt());
    }
    
    public Text getTextValue(){
        if(valueText == null)
            valueText = new Text();
        valueText.setType(jComboBoxType.getSelectedIndex());
        valueText.setTextTxt(getJTextFieldText().getText());
        return valueText;
    }
    public MeasurementEntry getEntity(){
        return getTextValue();
    }

    public JPanel getPanel() {
        return this;
    }

    public void setMeasurementEntity(MeasurementEntry e) {
        if(e instanceof Text)
            setTextValue((Text)e);
    }

    public void setEnableInserting(boolean b) {
        getJComboBoxType().setEnabled(b);
        getJTextFieldText().setEnabled(b);
        
    }

    public void eraseValue() {
        getJTextFieldText().setText("");
    }

    public void gainFocus() {
        getJTextFieldText().requestFocus();
        
    }

}

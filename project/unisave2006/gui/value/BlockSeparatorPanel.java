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

import unisave2006.data.value.BlockSeparator;
import unisave2006.data.value.MeasurementEntry;

public class BlockSeparatorPanel extends JPanel implements MeasurementEntityEditor{

    private static final long serialVersionUID = 1L;

    /**
     * @param args
     */
    public static void main(String[] args) {
        // TODO Auto-generated method stub

    }

    /**
     * This is the default constructor
     */
    public BlockSeparatorPanel() {
        super();
        initialize();
    }

    /**
     * This method initializes this
     * 
     * @return void
     */
    private void initialize() {
        this.setSize(300, 200);
        this.setLayout(new GridBagLayout());
    }
    public MeasurementEntry getEntity()  throws MeasurementEntityConstructionException{
        return getBlockSeparator();
    }

    public BlockSeparator getBlockSeparator() throws MeasurementEntityConstructionException {
        // TODO Auto-generated method stub
        return null;
    }

    public JPanel getPanel() {
        return this;
    }

    public void setMeasurementEntity(MeasurementEntry e) {
        if(e instanceof BlockSeparator)
            setBlockSeparator((BlockSeparator)e);
    }

    public void setBlockSeparator(BlockSeparator separator) {
        // TODO Auto-generated method stub
        
    }

    public void setEnableInserting(boolean b) {
        // TODO Auto-generated method stub
        
    }

    public void eraseValue() {
        // TODO Auto-generated method stub
        
    }

    public void gainFocus() {
        // TODO Auto-generated method stub
        
    }

}

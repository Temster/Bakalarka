/*
 * Created on 26.9.2006
 *
 * Copyright (C) 2006
 * David Je�ek 
 * david.jezek@vsb.cz.
 * All rights reserved.
 */
package unisave2006.gui.action;

import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.ImageIcon;
import javax.swing.KeyStroke;

public class AbstractActionUnitConversion extends AbstractAction {

    /**
     * 
     */
    private static final long serialVersionUID = 637248305403133341L;

    public AbstractActionUnitConversion() {
        super("Převod jednotek...");
        setEnabled(false);
        putValue(Action.SHORT_DESCRIPTION, "Umožní převádět hodnoty v tabulce na jiné jednotky.");
        putValue(Action.MNEMONIC_KEY, KeyEvent.VK_P);
        putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke("control J"));
        putValue(Action.ACTION_COMMAND_KEY, "UnitConversion");
        putValue(Action.DISPLAYED_MNEMONIC_INDEX_KEY, 0);
        putValue(Action.LONG_DESCRIPTION, "Zobrazí dialog ve kterém lze převádět vybrané nebo všechny hodnot v tabulce na jiné jednotky.");
        putValue(Action.LARGE_ICON_KEY,
                new ImageIcon(getClass().getResource("/resource/icons/unitConversion.gif")));
        putValue(Action.SMALL_ICON, new ImageIcon(getClass().getResource("/resource/icons/unitConversion_small.gif")));
    }

    public void actionPerformed(ActionEvent e) {
    }

}

/*
 * Created on 25.9.2006
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

public class AbstractActionCut extends AbstractAction{

    private static final long serialVersionUID = -5639613482235846188L;

    public AbstractActionCut() {
        super("Vyjmout...");
        setEnabled(false);
        putValue(Action.SHORT_DESCRIPTION, "Vyjme označené hodnoty do schránky.");
        putValue(Action.MNEMONIC_KEY, KeyEvent.VK_V);
        putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke("control X"));
        putValue(Action.ACTION_COMMAND_KEY, "Cut");
        putValue(Action.DISPLAYED_MNEMONIC_INDEX_KEY, 0);
        putValue(Action.LARGE_ICON_KEY,
                new ImageIcon(getClass().getResource("/resource/icons/cut.gif")));
        putValue(Action.LONG_DESCRIPTION, "Vyjme označené hodnoty z tabulky a vloží je do systémové schránky.");
        putValue(Action.SMALL_ICON, new ImageIcon(getClass().getResource("/resource/icons/cut_small.gif")));
    }

    public void actionPerformed(ActionEvent e) {
    }

}

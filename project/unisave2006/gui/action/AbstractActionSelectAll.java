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
import javax.swing.KeyStroke;


public class AbstractActionSelectAll extends AbstractAction {

    /**
     * 
     */
    private static final long serialVersionUID = -6820611450311464508L;

    public AbstractActionSelectAll() {
        super("Vybrat vše");
        setEnabled(true);
        putValue(Action.SHORT_DESCRIPTION, "Vybere všechny položky v tabulce.");
        putValue(Action.MNEMONIC_KEY, KeyEvent.VK_A);
        putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke("control A"));
        putValue(Action.ACTION_COMMAND_KEY, "SelectAll");
        putValue(Action.DISPLAYED_MNEMONIC_INDEX_KEY, 4);
        putValue(Action.LONG_DESCRIPTION, "Vymaže označené položky z tabulky naměřených hodnot.");
    }

    public void actionPerformed(ActionEvent e) {
    }

}

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

public class AbstractActionDeleteSelected extends AbstractAction {
    private static final long serialVersionUID = 672506255616343222L;

    public AbstractActionDeleteSelected() {
        super("Vymazat vybrané");
        setEnabled(false);
        putValue(Action.SHORT_DESCRIPTION, "Vymaže vybrané položky z tabulky.");
        putValue(Action.MNEMONIC_KEY, KeyEvent.VK_M);
        putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke("DELETE"));
        putValue(Action.ACTION_COMMAND_KEY, "Delete");
        putValue(Action.DISPLAYED_MNEMONIC_INDEX_KEY, 2);
        putValue(Action.LARGE_ICON_KEY,
                new ImageIcon(getClass().getResource("/resource/icons/delete.gif")));
        putValue(Action.LONG_DESCRIPTION, "Vymaže označené položky z tabulky naměřených hodnot.");
        putValue(Action.SMALL_ICON, new ImageIcon(getClass().getResource("/resource/icons/delete_small.gif")));
    }

    public void actionPerformed(ActionEvent e) {
    }

}

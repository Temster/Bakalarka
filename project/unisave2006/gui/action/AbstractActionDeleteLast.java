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

public class AbstractActionDeleteLast extends AbstractAction{
    private static final long serialVersionUID = 7996399792575795731L;
    public AbstractActionDeleteLast(){
        super("Smazat poslední");
        setEnabled(false);
        putValue(Action.SHORT_DESCRIPTION, "Smaže poslední položku v tabulce.");
        putValue(Action.MNEMONIC_KEY, KeyEvent.VK_P);
        putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke("BACK_SPACE"));
        putValue(Action.ACTION_COMMAND_KEY, "DeleteLast");
        putValue(Action.DISPLAYED_MNEMONIC_INDEX_KEY, 7);
        putValue(Action.LARGE_ICON_KEY,
                new ImageIcon(getClass().getResource("/resource/icons/deleteLast.gif")));
        putValue(Action.LONG_DESCRIPTION, "Smaže poslední položku v tabulce.");
        putValue(Action.SMALL_ICON, new ImageIcon(getClass().getResource("/resource/icons/deleteLast_small.gif")));
    }
    public void actionPerformed(ActionEvent e) {
    }

}

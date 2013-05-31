/*
 * Created on 27.9.2006
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

public class AbstractActionEdit extends AbstractAction {

    /**
     * 
     */
    private static final long serialVersionUID = 4276574733882184417L;

    public AbstractActionEdit() {
        super("Editovat...");
        setEnabled(false);
        putValue(Action.SHORT_DESCRIPTION, "Umožní edtitovat položku z tabulky naměřených hodnot.");
        putValue(Action.MNEMONIC_KEY, KeyEvent.VK_E);
        putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke("control E"));
        putValue(Action.ACTION_COMMAND_KEY, "Edit");
        putValue(Action.DISPLAYED_MNEMONIC_INDEX_KEY, 0);
        putValue(Action.LONG_DESCRIPTION, "Zobrazí dialog ve kterém lze editovat vybranou hodnotu z tabulky naměřených hodnot.");
/*        putValue(Action.LARGE_ICON_KEY,
                new ImageIcon(getClass().getResource("/resource/icons/unitConversion.gif")));
        putValue(Action.SMALL_ICON, new ImageIcon(getClass().getResource("/resource/icons/unitConversion_small.gif")));
*/    }

    public void actionPerformed(ActionEvent e) {
    }

}

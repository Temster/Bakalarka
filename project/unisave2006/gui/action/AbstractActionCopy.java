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

public class AbstractActionCopy extends AbstractAction {

    /**
     * 
     */
    private static final long serialVersionUID = 3537038781366498304L;

    public AbstractActionCopy() {
        super("Kopírovat");
        setEnabled(false);
        putValue(Action.SHORT_DESCRIPTION, "Zkopíruje označené hodnoty do schránky.");
        putValue(Action.MNEMONIC_KEY, KeyEvent.VK_K);
        putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke("control C"));
        putValue(Action.ACTION_COMMAND_KEY, "CopyEntities");
        putValue(Action.DISPLAYED_MNEMONIC_INDEX_KEY, 0);
        putValue(Action.LARGE_ICON_KEY,
                new ImageIcon(getClass().getResource("/resource/icons/copy.gif")));
        putValue(Action.LONG_DESCRIPTION, "Zkopíruje vybrané hodnoty měření do schránky.");
        putValue(Action.SMALL_ICON, new ImageIcon(getClass().getResource("/resource/icons/copy_small.gif")));
    }

    public void actionPerformed(ActionEvent e) {
    }

}

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

public class AbstractActionViewGraph extends AbstractAction {

    /**
     * 
     */
    private static final long serialVersionUID = -5442301238581224333L;

    public AbstractActionViewGraph() {
        super("Graf");
        setEnabled(false);
        putValue(Action.SHORT_DESCRIPTION, "Zobrazí naměřené hodnoty v grafu.");
        putValue(Action.MNEMONIC_KEY, KeyEvent.VK_G);
        putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke("control G"));
        putValue(Action.ACTION_COMMAND_KEY, "ViewGraph");
        putValue(Action.DISPLAYED_MNEMONIC_INDEX_KEY, 0);
        putValue(Action.LARGE_ICON_KEY,
                new ImageIcon(getClass().getResource("/resource/icons/graph.gif")));
        putValue(Action.LONG_DESCRIPTION, "Zobrazí naměřené hodnoty v grafu.");
        putValue(Action.SMALL_ICON, new ImageIcon(getClass().getResource("/resource/icons/graph_small.gif")));
    }

    public void actionPerformed(ActionEvent e) {
    }

}

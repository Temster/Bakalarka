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

public class AbstractActionCreateProtocol extends AbstractAction {
    private static final long serialVersionUID = 2174655661837355098L;
    public AbstractActionCreateProtocol(){
        super("Vytvořit...");
        setEnabled(false);
        putValue(Action.SHORT_DESCRIPTION, "Vytvoří protokol o měření.");
        putValue(Action.MNEMONIC_KEY, KeyEvent.VK_V);
        putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke("control P"));
        putValue(Action.ACTION_COMMAND_KEY, "CrteateProtocol");
        putValue(Action.DISPLAYED_MNEMONIC_INDEX_KEY, 0);
        putValue(Action.LARGE_ICON_KEY,
                new ImageIcon(getClass().getResource("/resource/icons/createProtocol.gif")));
        putValue(Action.LONG_DESCRIPTION, "Vytvoří protokol o měření na základě naměřených hodnot.");
        putValue(Action.SMALL_ICON, new ImageIcon(getClass().getResource("/resource/icons/createProtocol_small.gif")));
    }
    public void actionPerformed(ActionEvent e) {
    }
}

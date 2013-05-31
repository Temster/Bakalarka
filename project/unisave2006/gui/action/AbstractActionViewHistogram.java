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

public class AbstractActionViewHistogram extends AbstractAction {

    /**
     * 
     */
    private static final long serialVersionUID = 9189765056204618477L;

    public AbstractActionViewHistogram() {
        super("Histogram");
        setEnabled(false);
        putValue(Action.SHORT_DESCRIPTION, "Zobrazí histogram pro naměřené hodnoty.");
        putValue(Action.MNEMONIC_KEY, KeyEvent.VK_H);
        putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke("control H"));
        putValue(Action.ACTION_COMMAND_KEY, "ViewHistogram");
        putValue(Action.DISPLAYED_MNEMONIC_INDEX_KEY, 0);
        putValue(Action.LARGE_ICON_KEY,
                new ImageIcon(getClass().getResource("/resource/icons/histogram.gif")));
        putValue(Action.LONG_DESCRIPTION, "Zobrazí četnost rozložení naměřených hodnot v jednotlivých intervalech v grafu který je standartní označován jako histogram.");
        putValue(Action.SMALL_ICON, new ImageIcon(getClass().getResource("/resource/icons/histogram_small.gif")));
    }

    public void actionPerformed(ActionEvent e) {
    }

}

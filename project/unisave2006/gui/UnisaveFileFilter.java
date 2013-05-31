/*
 * Created on 22.8.2006
 *
 * Copyright (C) 2006
 * David Ježek 
 * david.jezek@vsb.cz.
 * All rights reserved.
 */
package unisave2006.gui;

import java.io.File;

import javax.swing.filechooser.FileFilter;


public class UnisaveFileFilter extends FileFilter {

    @Override
    public boolean accept(File f) {
        if(f.isDirectory())
            return true;
        if(f.isFile()){
            if(f.getName().endsWith(".unisave"))
                return true;
        }
        return false;
    }

    @Override
    public String getDescription() {
        return "Soubory programu UniSave 2006 (*.unisave)";
    }

}

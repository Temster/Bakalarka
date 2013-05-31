/*
 * Created on Jul 12, 2006
 *
 * Copyright (C) 2006
 * David Ježek 
 * david.jezek@vsb.cz.
 * All rights reserved.
 */
package unisave2006;



/**
 * @author David Ježek
 *
 * Copyright (C) 2006
 * David Ježek 
 * david.jezek@vsb.cz.
 * All rights reserved.
 * This class was created as part of project UniSave2006Implementation.
 */
public class Run {

    private static void createAndShowGUI() {

        //Create and set up the window.
        unisave2006.gui.MainFrame frame = new unisave2006.gui.MainFrame();
        
/*        UIManager.LookAndFeelInfo[] lafInfo = UIManager.getInstalledLookAndFeels();
        for(int i=0; i<lafInfo.length; i++)
            System.out.println(lafInfo[i]);
        try {
            UIManager.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel");
        } catch (ClassNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (InstantiationException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (UnsupportedLookAndFeelException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        //Make sure we have nice window decorations.
        JFrame.setDefaultLookAndFeelDecorated(true);
*/
        frame.pack();
        frame.requestFocus();
        frame.setVisible(true);
    }

    public static void main(String[] args) {
        //Schedule a job for the event-dispatching thread:
        //creating and showing this application's GUI.
        javax.swing.SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                createAndShowGUI();
            }
        });
    }
}

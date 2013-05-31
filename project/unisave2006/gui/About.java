/*
 * Created on 23.9.2006
 *
 * Copyright (C) 2006
 * David Je�ek 
 * david.jezek@vsb.cz.
 * All rights reserved.
 */
package unisave2006.gui;

import javax.swing.SwingUtilities;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.JButton;
import javax.swing.WindowConstants;

import java.awt.Frame;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.SwingConstants;

public class About extends JDialog {

    public About(Frame owner, boolean modal) {
        super(owner, modal);
        initialize();
    }

    private static final long serialVersionUID = 1L;

    private JPanel jContentPane = null;

    private JButton jButtonOk = null;


    private JLabel jLabelUnimetraLogo = null;

    private JLabel jLabelJezekLogo = null;

    private JLabel jLabelUnisaveLogo = null;

	private JPanel jPanelAutor = null;

	private JLabel jLabelAutor = null;

	private JLabel jLabelJezek = null;

	private JLabel jLabelEmail = null;

	private JLabel jLabelEmail1 = null;

	private JPanel jPanelUnimetra = null;

	private JLabel jLabelUnimetraName = null;

	private JLabel jLabelWeb = null;

    /**
     * This method initializes jButtonOk	
     * 	
     * @return javax.swing.JButton	
     */
    private JButton getJButtonOk() {
        if (jButtonOk == null) {
            jButtonOk = new JButton();
            jButtonOk.setText("Ok");
            jButtonOk.setSelected(true);
            jButtonOk.addActionListener(new java.awt.event.ActionListener() {
                public void actionPerformed(java.awt.event.ActionEvent e) {
                    dispose();
                }
            });
        }
        return jButtonOk;
    }

    /**
	 * This method initializes jPanelAutor	
	 * 	
	 * @return javax.swing.JPanel	
	 */
	private JPanel getJPanelAutor() {
		if (jPanelAutor == null) {
			GridBagConstraints gridBagConstraints9 = new GridBagConstraints();
			gridBagConstraints9.insets = new Insets(2, 2, 2, 2);
			GridBagConstraints gridBagConstraints5 = new GridBagConstraints();
			gridBagConstraints5.gridwidth = 0;
			gridBagConstraints5.insets = new Insets(2, 2, 2, 2);
			jLabelEmail1 = new JLabel();
			jLabelEmail1.setText("davidjezek@seznam.cz");
			GridBagConstraints gridBagConstraints4 = new GridBagConstraints();
			gridBagConstraints4.gridx = -1;
			gridBagConstraints4.insets = new Insets(2, 2, 2, 2);
			gridBagConstraints4.gridy = -1;
			GridBagConstraints gridBagConstraints3 = new GridBagConstraints();
			gridBagConstraints3.gridwidth = 0;
			gridBagConstraints3.insets = new Insets(2, 2, 2, 2);
			jLabelJezek = new JLabel();
			jLabelJezek.setText("David Ježek");
			jLabelAutor = new JLabel();
			jLabelAutor.setText("Autor:");
			GridBagConstraints gridBagConstraints2 = new GridBagConstraints();
			gridBagConstraints2.gridx = -1;
			gridBagConstraints2.gridwidth = 0;
			gridBagConstraints2.insets = new Insets(2, 2, 2, 2);
			gridBagConstraints2.gridy = -1;
			jPanelAutor = new JPanel();
			jPanelAutor.setLayout(new GridBagLayout());
			jPanelAutor.add(jLabelAutor, gridBagConstraints9);
			jPanelAutor.add(jLabelJezek, gridBagConstraints3);
			jPanelAutor.add(jLabelEmail, gridBagConstraints4);
			jPanelAutor.add(jLabelEmail1, gridBagConstraints5);
			jPanelAutor.add(jLabelJezekLogo, gridBagConstraints2);
		}
		return jPanelAutor;
	}

	/**
	 * This method initializes jPanelUnimetra	
	 * 	
	 * @return javax.swing.JPanel	
	 */
	private JPanel getJPanelUnimetra() {
		if (jPanelUnimetra == null) {
			GridBagConstraints gridBagConstraints8 = new GridBagConstraints();
			gridBagConstraints8.gridwidth = 0;
			gridBagConstraints8.insets = new Insets(2, 2, 2, 2);
			GridBagConstraints gridBagConstraints7 = new GridBagConstraints();
			gridBagConstraints7.gridwidth = 0;
			gridBagConstraints7.gridy = -1;
			gridBagConstraints7.insets = new Insets(2, 2, 2, 2);
			gridBagConstraints7.gridx = -1;
			GridBagConstraints gridBagConstraints6 = new GridBagConstraints();
			gridBagConstraints6.gridwidth = 0;
			gridBagConstraints6.insets = new Insets(2, 2, 2, 2);
			jLabelWeb = new JLabel();
			jLabelWeb.setText("www.unimetra.cz");
			jLabelUnimetraName = new JLabel();
			jLabelUnimetraName.setText("Ve spolupráci s firmou UNIMETRA");
			jPanelUnimetra = new JPanel();
			jPanelUnimetra.setLayout(new GridBagLayout());
			jPanelUnimetra.add(jLabelUnimetraName, gridBagConstraints6);
			jPanelUnimetra.add(jLabelWeb, gridBagConstraints8);
			jPanelUnimetra.add(jLabelUnimetraLogo, gridBagConstraints7);
		}
		return jPanelUnimetra;
	}

	/**
     * @param args
     */
    public static void main(String[] args) {
        // TODO Auto-generated method stub

        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                About thisClass = new About();
                thisClass.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
                thisClass.setVisible(true);
            }
        });
    }

    /**
     * This is the default constructor
     */
    public About() {
        this(null, true);
    }

    /**
     * This method initializes this
     * 
     * @return void
     */
    private void initialize() {
        this.setSize(314, 329);
        this.setResizable(false);
        this.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        this.setContentPane(getJContentPane());
        this.setTitle("O Aplikaci UniSave 2006");
    }

    /**
     * This method initializes jContentPane
     * 
     * @return javax.swing.JPanel
     */
    private JPanel getJContentPane() {
        if (jContentPane == null) {
            GridBagConstraints gridBagConstraints31 = new GridBagConstraints();
            gridBagConstraints31.gridwidth = 0;
            GridBagConstraints gridBagConstraints21 = new GridBagConstraints();
            gridBagConstraints21.insets = new Insets(2, 10, 10, 10);
            gridBagConstraints21.gridwidth = 0;
            jLabelEmail = new JLabel();
            jLabelEmail.setText("Email:");
            GridBagConstraints gridBagConstraints11 = new GridBagConstraints();
            gridBagConstraints11.gridwidth = 0;
            gridBagConstraints11.insets = new Insets(10, 10, 2, 10);
            gridBagConstraints11.fill = GridBagConstraints.NONE;
            jLabelUnisaveLogo = new JLabel();
            jLabelUnisaveLogo.setText("Verze 1.0");
            jLabelUnisaveLogo.setHorizontalTextPosition(SwingConstants.CENTER);
            jLabelUnisaveLogo.setVerticalTextPosition(SwingConstants.BOTTOM);
            jLabelUnisaveLogo.setIcon(new ImageIcon(getClass().getResource("/resource/pictures/UniSaveLogoSmall.gif")));
            jLabelJezekLogo = new JLabel();
            jLabelJezekLogo.setText("");
            jLabelJezekLogo.setIcon(new ImageIcon(getClass().getResource("/resource/pictures/JezekLogoSmall.gif")));
            jLabelUnimetraLogo = new JLabel();
            jLabelUnimetraLogo.setText("");
            jLabelUnimetraLogo.setIcon(new ImageIcon(getClass().getResource("/resource/pictures/UnimetraLogoSmall.gif")));
            GridBagConstraints gridBagConstraints1 = new GridBagConstraints();
            gridBagConstraints1.insets = new Insets(0, 0, 0, 0);
            gridBagConstraints1.gridy = -1;
            gridBagConstraints1.ipady = 0;
            gridBagConstraints1.gridx = -1;
            GridBagConstraints gridBagConstraints = new GridBagConstraints();
            gridBagConstraints.gridx = -1;
            gridBagConstraints.ipadx = 0;
            gridBagConstraints.gridwidth = 0;
            gridBagConstraints.insets = new Insets(10, 2, 10, 2);
            gridBagConstraints.gridy = -1;
            jContentPane = new JPanel();
            jContentPane.setLayout(new GridBagLayout());
            jContentPane.add(jLabelUnisaveLogo, gridBagConstraints11);
            jContentPane.add(getJPanelAutor(), gridBagConstraints21);
            jContentPane.add(getJPanelUnimetra(), gridBagConstraints31);
            jContentPane.add(getJButtonOk(), gridBagConstraints);
        }
        return jContentPane;
    }

}  //  @jve:decl-portIndex=0:visual-constraint="85,7"

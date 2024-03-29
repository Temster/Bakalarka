package unisave2006;

/*
 * MonthCalendar.java
 *
 * Created on 26. �ervenec 2003, 10:10
 */

import javax.swing.*;
import javax.swing.border.*;
import java.awt.*;
import java.awt.event.*;
import java.text.DateFormat;
import java.util.*;

/**
 *
 * @author  David Jezek
 */
public class MonthCalendar extends javax.swing.JPanel implements
        ListCellRenderer {
    /**
     * 
     */
    private static final long serialVersionUID = 3265189070492336166L;

    protected int week_shown = 6;

    protected JLabel days[][] = new JLabel[week_shown][7];

    protected Calendar dates[][] = new Calendar[week_shown][7];

    protected Calendar selected = Calendar.getInstance();

    protected Calendar dnes = Calendar.getInstance();

    protected Calendar temp = Calendar.getInstance();

    protected Border todayBorder = new LineBorder(Color.RED);

    public final static String[] mesice = { "Leden", "�nor", "B�ezen", "Duben",
            "Kv�ten", "�erven", "�ervenec", "Srpen", "Z���", "��jen",
            "Listopad", "Prosinec" };

    protected boolean ok = true;

    protected Vector<ActionListener> actionListeners = new Vector<ActionListener>();

    public void addActionListener(ActionListener l) {
        actionListeners.add(l);
    }

    public void removeActionListener(ActionListener l) {
        actionListeners.remove(l);
    }

    protected void fireActionPerformed() {
        ActionEvent a = new ActionEvent(this, ActionEvent.ACTION_PERFORMED,
                "dateSelected");
        for (ActionListener al : actionListeners) {
            al.actionPerformed(a);
        }
    }

    /** Creates new form MonthCalendar */
    public MonthCalendar() {
        super();
        //this.setUndecorated(false);
        //this.setResizable(false);
        //this.setDefaultLookAndFeelDecorated(false);
        initComponents();
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(0, 2, 0, 2);
        gbc.fill = GridBagConstraints.BOTH;
        for (int i = 0; i < week_shown; i++)
            for (int j = 0; j < 7; j++) {
                days[i][j] = new JLabel(String.valueOf(((i * 7 + j) % 31) + 1));
                days[i][j].setHorizontalAlignment(SwingConstants.CENTER);
                days[i][j].setOpaque(true);
                dates[i][j] = Calendar.getInstance();
                gbc.gridy = i + 2;
                gbc.gridx = j;
                jPanel1.add(days[i][j], gbc);
                days[i][j].addMouseListener(new MouseAdapter() {
                    protected Color c = null;
                    public void mouseClicked(MouseEvent evt) {
                        vse: for (int i = 0; i < week_shown; i++)
                            for (int j = 0; j < 7; j++) {
                                if (evt.getSource() == days[i][j]) {
                                    setSelectedDate(dates[i][j]);
                                    ok = true;
                                    //MonthCalendar.this.setVisible(false);
                                    fireActionPerformed();
                                    break vse;
                                }
                            }
                    }

                    @Override
                    public void mouseEntered(MouseEvent e) {
                        JLabel l = (JLabel) e.getSource();
                        c = l.getBackground();
                        l.setBackground(Color.YELLOW);
                    }

                    @Override
                    public void mouseExited(MouseEvent e) {
                        JLabel l = (JLabel) e.getSource();
                        l.setBackground(c);
                    }
                });
            }
        this.setSelectedDate(dnes);
    }

    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    private void initComponents()//GEN-BEGIN:initComponents
    {
        java.awt.GridBagConstraints gridBagConstraints;

        jPanel2 = new javax.swing.JPanel();
        prevousMonth = new javax.swing.JButton();
        monthName = new javax.swing.JLabel();
        nextMounth = new javax.swing.JButton();
        jPanel1 = new javax.swing.JPanel();
        monday = new javax.swing.JLabel();
        tuesday = new javax.swing.JLabel();
        wednesday = new javax.swing.JLabel();
        thursday = new javax.swing.JLabel();
        friday = new javax.swing.JLabel();
        saturday = new javax.swing.JLabel();
        sunday = new javax.swing.JLabel();
        jSeparator1 = new javax.swing.JSeparator();
        jSeparator2 = new javax.swing.JSeparator();
        jPanel3 = new javax.swing.JPanel();
        today = new javax.swing.JButton();
        cancel = new javax.swing.JButton();

        jPanel2.setLayout(new java.awt.GridBagLayout());

        jPanel2.setBorder(new javax.swing.border.BevelBorder(
                javax.swing.border.BevelBorder.RAISED));
        prevousMonth.setIcon(new ImageIcon(getClass().getClassLoader()
                .getResource("resource/icons/left.gif")));
        prevousMonth.setActionCommand("left");
        prevousMonth.setIconTextGap(0);
        prevousMonth.setMargin(new java.awt.Insets(0, 0, 0, 0));
        prevousMonth.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                selected.add(Calendar.MONTH, -1);
                updateView();
            }
        });

        jPanel2.add(prevousMonth, new java.awt.GridBagConstraints());

        monthName.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.weightx = 1.0;
        jPanel2.add(monthName, gridBagConstraints);

        nextMounth.setIcon(new ImageIcon(getClass().getClassLoader()
                .getResource("resource/icons/right.gif")));
        nextMounth.setActionCommand("right");
        nextMounth.setIconTextGap(0);
        nextMounth.setMargin(new java.awt.Insets(0, 0, 0, 0));
        nextMounth.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                selected.add(Calendar.MONTH, 1);
                updateView();
            }
        });

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridwidth = java.awt.GridBagConstraints.REMAINDER;
        jPanel2.add(nextMounth, gridBagConstraints);

        jPanel1.setLayout(new java.awt.GridBagLayout());

        monday.setText("Po");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.insets = new java.awt.Insets(2, 2, 2, 2);
        jPanel1.add(monday, gridBagConstraints);

        tuesday.setText("\u00dat");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.insets = new java.awt.Insets(2, 2, 2, 2);
        jPanel1.add(tuesday, gridBagConstraints);

        wednesday.setText("St");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.insets = new java.awt.Insets(2, 2, 2, 2);
        jPanel1.add(wednesday, gridBagConstraints);

        thursday.setText("\u010ct");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.insets = new java.awt.Insets(2, 2, 2, 2);
        jPanel1.add(thursday, gridBagConstraints);

        friday.setText("P\u00e1");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.insets = new java.awt.Insets(2, 2, 2, 2);
        jPanel1.add(friday, gridBagConstraints);

        saturday.setText("So");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.insets = new java.awt.Insets(2, 2, 2, 2);
        jPanel1.add(saturday, gridBagConstraints);

        sunday.setText("Ne");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridwidth = java.awt.GridBagConstraints.REMAINDER;
        gridBagConstraints.insets = new java.awt.Insets(2, 2, 2, 2);
        jPanel1.add(sunday, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridwidth = java.awt.GridBagConstraints.REMAINDER;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        jPanel1.add(jSeparator1, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 8;
        gridBagConstraints.gridwidth = java.awt.GridBagConstraints.REMAINDER;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        jPanel1.add(jSeparator2, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridwidth = java.awt.GridBagConstraints.REMAINDER;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.weighty = 1.0;
        jPanel2.add(jPanel1, gridBagConstraints);

        jPanel3.setLayout(new java.awt.GridBagLayout());

        today.setText("Dnes");
        today.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ok = true;
                setSelectedDate(dnes);
                //setVisible(false);
                fireActionPerformed();
            }
        });

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel3.add(today, gridBagConstraints);

        cancel.setText("Zp\u011bt");
        cancel.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cancelActionPerformed(evt);
            }
        });

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridwidth = java.awt.GridBagConstraints.RELATIVE;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel3.add(cancel, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridwidth = java.awt.GridBagConstraints.REMAINDER;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.weightx = 1.0;
        jPanel2.add(jPanel3, gridBagConstraints);

        add(jPanel2, java.awt.BorderLayout.CENTER);

    }//GEN-END:initComponents

    private void cancelActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_cancelActionPerformed
    {//GEN-HEADEREND:event_cancelActionPerformed
        // Add your handling code here:
        ok = false;
        //setVisible(false);
        fireActionPerformed();
    }//GEN-LAST:event_cancelActionPerformed

    /** Closes the dialog */

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        JFrame f = new JFrame();
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        MonthCalendar c = new MonthCalendar();
        c.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                MonthCalendar c = (MonthCalendar) e.getSource();
                if (c.isOk()) {
                    JOptionPane.showConfirmDialog(null, DateFormat
                            .getDateInstance().format(
                                    c.getSelectedDate().getTime()));
                } else {
                    JOptionPane.showConfirmDialog(null, "canceled");
                }
            }

        });
        f.getContentPane().add(c);
        f.pack();
        f.setVisible(true);
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JSeparator jSeparator2;

    private javax.swing.JLabel thursday;

    private javax.swing.JLabel friday;

    private javax.swing.JPanel jPanel3;

    private javax.swing.JLabel monday;

    private javax.swing.JLabel wednesday;

    private javax.swing.JLabel monthName;

    private javax.swing.JPanel jPanel2;

    private javax.swing.JLabel tuesday;

    private javax.swing.JButton cancel;

    private javax.swing.JButton today;

    private javax.swing.JLabel saturday;

    private javax.swing.JSeparator jSeparator1;

    private javax.swing.JPanel jPanel1;

    private javax.swing.JButton prevousMonth;

    private javax.swing.JButton nextMounth;

    private javax.swing.JLabel sunday;

    // End of variables declaration//GEN-END:variables

    public void setSelectedDate(Calendar date) {
        selected.setTimeInMillis(date.getTimeInMillis());
        updateView();
    }

    public Calendar getSelectedDate() {
        return selected;
    }

    public void updateView() {
        monthName.setText(mesice[selected.get(Calendar.MONTH)] + " "
                + selected.get(Calendar.YEAR));
        temp.setTimeInMillis(selected.getTimeInMillis());
        temp.add(Calendar.DAY_OF_MONTH, -temp.get(Calendar.DAY_OF_MONTH) + 1);
        int offset = temp.get(Calendar.DAY_OF_WEEK) - temp.getFirstDayOfWeek();
        if (offset < 0)
            offset += 7;
        temp.add(Calendar.DAY_OF_MONTH, -offset);
        for (int i = 0; i < week_shown; i++)
            for (int j = 0; j < 7; j++) {
                days[i][j].setText(String.valueOf(temp
                        .get(Calendar.DAY_OF_MONTH)));
                if (temp.get(Calendar.MONTH) == selected.get(Calendar.MONTH))
                    days[i][j].setForeground(Color.BLACK);
                else {
                    days[i][j].setForeground(Color.GRAY);
                }
                if (temp.equals(dnes))
                    days[i][j].setBorder(todayBorder);
                else
                    days[i][j].setBorder(null);
                if (temp.equals(selected)) {
                    days[i][j].setBackground(Color.CYAN);
                } else {
                    days[i][j].setBackground(this.getBackground());
                }
                dates[i][j].setTimeInMillis(temp.getTimeInMillis());
                temp.add(Calendar.DAY_OF_MONTH, 1);
            }
    }

    public boolean isOk() {
        return ok;
    }

    public Component getListCellRendererComponent(JList list, Object value,
            int index, boolean isSelected, boolean cellHasFocus) {
        // TODO Auto-generated method stub
        return null;
    }

}

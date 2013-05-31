
package unisave2006.gui;

import java.awt.GridBagLayout;
import javax.swing.JPanel;
import javax.swing.JLabel;
import java.awt.GridBagConstraints;
import javax.swing.JTextField;
import java.awt.Insets;
import javax.swing.*;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;

import unisave2006.data.Organization;
import unisave2006.dao.data.OrganizationDAO;

import java.awt.Dimension;
import java.awt.Font;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class CustomerPanel extends JPanel implements LoadCustomerListener {

    private static final long serialVersionUID = 1L;
    private JLabel jLabeltitle = null;
    private JTextField jTextFieldtitle = null;
    private JLabel jLabelStreet = null;
    private JTextField jTextFieldStreet = null;
    private JLabel jLabelPsc = null;
    private JTextField jTextFieldPsc = null;
    private JLabel jLabelCity = null;
    private JTextField jTextFieldCity = null;
    private JLabel jLabelTel = null;
    private JTextField jTextFieldTel = null;
    private JButton changeCustomer = null;
    private CustomerListDialog customerList;
    private OrganizationDAO orgDAO;
    
    protected Organization org = new Organization();

    /**
     * This is the default constructor
     */
    public CustomerPanel() {
        super();
        initialize();
        orgDAO = new OrganizationDAO();
        updateValues();
    }

    /**
     * This method initializes this
     * 
     * @return void
     */
    private void initialize() {
    	GridBagConstraints gridBagConstraints22 = new GridBagConstraints();
    	gridBagConstraints22.insets = new Insets(2, 2, 2, 2);
    	gridBagConstraints22.gridx = 1;
        gridBagConstraints22.gridy = 4;
        gridBagConstraints22.anchor = GridBagConstraints.WEST;
        GridBagConstraints gridBagConstraints21 = new GridBagConstraints();
        gridBagConstraints21.insets = new Insets(2, 2, 2, 2);
        gridBagConstraints21.fill = GridBagConstraints.HORIZONTAL;
        gridBagConstraints21.gridwidth = 0;
        gridBagConstraints21.weightx = 2.0;
        GridBagConstraints gridBagConstraints11 = new GridBagConstraints();
        gridBagConstraints11.insets = new Insets(2, 5, 2, 5);
        jLabelTel = new JLabel();
        jLabelTel.setText("Tel./Fax.:");
        GridBagConstraints gridBagConstraints7 = new GridBagConstraints();
        gridBagConstraints7.insets = new Insets(2, 5, 2, 5);
        GridBagConstraints gridBagConstraints6 = new GridBagConstraints();
        gridBagConstraints6.insets = new Insets(2, 2, 2, 2);
        GridBagConstraints gridBagConstraints5 = new GridBagConstraints();
        gridBagConstraints5.insets = new Insets(2, 2, 2, 2);
        GridBagConstraints gridBagConstraints4 = new GridBagConstraints();
        gridBagConstraints4.fill = GridBagConstraints.HORIZONTAL;
        gridBagConstraints4.insets = new Insets(2, 2, 2, 2);
        gridBagConstraints4.gridwidth = 0;
        gridBagConstraints4.weightx = 2.0D;
        jLabelCity = new JLabel();
        jLabelCity.setText("Město:");
        GridBagConstraints gridBagConstraints3 = new GridBagConstraints();
        gridBagConstraints3.fill = GridBagConstraints.HORIZONTAL;
        gridBagConstraints3.insets = new Insets(2, 2, 2, 2);
        gridBagConstraints3.weightx = 1.0;
        jLabelPsc = new JLabel();
        jLabelPsc.setText("PSČ:");
        GridBagConstraints gridBagConstraints2 = new GridBagConstraints();
        gridBagConstraints2.fill = GridBagConstraints.HORIZONTAL;
        //gridBagConstraints2.gridwidth = 0;
        gridBagConstraints2.insets = new Insets(2, 2, 2, 2);
        gridBagConstraints2.weightx = 1.0;
        jLabelStreet = new JLabel();
        jLabelStreet.setText("Ulice:");
        GridBagConstraints gridBagConstraints1 = new GridBagConstraints();
        gridBagConstraints1.ipadx = 0;
        gridBagConstraints1.insets = new Insets(2, 2, 2, 2);
        GridBagConstraints gridBagConstraints = new GridBagConstraints();
        gridBagConstraints.fill = GridBagConstraints.HORIZONTAL;
        gridBagConstraints.gridwidth = 0;
        gridBagConstraints.insets = new Insets(2, 2, 2, 2);
        gridBagConstraints.weightx = 1.0;
        jLabeltitle = new JLabel();
        jLabeltitle.setText("Název:");
        this.setSize(300, 200);
        this.setLayout(new GridBagLayout());
        this.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(EtchedBorder.LOWERED), "Organizace", TitledBorder.DEFAULT_JUSTIFICATION, TitledBorder.DEFAULT_POSITION, new Font("Dialog", Font.BOLD, 12), new Color(51, 51, 51)));
        this.add(jLabeltitle, gridBagConstraints1);
        this.add(getJTextFieldtitle(), gridBagConstraints);
        this.add(jLabelStreet, gridBagConstraints5);
        this.add(getJTextFieldStreet(), gridBagConstraints2);
        this.add(jLabelCity, gridBagConstraints7);
        this.add(getJTextFieldCity(), gridBagConstraints4);      
        this.add(jLabelPsc, gridBagConstraints6);
        this.add(getJTextFieldPsc(), gridBagConstraints3);
        this.add(jLabelTel, gridBagConstraints11);
        this.add(getJTextFieldTel(), gridBagConstraints21);
        this.add(getChangeCustomer(), gridBagConstraints22);
    }

    /**
     * This method initializes jTextFieldtitle	
     * 	
     * @return javax.swing.JTextField	
     */
    private JTextField getJTextFieldtitle() {
        if (jTextFieldtitle == null) {
            jTextFieldtitle = new JTextField();
            jTextFieldtitle.addFocusListener(new java.awt.event.FocusAdapter() {
                public void focusLost(java.awt.event.FocusEvent e) {
                    org.setName(getJTextFieldtitle().getText());
                }
            });
        }
        return jTextFieldtitle;
    }

    /**
     * This method initializes jTextFieldStreet	
     * 	
     * @return javax.swing.JTextField	
     */
    private JTextField getJTextFieldStreet() {
        if (jTextFieldStreet == null) {
            jTextFieldStreet = new JTextField();
            jTextFieldStreet.addFocusListener(new java.awt.event.FocusAdapter() {
                public void focusLost(java.awt.event.FocusEvent e) {
                    org.getAddress().setStreet(getJTextFieldStreet().getText());
                }
            });
        }
        return jTextFieldStreet;
    }

    /**
     * This method initializes jTextFieldPsc	
     * 	
     * @return javax.swing.JTextField	
     */
    private JTextField getJTextFieldPsc() {
        if (jTextFieldPsc == null) {
            jTextFieldPsc = new JTextField();
            jTextFieldPsc.addFocusListener(new java.awt.event.FocusAdapter() {
                public void focusLost(java.awt.event.FocusEvent e) {
                    org.getAddress().setPsc(getJTextFieldPsc().getText());
                }
            });
        }
        return jTextFieldPsc;
    }

    /**
     * This method initializes jTextFieldCity	
     * 	
     * @return javax.swing.JTextField	
     */
    private JTextField getJTextFieldCity() {
        if (jTextFieldCity == null) {
            jTextFieldCity = new JTextField();
            jTextFieldCity.addFocusListener(new java.awt.event.FocusAdapter() {
                public void focusLost(java.awt.event.FocusEvent e) {
                    org.getAddress().setCity(getJTextFieldCity().getText());
                }
            });
        }
        return jTextFieldCity;
    }
    
    public void setTitle(String title){
        if(this.getBorder() instanceof TitledBorder){
            ((TitledBorder)this.getBorder()).setTitle(title);
        }
    }

    /**
     * This method initializes jTextFieldTel	
     * 	
     * @return javax.swing.JTextField	
     */
    private JTextField getJTextFieldTel() {
        if (jTextFieldTel == null) {
            jTextFieldTel = new JTextField();
            jTextFieldTel.addFocusListener(new java.awt.event.FocusAdapter() {
                public void focusLost(java.awt.event.FocusEvent e) {
                    org.setTel(getJTextFieldTel().getText());
                }
            });
        }
        return jTextFieldTel;
    }
    
    private JButton getChangeCustomer() {
    	if(changeCustomer == null) {
    		changeCustomer = new JButton();
    		changeCustomer.setPreferredSize(new Dimension(140, 23));
    		changeCustomer.setText("Změnit zadavatel");
    		changeCustomer.addActionListener(
    				new ActionListener() {
    					public void actionPerformed(ActionEvent event) {
    						changeCustomer();
    					}
    				}
    		);
    	}
    	return changeCustomer;
    }
    
    public void changeCustomer() {
    	customerList = new CustomerListDialog();
    	customerList.addLoadCustomerListener(this);
    	customerList.pack();
    	customerList.setLocationRelativeTo(this);
    	customerList.setVisible(true);
    }

    public void loadedCustomerChanged() {
    	if(customerList.getLoadedCustomer() != null) {
    		Organization o = orgDAO.find(customerList.getLoadedCustomer());
    		setOrg(o);
    		MainFrame.getMeasurement().getProtocolSetting().setCustomer(o);
    		MainFrame.getMeasurement().getProtocolSetting().getCustomer().setAddress(o.getAddress());
    	}
    }
    
    public void addCustomer() {
    	
    }
    
    public void deleteCustomer() {
    	if(customerList.getLoadedCustomer() != null) {
    			Organization o = orgDAO.find(customerList.getLoadedCustomer());
    			o.setIsCustomer(false);
    			orgDAO.saveOrUpdate(o);
    		
    	}
    }
    
    public Organization getOrg() {
        return org;
    }

    public void setOrg(Organization org) {
        this.org = org;
        updateValues();
    }

    protected void updateValues() {
        getJTextFieldtitle().setText(org.getName());
        getJTextFieldStreet().setText(org.getAddress().getStreet());
        getJTextFieldPsc().setText(org.getAddress().getPsc());
        getJTextFieldCity().setText(org.getAddress().getCity());
        getJTextFieldTel().setText(org.getTel());
    }

    public void disableComponents() {
    	jTextFieldtitle.setEditable(false);
    	jTextFieldStreet.setEditable(false);
    	jTextFieldPsc.setEditable(false);
    	jTextFieldCity.setEditable(false);
    	jTextFieldTel.setEditable(false);
    }
    
}


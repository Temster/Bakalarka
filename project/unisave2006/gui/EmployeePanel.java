package unisave2006.gui;



import javax.swing.BorderFactory;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import java.awt.*;
import javax.swing.*;
import javax.swing.border.*;
import javax.swing.JLabel;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.awt.event.KeyEvent;
import javax.swing.JTextField;

import unisave2006.dao.data.EmployeeDAO;
import unisave2006.data.Employee;

import java.awt.GridBagConstraints;
import java.awt.Insets;

public class EmployeePanel extends JPanel implements LoadEmployeeListener {

	private JLabel jLabelFirstName = null;
    private JTextField jTextFieldFirstName = null;
    private JLabel jLabelLastName = null;
    private JTextField jTextFieldLastName = null;
    private JLabel jLabelPosition = null;
    private JButton changeEmployee = null;
	private JTextField jTextFieldPosition = null;
	private JLabel jLabelDepartment = null;
	private JTextField jTextFieldDepartment = null;
	private EmployeeListDialog employeeList;
	private EmployeeDAO empDAO;
	private Employee employee = new Employee();

    /**
     * This is the default constructor
     */
    public EmployeePanel() {
        super();
        initialize();
        empDAO = new EmployeeDAO();
        updateValues();

    }

    /**
     * This method initializes this
     * 
     * @return void
     */
    private void initialize() {
    	GridBagConstraints gridBagConstraints2 = new GridBagConstraints();
    	gridBagConstraints2.fill = GridBagConstraints.HORIZONTAL;
    	gridBagConstraints2.gridy = 1;
    	gridBagConstraints2.weightx = 1.0;
    	gridBagConstraints2.insets = new Insets(2, 2, 2, 2);
    	gridBagConstraints2.gridx = 3;
    	GridBagConstraints gridBagConstraints1 = new GridBagConstraints();
    	gridBagConstraints1.gridx = 2;
    	gridBagConstraints1.insets = new Insets(2, 5, 2, 5);
    	gridBagConstraints1.gridy = 1;
    	jLabelDepartment = new JLabel();
    	jLabelDepartment.setText("Oddělení:");
    	GridBagConstraints gridBagConstraints = new GridBagConstraints();
    	gridBagConstraints.fill = GridBagConstraints.HORIZONTAL;
    	gridBagConstraints.gridy = 1;
    	gridBagConstraints.weightx = 1.0;
    	gridBagConstraints.insets = new Insets(2, 2, 2, 2);
    	gridBagConstraints.gridx = 1;
    	GridBagConstraints gridBagConstraints22 = new GridBagConstraints();
    	gridBagConstraints22.insets = new Insets(2, 2, 2, 2);
    	gridBagConstraints22.gridx = 1;
        gridBagConstraints22.gridy = 4;
        gridBagConstraints22.anchor = GridBagConstraints.WEST;
        GridBagConstraints gridBagConstraints11 = new GridBagConstraints();
        gridBagConstraints11.insets = new Insets(2, 2, 2, 2);
        jLabelPosition = new JLabel();
        jLabelPosition.setText("Provozní zařazení:");
        jLabelPosition.setDisplayedMnemonic(KeyEvent.VK_UNDEFINED);
        GridBagConstraints gridBagConstraints7 = new GridBagConstraints();
        gridBagConstraints7.insets = new Insets(2, 5, 2, 5);
        GridBagConstraints gridBagConstraints6 = new GridBagConstraints();
        gridBagConstraints6.insets = new Insets(2, 2, 2, 2);
        gridBagConstraints6.anchor = GridBagConstraints.WEST;
        GridBagConstraints gridBagConstraints4 = new GridBagConstraints();
        gridBagConstraints4.fill = GridBagConstraints.HORIZONTAL;
        gridBagConstraints4.insets = new Insets(2, 2, 2, 2);
        gridBagConstraints4.gridwidth = 0;
        gridBagConstraints4.weightx = 2.0D;
        jLabelLastName = new JLabel();
        jLabelLastName.setText("Příjmení:");
        GridBagConstraints gridBagConstraints3 = new GridBagConstraints();
        gridBagConstraints3.fill = GridBagConstraints.HORIZONTAL;
        gridBagConstraints3.insets = new Insets(2, 2, 2, 2);
        gridBagConstraints3.weightx = 1.0;
        jLabelFirstName = new JLabel();
        jLabelFirstName.setText("Jméno:");
        this.setSize(400, 100);
        this.setLayout(new GridBagLayout());
        this.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(EtchedBorder.LOWERED), "Organizace", TitledBorder.DEFAULT_JUSTIFICATION, TitledBorder.DEFAULT_POSITION, new Font("Dialog", Font.BOLD, 12), new Color(51, 51, 51)));
        this.add(jLabelFirstName, gridBagConstraints6);
        this.add(getJTextFieldFirstName(), gridBagConstraints3);
        this.add(jLabelLastName, gridBagConstraints7);
        this.add(getJTextFieldLastName(), gridBagConstraints4);
        this.add(jLabelPosition, gridBagConstraints11);
        this.add(getChangeEmployee(), gridBagConstraints22);
        this.add(getJTextFieldPosition(), gridBagConstraints);
        this.add(jLabelDepartment, gridBagConstraints1);
        this.add(getJTextFieldDepartment(), gridBagConstraints2);
    }

    public Employee getEmployee() {
    	return employee;
    }
    public void setEmployee(Employee employee) {
    	this.employee = employee;
    	updateValues();
    }
    
    /**
     * This method initializes jTextFieldFirstName	
     * 	
     * @return javax.swing.JTextField	
     */
    private JTextField getJTextFieldFirstName() {
        if (jTextFieldFirstName == null) {
            jTextFieldFirstName = new JTextField();
            jTextFieldFirstName.addFocusListener(new FocusAdapter() {
                public void focusLost(FocusEvent e) {
                    employee.setFirstName(getJTextFieldFirstName().getText());
                }
            });
            
        }
        return jTextFieldFirstName;
    }

    /**
     * This method initializes jTextFieldLastName	
     * 	
     * @return javax.swing.JTextField	
     */
    private JTextField getJTextFieldLastName() {
        if (jTextFieldLastName == null) {
            jTextFieldLastName = new JTextField();
            jTextFieldLastName.addFocusListener(new FocusAdapter() {
                public void focusLost(FocusEvent e) {
                    employee.setLastName(getJTextFieldLastName().getText());
                }
            });
            
        }
        return jTextFieldLastName;
    }
    
    public void setTitle(String title){
        if(this.getBorder() instanceof TitledBorder){
            ((TitledBorder)this.getBorder()).setTitle(title);
        }
    }

    private JButton getChangeEmployee() {
    	if(changeEmployee == null) {
    		changeEmployee = new JButton();
    		changeEmployee.setPreferredSize(new Dimension(160, 23));
    		changeEmployee.setText("Změnit zaměstnance");
    		changeEmployee.addActionListener(
    				new ActionListener() {
    					public void actionPerformed(ActionEvent event) {
    						changeEmployee();
    					}
    				}
    		);
    		
    	}
    	return changeEmployee;
    }

	/**
	 * This method initializes jTextFieldPosition	
	 * 	
	 * @return javax.swing.JTextField	
	 */
	private JTextField getJTextFieldPosition() {
		if (jTextFieldPosition == null) {
			jTextFieldPosition = new JTextField();
			jTextFieldPosition.addFocusListener(new FocusAdapter() {
                public void focusLost(FocusEvent e) {
                    employee.setPosition(getJTextFieldPosition().getText());
                }
            });
		}
		return jTextFieldPosition;
	}

	/**
	 * This method initializes jTextFieldDepartment	
	 * 	
	 * @return javax.swing.JTextField	
	 */
	private JTextField getJTextFieldDepartment() {
		if (jTextFieldDepartment == null) {
			jTextFieldDepartment = new JTextField();
			jTextFieldDepartment.addFocusListener(new FocusAdapter() {
                public void focusLost(FocusEvent e) {
                    employee.setDepartment(getJTextFieldDepartment().getText());
                }
            });
		}
		return jTextFieldDepartment;
	}

	public void changeEmployee() {
		employeeList = new EmployeeListDialog();
		employeeList.addLoadEmployeeListener(this);
		employeeList.pack();
		employeeList.setLocationRelativeTo(this);
		employeeList.setVisible(true);
	}
	
	public void loadedEmployeeChanged() {
		if(employeeList.getLoadedEmployee() != null) {
			Employee e = empDAO.find(employeeList.getLoadedEmployee());
			setEmployee(e);
			MainFrame.getMeasurement().getProtocolSetting().setResponsiblePerson(e);
		}
	}
	
	public void addEmployee() {
		
	}
	
	public void deleteEmployee() {
		if(employeeList.getLoadedEmployee() != null) {
			Employee emp = empDAO.find(employeeList.getLoadedEmployee());
			emp.setIsDeleted(true);
			empDAO.saveOrUpdate(emp);
		}
	}
	
	private void updateValues() {
		getJTextFieldFirstName().setText(employee.getFirstName());
		getJTextFieldLastName().setText(employee.getLastName());
		getJTextFieldPosition().setText(employee.getPosition());
		getJTextFieldDepartment().setText(employee.getDepartment());
	}
	
	public void disableComponents() {
		getJTextFieldFirstName().setEditable(false);
		getJTextFieldLastName().setEditable(false);
		getJTextFieldPosition().setEditable(false);
		getJTextFieldDepartment().setEditable(false);
	}
	
}  //  @jve:decl-index=0:visual-constraint="10,10"


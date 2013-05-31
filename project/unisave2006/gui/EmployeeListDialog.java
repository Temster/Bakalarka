package unisave2006.gui;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

import javax.swing.*;
import javax.swing.event.*;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.TableRowSorter;

import unisave2006.data.Employee;
import unisave2006.dao.data.EmployeeDAO;
import unisave2006.dao.data.OrganizationDAO;
import unisave2006.gui.CustomerListDialog.CustomerListTableModel;

public class EmployeeListDialog extends JDialog implements TableModelListener {

	private int selectedEmployee = -1;
	private Long loadedEmployee = null;
	int selectedRow;
	private JTable table;
	private JButton loadButton;
	private JButton cancelButton;
	private JButton deleteButton;
	private JButton addButton;
	private JPanel buttonPanel;
	private TableRowSorter<EmployeeListTableModel> sorter;
	private EmployeeListTableModel tableModel;
	private AddNewEmployeePanel addEmployee;
	private GridBagLayout grBag;
	private GridBagConstraints constraint1;
	private GridBagConstraints constraint2;
	private GridBagConstraints constraint3;
	private GridBagConstraints constraint4;
	
	private Vector<LoadEmployeeListener> listeners = new Vector<LoadEmployeeListener>();
	EmployeeDAO empDAO;
	
	public EmployeeListDialog() {
		super();
		super.setTitle("Seznam zaměstnanců");
		this.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
		this.setLayout(new BorderLayout());
		empDAO = new EmployeeDAO();
		tableModel = new EmployeeListTableModel();
		sorter = new TableRowSorter<EmployeeListTableModel>(tableModel);
		table = new JTable(tableModel);
		table.setFillsViewportHeight(true);
		table.setRowSorter(sorter);
		table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		table.getModel().addTableModelListener(this);
		table.getSelectionModel().addListSelectionListener(
				new ListSelectionListener() {
					public void valueChanged(ListSelectionEvent event) {
						selectedRow = table.getSelectedRow();
						if(selectedRow >=0) {
							if(!deleteButton.isEnabled())
								deleteButton.setEnabled(true);
							if(!loadButton.isEnabled())
								loadButton.setEnabled(true);
							selectedEmployee = table.convertRowIndexToModel(selectedRow);
						}
					}
				}
		);
		
		grBag = new GridBagLayout();
		constraint1 = new GridBagConstraints();
		buttonPanel = new JPanel();
		buttonPanel.setLayout(grBag);
		
		constraint1.gridx = 0;
		constraint1.gridy = 0;
		constraint1.insets = new Insets(5, 2, 5, 2);
		constraint1.anchor = GridBagConstraints.WEST;
		deleteButton = new JButton("Smazat");
		deleteButton.setPreferredSize(new Dimension(80, 24));
		deleteButton.setEnabled(false);
		deleteButton.addActionListener(
				new ActionListener() {
					public void actionPerformed(ActionEvent event) {
						if(selectedEmployee >= 0) {
							int select = JOptionPane.showConfirmDialog(null, "Opravdu chcete tento záznam smazat?", "Unisave 2006", 
									JOptionPane.YES_NO_OPTION);
							if(select == JOptionPane.YES_OPTION) {
								loadedEmployee = new Long(tableModel.entries.get(selectedEmployee).getId());
								fireDeleteEmployeeListener();
								tableModel.fireTableRowsDeleted(selectedRow, selectedRow);
							}							
						}
					}
				}
		);
		buttonPanel.add(deleteButton, constraint1);
		
		constraint2 = new GridBagConstraints();
		constraint2.gridx = 1;
		constraint2.gridy = 0;
		constraint2.insets = new Insets(5, 2, 5, 120);
		constraint2.anchor = GridBagConstraints.WEST;
		addButton = new JButton("Pridat");
		addButton.setPreferredSize(new Dimension(80, 24));
		addButton.addActionListener(
				new ActionListener() {
					public void actionPerformed(ActionEvent event) {
						addEmployeePerformed();
					}
				}
		);
		buttonPanel.add(addButton, constraint2);
		
		constraint3 = new GridBagConstraints();
		constraint3.gridx = 2;
		constraint3.gridy = 0;
		constraint3.insets = new Insets(5, 2, 5, 2);
		constraint3.anchor = GridBagConstraints.EAST;
		loadButton = new JButton("Změnit");
		loadButton.setPreferredSize(new Dimension(80, 24));
		loadButton.setEnabled(false);
		loadButton.addActionListener(
				new ActionListener() {
					public void actionPerformed(ActionEvent event) {
						if(selectedEmployee >= 0) {
							loadedEmployee = new Long(tableModel.entries.get(selectedEmployee).getId());
							fireLoadEmployeeListener();
							dispose();
						}
					}
				}
		);
		
		buttonPanel.add(loadButton, constraint3);
		
		constraint4 = new GridBagConstraints();
		constraint4.gridx = 3;
		constraint4.gridy = 0;
		constraint4.insets = new Insets(2, 2, 2, 2);
		constraint4.anchor = GridBagConstraints.EAST;
		cancelButton = new JButton("Zrušit");
		cancelButton.setPreferredSize(new Dimension(80, 24));
		cancelButton.addActionListener(
				new ActionListener() {
					public void actionPerformed(ActionEvent event) {
						dispose();
					}
				}
		);
		buttonPanel.add(cancelButton, constraint4);
				
		this.add(buttonPanel, BorderLayout.SOUTH);
		this.add(new JScrollPane(table), BorderLayout.CENTER);
		
	}
	
	public Long getLoadedEmployee() {
		return loadedEmployee;
	}
	
	public void tableChanged(TableModelEvent e) {
		tableModel.entries = empDAO.findAll();
		List<Employee> tempEmp = new ArrayList<Employee>();
		for(Employee emp : tableModel.entries) {
			if(!emp.getIsDeleted())
				tempEmp.add(emp);
		}
		tableModel.entries = tempEmp;
		tableModel.data = new String[tableModel.entries.size()][tableModel.columnNames.length];
		int count = 0;
		for(Employee entry : tableModel.entries) {
			tableModel.data[count][0] = entry.getFirstName();
			tableModel.data[count][1] = entry.getLastName();
			tableModel.data[count][2] = entry.getPosition();
			tableModel.data[count][3] = entry.getDepartment();
			count++;
		}
	}
	
	private void addEmployeePerformed() {
		addEmployee = new AddNewEmployeePanel(empDAO);
		addEmployee.pack();
		addEmployee.setLocationRelativeTo(this);
		addEmployee.setVisible(true);
		addEmployee.addWindowListener(new java.awt.event.WindowAdapter() {
			public void windowClosed(java.awt.event.WindowEvent e) {
				tableModel.fireTableRowsInserted(selectedRow, selectedRow);
			}
		});
	}
	
	public void addLoadEmployeeListener(LoadEmployeeListener l) {
		listeners.add(l);
	}
	
	public void fireLoadEmployeeListener() {
		for(LoadEmployeeListener l : listeners) {
			l.loadedEmployeeChanged();
		}
	}
	
	public void fireDeleteEmployeeListener() {
		for(LoadEmployeeListener l : listeners) {
			l.deleteEmployee();
		}
	}
	
	class EmployeeListTableModel extends AbstractTableModel {
		
		private String[] columnNames = { "Jméno",
										 "Příjmení",
										 "Provozní zařazení",
										 "Oddělení"};
		
		private List<Employee> entries;
		
		private Object[][] data;
		
		
		public EmployeeListTableModel() {
			entries = empDAO.findAll();
			List<Employee> tempEmp = new ArrayList<Employee>();
			for(Employee emp : entries) {
				if(!emp.getIsDeleted())
					tempEmp.add(emp);
			}
			entries = tempEmp;
			data = new String[entries.size()][columnNames.length];
			int count = 0;
			for(Employee entry : entries) {
				data[count][0] = entry.getFirstName();
				data[count][1] = entry.getLastName();
				data[count][2] = entry.getPosition();
				data[count][3] = entry.getDepartment();
				count++;
			}
		}
		
		public int getColumnCount() {
			return columnNames.length;
		}
		
		public int getRowCount() {
			return data.length;
		}
		
		public String getColumnName(int col) {
            return columnNames[col];
        }
		
		public Object getValueAt(int row, int col) {
			return data[row][col];
		}
	
		public Class getColumnClass(int c) {
			return getValueAt(0, c).getClass();
		}

		
	}
	
}

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

import unisave2006.data.Organization;
import unisave2006.dao.data.OrganizationDAO;

public class CustomerListDialog extends JDialog implements TableModelListener {

	private int selectedCustomer = -1;
	private Long loadedCustomer = null;
	int selectedRow;
	private JTable table;
	private JButton loadButton;
	private JButton cancelButton;
	private JButton deleteButton;
	private JButton addButton;
	private JPanel buttonPanel;
	private TableRowSorter<CustomerListTableModel> sorter;
	private CustomerListTableModel tableModel;
	private AddNewCustomerPanel addCustomer;
	private GridBagLayout grBag;
	private GridBagConstraints constraint1;
	private GridBagConstraints constraint2;
	private GridBagConstraints constraint3;
	private GridBagConstraints constraint4;
	
	private Vector<LoadCustomerListener> listeners = new Vector<LoadCustomerListener>();
	private OrganizationDAO orgDAO;
	
	public CustomerListDialog() {
		super();
		super.setTitle("Seznam zadavatelů");
		this.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
		this.setLayout(new BorderLayout());
		orgDAO = new OrganizationDAO();
		tableModel = new CustomerListTableModel();
		sorter = new TableRowSorter<CustomerListTableModel>(tableModel);
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
							selectedCustomer = table.convertRowIndexToModel(selectedRow);
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
						if(selectedCustomer >= 0) {
							int select = JOptionPane.showConfirmDialog(null, "Opravdu chcete tento záznam smazat?", "Unisave 2006", 
									JOptionPane.YES_NO_OPTION);
							if(select == JOptionPane.YES_OPTION) {
								loadedCustomer = new Long(tableModel.entries.get(selectedCustomer).getId());
								fireDeleteCustomerListener();
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
						addCustomerPerformed();
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
						if(selectedCustomer >= 0) {
							loadedCustomer = new Long(tableModel.entries.get(selectedCustomer).getId());
							fireLoadCustomerListener();
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
	
	public Long getLoadedCustomer() {
		return loadedCustomer;
	}
	
	public void tableChanged(TableModelEvent e) {
		tableModel.entries = orgDAO.findAll();
		List<Organization> tempOrg = new ArrayList<Organization>();
		for(Organization entry : tableModel.entries) {			
			if(entry.getIsCustomer())
				tempOrg.add(entry);
		}
		tableModel.entries = tempOrg;
		tableModel.data = new String[tableModel.entries.size()][tableModel.columnNames.length];
		int count = 0;
		for(Organization entry : tableModel.entries) {
			tableModel.data[count][0] = entry.getName();
			tableModel.data[count][1] = entry.getAddress().getStreet();
			tableModel.data[count][2] = entry.getAddress().getPsc();
			tableModel.data[count][3] = entry.getAddress().getCity();
			tableModel.data[count][4] = entry.getTel();
			count++;
		}
		
	}
	
	public void addCustomerPerformed() {
		addCustomer = new AddNewCustomerPanel(orgDAO);
		addCustomer.pack();
		addCustomer.setLocationRelativeTo(this);
		addCustomer.setVisible(true);
		addCustomer.addWindowListener(new java.awt.event.WindowAdapter() {
			public void windowClosed(java.awt.event.WindowEvent e) {
				tableModel.fireTableRowsInserted(selectedRow, selectedRow);
			}
		});
	}
	
	public void addLoadCustomerListener(LoadCustomerListener l) {
		listeners.add(l);
	}
	
	public void fireLoadCustomerListener() {
		for(LoadCustomerListener l : listeners) {
			l.loadedCustomerChanged();
		}
	}
	
	public void fireDeleteCustomerListener() {
		for(LoadCustomerListener l : listeners) {
			l.deleteCustomer();
		}
	}
	
	
	class CustomerListTableModel extends AbstractTableModel {
		
		private String[] columnNames = {"Název",
										"Ulice",
										"PSČ",
										"Město",
										"Tel/Fax"};
		
		List<Organization> entries;
		
		private Object[][] data;
		
		public CustomerListTableModel() {
			entries = orgDAO.findAll();
			List<Organization> tempOrg = new ArrayList<Organization>();
			for(Organization entry : entries) {			
				if(entry.getIsCustomer())
					tempOrg.add(entry);
			}
			entries = tempOrg;
			data = new String[entries.size()][columnNames.length];
			int count = 0;
			for(Organization entry : entries) {
				data[count][0] = entry.getName();
				data[count][1] = entry.getAddress().getStreet();
				data[count][2] = entry.getAddress().getPsc();
				data[count][3] = entry.getAddress().getCity();
				data[count][4] = entry.getTel();
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

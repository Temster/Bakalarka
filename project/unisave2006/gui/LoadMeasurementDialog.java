package unisave2006.gui;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import java.util.Vector;

import javax.swing.*;
import javax.swing.event.*;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.TableRowSorter;

import unisave2006.dao.data.MeasurementDAO;
import unisave2006.data.MeasurementPOJO;

public class LoadMeasurementDialog extends JDialog implements TableModelListener {


	private int selectedRecord = -1;
	private Long loadedRecord = null;
	private int selectedRow;
	private JTable table;
	private JButton loadButton;
	private JButton cancelButton;
	private JButton deleteButton;
	private JPanel buttonPanel;
	private TableRowSorter<MeasurementTableModel> sorter;
	private MeasurementTableModel tableModel;
	private GridBagLayout grBag;
	private GridBagConstraints constraint1;
	private GridBagConstraints constraint2;
	private GridBagConstraints constraint3;
	
	private Vector<LoadMeasurementListener> listeners = new Vector<LoadMeasurementListener>();
	private MeasurementDAO measurementDAO;
	
	public LoadMeasurementDialog(Frame owner) {
		super(owner);
		super.setTitle("Načítat měření");
		this.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
		this.setLayout(new BorderLayout());
		measurementDAO = new MeasurementDAO();
		tableModel = new MeasurementTableModel();
		sorter = new TableRowSorter<MeasurementTableModel>(tableModel);
		table = new JTable(tableModel);
		table.setFillsViewportHeight(true);
		table.getColumnModel().getColumn(0).setPreferredWidth(10);
		table.getColumnModel().getColumn(1).setPreferredWidth(120);
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
							selectedRecord = table.convertRowIndexToModel(selectedRow);
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
		constraint1.insets = new Insets(5, 2, 5, 200);
		constraint1.anchor = GridBagConstraints.WEST;
		deleteButton = new JButton("Smazat");
		deleteButton.setPreferredSize(new Dimension(80, 24));
		deleteButton.setEnabled(false);
		deleteButton.addActionListener(
				new ActionListener() {
					public void actionPerformed(ActionEvent event) {
						if(selectedRecord >= 0) {
							int select = JOptionPane.showConfirmDialog(null, "Opravdu chcete tento záznam smazat?", "Unisave 2006", 
									JOptionPane.YES_NO_OPTION);
							if(select == JOptionPane.YES_OPTION) {
								loadedRecord = new Long(tableModel.entries.get(selectedRecord).getId());
								fireDeleteMeasurementListener();
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
		constraint2.insets = new Insets(5, 2, 5, 2);
		constraint2.anchor = GridBagConstraints.EAST;
		loadButton = new JButton("Otevřit");
		loadButton.setPreferredSize(new Dimension(80, 24));
		loadButton.setEnabled(false);
		loadButton.addActionListener(
				new ActionListener() {
					public void actionPerformed(ActionEvent event) {
						if(selectedRecord >= 0) {
							loadedRecord = new Long(tableModel.entries.get(selectedRecord).getId());
							fireLoadMeasurementListener();
							dispose();
						}
					}
				}
		);
		buttonPanel.add(loadButton, constraint2);
		
		constraint3 = new GridBagConstraints();
		constraint3.gridx = 2;
		constraint3.gridy = 0;
		constraint3.insets = new Insets(2, 2, 2, 2);
		constraint3.anchor = GridBagConstraints.EAST;
		cancelButton = new JButton("Zrušit");
		cancelButton.setPreferredSize(new Dimension(80, 24));
		cancelButton.addActionListener(
				new ActionListener() {
					public void actionPerformed(ActionEvent event) {
						dispose();
					}
				}
		);
		buttonPanel.add(cancelButton, constraint3);
				
		this.add(buttonPanel, BorderLayout.SOUTH);
		this.add(new JScrollPane(table), BorderLayout.CENTER);
	}
	
	public Long getLoadedRecord() {
		return loadedRecord;
	}
	
	public void tableChanged(TableModelEvent e) {
		tableModel.entries = measurementDAO.findAll();
		tableModel.data = new String[tableModel.entries.size()][tableModel.columnNames.length];
		int count = 0;
		for(MeasurementPOJO entry : tableModel.entries) 
		{
			tableModel.data[count][0] = entry.getId().toString();
			tableModel.data[count][1] = entry.getProtocolSetting().getDateOfMeasurement().toString();
			tableModel.data[count][2] = entry.getProtocolSetting().getCustomer().getName();
			tableModel.data[count][3] = entry.getProtocolSetting().getResponsiblePerson().getLastName();
			tableModel.data[count][4] = entry.getProtocolSetting().getMeasurer().getIdentification();
			count++;
		}
	}
	
	public void addLoadMeasurementListener(LoadMeasurementListener l) {
		listeners.add(l);
	}
	
	public void fireLoadMeasurementListener() {
		for(LoadMeasurementListener l : listeners) {
			l.loadedRecordChanged();
		}
	}
	
	public void fireDeleteMeasurementListener() {
		for(LoadMeasurementListener l : listeners) {
			l.deleteRecord();
		}
	}
	
	class MeasurementTableModel extends AbstractTableModel {
		
		private String[] columnNames = {"ID",
										"Datum měření",
										"Zakázník",
										"Měřil",
										"Měřidlo"};
		
		private List<MeasurementPOJO> entries;
		
		private Object[][] data;
		
			
		public MeasurementTableModel() {
			entries = measurementDAO.findAll();
			data = new String[entries.size()][columnNames.length];
			int count = 0;
			for(MeasurementPOJO entry : entries) 
			{
				data[count][0] = entry.getId().toString();
				data[count][1] = entry.getProtocolSetting().getDateOfMeasurement().toString();
				data[count][2] = entry.getProtocolSetting().getCustomer().getName();
				data[count][3] = entry.getProtocolSetting().getResponsiblePerson().getLastName();
				data[count][4] = entry.getProtocolSetting().getMeasurer().getIdentification();
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

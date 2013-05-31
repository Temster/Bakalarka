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

import unisave2006.data.Measurer;
import unisave2006.dao.data.MeasurerDAO;

public class MeasurerListDialog extends JDialog implements TableModelListener {

	private int selectedMeasurer = -1;
	private Long loadedMeasurer = null;
	private int selectedRow;
	private JTable table;
	private JButton loadButton;
	private JButton cancelButton;
	private JButton deleteButton;
	private JButton addButton;
	private JPanel buttonPanel;
	private TableRowSorter<MeasurerListTableModel> sorter;
	private MeasurerListTableModel tableModel;
	private AddNewMeasurerPanel addMeasurer;
	private GridBagLayout grBag;
	private GridBagConstraints constraint1;
	private GridBagConstraints constraint2;
	private GridBagConstraints constraint3;
	private GridBagConstraints constraint4;
	
	private Vector<LoadMeasurerListener> listeners = new Vector<LoadMeasurerListener>();
	private MeasurerDAO mDAO;
	
	public MeasurerListDialog() {
		super();
		super.setTitle("Seznam zařízení");
		this.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
		this.setLayout(new BorderLayout());
		mDAO = new MeasurerDAO();
		tableModel = new MeasurerListTableModel();
		sorter = new TableRowSorter<MeasurerListTableModel>(tableModel);
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
							selectedMeasurer = table.convertRowIndexToModel(selectedRow);
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
						if(selectedMeasurer >= 0) {
							int select = JOptionPane.showConfirmDialog(null, "Opravdu chcete tento záznam smazat?", "Unisave 2006", 
									JOptionPane.YES_NO_OPTION);
							if(select == JOptionPane.YES_OPTION) {
								loadedMeasurer = new Long(tableModel.entries.get(selectedMeasurer).getId());
								fireDeleteMeasurerListener();
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
						addMeasurerPerformed();
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
						if(selectedMeasurer >= 0) {
							loadedMeasurer = new Long(tableModel.entries.get(selectedMeasurer).getId());
							fireLoadMeasurerListener();
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
	
	public Long getLoadedMeasurer() {
		return loadedMeasurer;
	}
	
	public void tableChanged(TableModelEvent e) {
		tableModel.entries = mDAO.findAll();
		List<Measurer> tempMeasurer = new ArrayList<Measurer>();
		for(Measurer entry : tableModel.entries) {
			if(!entry.getIsDeleted())
				tempMeasurer.add(entry);
		}
		tableModel.entries = tempMeasurer;
		tableModel.data = new String[tableModel.entries.size()][tableModel.columnNames.length];
		int count = 0;
		for(Measurer entry : tableModel.entries) {
			tableModel.data[count][0] = entry.getIdentification();
			tableModel.data[count][1] = entry.getTitle();
			tableModel.data[count][2] = entry.getType();
			tableModel.data[count][3] = entry.getcalibrationDate().toString();
			count++;
		}
	}
	
	public void addLoadMeasurerListener(LoadMeasurerListener l) {
		listeners.add(l);
	}
	
	private void addMeasurerPerformed() {
		addMeasurer = new AddNewMeasurerPanel(mDAO);
		addMeasurer.pack();
		addMeasurer.setLocationRelativeTo(this);
		addMeasurer.setVisible(true);
		addMeasurer.addWindowListener(new java.awt.event.WindowAdapter() {
			public void windowClosed(java.awt.event.WindowEvent e) {
				tableModel.fireTableRowsInserted(selectedRow, selectedRow);
			}
		});
	}
	
	private void fireDeleteMeasurerListener() {
		for(LoadMeasurerListener l : listeners) {
			l.deleteMeasurer();
		}
	}
	
	private void fireLoadMeasurerListener() {
		for(LoadMeasurerListener l : listeners) {
			l.loadedMeasurerChanged();
		}
	}
	
	class MeasurerListTableModel extends AbstractTableModel {
		
		private String[] columnNames = {"Identifikace",
										"Název",
										"Typ",
										"Datum kalibrace"};
		
		private List<Measurer> entries;
		
		private Object[][] data;
		
		public MeasurerListTableModel() {
			entries = mDAO.findAll();
			List<Measurer> tempMeasurer = new ArrayList<Measurer>();
			for(Measurer entry : entries) {
				if(!entry.getIsDeleted())
					tempMeasurer.add(entry);
			}
			entries = tempMeasurer;
			data = new String[entries.size()][columnNames.length];
			int count = 0;
			for(Measurer entry : entries) {
				data[count][0] = entry.getIdentification();
				data[count][1] = entry.getTitle();
				data[count][2] = entry.getType();
				data[count][3] = entry.getcalibrationDate().toString();
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

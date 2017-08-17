package com.datum.article;

import javax.swing.JTable;
import javax.swing.JTextPane;
import javax.swing.ListSelectionModel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;

class Meeting extends JTextPane implements ListSelectionListener {
	private JTable table;
	public Meeting() {
		super();
	}
	public JTable getTable() {
		return table;
	}
	public void setTable(JTable tbl) {
		table = tbl;
	}
	@Override
	public void valueChanged(ListSelectionEvent lse) {
		// TODO Auto-generated method stub
		ListSelectionModel  lsm = (ListSelectionModel)lse.getSource();
		int row = lsm.getLeadSelectionIndex();
		String meeting;
		DefaultTableModel model = (DefaultTableModel) table.getModel();
		try {
			meeting = (String)table.getValueAt(row, model.findColumn("MEETING"));
		}
		catch(NullPointerException npe) {
			meeting = "";
		}
		setText(meeting);
		table.repaint();
	}
}

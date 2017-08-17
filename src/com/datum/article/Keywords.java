package com.datum.article;

import javax.swing.JTable;
import javax.swing.JTextPane;
import javax.swing.ListSelectionModel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;

class Keywords extends JTextPane implements ListSelectionListener {
	private JTable table;
	public Keywords() {
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
		String keywords;
		DefaultTableModel model = (DefaultTableModel) table.getModel();
		try {
			keywords = (String)table.getValueAt(row, model.findColumn("KEYWORDS"));
		}
		catch(NullPointerException npe) {
			keywords = "";
		}
		setText(keywords);
		table.repaint();
	}
}

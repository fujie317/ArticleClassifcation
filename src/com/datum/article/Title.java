package com.datum.article;

import javax.swing.JTable;
import javax.swing.JTextPane;
import javax.swing.ListSelectionModel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;

class Title extends JTextPane implements ListSelectionListener {
	/**
	 * 
	 */
	private static final long serialVersionUID = 2048776888242327069L;
	private JTable table;
	public Title() {
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
		String title;
		DefaultTableModel model = (DefaultTableModel) table.getModel();
		try {
			title = (String)table.getValueAt(row, model.findColumn("TITLE"));
		}
		catch(NullPointerException npe) {
			title = "";
		}
		setText(title);
		table.repaint();
	}
}

package com.datum.article;

import javax.swing.JTable;
import javax.swing.JTextPane;
import javax.swing.ListSelectionModel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;

class Abs extends JTextPane implements ListSelectionListener {
	private static final long serialVersionUID = 8600565545857038171L;
	private JTable articles;
	public Abs() {
		super();
	}
	public JTable getArticles() {
		return articles;
	}
	public void setArticles(JTable tbl) {
		articles = tbl;
	}
	@SuppressWarnings("unused")
	@Override
	public void valueChanged(ListSelectionEvent lse) {
		// TODO Auto-generated method stub
		ListSelectionModel  lsm = (ListSelectionModel)lse.getSource();
		int row = lsm.getLeadSelectionIndex();
		String abs;
		DefaultTableModel model = (DefaultTableModel) articles.getModel();
		try {
			abs = (String)articles.getValueAt(row, model.findColumn("ABSTRACT"));
		}
		catch(NullPointerException npe) {
			abs = "";
		}
		if (lsm == null) 
			//Nothing is selected.    
			return;
		else 
			setText(abs);
		articles.repaint();
	}
}

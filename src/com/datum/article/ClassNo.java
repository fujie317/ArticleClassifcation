package com.datum.article;

import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.JTree;
import javax.swing.ListSelectionModel;
import javax.swing.SwingConstants;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.tree.DefaultMutableTreeNode;

class ClassNo extends JTextField implements TreeSelectionListener, ListSelectionListener {
	private JTable articles;
	private String code;                                  // class code from main table
	private String ext;                                   // class code from secondary table
	private final static Double StartIDofSecTbl = new Double(54000);     // largest main tbl ID must be smaller
	public ClassNo() {
		super()	;
		code = "";
		setHorizontalAlignment(SwingConstants.CENTER);
		addMouseListener(new ContextMenuMouseListener());
	}
	@Override
	public void valueChanged(TreeSelectionEvent tse) {
		// TODO Auto-generated method stub
		ext = "";
		JTree tree = (JTree)tse.getSource();
		DefaultMutableTreeNode node = (DefaultMutableTreeNode)tree.getLastSelectedPathComponent();
		ClassInfo nodeInfo;
		if (node == null)
			//Nothing is selected.     
			return;
		else {
			nodeInfo = (ClassInfo)node.getUserObject();
			if(nodeInfo.getID() < StartIDofSecTbl) 
				code = nodeInfo.getCode();
			else 
				ext = nodeInfo.getCode();
			setText(code + ext);
			setCaretPosition(code.length() + ext.length());
		}
	}
	@Override
	public void valueChanged(ListSelectionEvent lse) {
		// TODO Auto-generated method stub
		ListSelectionModel  lsm = (ListSelectionModel)lse.getSource();
		int row = lsm.getLeadSelectionIndex();
		String code1 = null;
		DefaultTableModel model = (DefaultTableModel) articles.getModel();
		try {
			code1 = (String)articles.getValueAt(row, model.findColumn("CLASSCODE1"));
		}
		catch(NullPointerException npe) {
			code1 = "";
		}
		catch(ArrayIndexOutOfBoundsException aobe) {
		}
		if (lsm == null) 
			//Nothing is selected.    
			return;
		else 
			setText(code1);
		articles.repaint();
	}
	public JTable getArticles() {
		return articles;
	}
	public void setArticles(JTable articles) {
		this.articles = articles;
	}
}

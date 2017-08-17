package com.datum.article;

import javax.swing.JTextArea;
import javax.swing.JTree;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.tree.DefaultMutableTreeNode;

class ClassNoDes extends JTextArea implements TreeSelectionListener {
	public ClassNoDes(String classInfo) {
		setText(classInfo);
	}
	@Override
	public void valueChanged(TreeSelectionEvent tse) {
		// TODO Auto-generated method stub
		JTree tree = (JTree)tse.getSource();
		DefaultMutableTreeNode node = (DefaultMutableTreeNode)tree.getLastSelectedPathComponent();
		ClassInfo nodeInfo;
		if (node == null)
			//Nothing is selected.     
			return;
		else {
			nodeInfo = (ClassInfo)node.getUserObject();
			setText(nodeInfo.toString() + "\n\n\t" + nodeInfo.getMemo() + "\n\n" + nodeInfo.getKeywords()	);
			setCaretPosition(0);
		}
	}
}

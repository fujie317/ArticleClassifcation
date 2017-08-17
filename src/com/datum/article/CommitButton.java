package com.datum.article;

import java.awt.Point;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.Box;
import javax.swing.JButton;
import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;
import javax.swing.JRadioButtonMenuItem;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.JViewport;

public class CommitButton extends JButton  implements ActionListener{
	JTextField classNo;
	JTable articles;
	public CommitButton(String text) {
		super(text);
		// TODO Auto-generated constructor stub
		JPopupMenu commitMenu = new JPopupMenu();
		// Classcode1
		JMenuItem code1 = new JMenuItem("CLASSCODE1");
		code1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ae) {
				try {
					articles.setValueAt(classNo.getText(), articles.getSelectedRow(), articles.getColumn("CLASSCODE1").getModelIndex() );
				}
				catch(NullPointerException npe) {
				}
			}
		});
		commitMenu.add(code1);
		commitMenu.add( Box.createVerticalStrut( 5 ) );
		// Classcode2
		JMenuItem code2 = new JMenuItem("CLASSCODE2");
		code2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ae) {
				try {
					articles.setValueAt(classNo.getText(), articles.getSelectedRow(), articles.getColumn("CLASSCODE2").getModelIndex() );
				}
				catch(NullPointerException npe) {
				}
			}
		});
		commitMenu.add(code2);
		commitMenu.add( Box.createVerticalStrut( 5 ) );
		// Classcode3
		JMenuItem code3 = new JMenuItem("CLASSCODE3");
		code3.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ae) {
				try {
					articles.setValueAt(classNo.getText(), articles.getSelectedRow(), articles.getColumn("CLASSCODE3").getModelIndex() );
				}
				catch(NullPointerException npe) {
				}
			}
		});
		commitMenu.add(code3);
		setComponentPopupMenu(commitMenu);
		// add actionListener 
		addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ae) {
				int row, col;
				try {
					row = articles.getSelectedRow();
					col = articles.getColumn("CLASSCODE1").getModelIndex();
					articles.setValueAt(classNo.getText(), row, col);
					row++;
					articles.setRowSelectionInterval(row, row);
					articles.scrollRectToVisible(articles.getCellRect(row,col, true)); 
					 JViewport vp = (JViewport) articles.getParent();
           Rectangle rect = articles.getCellRect(row-3,0, true);
           vp.setViewPosition( new Point(rect.x, rect.y ) );
           articles.revalidate();
           articles.repaint();
				}
				catch(NullPointerException npe) {
				}
				catch(ArrayIndexOutOfBoundsException aiobe) {
				}
				catch(IllegalArgumentException iae) {
				}
			}
		});
	}
	void setClassNo (JTextField classNo){
		this.classNo = classNo;
	}
	void setArticles(JTable articles) {
		this.articles = articles;
	}
	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		String selectedMode = ((JRadioButtonMenuItem)(e.getSource())).getActionCommand();
		if(selectedMode.equalsIgnoreCase("single")) 
			setEnabled(false);
		else
			setEnabled(true);
	}
}

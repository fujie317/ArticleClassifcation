package com.datum.article;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JRadioButtonMenuItem;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;

public class CodeGroup implements ActionListener, ListSelectionListener{
	private ArrayList<ClassButton> buttons = new ArrayList<>();
	private Connection connection;
	private JTable articles;
	private Article article;
	private String selectedMode = "auto";

	public CodeGroup(Connection connection) {
		this.connection = connection;
	}
	
	public ArrayList<ClassButton> getButtons() {
		return buttons;
	}

	public void setButtons(ArrayList<ClassButton> buttons) {
		this.buttons = buttons;
	}

	public void setArticles(JTable articles) {
		this.articles = articles;
	}

	public void setArticle(Article article) {
		this.article = article;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		try{
			selectedMode = ((JRadioButtonMenuItem)(e.getSource())).getActionCommand();
			if(selectedMode.equalsIgnoreCase("auto") || selectedMode.equalsIgnoreCase("single") || selectedMode.equalsIgnoreCase("offline") || selectedMode.equalsIgnoreCase("qc")) {
				for(ClassButton cb: buttons)
					cb.setEnabled(true);
			}
			if(selectedMode.equalsIgnoreCase("manual")) {
				for(ClassButton cb: buttons)
					cb.setEnabled(false);
			}	
		}
		catch (ClassCastException cse) {
		}
		String runButtonText = null;
		try{
			runButtonText = ((JButton)(e.getSource())).getActionCommand();
			if(runButtonText.equalsIgnoreCase("Run")) {
				article.lookup(connection);
				String[] classCodes = article.getClassCodes();
				int i;
				for(i = 0; i < classCodes.length; ++i){
					buttons.get(i).setActionCommand(classCodes[i]);
					buttons.get(i).setText(classCodes[i]);
				}
			}
		}
		catch (ClassCastException cse) {
		}
	}
	
	@Override
	public void valueChanged(ListSelectionEvent lse) {
		// TODO Auto-generated method stub
		ListSelectionModel  lsm = (ListSelectionModel)lse.getSource();
		int row = lsm.getLeadSelectionIndex();
		String title;
		DefaultTableModel model = (DefaultTableModel) articles.getModel();
		try {
			title = (String)articles.getValueAt(row, model.findColumn("TITLE"));
		}
		catch(NullPointerException npe) {
			title = "";
		}
		String keywords;
		try {
			keywords = (String)articles.getValueAt(row, model.findColumn("KEYWORDS"));
		}
		catch(NullPointerException npe) {
			keywords = "";
		}
		String abs;
		try {
			abs = (String)articles.getValueAt(row, model.findColumn("ABSTRACT"));
		}
		catch(NullPointerException npe) {
			abs = "";
		}
		String meeting;
		try {
			meeting = (String)articles.getValueAt(row, model.findColumn("MEETING"));
		}
		catch(NullPointerException npe) {
			meeting = "";
		}
		article = new Article(title, keywords, abs, meeting);
		if (lsm == null) 
			//Nothing is selected.    
			return;
		else if(selectedMode.equalsIgnoreCase("auto") || selectedMode.equalsIgnoreCase("qc")) {
			article.lookup(connection);
			String[] classCodes = article.getClassCodes();
			int i;
			if(classCodes != null && classCodes.length > 0)
				for(i = 0; i < classCodes.length; ++i){
					buttons.get(i).setActionCommand(classCodes[i]);
					buttons.get(i).setText(classCodes[i]);
				}
		}
		articles.repaint();
	}
}

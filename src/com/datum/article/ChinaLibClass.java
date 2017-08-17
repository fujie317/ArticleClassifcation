package com.datum.article;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.Iterator;

import javax.swing.JButton;
import javax.swing.JTextField;
import javax.swing.JTree;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.text.BadLocationException;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.TreePath;
import javax.swing.tree.TreeSelectionModel;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

class ChinaLibClass implements ActionListener, DocumentListener{
	private static final long serialVersionUID = 1000L;
	private JTree tree;
	private ArrayList<Double>  ids = new ArrayList<Double>();
	private ArrayList<DefaultMutableTreeNode> nodes = new ArrayList<DefaultMutableTreeNode>();
	private DefaultMutableTreeNode rootNode = new DefaultMutableTreeNode(new ClassInfo(-2.0, -3.0, "", "《中国图书馆分类法》第五版", "", ""));
	private DefaultMutableTreeNode mainNode = new DefaultMutableTreeNode(new ClassInfo(-1.0, -2.0, "", "主表", "", ""));
	private DefaultMutableTreeNode secNode = new DefaultMutableTreeNode(new ClassInfo(0.0, -2.0, "", "通用复分表", "", ""));
	public ChinaLibClass() {
		ids.add(-2.0);
		ids.add(0.0);
		ids.add(-1.0);
		nodes.add(rootNode);
		nodes.add(mainNode);
		nodes.add(secNode);
		rootNode.add(mainNode); 
		rootNode.add(secNode);
	
		populateTree("files" + File.separator + "clsdef0.xlsx", 4722);
		populateTree("files" + File.separator + "clsdef1.xlsx", 5442);
		populateTree("files" + File.separator + "clsdef2.xlsx", 4526);
		populateTree("files" + File.separator + "clsdef3.xlsx", 3484);
		populateTree("files" + File.separator + "clsdef3a.xlsx", 3798);
		populateTree("files" + File.separator + "clsdef4.xlsx", 4717);
		populateTree("files" + File.separator + "clsdef5.xlsx", 5454);
		populateTree("files" + File.separator + "clsdef6.xlsx", 4859);
		populateTree("files" + File.separator + "clsdef7.xlsx", 4827);
		populateTree("files" + File.separator + "clsdef8.xlsx", 5807);
		populateTree("files" + File.separator + "clsdef9.xlsx", 5292);
		populateTree("files" + File.separator + "secTables.xlsx", 1231);
		tree = new JTree(rootNode);
		tree.getSelectionModel().setSelectionMode(TreeSelectionModel.SINGLE_TREE_SELECTION);
	}
	void populateTree(String classDef, int size){
		 FileInputStream fis = null;
	//	java.io.InputStream is = null;
		XSSFWorkbook workbook = null;
		try {
			//ClassLoader cl = this.getClass().getClassLoader();
			//is = cl.getResourceAsStream(classDef);
			//workbook = new XSSFWorkbook(is);

			fis = new FileInputStream(new File(classDef));
			workbook = new XSSFWorkbook(fis);   // replace above three lines to work in Eclipse, not JAR
		} 
		catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		XSSFRow row;
		XSSFSheet spreadsheet = workbook.getSheetAt(0);
		Iterator < Row > rowIterator = spreadsheet.iterator();		
		int idCol=0, codeCol=0, nameCol=0, memoCol=0, keywordsCol=0, parentCol=0;
		row = (XSSFRow) rowIterator.next();
		if(row.getRowNum() == 0){
			for(int i = 0; i < row.getLastCellNum(); ++i){
				switch(row.getCell(i).getStringCellValue()){
				case "ID": idCol = i;
				break;
				case "ClassCode": codeCol = i;
				break;
				case "ClassName": nameCol = i;
				break;
				case "Memo": memoCol = i;
				break;
				case "Keywords": keywordsCol = i;
				break;
				case "ParentID": parentCol = i;
				break;
				}
			}
		}
		ClassInfo clsInfo;
		DefaultMutableTreeNode node = null;
		Double ID;
		Double parentID;
		String code;
		String name;
		String memo;
		String keywords;
		int counter = 0;
		while (counter < size){
			counter++;
			try{
				row = (XSSFRow) rowIterator.next();
			}
			catch(Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			ID = row.getCell(idCol).getNumericCellValue();
			parentID = row.getCell(parentCol).getNumericCellValue();
			try {
				code = row.getCell(codeCol).getStringCellValue();
			}
			catch(IllegalStateException lse) {
				Double tmpCode = row.getCell(codeCol).getNumericCellValue();
				code = tmpCode.toString();
				if(code.endsWith(".0")) {
					code = code.substring(0, code.length() - 2);
				}
			}
			name = row.getCell(nameCol).getStringCellValue();
			try {
				memo = row.getCell(memoCol).getStringCellValue();
			}
			catch(NullPointerException exc) {
				memo = "";
			}
			try {
				keywords = row.getCell(keywordsCol).getStringCellValue();
			}
			catch(NullPointerException exc){
				keywords = "";
			}
			clsInfo = new ClassInfo(ID, parentID, code, name, memo, keywords);
			node = new DefaultMutableTreeNode (clsInfo);
			ids.add(ID);
			nodes.add(node);
			try{
				nodes.get(ids.indexOf(parentID)).add(node); 
			}
			catch (NullPointerException exc){}
			catch(ArrayIndexOutOfBoundsException aiobe) {
				System.out.println(parentID);
			}
		}
		try {
			workbook.close();
			//is.close();
			fis.close();
		} 
		catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}	
	}
	@Override
	public void actionPerformed(ActionEvent ae) {
		// TODO Auto-generated method stub
		JButton btn;
		JTextField classField;
		if(ae.getSource() instanceof JButton) {
			btn = (JButton) ae.getSource();
			find(tree, btn.getActionCommand()	);
		}
		else if(ae.getSource() instanceof JTextField) {
			classField = (JTextField) ae.getSource();
			find(tree, classField.getText());
		}
	}

	private void find(JTree tree, String s) {
		@SuppressWarnings("unchecked")
		Enumeration<DefaultMutableTreeNode> e = ((DefaultMutableTreeNode)tree.getModel().getRoot()).depthFirstEnumeration();
		TreePath path;
		while (e.hasMoreElements()) {
			DefaultMutableTreeNode node = e.nextElement();
			if (((ClassInfo)node.getUserObject()).getCode().equalsIgnoreCase(s)) {
				path = new TreePath(node.getPath());
				tree.setSelectionPath(path);
				tree.scrollPathToVisible(path);
			}
		}
	}
	public JTree getTree() {
		return tree;
	}

	@Override
	public void insertUpdate(DocumentEvent de) {
		// TODO Auto-generated method stub

	}
	@Override
	public void removeUpdate(DocumentEvent de) {
		// TODO Auto-generated method stub
		String classField = null;
		try {
			classField = de.getDocument().getText(0, de.getLength());
		} catch (BadLocationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		find(tree, classField);
	}
	@Override
	public void changedUpdate(DocumentEvent de) {
		// TODO Auto-generated method stub

	}
}

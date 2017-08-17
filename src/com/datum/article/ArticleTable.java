package com.datum.article;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Vector;

import javax.swing.JFileChooser;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class ArticleTable implements ActionListener {
	private File file;
	private DefaultTableModel model;

	public ArticleTable( DefaultTableModel model) {
		this.model = model;
	}
	@Override
	public void actionPerformed(ActionEvent e) {
		//Handle open button action.
		String src = ((JMenuItem)e.getSource()).getActionCommand();
		if(src.equalsIgnoreCase("Open")) {
			try {
				populateTable();
			}
			catch(FileNotFoundException ex) {
				ex.printStackTrace();
			}
		}
		else if(src.equalsIgnoreCase("Close")){
				closeTable();
		}
		else if(src.equalsIgnoreCase("Save")){
			updateTable();
		}
		else if(src.equalsIgnoreCase("Save As")) {
			JFileChooser fileChooser;
			File newfile = null;
			do{
				fileChooser = new JFileChooser();
				if (fileChooser.showSaveDialog(null) == JFileChooser.APPROVE_OPTION) 
					newfile = fileChooser.getSelectedFile();
				if(newfile.exists()) 
					JOptionPane.showMessageDialog(null, "File Already Exists! Choose a New One.", "Warning!", JOptionPane.WARNING_MESSAGE);
			}while(newfile.exists());
			createNew(newfile);
			file = newfile;
		}
	}
	@SuppressWarnings("unused")
	private void createNew(File output) {
		try {
			FileInputStream fis= new FileInputStream(file);
			XSSFWorkbook workbook = new XSSFWorkbook(fis);
			XSSFSheet sheet = workbook.getSheetAt(0);
			DataFormatter fmt = new DataFormatter();
			Map<String,Integer> headings = new HashMap<String,Integer>();
			XSSFRow topRow = sheet.getRow(0);
			if (headings == null) throw new IllegalArgumentException("Empty headings row");
			for (Cell c : topRow) {
				headings.put(fmt.formatCellValue(c), c.getColumnIndex());
			}			
			int code1 = model.findColumn("CLASSCODE1");
			int code2 = model.findColumn("CLASSCODE2");
			int code3 = model.findColumn("CLASSCODE3");

			int col1 = headings.get("CLASSCODE1");
			int col2 = headings.get("CLASSCODE2");
			int col3 = headings.get("CLASSCODE3");
			for(int row=1; row <= sheet.getLastRowNum(); row++) {
				try {
					sheet.getRow(row).getCell(col1).setCellValue((String)(model.getValueAt(row - 1, code1)));
				}
				catch(NullPointerException npe) {
					sheet.getRow(row).createCell(col1).setCellValue((String)(model.getValueAt(row - 1, code1)));
				}
				try {
					sheet.getRow(row).getCell(col2).setCellValue((String)model.getValueAt(row - 1, code2));
				}
				catch(NullPointerException npe) {
					sheet.getRow(row).createCell(col2).setCellValue((String)model.getValueAt(row - 1, code2));
				}
				try {
					sheet.getRow(row).getCell(col3).setCellValue((String)model.getValueAt(row - 1, code3));
				}
				catch(NullPointerException npe) {
					sheet.getRow(row).createCell(col3).setCellValue((String)model.getValueAt(row - 1, code3));
				}
			}
			fis.close();

			FileOutputStream fos =new FileOutputStream(output);
			workbook.write(fos);
			workbook.close();
			fos.close();

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	private void updateTable() {
		try {
			FileInputStream fis= new FileInputStream(file);
			XSSFWorkbook workbook = new XSSFWorkbook(fis);
			XSSFSheet sheet = workbook.getSheetAt(0);
			DataFormatter fmt = new DataFormatter();
			Map<String,Integer> headings = new HashMap<String,Integer>();
			XSSFRow topRow = sheet.getRow(0);
	//		if (headings == null) throw new IllegalArgumentException("Empty headings row");
			for (Cell c : topRow) {
				headings.put(fmt.formatCellValue(c), c.getColumnIndex());
			}			
			int code1 = model.findColumn("CLASSCODE1");
			int code2 = model.findColumn("CLASSCODE2");
			int code3 = model.findColumn("CLASSCODE3");

			int col1 = headings.get("CLASSCODE1");
			int col2 = headings.get("CLASSCODE2");
			int col3 = headings.get("CLASSCODE3");
			for(int row=1; row <= sheet.getLastRowNum(); row++) {
				try {
					sheet.getRow(row).getCell(col1).setCellValue((String)(model.getValueAt(row - 1, code1)));
				}
				catch(NullPointerException npe) {
					sheet.getRow(row).createCell(col1).setCellValue((String)(model.getValueAt(row - 1, code1)));
				}
				try {
					sheet.getRow(row).getCell(col2).setCellValue((String)model.getValueAt(row - 1, code2));
				}
				catch(NullPointerException npe) {
					sheet.getRow(row).createCell(col2).setCellValue((String)model.getValueAt(row - 1, code2));
				}
				try {
					sheet.getRow(row).getCell(col3).setCellValue((String)model.getValueAt(row - 1, code3));
				}
				catch(NullPointerException npe) {
					sheet.getRow(row).createCell(col3).setCellValue((String)model.getValueAt(row - 1, code3));
				}
			}
			fis.close();

			FileOutputStream fos =new FileOutputStream(file);
			workbook.write(fos);
			workbook.close();
			fos.close();

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private void closeTable() {
		if(model.getRowCount() > 0)
			model.setRowCount(0);
	}
	
	private void populateTable() throws FileNotFoundException {
		if(model.getRowCount() > 0)
			model.setRowCount(0);
		JFileChooser fc = new JFileChooser();
		fc.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
		int returnVal;
		returnVal = fc.showOpenDialog(null);
		if (returnVal == JFileChooser.APPROVE_OPTION) {
			file = fc.getSelectedFile();
			FileInputStream fis = new FileInputStream(file);
			XSSFWorkbook workbook = null;
			try {
				workbook = new XSSFWorkbook(fis);
			} 
			catch (IOException ioe) {
				// TODO Auto-generated catch block
				ioe.printStackTrace();
			}
			XSSFSheet sheet = workbook.getSheetAt(0);
			DataFormatter fmt = new DataFormatter();
			Map<String,Integer> headings = new HashMap<String,Integer>();
			Row topRow = sheet.getRow(0);
	//		if (headings == null) throw new IllegalArgumentException("Empty headings row");
			for (Cell c : topRow) {
				headings.put(fmt.formatCellValue(c), c.getColumnIndex());
			}			
			String ID, title,  code1, code2, code3, keyword, abs, meeting;
			for(int row = 1; row <= sheet.getLastRowNum(); row++){
				Vector<String> rowData = new Vector<>();
				try {
					ID = sheet.getRow(row).getCell(headings.get("ID")).getStringCellValue();
				}
				catch(NullPointerException npe) {
					ID = "";
				}
				rowData.add(ID);
				try {
					title = sheet.getRow(row).getCell(headings.get("TITLE")).getStringCellValue();
				}
				catch(Exception eee){
					title = "";
				}
				rowData.add(title);
				try{
					code1 = sheet.getRow(row).getCell(headings.get("CLASSCODE1")).getStringCellValue();
				}
				catch(NullPointerException npe) {
					code1 = "";
				}
				rowData.add(code1);
				try {
					code2 = sheet.getRow(row).getCell(headings.get("CLASSCODE2")).getStringCellValue();
				}
				catch(NullPointerException npe) {
					code2 = "";
				}
				rowData.add(code2);
				try {
					code3 = sheet.getRow(row).getCell(headings.get("CLASSCODE3")).getStringCellValue();
				}
				catch(NullPointerException npe) {
					code3 = "";
				}
				rowData.add(code3);
				try {
					keyword = sheet.getRow(row).getCell(headings.get("KEYWORD")).getStringCellValue();
				}
				catch(NullPointerException npe) {
					keyword = "";
				}
				rowData.add(keyword);
				try {
					abs = sheet.getRow(row).getCell(headings.get("ABSTRACT")).getStringCellValue();
				}
				catch(NullPointerException npe) {
					abs = "";
				}
				rowData.add(abs);
				try {
					meeting = sheet.getRow(row).getCell(headings.get("MEETING_NAME")).getStringCellValue();
				}
				catch(NullPointerException npe) {
					meeting = "";
				}
				rowData.add(meeting);
				model.addRow(rowData);
			}
			try {
				workbook.close();
				fis.close();
			} 
			catch (IOException ioe) {
				// TODO Auto-generated catch block
				ioe.printStackTrace();
			}	
		}
	}
}

package com.datum.article;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.lang.reflect.Field;
import java.net.Socket;
import java.nio.charset.Charset;

import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.text.BadLocationException;

public class Article implements DocumentListener {
	String title;
	String keywords;
	String abs;
	String meeting;
	String [] classCodes;

	public Article () {
	}
	
	public Article (String title, String keywords, String abs, String meeting) {
		this.title = title;
		this.keywords = keywords;
		this.abs = abs;
		this.meeting = meeting;
	}
	
	private String cutText(String original, String start, String end){
	  int s, e;                                          // start and end indecies
	  s = original.indexOf(start);
	  e = original.lastIndexOf(end);
	  if(s == -1 || e == -1 || s > e)
	    return original;
	  return original.substring(s + start.length(), e);
	}
	public void lookup(Connection connection) {
		// TODO Auto-generated method stub
		System.setProperty("file.encoding","UTF-8");
		Field charset;
		try {
			charset = Charset.class.getDeclaredField("defaultCharset");
			charset.setAccessible(true);
			charset.set(null, null);
		}catch (IllegalArgumentException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IllegalAccessException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (NoSuchFieldException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SecurityException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		String userInput = cutText(title, "在", "应用")  + "##" + keywords + "##" + abs + "##" + meeting + "\n";
		Socket socket = null;
		try {
			if(title != null && title.length() > 0) {
				socket = new Socket(connection.getHost(), connection.getPort());
				BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
				PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
				out.println(userInput);
				classCodes = in.readLine().split("##");
			}
			else {
				classCodes = null;
			}
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			System.err.println("Socket I/O error for the connection to " +  connection.getHost());
			e1.printStackTrace();
		}catch(NullPointerException npe) {
			System.out.println("socket stream error!");
		}
	}
	public void insertUpdate(DocumentEvent e) {
		String name = (String)(e.getDocument().getProperty("name"));
		if(name == "title") {
			try {
				setTitle(e.getDocument().getText(0, e.getDocument().getLength()));
			}catch(BadLocationException ble) {
				System.out.println("Error getting title text!");
			}
		}
		if(name == "keywords") {
			try {
				setKeywords(e.getDocument().getText(0, e.getDocument().getLength()));
			}catch(BadLocationException ble) {
				System.out.println("Error getting keywords text!");
			}
		}
		if(name == "abs") {
			try {
				setAbs(e.getDocument().getText(0, e.getDocument().getLength()));
			}catch(BadLocationException ble) {
				System.out.println("Error getting abstract text!");
			}
		}
		if(name == "meeting") {
			try {
			setMeeting(e.getDocument().getText(0, e.getDocument().getLength()));
			}catch(BadLocationException ble) {
				System.out.println("Error getting meeting text!");
			}
		}
}
public void removeUpdate(DocumentEvent e) {
}
public void changedUpdate(DocumentEvent e) {
    //Plain text components do not fire these events
}
public void setClassCodes(String[] classCodes) {
	this.classCodes = classCodes;
}
public String[] getClassCodes() {
		return classCodes;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getTitle() {
		return title;
	}

	public String getKeywords() {
		return keywords;
	}

	public String getAbs() {
		return abs;
	}

	public String getMeeting() {
		return meeting;
	}

	public void setKeywords(String keywords) {
		this.keywords = keywords;
	}
	public void setAbs(String abs) {
		this.abs = abs;
	}
	public void setMeeting(String meeting) {
		this.meeting = meeting;
	}
}

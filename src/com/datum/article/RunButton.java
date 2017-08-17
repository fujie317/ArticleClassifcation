package com.datum.article;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JRadioButtonMenuItem;

public class RunButton extends JButton implements ActionListener {
	private static final long serialVersionUID = -802193093707630901L;

	public RunButton(String str) {
		super("Run");
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		String selectedMode = ((JRadioButtonMenuItem)(e.getSource())).getActionCommand();
		if(selectedMode.equalsIgnoreCase("auto") ||  selectedMode.equalsIgnoreCase("manual")) {
			setEnabled(false);
		}
		if(selectedMode.equalsIgnoreCase("qc") || selectedMode.equalsIgnoreCase("single")) {
			setEnabled(true);
		}
	}
}

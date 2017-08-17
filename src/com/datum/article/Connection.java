package com.datum.article;

import java.awt.Component;
import java.awt.Container;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;

import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

public class Connection implements ActionListener{
	String host;
	int port;
	Socket socket;
	BufferedReader in;
	PrintWriter out;

	public Connection() {
	}
	private int enterHostURLPort() {
		JTextField hostField = new JTextField(20);
		JTextField portField = new JTextField(20);
		
		GridBagConstraints gbc = new GridBagConstraints();

		gbc.weighty = 1.0;
		gbc.fill = GridBagConstraints.BOTH;
		gbc.gridwidth = 1;
		gbc.ipadx = 10;
		gbc.insets = new Insets(5, 5, 5, 5);
		JPanel svrPanel = new JPanel();
		svrPanel.setLayout(new GridBagLayout());
		JLabel hostLabel = new JLabel("Host IP Address:");
		hostLabel.setHorizontalAlignment(SwingConstants.RIGHT);
		gbc.weightx = 0.2;
		gbc.anchor = GridBagConstraints.EAST;
		addGB(svrPanel, hostLabel, 0, 0, gbc);
		gbc.weightx = 0.8;
		gbc.anchor = GridBagConstraints.WEST;
		addGB(svrPanel, hostField, 1, 0, gbc);
		hostField.requestFocus();
		
		JLabel portLabel = new JLabel("Server Port Number:");
		portLabel.setHorizontalAlignment(SwingConstants.RIGHT);
		gbc.weightx = 0.2;
		gbc.anchor = GridBagConstraints.EAST;
		addGB(svrPanel, portLabel, 0, 1, gbc);
		gbc.weightx = 0.8;
		gbc.anchor = GridBagConstraints.WEST;
		addGB(svrPanel, portField, 1, 1, gbc);
		int result = JOptionPane.showConfirmDialog(null, svrPanel, 
				"Server Host URL or IP & Port Number",  JOptionPane.PLAIN_MESSAGE);
		try 	{
			if(result == JOptionPane.OK_OPTION) {
				host = hostField.getText();
				port = Integer.parseInt(portField.getText());
			}
			socket = new Socket(host, port);
			in =  new BufferedReader(new InputStreamReader(socket.getInputStream()));
			out =  new PrintWriter(socket.getOutputStream(), true);
		} catch (UnknownHostException e) {
			JOptionPane.showMessageDialog(null, "Unreachable host or wrong port number!");
			port = enterHostURLPort();
		} catch (IOException e) {
			JOptionPane.showMessageDialog(null, "Connection to server failed!");
			port = enterHostURLPort();
		} catch(NumberFormatException e) {
			// Do nothing
		}
		return port;
	}

	//Helper method
	private void addGB(Container cont, Component comp, int x, int y, GridBagConstraints gbc) {
		gbc.gridx = x; 
		gbc.gridy = y;
		cont.add(comp, gbc);
	}
	
	public String getHost() {
		return host;
	}
	public int getPort() {
		return port;
	}
	public Socket getSocket() {
		return socket;
	}
	public BufferedReader getIn() {
		return in;
	}
	public PrintWriter getOut() {
		return out;
	}
	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		port = enterHostURLPort();
	}
}

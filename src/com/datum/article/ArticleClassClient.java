package com.datum.article;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

import javax.swing.border.*;
import javax.swing.table.DefaultTableModel;

public class ArticleClassClient extends JFrame implements ActionListener {
	static void renderSplashFrame(Graphics2D g, int frame) {
		final String[] comps = {"Datum", "Data", "Shanghai", "Library", "Article", "Classification"};
		g.setComposite(AlphaComposite.Clear);
		g.fillRect(120,140,200,40);
		g.setPaintMode();
		g.setColor(Color.MAGENTA);
		g.drawString(comps[(frame/5)%6]+" ...", 120, 150);
	}
	private static final long serialVersionUID = 9185713514995202694L;
	static final int CHOICES = 12;
	GridBagConstraints gbc = new GridBagConstraints();
	public ArticleClassClient() {
		super("Article AutoClassification System");
		setDefaultCloseOperation( JFrame.EXIT_ON_CLOSE );
		pack();
		setSize( 1000, 600 );
    getRootPane().setBorder(BorderFactory.createEtchedBorder());
		setLocation(20, 40);

		// Main menu
		JMenuBar mb = new JMenuBar();
		mb.setMargin(new Insets(20, 20, 20, 20));
		mb.setPreferredSize(new Dimension(100, 40));
		mb.setBorder(BorderFactory.createBevelBorder(CHOICES, getForeground(), getForeground()));
		Font f = new Font("sans-serif", Font.BOLD, 16);
		UIManager.put("Menu.font", f);
		JMenu file = new JMenu("File", true);
		file.setMargin(new Insets(20, 20, 20, 20));
		file.add( Box.createVerticalStrut( 5) );
		JMenuItem open = new JMenuItem("Open");
		open.setMargin(new Insets(20, 20, 20, 20));
		file.add(open);
		file.add( Box.createVerticalStrut( 10) );
		JMenuItem close = new JMenuItem("Close");
		close.setMargin(new Insets(20, 20, 20, 20));
		file.add(close);
		file.add( Box.createVerticalStrut( 10) );
		JMenuItem save = new JMenuItem("Save");
		save.setMargin(new Insets(20, 20, 20, 20));
		file.add(save);
		file.add( Box.createVerticalStrut( 10) );
		JMenuItem saveAs = new JMenuItem("Save As");
		saveAs.setMargin(new Insets(20, 20, 20, 20));
		file.add(saveAs);
		file.add( Box.createVerticalStrut( 10) );
		JMenuItem quit = new JMenuItem("Quit");
		quit.setMargin(new Insets(20, 20, 20, 20));
		quit.addActionListener(this);
		file.add(quit);
		file.add( Box.createVerticalStrut( 5) );

		JMenu mode = new JMenu("Mode", true);
		mode.setMargin(new Insets(20, 20, 20, 20));
		mode.add( Box.createVerticalStrut( 5) );

		ButtonGroup modeGroup = new ButtonGroup();

		JRadioButtonMenuItem manualMode = new JRadioButtonMenuItem("Manual");
		manualMode.setMargin(new Insets(20, 20, 20, 20));
		modeGroup.add(manualMode);
		mode.add(manualMode);
		mode.add( Box.createVerticalStrut( 10) );
		JRadioButtonMenuItem singleMode = new JRadioButtonMenuItem("Single");
		singleMode.setMargin(new Insets(20, 20, 20, 20));
		modeGroup.add(singleMode);
		mode.add(singleMode);
		mode.add( Box.createVerticalStrut( 10) );
		JRadioButtonMenuItem autoMode = new JRadioButtonMenuItem("Auto");
		autoMode.setMargin(new Insets(20, 20, 20, 20));
		autoMode.setSelected(true);
		modeGroup.add(autoMode);
		mode.add(autoMode);
		mode.add( Box.createVerticalStrut( 10) );
		JRadioButtonMenuItem qcMode = new JRadioButtonMenuItem("QC");
		qcMode.setMargin(new Insets(20, 20, 20, 20));
		modeGroup.add(qcMode);
		mode.add(qcMode);
		mode.add( Box.createVerticalStrut( 5) );

		JMenu conxion = new JMenu("Connection", true);
		conxion.setMargin(new Insets(20, 20, 20, 20));
		conxion.add( Box.createVerticalStrut( 5) );
		JMenuItem connect = new JMenuItem("Connect");
		connect.setMargin(new Insets(20, 20, 20, 20));
		conxion.add(connect);
		conxion.add( Box.createVerticalStrut( 10) );
		JMenuItem reconnect = new JMenuItem("Reconnect");
		reconnect.setMargin(new Insets(20, 20, 20, 20));
		conxion.add(reconnect);
		conxion.add( Box.createVerticalStrut( 5) );

		JMenu help = new JMenu("Help", true);
		help.setMargin(new Insets(20, 20, 20, 20));
		help.add( Box.createVerticalStrut( 5) );
		JMenuItem tut = new JMenuItem("Tutorial");
		tut.setMargin(new Insets(20, 20, 20, 20));
		help.add(tut);
		help.add( Box.createVerticalStrut( 10) );
		JMenuItem about = new JMenuItem("About");
		about.setMargin(new Insets(20, 20, 20, 20));
		help.add(about);
		help.add( Box.createVerticalStrut( 5) );

		this.addWindowListener(closeWindow);

		mb.add(file);
		mb.add( Box.createHorizontalStrut( 20 ) );
		mb.add(conxion);
		mb.add( Box.createHorizontalStrut( 20 ) );
		mb.add(mode);
		mb.add( Box.createHorizontalStrut( 20 ) );
		mb.add(help);
		setJMenuBar(mb);

		final SplashScreen splash = SplashScreen.getSplashScreen();
		if (splash == null) {
			System.out.println("SplashScreen.getSplashScreen() returned null");
			return;
		}
		Graphics2D g = splash.createGraphics();
		if (g == null) {
			System.out.println("g is null");
			return;
		}
		for(int i=0; i<200; i++) {
			renderSplashFrame(g, i);
			splash.update();
			try {
				Thread.sleep(20);
			}
			catch(InterruptedException e) {
			}
		}

		//National Article Classification Standard
		ChinaLibClass classTree = new ChinaLibClass();
		JTree leftUpper = classTree.getTree();
		JScrollPane leftUpperScroll = new JScrollPane(leftUpper);
		ClassNoDes leftLower = new ClassNoDes("Show Selected Class Info");
		leftLower.setEditable(false);
		leftLower.setLineWrap(true);
		leftLower.setWrapStyleWord(true);
		JScrollPane leftLowerScroll = new JScrollPane(leftLower);
		leftUpper.addTreeSelectionListener(leftLower);
		leftUpper.addTreeSelectionListener(leftLower);
		JSplitPane left = new JSplitPane(JSplitPane.VERTICAL_SPLIT, leftUpperScroll, leftLowerScroll);

		// Article data fields
		JPanel rightUpper = new JPanel(new GridBagLayout());
		Border subPanelBorder = BorderFactory.createLoweredBevelBorder();
		rightUpper.setBorder(BorderFactory.createTitledBorder(subPanelBorder, "Article Attributes", TitledBorder.CENTER, TitledBorder.TOP));
		gbc.weighty = 1.0;
		gbc.fill = GridBagConstraints.BOTH;
		gbc.gridwidth = 1;
		gbc.ipadx = 10;
		gbc.insets = new Insets(5, 5, 5, 5);

		JLabel titleLabel = new JLabel("TITLE:");
		titleLabel.setHorizontalAlignment(SwingConstants.RIGHT);
		gbc.weightx = 0.2;
		gbc.anchor = GridBagConstraints.EAST;
		addGB(rightUpper, titleLabel, 0, 0);
		Title title = new Title();
		gbc.weightx = 0.8;
		gbc.anchor = GridBagConstraints.WEST;
		gbc.gridwidth = 5;
		title.getDocument().putProperty("name", "title");
		addGB(rightUpper, title, 1, 0);

		JLabel keywordsLabel = new JLabel("KEYWORDS:");
		keywordsLabel.setHorizontalAlignment(SwingConstants.RIGHT);
		gbc.weightx = 0.2;
		gbc.anchor = GridBagConstraints.EAST;
		gbc.gridwidth = 1;
		addGB(rightUpper, keywordsLabel, 0, 1);
		Keywords keywords = new Keywords();
		gbc.weightx = 0.8;
		gbc.anchor = GridBagConstraints.WEST;
		gbc.gridwidth = 5;
		keywords.getDocument().putProperty("name", "keywords");
		addGB(rightUpper, keywords, 1, 1);

		JLabel absLabel = new JLabel("ABSTRACT:");
		absLabel.setHorizontalAlignment(SwingConstants.RIGHT);
		gbc.weightx = 0.2;
		gbc.anchor = GridBagConstraints.EAST;
		gbc.gridwidth = 1;
		addGB(rightUpper, absLabel, 0, 2);
		Abs abs = new Abs();
		abs.setCaretPosition(0);
		gbc.weightx = 0.8;
		gbc.anchor = GridBagConstraints.WEST;
		gbc.gridwidth = 5;
		abs.getDocument().putProperty("name", "abs");
		addGB(rightUpper, abs, 1, 2);

		JLabel meetLabel = new JLabel("MEETING:");
		meetLabel.setHorizontalAlignment(SwingConstants.RIGHT);
		gbc.weightx = 0.2;
		gbc.anchor = GridBagConstraints.EAST;
		gbc.gridwidth = 1;
		addGB(rightUpper, meetLabel, 0, 3);
		Meeting meeting = new Meeting();
		gbc.weightx = 0.8;
		gbc.anchor = GridBagConstraints.WEST;
		gbc.gridwidth = 5;
		meeting.getDocument().putProperty("name", "meeting");
		addGB(rightUpper, meeting, 1, 3);		

		// Open server connection window
		Connection connection = new Connection();
		connect.addActionListener(connection);
		reconnect.addActionListener(connection);

		// Auto generated class codes
		gbc.weightx = 1.0;
		gbc.anchor = GridBagConstraints.CENTER;
		gbc.gridwidth = 1;
		gbc.ipadx = 0;
		CodeGroup cg = new CodeGroup(connection);
		for(int i = 0; i < CHOICES; ++i){
			cg.getButtons().add(new ClassButton("Code" + (i+1)));
			cg.getButtons().get(i).addActionListener(classTree);
			addGB(rightUpper, cg.getButtons().get(i), i < 6 ? i : i - 6, i < 6 ? 4 : 5);
		}
		manualMode.addActionListener(cg);
		singleMode.addActionListener(cg);
		autoMode.addActionListener(cg);
		qcMode.addActionListener(cg);

		// Class code panel
		gbc.anchor = GridBagConstraints.EAST;
		gbc.weightx = 0.2;
		JLabel classNoLabel = new JLabel("Class Number:");
		classNoLabel.setHorizontalAlignment(SwingConstants.RIGHT);
		addGB(rightUpper, classNoLabel, 0, 6);
		gbc.anchor = GridBagConstraints.CENTER;
		gbc.gridwidth = 3;
		gbc.weightx = 0.4;
		ClassNo classNo = new ClassNo();
		classNo.addActionListener(classTree);
		//		classNo.getDocument().addDocumentListener(classTree);
		leftUpper.addTreeSelectionListener(classNo);
		addGB(rightUpper, classNo, 1, 6);
		gbc.gridwidth = 1;
		gbc.weightx = 0.2;
		CommitButton commit = new CommitButton("Commit");
		commit.setClassNo(classNo);
		addGB(rightUpper, commit, 4, 6);
		RunButton rb = new RunButton("Run");
		rb.setEnabled(false);
		addGB(rightUpper, rb, 5, 6);

		// Article list table
		String[] headings = new String[] {"ID", "TITLE", "CLASSCODE1", "CLASSCODE2", "CLASSCODE3", "KEYWORDS",  "ABSTRACT", "MEETING"};
		DefaultTableModel model = new DefaultTableModel(0, 0);
		model.setColumnIdentifiers(headings);
		JTable articleProps = new JTable(model);
		articleProps.setRowSelectionAllowed(true);
		articleProps.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		title.setTable(articleProps);
		articleProps.getSelectionModel().addListSelectionListener(title);
		keywords.setTable(articleProps);
		articleProps.getSelectionModel().addListSelectionListener(keywords);
		abs.setArticles(articleProps);
		articleProps.getSelectionModel().addListSelectionListener(abs);
		meeting.setTable(articleProps);
		articleProps.getSelectionModel().addListSelectionListener(meeting);
		cg.setArticles(articleProps);
		articleProps.getSelectionModel().addListSelectionListener(cg);
		classNo.setArticles(articleProps);
		articleProps.getSelectionModel().addListSelectionListener(classNo);
		JScrollPane articlePropsScroll = new JScrollPane(articleProps);
		ArticleTable at = new ArticleTable(model);
		open.addActionListener(at);
		close.addActionListener(at);
		save.addActionListener(at);
		saveAs.addActionListener(at);
		for(ClassButton cb:cg.getButtons()) {
			cb.setArticles(articleProps);
		}
		commit.setArticles(articleProps);
		JPanel rightLower = new JPanel();
		rightLower.setLayout(new GridBagLayout());
		addGB(rightLower, articlePropsScroll, 0, 0);
		rightLower.setPreferredSize(rightUpper.getPreferredSize());
		TitledBorder titledBorder = BorderFactory.createTitledBorder(subPanelBorder, "List of Articles", TitledBorder.CENTER, TitledBorder.TOP);
		rightLower.setBorder(titledBorder);

		// Run button listeners
		manualMode.addActionListener(rb);
		manualMode.addActionListener(commit);
		singleMode.addActionListener(rb);
		singleMode.addActionListener(commit);
		autoMode.addActionListener(rb);
		autoMode.addActionListener(commit);
		qcMode.addActionListener(rb);
		qcMode.addActionListener(commit);
		rb.addActionListener(cg);

		// Main split pane
		JSplitPane right = new JSplitPane(JSplitPane.VERTICAL_SPLIT, rightUpper, rightLower);
		right.setDividerLocation(200);
		JSplitPane main = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, left, right);
		main.setDividerLocation(200);
		add(main);

		splash.close();
		setVisible( true );
		toFront();
	}
	// Helper method
	void addGB(Container cont, Component comp, int x, int y) {
		gbc.gridx = x; 
		gbc.gridy = y;
		cont.add(comp, gbc);
	}

	public void actionPerformed(ActionEvent ae) {
		System.exit(0);
	}

	private static WindowListener closeWindow = new WindowAdapter(){
		public void windowClosing(WindowEvent e){
			e.getWindow().dispose();
		}
	};

	public static void main(String argv[]){
		java.awt.EventQueue.invokeLater(new Runnable() {
			public void run() {
				UIManager.getLookAndFeelDefaults() .put("defaultFont", new Font("Song", 0, 12));
				UIManager.put("Label.font", new Font("Song", 1, 12));
				new ArticleClassClient();
			}
		});
	}
}

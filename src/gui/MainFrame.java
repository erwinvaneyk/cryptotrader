package gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.UIManager;

public class MainFrame extends JFrame {
	private static final long serialVersionUID = -8026416994513756565L;

	public MainFrame() {
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setMinimumSize(new Dimension(800, 600));
		
		addMenuBar();
		
		addPanels();
		
		setVisible(true);
	}

	private void addMenuBar() {
		JMenuBar menuBar = new JMenuBar();
		
		// Exchanges
		JMenu exchangeMenu = new JMenu("Exchanges");
		exchangeMenu.add(new JMenuItem("BTC-E"));
		JMenuItem mtgox = new JMenuItem("MTGox");
		mtgox.setEnabled(false);
		exchangeMenu.add(mtgox);
		
		// Balance
		JMenu balanceMenu = new JMenu("Balance");
		
		
		menuBar.add(exchangeMenu);
		menuBar.add(balanceMenu);
		
		setJMenuBar(menuBar);
	}
	
	private void addPanels() {
		Container pane = getContentPane();
		pane.setLayout(new BorderLayout());
		
		JPanel leftPanel, rightPanel, bottomPanel;
		// left panel
		leftPanel = new JPanel();
		leftPanel.setBackground(Color.RED);
		pane.add(leftPanel, BorderLayout.WEST);
		// add an invisible panel for fixed width
		JPanel leftTest = new JPanel();
		pack(); // important! Otherwise pane.getWidth will be 0.
		leftTest.setPreferredSize(new Dimension(pane.getWidth()/2-10,0)); // default width is 0.5*width (- 10 margin)
		leftPanel.add(leftTest);
		
		// right panel
		rightPanel = new JPanel();
		rightPanel.setBackground(Color.BLUE);
		rightPanel.setLayout(new BorderLayout());
		
		JPanel graphPanel = new JPanel();
		graphPanel.setBackground(Color.CYAN);
		rightPanel.add(graphPanel, BorderLayout.CENTER);
		
		JPanel logPanel = new JPanel();
		rightPanel.add(logPanel, BorderLayout.PAGE_END);
		// add an invisible panel for fixed width
		JPanel logTest = new JPanel();
		logTest.setPreferredSize(new Dimension(0,190)); // width of 300 (- 10 margin)
		logPanel.add(logTest);
		
		pane.add(rightPanel, BorderLayout.CENTER);
		
		// bottom panel
		bottomPanel = new JPanel();
		bottomPanel.setBackground(Color.GREEN);
		bottomPanel.add(new JLabel("Erwin van Eyk & Tim Rensen"));
		pane.add(bottomPanel, BorderLayout.PAGE_END);		
	}
	
	public static void main(String[] args) {
		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (Exception e) {}
		
		new MainFrame();
	}
}

package gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;

import javax.swing.BoxLayout;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.UIManager;

public class MainFrame extends JFrame {
	private static final long serialVersionUID = -8026416994513756565L;
	
	private JPanel leftPanel, rightPanel, bottomPanel;

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
		
		// important! Otherwise pane.getWidth will be 0.
		pack();
		
		// left panel
		leftPanel = new JPanel();
		leftPanel.setBackground(Color.GRAY);
		leftPanel.setLayout(new BoxLayout(leftPanel, BoxLayout.Y_AXIS));
		
		// right panel
		rightPanel = new JPanel();
		rightPanel.setBackground(Color.BLUE);
		rightPanel.setLayout(new BorderLayout());
		
		// bottom panel
		bottomPanel = new JPanel();
		bottomPanel.setBackground(Color.GREEN);
		bottomPanel.add(new JLabel("Erwin van Eyk & Tim Rensen"));
		
		pane.add(leftPanel,   BorderLayout.WEST);
		pane.add(rightPanel,  BorderLayout.CENTER);
		pane.add(bottomPanel, BorderLayout.PAGE_END);
		

		JPanel p;
		// add more specific panels		
		p = new JPanel();
		p.setPreferredSize(new Dimension(0, 190));
		p.setBackground(Color.WHITE);
		rightPanel.add(p, BorderLayout.PAGE_END);
		
		p = new JPanel();
		p.setBackground(Color.CYAN);
		rightPanel.add(p, BorderLayout.CENTER);
		
		
		p = new JPanel();
		p.setBackground(Color.BLACK);
		p.setPreferredSize(new Dimension(pane.getWidth()/2 - 10, 75));
		p.setMaximumSize(p.getPreferredSize());
		leftPanel.add(p);
		
		p = new JPanel();
		p.setBackground(Color.BLUE);
		p.setPreferredSize(new Dimension(pane.getWidth()/2 - 10, 100));
		p.setMaximumSize(p.getPreferredSize());
		leftPanel.add(p);
		
		p = new JPanel();
		p.setBackground(Color.MAGENTA);
		p.setPreferredSize(new Dimension(pane.getWidth()/2 - 10, 125));
		p.setMaximumSize(p.getPreferredSize());
		leftPanel.add(p);
	}
	
	public static void main(String[] args) {
		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (Exception e) {}
		
		new MainFrame();
	}
}

package gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.util.ArrayList;
import java.util.Collections;

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
	private ArrayList<JPanel> panelsLeft = new ArrayList<JPanel>();

	public MainFrame() {
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setMinimumSize(new Dimension(800, 600));
		setLocationRelativeTo(null);
		
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
		((BorderLayout)pane.getLayout()).setHgap(0);
		((BorderLayout)pane.getLayout()).setVgap(0);
		
		// Important: pane.getWidth() will return 0 otherwise.
		pack();
		
		// left panel
		leftPanel = new JPanel();
		leftPanel.setLayout(new BoxLayout(leftPanel, BoxLayout.Y_AXIS));
		
		// right panel
		rightPanel = new JPanel();
		rightPanel.setBackground(Color.BLUE);
		rightPanel.setLayout(new BorderLayout());
		
		// bottom panel
		bottomPanel = new JPanel();
		bottomPanel.setBackground(Color.GREEN);
		bottomPanel.add(new JLabel("Erwin van Eyk & Tim Rensen"));
		
		JPanel p = new JPanel();
		p.add(leftPanel);
		p.setBackground(Color.GRAY);
		((FlowLayout) p.getLayout()).setVgap(0);
		((FlowLayout) p.getLayout()).setHgap(0);
		
		pane.add(p,   BorderLayout.WEST);
		pane.add(rightPanel,  BorderLayout.CENTER);
		pane.add(bottomPanel, BorderLayout.PAGE_END);
		

		// add more specific panels		
		p = new JPanel();
		p.setPreferredSize(new Dimension(0, 200));
		p.setBackground(Color.WHITE);
		rightPanel.add(p, BorderLayout.PAGE_END);
		
		p = new JPanel();
		p.setBackground(Color.CYAN);
		rightPanel.add(p, BorderLayout.CENTER);
		
		
		p = new JPanel();
		p.setBackground(Color.BLACK);
		p.setPreferredSize(new Dimension(pane.getWidth()/2, 75));
		p.setMaximumSize(p.getPreferredSize());
		addAtTop(p);
		
		p = new JPanel();
		p.setBackground(Color.BLUE);
		p.setPreferredSize(new Dimension(pane.getWidth()/2, 100));
		p.setMaximumSize(p.getPreferredSize());
		addAtBottom(p);
		
		p = new JPanel();
		p.setBackground(Color.MAGENTA);
		p.setPreferredSize(new Dimension(pane.getWidth()/2, 125));
		p.setMaximumSize(p.getPreferredSize());
		addAtBottom(p);
		
		
		p = new JPanel();
		p.setBackground(Color.RED);
		p.setPreferredSize(new Dimension(pane.getWidth()/2, 250));
		p.setMaximumSize(p.getPreferredSize());
		
		addAtBottom(p);
		removePanel(p);
		
		addAtTop(p);
		removeIndex(0);
		
		swapPanels(0, 1);
		swapPanels(panelsLeft.get(0), panelsLeft.get(2));
	}
	
	private void addAtBottom(JPanel p) {
		panelsLeft.add(p);
		
		updateLeftPanel();
	}
	
	private void addAtTop(JPanel p) {
		panelsLeft.add(0, p);
		
		updateLeftPanel();
	}
	
	private void removeIndex(int index) {
		try {
			panelsLeft.remove(index);
			updateLeftPanel();
		}
		catch(IndexOutOfBoundsException e) {
			// TODO Logger
		}
	}
	
	private void removePanel(JPanel panel) {
		if(panelsLeft.remove(panel))
			updateLeftPanel();
	}
	
	private void swapPanels(int i, int j) {
		try {
			Collections.swap(panelsLeft, i, j);
			updateLeftPanel();
		} catch(IndexOutOfBoundsException e) {
			// TODO Logger
		}
	}
	
	private void swapPanels(JPanel i, JPanel j) {
		swapPanels(panelsLeft.indexOf(i), panelsLeft.indexOf(j));
	}
	
	private void updateLeftPanel() {
		leftPanel.removeAll();
		
		for(JPanel p : panelsLeft)
			leftPanel.add(p);
	}

	public static void main(String[] args) {
		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (Exception e) {}
		
		new MainFrame();
	}
}

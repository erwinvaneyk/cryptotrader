package gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
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

@SuppressWarnings("unused")
public class MainFrame extends JFrame {
	private static final long serialVersionUID = -8026416994513756565L;
	
	private JPanel leftPanel, rightPanel, bottomPanel;
	private ArrayList<PanelFixedSize> panelsLeft = new ArrayList<PanelFixedSize>();
	private ArrayList<Integer> prefixsum = new ArrayList<Integer>();
	private SwapListener swapListener = new SwapListener();

	public MainFrame() {
		super("CryptoTrader");
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
		p.addMouseMotionListener(swapListener);
		p.addMouseListener(swapListener);
		
		
		pane.add(p,   			BorderLayout.WEST);
		pane.add(rightPanel,  	BorderLayout.CENTER);
		pane.add(bottomPanel, 	BorderLayout.PAGE_END);
		

		// add more specific panels		
		p = new JPanel();
		p.setPreferredSize(new Dimension(0, 200));
		p.setBackground(Color.WHITE);
		rightPanel.add(p, BorderLayout.PAGE_END);
		
		p = new JPanel();
		p.setBackground(Color.CYAN);
		rightPanel.add(p, BorderLayout.CENTER);
		
		PanelFixedSize pfh;
		pfh = new PanelFixedSize(pane.getWidth()/2, 75);
		pfh.setBackground(Color.BLACK);
		addAtTop(pfh);
		
		pfh = new PanelFixedSize(pane.getWidth()/2, 100);
		pfh.setBackground(Color.BLUE);
		addAtBottom(pfh);
		
		pfh = new PanelFixedSize(pane.getWidth()/2, 125);
		pfh.setBackground(Color.MAGENTA);
		addAtBottom(pfh);		
	}
	
	private void addAtBottom(PanelFixedSize p) {
		panelsLeft.add(p);
		
		updateLeftPanel();
	}
	
	private void addAtTop(PanelFixedSize p) {
		addBefore(p, 0);
	}
	
	private void addAfter(PanelFixedSize a, int index) {
		try {
			panelsLeft.add(index+1, a);
			
			updateLeftPanel();
		}
		catch(IndexOutOfBoundsException e) {
			// TODO Logger
		}
	}
	
	private void addAfter(PanelFixedSize a, PanelFixedSize p) {
		addAfter(a, panelsLeft.indexOf(p));
	}
	
	private void addBefore(PanelFixedSize a, int index) {
		try {
			panelsLeft.add(index, a);
			
			updateLeftPanel();
		}
		catch(IndexOutOfBoundsException e) {
			// TODO Logger
		}
	}
	
	private void addBefore(PanelFixedSize a, PanelFixedSize p) {
		addBefore(a, panelsLeft.indexOf(p));
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
		
		prefixsum.clear();
		int sum = 0;
		
		for(PanelFixedSize p : panelsLeft) {
			leftPanel.add(p);
			sum += p.getHeight();
			prefixsum.add(sum);
		}
		
		validate();
		repaint();
		
		/*System.out.println(panelsLeft);
		System.out.println(prefixsum);
		System.out.println("=====");*/
	}

	public static void main(String[] args) {
		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (Exception e) {}
		
		new MainFrame();
	}
	
	private class SwapListener implements MouseListener, MouseMotionListener {
		private int selected, grabY;
		
		@Override
		public void mouseDragged(MouseEvent e) {
			if(selected != -1
					&& e.getY() >= 0
					&& e.getY() <= prefixsum.get(prefixsum.size()-1)) {
				
				// panels that can be moved down
				if(selected < panelsLeft.size() - 1) {
					int y1 = 0;
					if(selected > 0)
						y1  = prefixsum.get(selected - 1);
					
					int y2  = prefixsum.get(selected);
					int y3  = prefixsum.get(selected + 1);
					
					int y2a = y3 - (y2 - y1) + grabY;

					//System.out.println(y2a + " && " + e.getY());
					if(e.getY() > y2a) {
						swapPanels(selected, selected + 1);
						selected = selected + 1;
					}
				}
				
				// panels that can be moved up
				if(selected > 0) {
					int y1 = 0;
					if(selected - 1 > 0)
						y1 = prefixsum.get(selected - 2);
					
					int y2  = prefixsum.get(selected - 1);
					int y3  = prefixsum.get(selected);
					
					int y2a = y1 + grabY;

					if(e.getY() < y2a) {
						swapPanels(selected, selected - 1);
						selected = selected - 1;
					}
				}
			}
		}

		@Override
		public void mousePressed(MouseEvent e) {
			if(e.getY() > prefixsum.get(prefixsum.size()-1)) {
				selected = -1;
				grabY = 0;
				return;
			}
			
			int index = 0;
			while(e.getY() > prefixsum.get(index))
				index++;
			
			selected = index;
			int start = (index == 0) ? 0 : prefixsum.get(selected - 1);
			grabY = e.getY() - start;
		}

		@Override
		public void mouseReleased(MouseEvent e) {}
		
		@Override
		public void mouseClicked(MouseEvent e) {}
		
		@Override
		public void mouseMoved(MouseEvent e) {}

		@Override
		public void mouseEntered(MouseEvent e) {}

		@Override
		public void mouseExited(MouseEvent e) {}
		
	}
}



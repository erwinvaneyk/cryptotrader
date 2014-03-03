package gui;

import java.awt.Dimension;

import javax.swing.JPanel;

public class PanelFixedSize extends JPanel {
	private static final long serialVersionUID = 8907334751490465291L;
	
	private int width, height;

	public PanelFixedSize(int width, int height) {
		setWidth(width);
		setHeight(height);
	}
	
	public int getWidth() {
		return width;
	}
	
	public void setWidth(int width) {
		this.width = width;
		setSize();
	}

	public int getHeight() {
		return height;
	}
	
	public void setHeight(int height) {
		this.height = height;
		setSize();
	}
	
	private void setSize() {
		setPreferredSize(new Dimension(width, height));
		setMaximumSize(getPreferredSize());
		setMinimumSize(getPreferredSize());
	}
}

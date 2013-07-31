package de.dc.menu;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;

import javax.swing.JPanel;

public class StartMenu extends JPanel implements Runnable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private boolean blink = false;
	private long delay = 400; // delay: ms

	public StartMenu(int w, int h) {
		this.setPreferredSize(new Dimension(w,h));
		this.setBackground(Color.orange);
		Thread t1 = new Thread(this);
		t1.start();
	}
	
	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		Dimension panelDim = this.getSize();
		g.setFont(new Font(Font.DIALOG, Font.BOLD, 20));
		if(blink)
		g.drawString("PRESS ENTER",
				(int)(panelDim.getWidth()/2 - 60),(int)(panelDim.getHeight()/2));
	}

	@Override
	public void run() {
		while(this.isVisible()) {
			repaint();
			blink = (!blink);
			try {
				Thread.sleep(delay);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
	}
}

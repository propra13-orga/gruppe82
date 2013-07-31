package de.dungencrawler.menu;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.image.BufferedImage;

import javax.swing.JPanel;

public class StoryPicViewer extends JPanel implements Runnable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private boolean blink = false;
	private long delay = 400; // delay: ms
	private BufferedImage[] img;
	private int current = -1;

	public StoryPicViewer(int w, int h, BufferedImage[] img) {
		this.setPreferredSize(new Dimension(w,h));
		this.setBackground(Color.orange);
		this.img = img;
		Thread t1 = new Thread(this);
		t1.start();
	}
	
	public StoryPicViewer(int w, int h, BufferedImage img) {
		this.setPreferredSize(new Dimension(w,h));
		this.setBackground(Color.orange);
		this.img = new BufferedImage[1]; 
		this.img[0] = img;
		Thread t1 = new Thread(this);
		t1.start();
	}
	
	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		if(current < 0)
			current = 0;
		System.out.println("current: " + current);
		g.drawImage(img[current], 0, 0, this.getWidth(), this.getHeight(), null);
	}
	
	public boolean next() {
		if(current < (img.length - 1 )) {
			current++;
			System.out.println("next = true");
			return true;
		} else {
			System.out.println("next = false");
			return false;
		}
		
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

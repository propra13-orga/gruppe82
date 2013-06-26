package de.dungencrawler.sprites;


import java.awt.image.BufferedImage;

import de.dungencrawler.Spielfeld;

public class SpellMonitor extends Sprite {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int delay = 0;

	public SpellMonitor(String name, BufferedImage[] i, double x, double y,
			long delay, Spielfeld p) {
		super(name, i, x, y, delay, p);
		
	}
	
	@Override
	public void doLogic(long delta) {
		super.doLogic(delta);
		delay += delta;
		if(delay > 2*1e9) {
			System.out.println("Remove");
			parent.removeActor(this);
		}
	}
}

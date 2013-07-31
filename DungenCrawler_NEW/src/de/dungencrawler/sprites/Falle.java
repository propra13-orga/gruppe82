package de.dungencrawler.sprites;

import java.awt.image.BufferedImage;

import de.dungencrawler.Spielfeld;


public class Falle extends Sprite {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private double damage = 2;

	public Falle(String name,BufferedImage[] i, double x, double y, long delay, Spielfeld p) {
		super(name,i, x, y, delay, p);
		// TODO Auto-generated constructor stub
	}
	
	public double getDamage(double resistance) {
		int dmg =(int)(this.damage  + 5 * Math.random());
		dmg *= Math.exp(-resistance * 3/600); // resistens von 600 bedeutet e^-3 => 95% weniger Schaden
		return dmg;								// 100 bedeutet 40% wengiger Schaden
	}
	

}


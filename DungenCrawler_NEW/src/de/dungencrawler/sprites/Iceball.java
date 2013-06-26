package de.dungencrawler.sprites;

import java.awt.image.BufferedImage;

import de.dungencrawler.Spielfeld;

public class Iceball extends Spell {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public Iceball(String name, BufferedImage[] i,
			double nextSpellDx, double nextSpellDy, int delay, Sprite c, Spielfeld p) {
		super(name, i, nextSpellDx, nextSpellDy, delay, c, p);
		// TODO Auto-generated constructor stub
	}
	
	public Iceball(String name, BufferedImage[] i, Sprite target, int delay,
			Sprite caster, Spielfeld parent) {
		super(name, i, delay, target, caster, parent);
	}
}

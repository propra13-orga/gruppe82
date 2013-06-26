package de.dungencrawler.sprites;

import java.awt.image.BufferedImage;

import de.dungencrawler.Spielfeld;

public class Feuerball extends Spell {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public Feuerball(String name, BufferedImage[] i,
			double nextSpellDx, double nextSpellDy, int delay, Sprite c, Spielfeld p) {
		super(name, i, nextSpellDx, nextSpellDy, delay, c, p);
	}
	
	public Feuerball(String name, BufferedImage[] i, int delay,
			Sprite caster, Sprite target, Spielfeld parent) {
		super(name, i, delay, caster, target, parent);
	}
}

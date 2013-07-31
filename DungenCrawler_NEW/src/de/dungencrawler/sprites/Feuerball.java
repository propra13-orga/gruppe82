package de.dungencrawler.sprites;

import java.awt.image.BufferedImage;

import de.dungencrawler.Spielfeld;
import de.dungencrawler.interfaces.Caster;

public class Feuerball extends Spell {

	private double damage = 5; //grundschaden
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public Feuerball(String name, BufferedImage[] i,
			double nextSpellDx, double nextSpellDy, int delay, Caster c, Spielfeld p) {
		super(name, i, nextSpellDx, nextSpellDy, delay, c, p);
	}
	
	public Feuerball(String name, BufferedImage[] i, int delay,
			Caster caster, Sprite target, Spielfeld parent) {
		super(name, i, delay, caster, target, parent);
	}
}

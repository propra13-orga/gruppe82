package de.dungencrawler.sprites;

import java.awt.image.BufferedImage;

import de.dungencrawler.Spielfeld;
import de.dungencrawler.interfaces.Caster;

public class Iceball extends Spell {
	
	private double damage = 5; //grundschaden
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public Iceball(String name, BufferedImage[] i,
			double nextSpellDx, double nextSpellDy, int delay, Caster c, Spielfeld p) {
		super(name, i, nextSpellDx, nextSpellDy, delay, c, p);
		// TODO Auto-generated constructor stub
	}
	
	public Iceball(String name, BufferedImage[] i, int delay,
			Caster caster, Sprite target, Spielfeld parent) {
		super(name, i, delay, caster, target, parent);
	}
}

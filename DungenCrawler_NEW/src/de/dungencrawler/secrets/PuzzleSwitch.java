package de.dungencrawler.secrets;

import java.awt.image.BufferedImage;

import de.dungencrawler.Spielfeld;
import de.dungencrawler.sprites.Sprite;

public class PuzzleSwitch extends Sprite {

	private int number;

	public PuzzleSwitch(String name, BufferedImage[] i, double x, double y,
			long delay, Spielfeld p, int number) {
		super(name, i, x, y, delay, p);
		this.number = number;
	}

	public int getNr() {
		return this.number;
	}
}

package de.dungencrawler.sprites;

import java.awt.Graphics;
import java.awt.image.BufferedImage;

import de.dungencrawler.Spielfeld;

public class PlayerPositionDummy extends Sprite {

	public PlayerPositionDummy(String name, BufferedImage[] i, double x,
			double y, long delay, Spielfeld p) {
		super(name, i, x, y, delay, p);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void drawObjects(Graphics g) {
		//should do nothing
	}
}

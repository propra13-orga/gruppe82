package de.dungencrawler.sprites.shopitems;

import java.awt.image.BufferedImage;

import de.dungencrawler.Spielfeld;
import de.dungencrawler.sprites.Spieler;

public class RefilLife extends ShopItem {

	public RefilLife(String name, BufferedImage[] i, double x, double y,
			long delay, Spielfeld p, int price) {
		super(name, i, x, y, delay, p, price);
		// TODO Auto-generated constructor stub
	}

	@Override
	protected void buyAction() {
		Spieler player = parent.getPlayer();
		player.addLife(50);
	}

}

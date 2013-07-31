package de.dungencrawler.sprites.shopitems;

import java.awt.image.BufferedImage;

import de.dungencrawler.Spielfeld;

public class AContinue extends ShopItem {
	final static public int price = 20;
	public AContinue(String name, BufferedImage[] i, double x, double y,
			long delay, Spielfeld p) {
		super(name, i, x, y, delay, p, price);
	}
	
	@Override
	protected void buyAction() {
		parent.getPlayer().addContinue(1);
	}
}

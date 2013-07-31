package de.dungencrawler.sprites.shopitems;

import java.awt.image.BufferedImage;

import de.dungencrawler.Spielfeld;

public class SmallSword extends ShopItem {

	public SmallSword(String name, BufferedImage[] i, double x, double y,
			long delay, Spielfeld p, int price) {
		super(name, i, x, y, delay, p, price);
	}

	@Override
	protected void buyAction() {
		parent.getPlayer().setWappon(new de.dungencrawler.items.SmallSword());
	}
}

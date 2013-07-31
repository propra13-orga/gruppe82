package de.dungencrawler.sprites.shopitems;

import java.awt.image.BufferedImage;

import de.dungencrawler.Spielfeld;

public class SmallWard extends ShopItem {

	public SmallWard(String name, BufferedImage[] i, double x, double y,
			long delay, Spielfeld p, int price) {
		super(name, i, x, y, delay, p, price);
		// TODO Auto-generated constructor stub
	}

	@Override
	protected void buyAction() {
		parent.getPlayer().setWappon(new de.dungencrawler.items.SmallWard());
	}

}

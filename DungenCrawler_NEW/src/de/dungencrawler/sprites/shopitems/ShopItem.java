package de.dungencrawler.sprites.shopitems;

import java.awt.Graphics;
import java.awt.image.BufferedImage;

import de.dungencrawler.Spielfeld;
import de.dungencrawler.interfaces.Interactable;
import de.dungencrawler.sprites.Spieler;
import de.dungencrawler.sprites.Sprite;

abstract public class ShopItem extends Sprite implements Interactable {

	private int price;

	public ShopItem(String name, BufferedImage[] i, double x, double y,
			long delay, Spielfeld p, int price) {
		super(name, i, x, y, delay, p);
		this.price = price;
	}
	
	@Override
	public void doInteraction(Spieler s) {
		boolean enoughMoney = parent.getPlayer().buyItem(price);
		if(enoughMoney) {
			buyAction();
		}
	}
	
	@Override
	public void drawObjects(Graphics g) {
		super.drawObjects(g);
		g.drawString("$ " + this.price, (int)this.x, (int)this.y);
	}

	abstract protected void buyAction();
}

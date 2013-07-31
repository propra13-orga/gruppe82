package de.dungencrawler.sprites;

import java.awt.Color;
import java.awt.Point;
import java.awt.image.BufferedImage;

import de.dungencrawler.Spielfeld;
import de.dungencrawler.interfaces.Interactable;
import de.dungencrawler.message.Message;

public class ManaPool extends Mauer implements Interactable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private boolean interacted = false;
	long interactedTime = 0;

	public ManaPool(String name, BufferedImage[] i, double x, double y,
			long delay, Spielfeld p) {
		super(name, i, x, y, delay, p);
		// TODO Auto-generated constructor stub
	}
	
	@Override
	public void doLogic(long delta) {
		// TODO Auto-generated method stub
		super.doLogic(delta);
		if(interacted) {
			Message m = new Message("Mana Refilled", new Point((int)(this.x),
					(int)(this.y + 10)), Color.BLACK, 1000, parent);
			parent.addMessage(m);
			interacted = false;
			interactedTime = System.nanoTime();
		}
	}

	@Override
	public void doInteraction(Spieler sp) {
		System.out.println(sp.name + " Interact wiht " + this.name);
		sp.setMana(Spieler.MAXMANA);
		interacted = true;
	}



}

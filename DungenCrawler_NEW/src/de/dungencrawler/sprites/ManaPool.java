package de.dungencrawler.sprites;

import java.awt.Point;
import java.awt.image.BufferedImage;

import de.dungencrawler.Spielfeld;
import de.dungencrawler.interfaces.interactable;
import de.dungencrawler.message.Message;

public class ManaPool extends Mauer implements interactable {

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
			parent.speak(new Message("Mana Refilled", new Point((int)(this.x),(int)(this.y + 10))));
			interacted = false;
			interactedTime = System.nanoTime();
		}
		
	
		if(System.nanoTime() - interactedTime >= 3e9 && interactedTime != 0) {
			parent.speak(null);
			interactedTime = 0;
		}
	}

	@Override
	public void doInteraction(Spieler sp) {
		System.out.println(sp.name + " Interact wiht " + this.name);
		sp.setMana(Spieler.MAX_MANA);
		interacted = true;
	}



}

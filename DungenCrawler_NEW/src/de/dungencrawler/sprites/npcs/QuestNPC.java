package de.dungencrawler.sprites.npcs;

import java.awt.Point;
import java.awt.image.BufferedImage;

import de.dungencrawler.Spielfeld;
import de.dungencrawler.interfaces.interactable;
import de.dungencrawler.message.Message;
import de.dungencrawler.sprites.Spieler;
import de.dungencrawler.sprites.Sprite;

public class QuestNPC extends Sprite implements interactable {

	private static final long serialVersionUID = 1L;
	private boolean interacted = false;
	long interactedTime = 0;


	public QuestNPC(String name, BufferedImage[] i, double x, double y,
			long delay, Spielfeld p) {
		super(name, i, x, y, delay, p);
		// TODO Auto-generated constructor stub
	}
	
	@Override
	public void doLogic(long delta) {
		// TODO Auto-generated method stub
		super.doLogic(delta);
		if(interacted) {
			parent.speak(new Message("Space = Interaction \n " +
					"Q = Normal Attack \n" +
					"W = Magic Attack \n " +
					"S = Switch Spell", new Point((int)(this.x + getWidth()), (int)(this.y + this.height + 10))));
			interacted = false;
			interactedTime = System.nanoTime();
		}
		
	
		if(System.nanoTime() - interactedTime >= 10e9 && interactedTime != 0) {
			parent.speak(null);
			interactedTime = 0;
		}
	}

	@Override
	public void doInteraction(Spieler sp) {
		interacted = true;
	}

}

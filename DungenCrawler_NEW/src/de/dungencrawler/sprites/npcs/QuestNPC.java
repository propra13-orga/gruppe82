package de.dungencrawler.sprites.npcs;

import java.awt.Color;
import java.awt.Point;
import java.awt.image.BufferedImage;

import de.dungencrawler.Spielfeld;
import de.dungencrawler.interfaces.Interactable;
import de.dungencrawler.message.Message;
import de.dungencrawler.message.QuestMessage;
import de.dungencrawler.sprites.Spieler;
import de.dungencrawler.sprites.Sprite;

public class QuestNPC extends Sprite implements Interactable, Runnable {

	private static final long serialVersionUID = 1L;
	private boolean interacted = false;
	Message lastMessage = null;
	long interactedTime = 0;
	int iterator = 0;
	private String[] questMessages;
	


	public QuestNPC(String name, BufferedImage[] i, double x, double y,
			long delay, Spielfeld p, String questMessages[]) {
		super(name, i, x, y, delay, p);
		this.questMessages = questMessages;
		// TODO Auto-generated constructor stub
	}
	
	@Override
	public void doLogic(long delta) {
		super.doLogic(delta);
	}

	@Override
	public void doInteraction(Spieler sp) {
		
		if(!interacted)
		{
			interacted = true;
			new Thread(this).start();
		}
			

	}
	
	@Override
	public void run() {
		if(interacted) {
			for(String s: questMessages){
				Message m = new QuestMessage(s, new Point((int)(this.x + this.width + 3),
						(int)(this.y + 10)), Color.BLACK, 5000, parent);
				lastMessage = m;
				parent.addMessage(m);
				try {
					Thread.sleep(2300);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			interacted = false;
			interactedTime = System.nanoTime();
		}

	}

}

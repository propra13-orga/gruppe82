package de.dungencrawler.sprites.npcs;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.util.Vector;

import de.dungencrawler.Spielfeld;
import de.dungencrawler.bibliothek.Animations;
import de.dungencrawler.sprites.Feuerball;
import de.dungencrawler.sprites.Slash;
import de.dungencrawler.sprites.Spell;
import de.dungencrawler.sprites.Spieler;
import de.dungencrawler.sprites.Sprite;

public class EnemyCaster extends Enemy {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	final static long MAX_SPELL_DELAY = 100; // in millisekunden
	long spellDelay = 100; 
	
	public EnemyCaster(String name, BufferedImage[] i, double x, double y,
			long delay, Spielfeld p) {
		super(name, i, x, y, delay, p);
		this.life = 10;
	}
	
	@Override
	public void doLogic(long delta) {
		super.doLogic(delta);
		
		if((spellDelay -= delta/1e8) <= 0) {
			spellDelay = MAX_SPELL_DELAY;
			Vector<Sprite> vec = this.parent.getActors();
			for(Sprite s: vec) {
				if (s instanceof Spieler) {
					parent.addActor(new Feuerball("feuerball", 
							Animations.FIRE_BALL,  
							100,
							this,
							s,
							parent));
				}
			}
		}
	}
	
	public void drawObjects(Graphics g) {
		super.drawObjects(g);
		for(int i = 0; i < this.life; i++) {
			g.drawImage(herz[0], (int)(this.x + herz[0].getWidth()*i + 1) , (int)(this.y + this.height), null);
		}
	}
	
	public void hit(Sprite s) {
		if ( s instanceof Spell) {
			this.life -= 1;
		} else if (s instanceof Slash) {
			this.life -= 1;
		}
		if(this.life <= 0) {
			parent.removeActor(this);
		}
	}

}

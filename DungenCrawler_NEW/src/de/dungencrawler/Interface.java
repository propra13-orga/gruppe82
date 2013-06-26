package de.dungencrawler;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.util.Vector;

import de.dungencrawler.bibliothek.Animations;
import de.dungencrawler.sprites.Spieler;
import de.dungencrawler.sprites.Sprite;

public class Interface  {
	
	private Spielfeld parent;

	public Interface(Spielfeld parent) {
		this.parent = parent;
	}
	
	public void drawObjects(Graphics g) {
		Spieler sp = findPlayer();
		g.drawImage(Animations.CONTINUES[0], parent.getWidth() - 50, 5, null);
		if(sp != null) {
			g.setColor(Color.BLACK);
			g.setFont(new Font("Arial", Font.BOLD, 12));
			g.drawString("x " + sp.getContinues() ,
					parent.getWidth() - 47 + Animations.CONTINUES[0].getWidth(), Animations.CONTINUES[0].getHeight()/2 +10);
		} else {
			g.setColor(Color.RED);
			g.drawString("Game Over" , parent.getWidth() - 50, 25);

		}
	}
	
	private Spieler findPlayer() {
		Vector<Sprite> vec = parent.getActors();
		for(Sprite s : vec) {
			if(s instanceof Spieler) {
				return (Spieler)s;
			}
		}
		return null;
	}
}

package de.dungencrawler;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.io.Serializable;
import java.util.Vector;

import de.dungencrawler.bibliothek.Animations;
import de.dungencrawler.sprites.Spieler;
import de.dungencrawler.sprites.Sprite;

public class Interface implements Serializable {
	
	private Spielfeld parent;

	public Interface(Spielfeld parent) {
		this.parent = parent;
	}
	
	public void drawObjects(Graphics g) {
		Spieler sp = findPlayer();
		g.drawImage(Animations.ARMOR_ICON[0], parent.getWidth() - 350, 5, null);
		g.drawImage(Animations.MAGIC_RESISTANCE_ICON[0], parent.getWidth() - 300, 5, null);
		g.drawImage(Animations.ATTACK_DAMAGE_ICON[0], parent.getWidth() - 250, 5, null);
		g.drawImage(Animations.MAGERY_ICON[0], parent.getWidth() - 200, 5, null);
		g.drawImage(Animations.COIN_INTERFACE[0], parent.getWidth() - 150, 5, null);
		g.drawImage(Animations.CONTINUES[0], parent.getWidth() - 100, 5, null);
		if(sp != null) {
			g.setColor(Color.BLACK);
			g.setFont(new Font("Arial", Font.BOLD, 12));
			g.drawString("" + sp.getArmor() ,
					parent.getWidth() - 347 + Animations.CONTINUES[0].getWidth(), Animations.CONTINUES[0].getHeight()/2 +10);
			g.drawString("" + sp.getMagicResistance() ,
					parent.getWidth() - 297 + Animations.CONTINUES[0].getWidth(), Animations.CONTINUES[0].getHeight()/2 +10);
			g.drawString("" + sp.getAttackPower() ,
					parent.getWidth() - 247 + Animations.CONTINUES[0].getWidth(), Animations.CONTINUES[0].getHeight()/2 +10);
			g.drawString("" + sp.getMagery() ,
					parent.getWidth() - 197 + Animations.CONTINUES[0].getWidth(), Animations.CONTINUES[0].getHeight()/2 +10);
			g.drawString("x " + sp.getContinues() ,
					parent.getWidth() - 97 + Animations.CONTINUES[0].getWidth(), Animations.CONTINUES[0].getHeight()/2 +10);
			g.drawString("x " + sp.getCoins() ,
					parent.getWidth() - 147 + Animations.CONTINUES[0].getWidth(), Animations.CONTINUES[0].getHeight()/2 +10);
		} else {
			g.setColor(Color.RED);
			g.drawString("Game Over" , parent.getWidth() - 100, 25);
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

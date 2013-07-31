package de.dungencrawler.sprites;

import java.awt.image.BufferedImage;
import java.util.Vector;

import de.dungencrawler.Spielfeld;
import de.dungencrawler.bibliothek.Animations;

abstract public class DynamicSprite extends Sprite {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	protected BufferedImage[] herz = Animations.HEARTH;
	
	public DynamicSprite(String name, BufferedImage[] i, double x, double y,
			long delay, Spielfeld p) {
		super(name, i, x, y, delay, p);
	}

	protected double life;
	protected double mana;
	protected double speed;
	
	@Override
	public void move(long delta) {
		double tempx=x;
		double tempy=y;
		if(dx!=0){
			x=x+dx*(delta/1e9); //Pixel pro sek
		}
		if(dy!=0){
			y=y+dy*(delta/1e9);
		}
		Vector <Sprite> actors = parent.getActors();
		for(Sprite s:actors){
			if(s instanceof Mauer){
				if(this.intersects(s)){
					x=tempx;
					y=tempy;
					break;
				}
			}
		}
	}
	
	public double getLife() {
		return life;
	}

	public void setLife(double life) {
		this.life = life;
	}

	public double getMana() {
		return mana;
	}

	public void setMana(double mana) {
		System.out.println("Mana before: " + this.mana);
		this.mana = mana;
		System.out.println("Mana is now: " + this.mana);
	}

	public double getSpeed() {
		return speed;
	}

	public void setSpeed(double speed) {
		this.speed = speed;
	}
}

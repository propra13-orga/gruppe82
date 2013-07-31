package de.dungencrawler.sprites;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.image.BufferedImage;
import java.util.Vector;

import de.dungencrawler.Spielfeld;
import de.dungencrawler.bibliothek.Animations;
import de.dungencrawler.message.Message;

abstract public class DynamicSprite extends Sprite {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	protected double MAX_LIFE = 20;
	protected double MAX_MANA = 0;
	protected BufferedImage[] herz = Animations.HEARTH;
	
	public DynamicSprite(String name, BufferedImage[] i, double x, double y,
			long delay, Spielfeld p) {
		super(name, i, x, y, delay, p);
	}

	protected double life;
	protected double mana;
	protected double speed;
	protected int dmg = 0;
	protected long dmgTime = 0;
	private double MAGIC_RESITANCE;
	private double ARMOR;
	
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
	
	protected void drawHealth(Graphics g) {
		g.setColor(Color.red);
		g.fillRect((int)(this.x),
				(int)(this.y + this.height + 1), 
				(int) (this.width/this.MAX_LIFE * this.life), 4);
		g.setColor(Color.BLACK);
		g.drawRect((int)(this.x),
				(int)(this.y + this.height + 1), 
				(int) this.width, 4);
	}
	
	protected void drawMana(Graphics g) {
		g.setColor(Color.blue);
		g.fillRect((int)(this.x),
				(int)(this.y + this.height + this.herz[0].getHeight() + 1), 
				(int) (this.width/this.MAX_MANA * this.mana), 4);
		g.setColor(Color.BLACK);
		g.drawRect((int)(this.x),
				(int)(this.y + this.height + this.herz[0].getHeight() + 1), 
				(int) this.width, 4);
		
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
	
	public void hit(Sprite s) {
		double dmg = 0;
		if ( s instanceof Spell) {
			Spell sp = (Spell) s;
			dmg = sp.getDamage(MAGIC_RESITANCE);
			this.life -= dmg;
		} else if (s instanceof Slash) {
			Slash sl = (Slash) s;
			dmg = sl.getDamage(ARMOR);
			this.life -= dmg;
		} else if(s instanceof Falle) {
			Falle f = (Falle) s;
			dmg = f.getDamage(ARMOR);
			this.life -= dmg;
		}
		Point point = new Point((int)this.x, (int)this.y);
		parent.addMessage(new Message("" + (int)dmg, point, Color.RED, 300, parent));
		if(this.life <= 0) {
			parent.removeActor(this);
		}
	}
}

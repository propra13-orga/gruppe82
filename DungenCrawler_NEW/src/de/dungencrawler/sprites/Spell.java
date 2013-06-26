package de.dungencrawler.sprites;

import java.awt.image.BufferedImage;
import java.util.Vector;

import de.dungencrawler.Spielfeld;
import de.dungencrawler.sprites.Sprite;

public abstract class Spell extends DynamicSprite {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	static final public double MANA_COST = 10;
	static final public double SPEED = 500;
	private Sprite caster;

	public Spell(String name, BufferedImage[] i,
			long delay,Sprite caster, Sprite target,  Spielfeld p) {
		super(name, i, caster.x, caster.y, delay, p);
		this.caster = caster;
		double deltaX = (target.x - this.x);
		double deltaY = (target.y - this.y);
		double vecLength = Math.sqrt((deltaX*deltaX + deltaY*deltaY));
		deltaX =  deltaX/vecLength;
		deltaY =  deltaY/vecLength;
		this.dx = SPEED * deltaX;
		this.dy = SPEED * deltaY;
	}

	
	public Spell(String name, BufferedImage[] i,
			double nextSpellDx, double nextSpellDy, int delay,Sprite caster, Spielfeld p) {
		super(name, i, caster.x, caster.y, delay, p);
		this.caster = caster;
		this.dx = SPEED * nextSpellDx;
		this.dy = SPEED * nextSpellDy;
	}


	@SuppressWarnings("unchecked")
	@Override
	public void doLogic(long delta) {
		super.doLogic(delta);
		if(x > 810 || (dx == 0 && dy == 0)) {
			parent.removeActor(this);
		}
		Vector<Sprite> actors = (Vector<Sprite>) parent.getActors().clone();
		for (Sprite s : actors) {
			if(this.intersects(s) && !this.equals(s)) {
				intersectLogic(s);
			}
		}
	}
	
	private void intersectLogic(Sprite s) {
		if (s instanceof Mauer) {
			System.out.println(this.name + " trifft " + s.name);
			parent.removeActor(this);
		} else if (s instanceof DynamicSprite && !(s.equals(caster)) && !(s instanceof Spell)) {
			System.out.println(this.name+ " trifft " + s.name);
			s.hit(this);
			parent.removeActor(this);
		}
		
	}


	@Override
	public void move(long delta) {
//		super.move(delta);
		if(dx!=0){
			x=x+dx*(delta/1e9); //Pixel pro sek
		}
		if(dy!=0){
			y=y+dy*(delta/1e9);
		}
	}
	
}

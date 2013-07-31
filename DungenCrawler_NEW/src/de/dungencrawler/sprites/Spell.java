package de.dungencrawler.sprites;

import java.awt.image.BufferedImage;
import java.util.Vector;

import de.dungencrawler.Spielfeld;
import de.dungencrawler.bibliothek.PlaySound;
import de.dungencrawler.bibliothek.Sounds;
import de.dungencrawler.interfaces.Caster;
import de.dungencrawler.sprites.Sprite;

public abstract class Spell extends DynamicSprite {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	static final public double MANA_COST = 10;
	static final public double SPEED = 500;
	private Caster caster;
	private double damage = 1; //grundschaden

	public Spell(String name, BufferedImage[] i,
			long delay, Caster caster, Sprite target, Spielfeld p) {
		super(name, i, caster.getX(), caster.getY(), delay, p);
		this.caster = caster;
		double deltaX = (target.x - this.x);
		double deltaY = (target.y - this.y);
		double vecLength = Math.sqrt((deltaX*deltaX + deltaY*deltaY));
		deltaX =  deltaX/vecLength;
		deltaY =  deltaY/vecLength;
		this.dx = SPEED * deltaX;
		this.dy = SPEED * deltaY;
		new PlaySound(Sounds.CAST, false, 100);
	}

	
	public Spell(String name, BufferedImage[] i,
			double nextSpellDx, double nextSpellDy, int delay,Caster caster, Spielfeld p) {
		super(name, i, caster.getX(), caster.getY(), delay, p);
		this.caster = caster;
		this.dx = SPEED * nextSpellDx;
		this.dy = SPEED * nextSpellDy;
		new PlaySound(Sounds.CAST, false, 100);

	}
	
	/** Returns the damage of this Spell without resistance calculation
	 * @see de.dungencrawler.sprites.Sprite#getDamage()
	 */

	public double getDamage(double resistance) {
		int dmg =(int)(this.damage  + 5 * Math.random());
		dmg = (Math.random() > (1 - caster.getCritChance()) ? 2*dmg : dmg);
		dmg += 0.1 * caster.getMagery();
		dmg *= Math.exp(-resistance * 3/600); // resistens von 600 bedeutet e^-3 => 95% weniger Schaden
		return dmg;								// 100 bedeutet 40% wengiger Schaden
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

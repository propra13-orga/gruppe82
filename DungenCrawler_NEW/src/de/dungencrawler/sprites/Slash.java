package de.dungencrawler.sprites;

import java.awt.image.BufferedImage;
import java.util.Vector;

import de.dungencrawler.Spielfeld;
import de.dungencrawler.interfaces.Caster;

public class Slash extends Sprite {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Caster caster;
	private double damage = 25;
	private Vector<Sprite> allreadyHitted = new Vector<Sprite>();

	public Slash(String name, BufferedImage[] i,
			double x, double y, long delay, Caster caster, Spielfeld p) {
		super(name, i, x, y, delay, p);
		this.caster = caster;
	}
	
	@Override
	protected void computeAnimation() {
		currentpic++;

		if (currentpic > loop_to) {
			parent.removeActor(this);

		}
	}
	
	@Override
	public void doLogic(long delta) {
		// TODO Auto-generated method stub
		super.doLogic(delta);
		@SuppressWarnings("unchecked")
		Vector<Sprite> vec = (Vector<Sprite>) parent.getActors().clone();
		for(Sprite s : vec)
			
		if (s instanceof DynamicSprite && s.intersects(this) && !s.equals(caster)) {
			if(!allreadyHitted.contains(s)) {
				s.hit(this);
				allreadyHitted.add(s);
			}
		}
	}

	public double getDamage(double resistance) {
		int dmg =(int)(this.damage  + 5 * Math.random());
		dmg = (Math.random() > (1 - caster.getCritChance()) ? 2*dmg : dmg);
		dmg += 0.1 * caster.getMagery();
		return dmg;
	}
}

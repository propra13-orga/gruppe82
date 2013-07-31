package de.dungencrawler.sprites;

import java.awt.image.BufferedImage;
import java.util.Vector;

import de.dungencrawler.Spielfeld;

public class Slash extends Sprite {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Sprite caster;

	public Slash(String name, BufferedImage[] i, double x, double y,
			long delay, Sprite caster, Spielfeld p) {
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
			s.hit(this);
		}
	}

}

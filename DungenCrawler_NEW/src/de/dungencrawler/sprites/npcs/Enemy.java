package de.dungencrawler.sprites.npcs;

import java.awt.Graphics;
import java.awt.image.BufferedImage;

import de.dungencrawler.Spielfeld;
import de.dungencrawler.interfaces.Caster;
import de.dungencrawler.sprites.DynamicSprite;
import de.dungencrawler.sprites.Slash;
import de.dungencrawler.sprites.Spell;
import de.dungencrawler.sprites.Sprite;

public class Enemy extends DynamicSprite {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private static final double MAGIC_RESITANCE = 10;
	private static final double ARMOR = 10;
	private int moveCnt = 0;
	public Enemy(String name, BufferedImage[] i, double x, double y,
			long delay, Spielfeld p) {
		super(name, i, x, y, delay, p);
		life = 5;
		MAX_LIFE = 5;
		speed = 200;
	}
	@Override
	public void doLogic(long delta){
		
		randomMove();
		
		super.doLogic(delta);
		if(getX()<0){
			setHorizontalSpeed(0);
			setX(0);
		}
		if(getX()+getWidth()>parent.getWidth()){
			setX(parent.getWidth()-getWidth());
			setHorizontalSpeed(0);
			
		}
		if(getY()<0){
			setY(0);
			setVerticalSpeed(0);
		}
		if(getY()+getHeight()>parent.getHeight()){
			setY(parent.getHeight()-getHeight());
			setVerticalSpeed(0);	
		}
	}
	private void randomMove() {
	moveCnt ++;
		
		if(moveCnt > 7500 * Math.random()) {   
			double x = Math.random();
			if(x < 0.5) {
				setHorizontalSpeed(-speed);
			} else {
				setHorizontalSpeed(speed);

			}
			moveCnt = 0;
		}
	}
	@SuppressWarnings("unused")
	private void crazyRandomMove() {
		moveCnt ++;
		
		if(moveCnt > 600*Math.random()) {
//			System.out.println("MOVE");
			double x = Math.random();
//			System.out.println(x);
			if(x < 0.125) {
				setVerticalSpeed(0);
				setHorizontalSpeed(-speed);
			} else if(x < 0.25) {
				setVerticalSpeed(+speed);
				setHorizontalSpeed(-speed);
			} else if(x < 0.375) {
				setVerticalSpeed(+speed );
				setHorizontalSpeed(0);
			} else if(x < 0.5) {
				setVerticalSpeed(+speed);
				setHorizontalSpeed(+speed);
			} else if(x < 0.625) {
				setVerticalSpeed(0);
				setHorizontalSpeed(+speed);
			} else if(x < 0.75) {
				setVerticalSpeed(-speed);
				setHorizontalSpeed(+speed);
			} else if(x < 0.825) {
				setVerticalSpeed(-speed);
				setHorizontalSpeed(0);
			} else  {
				setVerticalSpeed(-speed);
				setHorizontalSpeed(-speed);
			} 
		}
	}
	
	@Override
	public void drawObjects(Graphics g) {
		super.drawObjects(g);
//		for(int i = 0; i < this.life; i++) {
//			g.drawImage(herz[0], (int)(this.x + herz[0].getWidth()*i + 1) , (int)(this.y + this.height), null);
//		}
		drawHealth(g);
	}
}

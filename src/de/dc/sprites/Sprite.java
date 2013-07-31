package de.dc.sprites;

import java.awt.Graphics;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.util.Vector;


import de.dc.Mauer;
import de.dc.Spieler;
import de.dc.Spielfeld;
import de.dc.Ziel;
import de.dc.interfaces.Drawable;
import de.dc.interfaces.Movable;




public abstract class Sprite extends Rectangle2D.Double implements Drawable, Movable {

	private static final long serialVersionUID= 1L;
	long delay;
	long animation= 0;
	protected Spielfeld parent;
	BufferedImage[] pics;
	int currentpic= 0;
	
	protected double dx;
	protected double dy;
	private double x;
	private double y;
	
	public Sprite(String name, BufferedImage[] i, double x,double y, long delay, Spielfeld p){
		System.out.println(name + " erstellt" + " bei " + x + "/" + y);
		pics= i;
		this.x=x;
		this.y=y;
		this.delay=delay;
		this.width= pics[0].getWidth();
		this.height= pics[0].getHeight();
		parent = p;
		
		
	}
	

public void setY(double y) {
		this.y = y;
	}


public void setX(double x) {
		this.x = x;
	}
public double getX() {
	return x;
}
public double getY() {
	return y;
}


public double getHorizontalSpeed(){
	return dx;
}

public void setHorizontalSpeed(double dx){
	this.dx=dx;
}
public double getVerticalSpeed(){
	return dy;
	
}
public void setVerticalSpeed(double dy){
	this.dy=dy;
}
	
	@Override
	public void doLogic(long delta) {
		animation=animation+(delta/1000000);
		if (animation>delay){
			animation=0;
			computeAnimation();
			
		}
		// TODO Auto-generated method stub
		
	}

	private void computeAnimation() {
		currentpic++;
		
		if(currentpic>=pics.length){
			currentpic=0;
			
		}
		// TODO Auto-generated method stub
		
	}

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
		Vector <Sprite> actors =parent.getActors();
		for(Sprite s:actors){
			if(s instanceof Mauer){
				if(this.intersects(s)){
					x=tempx;
					y=tempy;
					break;
				}
			}else if( s instanceof Ziel && this instanceof Spieler){
				if(this.intersects(s)){
					((Spielfeld) parent).nextmap();
					break;
				}
				
			}
		}
		// TODO Auto-generated method stub
		
	}

	@Override
	public void drawObjects(Graphics g) {
		g.drawImage(pics[currentpic],(int) x,(int) y, null);
		// TODO Auto-generated method stub
		
	}

	
}

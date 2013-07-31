package de.dungencrawler.sprites;

import java.awt.Graphics;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.io.Serializable;

import de.dungencrawler.Spielfeld;
import de.dungencrawler.bibliothek.PlaySound;
import de.dungencrawler.bibliothek.Sounds;
import de.dungencrawler.interfaces.Drawable;
import de.dungencrawler.interfaces.Movable;

public abstract class Sprite extends Rectangle2D.Double implements Drawable, Serializable,
		Movable {

	private static final long serialVersionUID = 1L;
	long delay;
	long animation = 0;
	protected Spielfeld parent;
	BufferedImage[] pics;
	int currentpic = 0;

	protected double dx;
	protected double dy;
	protected double x;
	protected double y;

	protected String name;

	int loop_from;
	int loop_to;

	protected double startX, startY; // neu fuer Falle

	public Sprite(String name, BufferedImage[] i, double x, double y,
			long delay, Spielfeld p) {
//		System.out.println(name + " erstellt" + " bei " + x + "/" + y);
		this.name = name;
		pics = i;
		this.x = x;
		this.y = y;
		this.delay = delay;
		if(pics != null) {
			this.width = pics[0].getWidth();
			this.height = pics[0].getHeight();
			loop_to = pics.length - 1;
		} else {
			this.width = 100;
			this.height = 100;
			loop_to = 100;
		}
		parent = p;
		loop_from = 0;
		this.startX = x; // neu fuer Falle
		this.startY = y; // neu fuer Falle
	}


	@Override
	public void doLogic(long delta) {
		animation = animation + (delta / 1000000);
		if (animation > delay) {
			animation = 0;
			computeAnimation();

		}
		// TODO Auto-generated method stub

	}

	protected void computeAnimation() {
		currentpic++;

		if (currentpic > loop_to) {
			currentpic = loop_from;

		}
	}

	public void setLoop(int from, int to) {
		loop_from = from;
		loop_to = to;
		currentpic = from;

	}

	@Override
	public void move(long delta) {
	}

	// TODO Auto-generated method stub

	@Override
	public void drawObjects(Graphics g) {
		g.drawImage(pics[currentpic], (int) x, (int) y, null);
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

	public double getHorizontalSpeed() {
		return dx;
	}

	public void setHorizontalSpeed(double dx) {
		this.dx = dx;
	}

	public double getVerticalSpeed() {
		return dy;

	}

	public void setVerticalSpeed(double dy) {
		this.dy = dy;
	}
	
	public void hit(Sprite s) {
	}
	
	public double getStartX() {
		return startX;
	}

	public void setStartX(double startX) {
		this.startX = startX;
	}

	public double getStartY() {
		return startY;
	}

	public void setStartY(double startY) {
		this.startY = startY;
	}
	public void doInteraction(Spieler spieler) {
		// TODO Auto-generated method stub
		
	}
	

}

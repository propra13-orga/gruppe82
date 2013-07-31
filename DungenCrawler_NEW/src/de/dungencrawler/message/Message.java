package de.dungencrawler.message;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;
import java.io.Serializable;

import de.dungencrawler.Spielfeld;

public class Message implements Serializable {

	protected String content;
	protected Point position;
	protected Color color;
	protected long delay;
	protected Spielfeld parent;
	protected long actual;
	
	public Message(String string, Point point, Color color, long delay, Spielfeld parent) {
		this.content = string;
		this.position = point;
		this.color = color;
		this.delay = delay;
		this.parent = parent;
	}
	
	public void drawString(Graphics g) {
		Color temp = g.getColor();
		g.setColor(color);
		g.drawString(content, position.x, position.y );
		g.setColor(temp);
	}
	
	public void doLogic(long delta) {
		actual = actual + (delta / 1000000);
//		System.out.println("actual: " + actual + " delay: " + delay);
		this.position.y -=1;
		if (actual > delay) {
			parent.removeMessage(this);
		}
	}

	public String getContent() {
		return content;
	}
	
	public int positionX () {
		return position.y;
	}
	
	public int positionY () {
		return position.x;
	}
	
	
}

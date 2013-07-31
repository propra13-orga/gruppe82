package de.dungencrawler.message;

import java.awt.Color;
import java.awt.Point;

import de.dungencrawler.Spielfeld;

public class QuestMessage extends Message {

	private double textDelay;

	public QuestMessage(String string, Point point, Color color, long delay,
			Spielfeld parent) {
		super(string, point, color, delay, parent);
		// TODO Auto-generated constructor stub
	}
	
	public void doLogic(long delta) {
		textDelay = textDelay + (delta / 1000000);
		actual = actual + (delta / 1000000);
//		System.out.println("actual: " + actual + " delay: " + delay);
		if(textDelay > delay / 50) {
			this.position.y -= 1;
			textDelay = 0;
		}
		if (actual > delay) {
			parent.removeMessage(this);
		}
	}

}

package de.dungencrawler.message;

import java.awt.Point;

public class Message {

	private String content;
	private Point position;
	
	public Message(String string, Point point) {
		this.content = string;
		this.position = point;
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

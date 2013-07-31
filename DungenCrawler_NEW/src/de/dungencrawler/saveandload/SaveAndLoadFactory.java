package de.dungencrawler.saveandload;

import java.util.Vector;

import de.dungencrawler.sprites.Spieler;
import de.dungencrawler.sprites.Sprite;

public class SaveAndLoadFactory {
	private Vector<Sprite> actors;
	private Spieler spieler;
	public SaveAndLoadFactory(Vector<Sprite> actors, Spieler spieler) {
		super();
		this.actors = actors;
		this.spieler = spieler;
	}
	
	
}

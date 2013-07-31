package de.dungencrawler;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.Vector;


import de.dungencrawler.bibliothek.Animations;
import de.dungencrawler.interfaces.GameGenerator;
import de.dungencrawler.sprites.Falle;
import de.dungencrawler.sprites.ManaPool;
import de.dungencrawler.sprites.Mauer;
import de.dungencrawler.sprites.Sprite;
import de.dungencrawler.sprites.Ziel;
import de.dungencrawler.sprites.npcs.Enemy;
import de.dungencrawler.sprites.npcs.EnemyCaster;
import de.dungencrawler.sprites.npcs.QuestNPC;


public class GameMapParser implements GameGenerator {
	
	private Spielfeld panel;
	private Vector<Sprite> elements = new Vector<Sprite>();
	final public static char WALL='#';
	final public static char FREE='2';
	final public static char FALLE='X';
	final public static char ZIEL='z';
	final public static char ENEMY1 = 'E';
	final public static char BOSS = 'B';
	final public static char MANAPOOL = 'M';
	final public static char QUEST_NPC = 'N';
	
	
	
	public GameMapParser(String filePath,Spielfeld panel) {
		super();
		this.panel = panel;
		parseFile(filePath);
	}
	
	public void parseFile(String filePath){


		ArrayList<Character> allowCharacter = new ArrayList<Character>();
		allowCharacter.add(WALL);
		allowCharacter.add(FREE);
		allowCharacter.add(FALLE);
		allowCharacter.add(ZIEL);
		allowCharacter.add(ENEMY1);
		allowCharacter.add(BOSS);
		allowCharacter.add(MANAPOOL);
		allowCharacter.add(QUEST_NPC);
		URL map_url = getClass().getClassLoader().getResource(filePath);
		
		try {
			InputStream in = new FileInputStream(map_url.getFile());

			int chr;
			int atm = -30;
			while((chr = in.read())!= -1) {
				
				if(allowCharacter.contains((char) chr)){
					atm += 30;	
					int y = atm / 810 *30;
					int x = atm % 810;
					if(y > 600)
						break; //TODO Sinnvolle Fehler Behandlung
					if((char)chr==WALL){
						
						elements.add(new Mauer("Mauer",Animations.WALL , x,  y, 100, panel));
					}else if((char)chr==FREE){
						// FREIES FELD
					}else if((char)chr==FALLE){
						elements.add(new Falle("Falle", Animations.FIRE_TRAP, x,  y, 100, panel));
					}else if((char)chr==ZIEL){
						elements.add(new Ziel("Ziel", Animations.GOAL, x, y, 100, panel));
					} else if((char)chr == ENEMY1) {
						elements.add(new Enemy("Minion", Animations.ENEMY1, x, y, 100, panel));
					} else if ((char)chr == BOSS) {
						elements.add(new EnemyCaster("BOSS", Animations.ENEMY1, x, y, 100, panel));
					} else if ((char)chr == MANAPOOL) {
						elements.add(new ManaPool("Manapool", Animations.MANA_POOL, x, y, 100, panel));
					} else if((char)chr == QUEST_NPC) {
						elements.add(new QuestNPC("QUEST-NPC", Animations.ENEMY1, x, y, 100, panel));

					}
				}
			}
			in.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

	public Vector<Sprite> getMap() {
		return elements;
	}


	
}

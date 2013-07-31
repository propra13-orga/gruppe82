package de.dungencrawler;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.StreamCorruptedException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.Vector;


import de.dungencrawler.bibliothek.Animations;
import de.dungencrawler.interfaces.GameGenerator;
import de.dungencrawler.secrets.PuzzleSwitch;
import de.dungencrawler.sprites.Exit;
import de.dungencrawler.sprites.Falle;
import de.dungencrawler.sprites.ManaPool;
import de.dungencrawler.sprites.Mauer;
import de.dungencrawler.sprites.ShopLink;
import de.dungencrawler.sprites.Spieler;
import de.dungencrawler.sprites.Sprite;
import de.dungencrawler.sprites.Ziel;
import de.dungencrawler.sprites.items.Coin;
import de.dungencrawler.sprites.npcs.Enemy;
import de.dungencrawler.sprites.npcs.EnemyCaster;
import de.dungencrawler.sprites.npcs.QuestNPC;
import de.dungencrawler.sprites.shopitems.AContinue;
import de.dungencrawler.sprites.shopitems.RefilLife;
import de.dungencrawler.sprites.shopitems.SmallSword;
import de.dungencrawler.sprites.shopitems.SmallWard;


public class GameMapParser implements GameGenerator {
	
	private Spielfeld panel;
	private Vector<Sprite> elements = new Vector<Sprite>();
	private String levelType;
	final public static char WALL = '#';
	final public static char SHOP_CONTINUE = '+';
	final public static char FREE = '.';
	final public static char FALLE = 'X';
	final public static char ZIEL = 'z';
	final public static char ENEMY1 = 'E';
	final public static char BOSS = 'B';
	final public static char MANAPOOL = 'M';
	final public static char QUEST_NPC = 'N';
	final public static char SHOP = 'S';
	final public static char EXIT = 'e';
	final public static char START = 'P';
	final public static char COIN = 'C';
	final public static char REFILL_LIFE = 'R';
	final public static char SMALL_SWORD = 'J';
	final public static char SMALL_WARD = 'I';
	final public static char BREAKABLE_WALL = '=';
	final public static char[] SWITCHES = {'1','2','3','4','5','6','7','8','9'};



	
	
	public GameMapParser(String filePath,Spielfeld panel) {
		super();
		this.panel = panel;
		panel.clearPuzzle();
		parseFile(filePath);
	}
	
	public void parseFile(String filePath){

		ArrayList<Character> allowedCharacter = getAllowedCharacters();
		URL map_url = getClass().getClassLoader().getResource(filePath);
		
		try {
			InputStream in = new FileInputStream(map_url.getFile());
			BufferedReader reader = new BufferedReader(new FileReader(map_url.getFile()));
			String tempo;
			while((tempo = reader.readLine()) != null) {
				if(tempo.startsWith("!STORYPIC!=")) {
					tempo = tempo.substring(11,tempo.length());
					panel.setStoryPic(tempo);
				} else if(tempo.startsWith("!LEVELTYPE!=")) {
					this.levelType = tempo.substring(12,tempo.length());
					System.out.println(this.levelType);
				}
			}
			int chr;
			int atm = -30;
			while((chr = in.read())!= -1) {
				
				if(allowedCharacter.contains((char) chr)){
					atm += 30;	
					int y = atm / 810 *30 + 30;
					int x = atm % 810;
					if(y > 600)
						break; //TODO Sinnvolle Fehler Behandlung
					if((char)chr == WALL){
						elements.add(new Mauer("Mauer", Animations.getWallByMapType(levelType), x,  y, 100, panel));
					}else if((char)chr == FREE){
						// FREIES FELD
					}else if((char)chr == FALLE){
						elements.add(new Falle("Falle", Animations.getTrapByType(levelType), x,  y, 100, panel));
					}else if((char)chr == ZIEL){
						elements.add(new Ziel("Ziel", Animations.GOAL, x, y, 100, panel));
					} else if((char)chr == ENEMY1) {
						elements.add(new Enemy("Minion", Animations.ENEMY1, x, y, 100, panel));
					} else if ((char)chr == BOSS) {
						elements.add(new EnemyCaster("BOSS", Animations.ENEMY1, x, y, 100, panel));
					} else if ((char)chr == MANAPOOL) {
						elements.add(new ManaPool("Manapool", Animations.MANA_POOL, x, y, 100, panel));
					} else if((char)chr == QUEST_NPC) {
						File temp  = new File(map_url.getFile());
						elements.add(new QuestNPC("QUEST-NPC", Animations.QUEST_NPC, x, y, 300, panel, getQuestMessage(temp.getName())));
					} else if ((char)chr == SHOP) {
						if(x >= 780) {
							elements.add(new ShopLink("Shop Link", Animations.SHOP_RIGHT, x, y, 100, panel));
						} else if(x < 30) {
							elements.add(new ShopLink("Shop Link", Animations.SHOP_LEFT, x, y, 100, panel));
						} else if(y < 30) {
							elements.add(new ShopLink("Shop Link", Animations.SHOP_TOP, x, y, 100, panel));
						} else if (y >= 570) {
							elements.add(new ShopLink("Shop Link", Animations.SHOP_BOTTOM, x, y, 100, panel));
						}
					} else if((char)chr == EXIT) {
						elements.add(new Exit("Exit", Animations.SHOP_BOTTOM, x, y, 100, panel));
					} else if((char)chr == START) {
						Spieler spieler = panel.getPlayer();
						spieler.setX(x);
						spieler.setY(y);
						spieler.setStartX(x);
						spieler.setStartY(y);
					} else if ((char)chr == COIN) {
						elements.add(new Coin("Coin", Animations.COIN, x, y, 100, panel));
					} else if ((char)chr == SHOP_CONTINUE) {
						elements.add(new AContinue("Shop Continue", Animations.CONTINUES , x, y, 100, panel));
					} else if((char)chr == REFILL_LIFE) {
						elements.add(new RefilLife("Refill Life", Animations.REFIL_IMAGES, x, y, 100, panel, 5));
					} else if((char)chr == SMALL_SWORD) {
						elements.add(new SmallSword("Small Sword", Animations.ATTACK_DAMAGE_ICON, x, y, 100, panel, 50));
					} else if((char)chr == SMALL_WARD) {
						elements.add(new SmallWard("Small Ward", Animations.MAGERY_ICON, x, y, 100, panel, 80));
					} else if (((char) chr) >= '1' && ((char) chr) <= '9') {
						PuzzleSwitch pSwitch = new PuzzleSwitch("Switch", Animations.SWITCH,
								x, y, 100, panel, Integer.parseInt("" +(char)chr));
						elements.add(pSwitch);
						panel.addSwitch(pSwitch);
					} else if((char)chr == BREAKABLE_WALL) {
						Mauer wall = new Mauer("Mauer", Animations.WALL , x,  y, 100, panel);
						elements.add(wall);
						panel.addBreakableWall(wall);
					}
				}
			}
			in.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}

	private ArrayList<Character> getAllowedCharacters() {
		ArrayList<Character> list = new ArrayList<Character>();
		list.add(WALL);
		list.add(FREE);
		list.add(FALLE);
		list.add(ZIEL);
		list.add(ENEMY1);
		list.add(BOSS);
		list.add(MANAPOOL);
		list.add(QUEST_NPC);
		list.add(SHOP);
		list.add(EXIT);
		list.add(START);
		list.add(COIN);
		list.add(SHOP_CONTINUE);
		list.add(REFILL_LIFE);
		list.add(SMALL_SWORD);
		list.add(SMALL_WARD);
		list.add(BREAKABLE_WALL);
		for(int i = 1; i <= 9; i++) {
			list.add(("" + i).charAt(0));
		}
		return list;
	}

	public Vector<Sprite> getMap() {
		return elements;
	}
	
	private String[] getQuestMessage(String str) {
		Hashtable<String, String[]> table = new Hashtable<String, String[]>();
		   
			String[] temp = generateStringArray("Seit Jahren schon gesucht und keiner konnte ihn bis jetzt schnappen, bis jetzt. Folge Karlo, dem Bösen Halunken und rette deine geliebte Freundin, bevor er mit ihr verschwindet und über alle Berge ist. Es erwartet dich ein harter Kampf, verschiedene, unbekannte Orte und Lebewesen, denen man lieber aus dem Weg gehen sollte. Aber habe keine Angst. Ich begleite dich und helfe dir. Drücke \"Leertaste\" und ich verrate dir deine Fähigkeiten, die dir helfen deine Freundin zu retten denn bereits das erste unbekannte Wesen namens Minion erwartet dich schon. Los Geht's!" +
					" Space = Interaction \n " +
					"Q = Normal Attack \n" +
					"W = Magic Attack \n " +
					"S = Switch Spell");
			table.put("lvl1.txt", temp);
		

			temp = generateStringArray("Du kommst dem bösen Karlo immer näher auf deiner Verfolgungsjadg durch die verschiedenen Unbekannten Welten. Die dir bevorstehende und letzte Welt ist die Welt der Menschen. Laub, Bäume und ganz viele Fallen erwarten dich in deiner letzten Welt. Verhalte dich unauffällig Kämpfe tapfer, bald hast du es geschaft und hast deine Freundin gerettet.Aber Halt!Bevor du eilst dein letztes Rätsel steht dir bevor. Betätige die Schalter in der richtigen Reihenfolge und die heilige Kraft möge mit dir sein!"); 
			table.put("lvl8.txt", temp);

			temp=generateStringArray("Noch immer steckst du in den Katakomben fest und die unbekannten Lebewesen haben sich bereits vermehrt und haben Apetit mitgebracht. Auch hier muss du wieder die Schalter bestätigen - jedoch versteckt sich eine bestimmte Kombination dahinter. Wirst du es schaffen, die richtige Reihenfolge  heraus zu finden um dir den Weg freizuschaufeln?!"); 
			table.put("lvl2.txt", temp);

			temp = generateStringArray("Der böse Karlo ist doch schneller als gedacht und ist dir bereits wieder eine Welt voraus. Es wird kalt, rutschig und zerbrechlich. Willkommen in der Eiswelt. Halte durch und verliere dein Ziel nicht aus den Augen!");
			table.put("lvl5.txt", temp);

			temp = generateStringArray("Der böse Karlo macht es euch nicht leicht und verwischt seine Spuren. Löst wieder das Rätsel, in dem ihr die richtigen Schalter betätigt, damit sich der geheime Weg öffnet und ihr an Ziel gelangen könnt!");
			table.put("lvl6.txt", temp);
			
			temp = generateStringArray("Mutig, Mutig. Was man nicht alles für seine geliebte Freundin tut. Du hast es fast geschafft und siehst bereits ein helles Licht am Ende des Ganges der Katakomben. Doch was ist das dort drüben... Ein böse,wütende Königen, die verärgert wurde, da du ihre Untertanten getötet hast. Rache spiegelt sich in Ihren Augen wieder. Gehe am besten vorher noch in den Item-Shop, rüste dich gut aus und nehme den harten , bitteren Kampf auf!!! Get Ready for Rumble!");
			table.put("lvlTest.txt", temp);

		return(table.get(str));
	}
	
	private String[] generateStringArray(String str) {
		int maxCharacters = 80;
		String[] result = new String[str.length()/maxCharacters + 1]; 
		for(int i = 0; str.length() > maxCharacters; i++) {
			result[i] = str.substring(0,maxCharacters - 1);
			str = str.substring(maxCharacters);
			System.out.println(result[i]);
		}
		result[result.length - 1] = str;
		return result;
	}
}

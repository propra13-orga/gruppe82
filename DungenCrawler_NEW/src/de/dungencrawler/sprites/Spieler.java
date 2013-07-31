package de.dungencrawler.sprites;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.image.BufferedImage;
import java.io.Serializable;
import java.util.Vector;

import de.dungencrawler.Spielfeld;
import de.dungencrawler.bibliothek.Animations;
import de.dungencrawler.interfaces.Caster;
import de.dungencrawler.interfaces.Interactable;
import de.dungencrawler.items.BodyArmor;
import de.dungencrawler.items.Shoe;
import de.dungencrawler.items.Waffe;
import de.dungencrawler.message.Message;
import de.dungencrawler.secrets.PuzzleSwitch;
import de.dungencrawler.secrets.SwitchPuzzle;
import de.dungencrawler.sprites.items.Coin;
import de.dungencrawler.sprites.npcs.Enemy;

public class Spieler extends DynamicSprite implements Caster, Serializable {
	private static final long serialVersionUID = 1L;

	public static final double MAXMANA = 100;
	public static final double MAXLIFE = 100;

	private static final int FIREBALL = 1;
	private static final int ICEBALL = 2;

	private double magicResistance = 25;
	private double armor = 25;
	
	private int[] spells = {1,2};

	private boolean castSpell = false;
	private boolean slash = false;
	private boolean shopShild = false;
	private Sprite shopDummy = null;
	
	private double nextSpellDx = 1;
	private double nextSpellDy = 0;

	private int spellNr = 0;
	private int continues;
	private int coins = 0;
	
	private BodyArmor bodyArmor = null;
	private Shoe shoe = null;
	private Waffe wappon = null;
	
	Vector<Sprite> savedActors = null;

	private double magery = 1;
	private double critChance = 0.05;
	private double attackPower = 1;

	private SwitchPuzzle savedPuzzle;
	
	public Spieler(String name, BufferedImage[] i, double x, double y,
			long delay, Spielfeld p) {
		super(name, i, x, y, delay, p);
		life = MAXLIFE;
		mana = MAXMANA;
		MAX_MANA = MAXMANA;
		MAX_LIFE = MAXLIFE;
		continues = 3;
		speed = 300;
	}

	@Override
	public void doLogic(long delta) {

		super.doLogic(delta);
		if (getX() < 0) {
			setHorizontalSpeed(0);
			setX(0);
		}
		if (getX() + getWidth() > parent.getWidth()) {
			setX(parent.getWidth() - getWidth());
			setHorizontalSpeed(0);
		}
		if (getY() < 30) {
			setY(30);
			setVerticalSpeed(0);
		}
		if (getY() + getHeight() > parent.getHeight()) {
			setY(parent.getHeight() - getHeight());
			setVerticalSpeed(0);
		}
		castSpell();
		doSlash();
	}

	private void doSlash() {
		if(slash) {
			if(nextSpellDx > 0 && nextSpellDy == 0) {
				parent.addActor(new Slash("Slash", Animations.SLASH_RIGHT,
						(int)(this.x + this.width + 10), (int)(this.y), 30, this, parent));
			} else if(nextSpellDx < 0 && nextSpellDy == 0) {
				parent.addActor(new Slash("Slash", Animations.SLASH_LEFT,
						(int)(this.x - 10), (int)(this.y), 30, this, parent));
			} else if(nextSpellDx == 0 && nextSpellDy > 0) {
				parent.addActor(new Slash("Slash", Animations.SLASH_BOTTOM,
						(int)(this.x), (int)(this.y + this.height + 10), 30, this, parent));
			} else if(nextSpellDx == 0 && nextSpellDy < 0) {
				parent.addActor(new Slash("Slash", Animations.SLASH_TOP,
						(int)(this.x), (int)(this.y - 10), 30, this, parent));
			}
			this.slash = false;
		}
	}

	private void castSpell() {
		if(this.castSpell) {
			this.castSpell = false;
			if (this.mana > Spell.MANA_COST) {
				this.mana -= Spell.MANA_COST;
				if(spells[spellNr] == FIREBALL) {
					parent.addActor(new Feuerball("feuerball", 
							Animations.FIRE_BALL, 
							nextSpellDx, 
							nextSpellDy, 
							100,
							this,
							parent));
				}
				if(spells[spellNr] == ICEBALL) {
					parent.addActor(new Iceball("icqball", 
							Animations.ICE_BALL, 
							nextSpellDx, 
							nextSpellDy, 
							100,
							this,
							parent));
				}
			}
		}
	}
 
	@SuppressWarnings("unchecked")
	@Override 
	public void move(long delta) {
		super.move(delta);
		nextSpellWay();
		Vector<Sprite> actors = parent.getActors();
		for(Sprite s : actors) {
			if (this.intersects(s)) {
				if(s instanceof Ziel){
					System.out.println(this.name + " hat das Ziel erreicht!");
					String storyPicTemp = parent.getStoryPic();
					System.out.println("Story Pic: " + storyPicTemp);
					if(storyPicTemp != null && !storyPicTemp.equals("")) {
						parent.startStoryViewer(Animations.getStoryPic(storyPicTemp));
					} else {
						((Spielfeld) parent).nextMap();
						break;
					}
				} else if (s instanceof Falle) {
					System.out.println(this.name + " hat noch " + this.life + " Lebenspunkte!");
					this.hit(s);
				} else if (s instanceof Enemy) {
						this.hit(s);
				} else if ((s instanceof ShopLink) && !shopShild) {
					savedActors = parent.getActors();
					shopDummy = new PlayerPositionDummy("dummy", null, x, y, 100, parent);
					savedActors.add(shopDummy);
					savedPuzzle = parent.getPuzzle();
					savedActors.remove(this);
					parent.doShopInitalisation();
				} else if((s instanceof Exit)) {
					if(savedActors != null) {
						shopShild = true;
						System.out.println("ShopShild setted!");
						parent.loadMapFromActors((Vector<Sprite>) savedActors.clone());	
						parent.setPuzzle(savedPuzzle);
					} else {
						System.out.println("Schwerer Fehler, Actors der letzen Map wurden nicht gespeichert.");
					}
				} else if (s instanceof Coin) {
					parent.removeActor(s);
					coins++;
				} else if(s instanceof PuzzleSwitch) {
					parent.setSwitch((PuzzleSwitch) s);
				}
			}
		}
		if(shopShild)
			unsettingShopShild();
	}
	
	private void unsettingShopShild() {
		double dtX = (this.x - shopDummy.getX());
		double dtY = (this.y - shopDummy.getY());
		double abs = Math.sqrt(dtX * dtX + dtY * dtY);
		if(abs > 40) {
			shopShild = false;
			shopDummy = null;
		}
	}

	public void hit(Sprite s) {
		double dmg = 0;
		if ( s instanceof Spell) {
			Spell sp = (Spell) s;
			dmg = sp.getDamage(magicResistance);
			this.life -= dmg;
		} else if (s instanceof Slash) {
			Slash sl = (Slash) s;
			dmg = sl.getDamage(armor);
			this.life -= dmg;
		} else if(s instanceof Falle) {
			Falle f = (Falle) s;
			dmg = f.getDamage(armor);
			this.life -= dmg;
		} else if(s instanceof Enemy) {
			dmg = 1;
			this.life -= dmg;
		}
		Point point = new Point((int)this.x, (int)this.y);
		parent.addMessage(new Message("" + (int)dmg, point, Color.RED, 300, parent));
		
		if(this.life <= 0) {
			continues--;
			this.x = startX;
			this.y = startY;
			life = MAXLIFE;
			if(continues < 0) {
				System.out.println(s.name + "makes some dmg on " + this.name);
				parent.removeActor(this);
			}
		}
	}

	
	private void nextSpellWay() {
		if(dx > 0) {
			nextSpellDx = 1;
		} else if (dx < 0) {
			nextSpellDx = -1;
		}
		if(dy > 0) {
			nextSpellDy = 1;
		} else if (dy < 0) {
			nextSpellDy = -1;
		}
		if (dx == 0 && dy != 0) {
			nextSpellDx = 0;
		} else if (dy == 0 && dx != 0) {
			nextSpellDy = 0;
		}
	}

	@Override
	public void drawObjects(Graphics g) {
		super.drawObjects(g);
//		for(int i = 0; i < this.life; i++) {
//			g.drawImage(herz[0], (int)(this.x + herz[0].getWidth()*i + 1) , (int)(this.y + this.height), null);
//		}
		drawHealth(g);
		drawMana(g);
	}

	public void switchSpell() {
		spellNr++;
		if(spellNr >= spells.length) {
			spellNr = 0;
		}
		BufferedImage[] monitorPic = null;
		if(spells[spellNr] == FIREBALL) {
			monitorPic = Animations.FIRE_MONITOR;
		} else if(spells[spellNr] == ICEBALL) {
			monitorPic = Animations.ICE_MONITOR;
		}
		parent.addActor(new SpellMonitor("Spellmonitor", monitorPic,
				parent.getWidth() - 55, parent.getHeight() - 55, 100, parent));
	}

	public void interAction() {
		Vector<Sprite> vec = parent.getActors();
		Double rect = new Double(this.x - 30, this.y - 30, 90, 90);
		for(Sprite s : vec) {
			if(s instanceof Interactable && s.intersects(rect)) {
				System.out.println("Interaction with " + s.name);
				s.doInteraction(this);
			}
		}
	}
	public void throwSpell() {
		this.castSpell  = true;
	}

	public double getNextSpellDx() {
		return nextSpellDx;
	}

	public void setNextSpellDx(double nextSpellDx) {
		this.nextSpellDx = nextSpellDx;
	}

	public double getNextSpellDy() {
		return nextSpellDy;
	}

	public void setNextSpellDy(double nextSpellDy) {
		this.nextSpellDy = nextSpellDy;
	}

	public int getContinues() {
		return continues;
	}

	public void slash() {
		this.slash  = true;
	}

	public int getCoins() {
		return coins;
	}

	public boolean buyItem(int price) {
		if(coins >= price) {
			coins -= price;
			return true;
		} else {
			return false;	
		}
	}

	public void addContinue(int i) {
		this.continues += i;
	}
	
	@Override
	public double getMagery() {
		double temp = magery;
		if (wappon != null)
			temp += wappon.getMagicDamage();
		return temp;	}
	
	public double getAttackPower() {
		double temp = attackPower;
		if (wappon != null)
			temp += wappon.getNormalDamage();
		return temp;	
	}

	@Override
	public double getCritChance() {
		return critChance;
	}

	public void addLife(int i) {
		i = Math.abs(i);
		double temp = life + i;
		if(temp > MAXLIFE) {
			life = MAXLIFE;
		} else {
			life = temp;
		}
	}

	public double getMagicResistance() {
		double temp = magicResistance;
		if (bodyArmor != null)
			temp += bodyArmor.getMagicResistance();
		if(shoe != null)
			temp += shoe.getMagicResistance();
		
		return temp;
	}

	public double getArmor() {
		double temp = armor;
		if (bodyArmor != null)
			temp += bodyArmor.getArmor();
		if(shoe != null)
			temp += shoe.getArmor();
		return temp;
	}

	public void setBodyArmor(BodyArmor bodyArmor) {
		this.bodyArmor = bodyArmor;
	}

	public void setShoe(Shoe shoe) {
		this.shoe = shoe;
	}

	public void setWappon(Waffe wappon) {
		this.wappon = wappon;
	}
	
	
}

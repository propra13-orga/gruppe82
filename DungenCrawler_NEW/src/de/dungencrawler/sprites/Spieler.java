package de.dungencrawler.sprites;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.util.Vector;

import de.dungencrawler.Spielfeld;
import de.dungencrawler.bibliothek.Animations;
import de.dungencrawler.interfaces.interactable;
import de.dungencrawler.sprites.npcs.Enemy;

public class Spieler extends DynamicSprite {
	private static final long serialVersionUID = 1L;

	public static final double MAX_MANA = 100;
	public static final double MAX_LIFE = 5;
	private int[] spells = {1,2};

	private static final int FIREBALL = 1;
	private static final int ICEBALL = 2;

	private boolean fireball = false;
	private double nextSpellDx = 1;
	private double nextSpellDy = 0;

	private boolean slash = false;

	private int spellNr = 0;

	private int continues;
	
	public Spieler(String name, BufferedImage[] i, double x, double y,
			long delay, Spielfeld p) {
		super(name, i, x, y, delay, p);
		life = MAX_LIFE;
		mana = MAX_MANA;
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
		if (getY() < 0) {
			setY(0);
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
			parent.addActor(new Slash("Slash", Animations.SLASH_RIGHT,
					(int)(this.x + this.width + 10), (int)(this.y), 30, this, parent));
			this.slash = false;
		}
	}

	private void castSpell() {
		if(this.fireball) {
			this.fireball = false;
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
 
	@Override 
		public void move(long delta) {
		super.move(delta);
		nextSpellWay();
		Vector<Sprite> actors = parent.getActors();
		for(Sprite s : actors) {
			if( s instanceof Ziel && this instanceof Spieler){
				if(this.intersects(s)){
					System.out.println(this.name + " hat das Ziel erreicht!");
					((Spielfeld) parent).nextmap();
					break;
				}
							
			} else if (this instanceof Spieler &&
					( s instanceof Falle)) { // auch neu fuer Falle
				if(this.intersects(s)){
					System.out.println(this.name + " hat noch " + this.life + " Lebenspunkte!");
					this.life = this.life -1;
					 this.x = this.startX;
					 this.y = this.startY;
				}
			} else if (this instanceof Spieler &&
					( s instanceof Enemy)) {
				if(s.intersects(this)) {
					this.hit(s);
				}
			}
		}
	}
	
	public void hit(Sprite s) {
		if ( s instanceof Spell) {
			this.life -= 1;
		} else if (s instanceof Slash) {
			this.life -= 1;
		} else if (s instanceof Enemy) {
			this.life -= 1;
		}
		if(this.life <= 0) {
			continues--;
			this.x = startX;
			this.y = startY;
			life = 5;
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
		for(int i = 0; i < this.life; i++) {
			g.drawImage(herz[0], (int)(this.x + herz[0].getWidth()*i + 1) , (int)(this.y + this.height), null);
		}
		g.setColor(Color.blue);
		g.fillRect((int)(this.x),
				(int)(this.y + this.height + this.herz[0].getHeight() + 1), 
				(int) (this.width/MAX_MANA * this.mana), 5);
		g.setColor(Color.BLACK);
		g.drawRect((int)(this.x),
				(int)(this.y + this.height + this.herz[0].getHeight() + 1), 
				(int) this.width, 5);
		
	}

	public void throwFireBall() {
		this.fireball  = true;
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
		System.out.println("Interaction!!!");
		Vector<Sprite> vec = parent.getActors();
		Double rect = new Double(this.x - 30, this.y - 30, 90, 90);
		for(Sprite s : vec) {
			if(s instanceof interactable && s.intersects(rect)) {
				System.out.println("Interaction with " + s.name);
				s.doInteraction(this);
			}
		}
	}
}

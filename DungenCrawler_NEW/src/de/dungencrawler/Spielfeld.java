package de.dungencrawler;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Label;
import java.awt.LayoutManager;
import java.awt.Panel;
import java.awt.Point;
import java.awt.geom.Rectangle2D.Double;
import java.io.File;
import java.net.URL;
import java.util.ListIterator;
import java.util.Vector;

import javax.swing.JFrame;
import javax.swing.JPanel;

import de.dungencrawler.bibliothek.Animations;
import de.dungencrawler.interfaces.GameGenerator;
import de.dungencrawler.menu.StartMenu;
import de.dungencrawler.message.Message;
import de.dungencrawler.sprites.Spieler;
import de.dungencrawler.sprites.Sprite;

public class Spielfeld extends JPanel implements Runnable {

	/**
	 * 
	 */
	final static private boolean TEST_LEVEL_ON =  false;
	private static final long serialVersionUID = 1L;
	JFrame frame;
	JPanel speakBox;

	long delta = 0; // zeit die fuer den letzten durchlauf benoetigt wurde
	long last = 0; // speicherung systemzeit
	long fps = 0; // fps anzeigen
	int currentMap = 1;

	public Spieler player;
	Vector<Sprite> actors;
	Vector<Sprite> painter;

	private boolean started;
	private boolean up;
	private boolean down;
	private boolean left;
	private boolean right;
	
	StartMenu sMenu;
	private Interface gameInterface;
	private Message message;

	public static void main(String[] args) {
		new Spielfeld(810, 600);
	}

	public Spielfeld(int w, int h) {
		this.setPreferredSize(new Dimension(w, h));
		frame = new JFrame("GameDemo");
		frame.setLocation(50, 0); // Spielfeld setzen
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		sMenu = new StartMenu(w, h);
		frame.add(sMenu);
		frame.addKeyListener(new DCKeyListener(this));
		sMenu.setVisible(true);
		this.setVisible(false);
		frame.pack();
		frame.setVisible(true);

		Thread th = new Thread(this);
		th.start();
	}

	public void doInitializations() {
		sMenu.setVisible(false);
		frame.add(this);
		this.setVisible(true);
		last = System.nanoTime();
		actors = new Vector<Sprite>();
		painter = new Vector<Sprite>();
		player = new Spieler("Spieler1", Animations.PLAYER, 0, 30, 100, this);
		URL url = getClass().getClassLoader()
				.getResource("maps/lvl" + currentMap + ".txt");
		File testFile = new File(url.getFile());
		System.out.println(testFile.getAbsolutePath());
		GameGenerator gen;
		if (TEST_LEVEL_ON ) {
			gen = new GameMapParser("maps/lvlTest.txt",
					this);
		} else if (testFile.exists())
			gen = new GameMapParser("maps/lvl" + currentMap + ".txt",
					this);
		else
			gen = new GameMapParser("maps/lvl1.txt", this);
		actors = gen.getMap();
		actors.add(player);
		this.gameInterface = new Interface(this);
	}

	@SuppressWarnings("unused")
	private void gameOver() {
		started = false;
		actors.clear();
		player = null;
		painter.clear();
		currentMap = 1;
	}

	@Override
	public void run() {

		while (frame.isVisible()) {
			computeDelta();

			if (isStarted()) {
				checkKeys(); // tastatureingabe
				moveObjects();
				doLogic();
				cloneVectors();
				repaint();
			}
			try {
				Thread.sleep(10); // 10 MS schlafen
			} catch (InterruptedException e) {
			} // Ausnahmefall
		}
		// TODO Auto-generated method stub

	}

	@SuppressWarnings("unchecked")
	private void cloneVectors() {
		painter = (Vector<Sprite>) actors.clone();
	}

	@SuppressWarnings("unchecked")
	private void moveObjects() {
		Vector<Sprite> temp = (Vector<Sprite>) actors.clone();
		for (ListIterator<Sprite> it = temp.listIterator(); it.hasNext();) {
			Sprite r = it.next();
			r.move(delta);
		}
		// TODO Auto-generated method stub

	}

	@SuppressWarnings("unchecked")
	private void doLogic() {
		Vector<Sprite> temp = (Vector<Sprite>) actors.clone();
		for (ListIterator<Sprite> it = temp.listIterator(); it.hasNext();) {
			Sprite r = it.next();
			r.doLogic(delta);
		}
		// TODO Auto-generated method stub

	}

	private void checkKeys() {
		if (up) {
			player.setVerticalSpeed(-player.getSpeed());
		}

		if (down) {
			player.setVerticalSpeed(player.getSpeed());
		}
		if (right) {
			player.setHorizontalSpeed(player.getSpeed());
		}
		if (left) {
			player.setHorizontalSpeed(-player.getSpeed());
		}
		if (!up && !down) {
			player.setVerticalSpeed(0);
		}
		if (!left && !right) {
			player.setHorizontalSpeed(0);
		}
	}

	private void computeDelta() {
		delta = System.nanoTime() - last;
		last = System.nanoTime();
		fps = ((long) 1e9) / delta; // 1/Sekunde ausgabe

	}

	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		g.setColor(Color.green);
		g.drawString("FPS: " + Long.toString(fps), 20, 10);
		gameInterface.drawObjects(g);
		if(message != null)
			g.drawString(message.getContent(), message.positionX(), message.positionY());
		if (painter != null) {
			for (ListIterator<Sprite> it = painter.listIterator(); it.hasNext();) {
				Sprite r = it.next();
				r.drawObjects(g);
			}
		}
	}

	public boolean isStarted() {
		return started;
	}

	public void setStarted(boolean started) {
		this.started = started;
	}

	@SuppressWarnings("unchecked")
	public Vector<Sprite> getActors() {
		return (Vector<Sprite>) actors.clone();
	}

	public void nextmap() {
		started = false;
		actors.clear();
		player = null;
		painter.clear();
		currentMap++;
		doInitializations();
		started = true;
	}

	public boolean isUp() {
		return up;
	}

	public void setUp(boolean up) {
		this.up = up;
	}

	public boolean isDown() {
		return down;
	}

	public void setDown(boolean down) {
		this.down = down;
	}

	public boolean isLeft() {
		return left;
	}

	public void setLeft(boolean left) {
		this.left = left;
	}

	public boolean isRight() {
		return right;
	}

	public void setRight(boolean right) {
		this.right = right;
	}

	public void throwFireBall(boolean b) {
		player.throwFireBall();
		
	}

	public void addActor(Sprite actor) {
		this.actors.add(actor);
		
	}

	public void removeActor(Sprite actor) {
		System.out.println("Actors: " + actors.size());
		this.actors.remove(actor);
		System.out.println("Actors: " + actors.size());
	}

	public void slash() {
		player.slash();
	}

	public void switchSpell() {
		player.switchSpell();
		
	}

	public void interAction() {
		player.interAction();
	}

	public Spieler getPlayer() {
		return player;
	}

	public void speak(Message message) {
		this.message = message;
	}
}
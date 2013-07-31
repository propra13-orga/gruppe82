package de.dungencrawler;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInput;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.URL;
import java.util.Iterator;
import java.util.ListIterator;
import java.util.Vector;

import javax.imageio.ImageIO;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;

import org.omg.CosNaming.IstringHelper;

import de.dungencrawler.bibliothek.Animations;
import de.dungencrawler.bibliothek.PlaySound;
import de.dungencrawler.bibliothek.Sounds;
import de.dungencrawler.interfaces.GameGenerator;
import de.dungencrawler.menu.MenuActionListener;
import de.dungencrawler.menu.StartMenu;
import de.dungencrawler.menu.StoryPicViewer;
import de.dungencrawler.message.Message;
import de.dungencrawler.saveandload.SaveObject;
import de.dungencrawler.secrets.PuzzleSwitch;
import de.dungencrawler.secrets.SwitchPuzzle;
import de.dungencrawler.sprites.Mauer;
import de.dungencrawler.sprites.PlayerPositionDummy;
import de.dungencrawler.sprites.Spieler;
import de.dungencrawler.sprites.Sprite;

public class Spielfeld extends JPanel implements Runnable, Serializable {

	/**
	 * 
	 */
	final static public boolean TEST_LEVEL_ON =  false;
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

	private boolean paused = true;
	private boolean started = false;
	private boolean up;
	private boolean down;
	private boolean left;
	private boolean right;
	
	StartMenu sMenu;
	private Interface gameInterface;
	private Vector<Message> messages = new Vector<Message>();
	private Animations animations;
	private BufferedImage background;
	private SwitchPuzzle puzzle;
	private StoryPicViewer viewer;
	private String storyPic;
	private boolean viewerStarted = false;
	private boolean beServer;
	private ServerSocket socket;
	private Socket client;
	private boolean beClient;
	private Sounds sounds;
	private PlaySound music;
	
	public static void main(String[] args) {
		new Spielfeld(810, 630);
	}

	public Spielfeld(int w, int h) {
		this.animations = new Animations();
		this.sounds = new Sounds();
		this.setPreferredSize(new Dimension(w, h));
		frame = new JFrame("DungenCrawler");
		frame.setLocation(50, 0); // Spielfeld setzen
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		sMenu = new StartMenu(w, h);
		frame.add(sMenu);
		frame.addKeyListener(new DCKeyListener(this));
		sMenu.setVisible(true);
		this.setVisible(false);
		JMenuBar menu = getMenuBar();
		frame.setJMenuBar(menu);
		frame.pack();
		frame.setVisible(true);
		player = new Spieler("Spieler1", animations.PLAYER, 0, 30, 300, this);

		Thread th = new Thread(this);
		th.start();
		music = new PlaySound(Sounds.START_MUSIK, true, 15000);
	}
	
	public void beginStory() {
		sMenu.setVisible(false);
		viewerStarted = true;
		BufferedImage[] temp = new BufferedImage[2];
		temp[0] = Animations.STORY1;
		temp[1] = Animations.STORY2;

		startStoryViewer(temp);		
	}

	public void restartGame() {
		player = new Spieler("Spieler1", animations.PLAYER, 0, 30, 300, this);
		currentMap = 1;
		if(TEST_LEVEL_ON) {
			this.doInitializationsTestLevel();
		} else {
			this.doInitializations();
		}
	}
	
	public void startNetworkGameAsServer() {
		try {
			this.socket = new ServerSocket(3141);
			client = socket.accept();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		this.beServer = true;
		doInitializations();
	}
	
	public void startNetworkGameAsClient() {
		try {
			client = new Socket("localhost", 3141);
		} catch (IOException e) {
			e.printStackTrace();
		}
		this.beServer = true;
		doInitializations();
	}


	public void doInitializations() {
		sMenu.setVisible(false);
		if(viewer != null)
			viewer.setVisible(false);
		frame.add(this);
		this.setVisible(true);
		last = System.nanoTime();
		actors = new Vector<Sprite>();
		painter = new Vector<Sprite>();
		URL url = getClass().getClassLoader()
				.getResource("maps/lvl" + currentMap + ".txt");
		File testFile = new File(url.getFile());
		System.out.println(testFile.getAbsolutePath());
		GameMapParser gen;
		if (testFile.exists())
			gen = new GameMapParser("maps/lvl" + currentMap + ".txt",this);
		else
			gen = new GameMapParser("maps/lvl1.txt", this);
		actors = gen.getMap();
		actors.add(player);
		this.gameInterface = new Interface(this);
	}
	
	public void doShopInitalisation() {
		sMenu.setVisible(false);
		if(viewer != null)
			viewer.setVisible(false);		frame.add(this);
		this.setVisible(true);
		last = System.nanoTime();
		actors = new Vector<Sprite>();
		painter = new Vector<Sprite>();
		URL url = getClass().getClassLoader()
				.getResource("maps/lvl" + currentMap + ".txt");
		File testFile = new File(url.getFile());
		System.out.println(testFile.getAbsolutePath());
		GameMapParser gen;
		if (testFile.exists())
			gen = new GameMapParser("maps/shop1.txt",this);
		else
			gen = new GameMapParser("maps/lvl1.txt", this);
		actors = gen.getMap();
		actors.add(player);
	}
	
	public void doInitializationsTestLevel() {
		sMenu.setVisible(false);
		if(viewer != null)
			viewer.setVisible(false);		frame.add(this);
		this.setVisible(true);
		last = System.nanoTime();
		actors = new Vector<Sprite>();
		painter = new Vector<Sprite>();
		URL url = getClass().getClassLoader()
				.getResource("maps/lvl" + currentMap + ".txt");
		File testFile = new File(url.getFile());
		System.out.println(testFile.getAbsolutePath());
		GameMapParser gen;
		if (testFile.exists())
			gen = new GameMapParser("maps/lvlTest.txt", this);
		else
			gen = new GameMapParser("maps/lvl1.txt", this);
		actors = gen.getMap();
		actors.add(player);
		this.gameInterface = new Interface(this);
	}
	
	public void loadMapFromActors(Vector<Sprite> savedActors) {
		last = System.nanoTime();
		Sprite Dummy = null;
		for(Sprite s : savedActors) {
			if(s instanceof PlayerPositionDummy) {
				player.setX(((PlayerPositionDummy)s).getX());
				player.setY(((PlayerPositionDummy)s).getY());
				System.out.println("x: " + player.getX() + "\\y: " + player.getY());
				Dummy = s;
				break;
			}
		}
		savedActors.remove(Dummy);
		actors = savedActors;
		actors.add(player);

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
			if(beServer && socket != null && client != null){
				try {
					ObjectOutputStream out = new ObjectOutputStream(client.getOutputStream());
					out.writeObject(messages);
				} catch (IOException e) {
					e.printStackTrace();
				}
			} else if(beClient && socket != null && client != null) {
				try {
					ObjectInputStream in = new ObjectInputStream(client.getInputStream());
					messages = (Vector<Message>) in.readObject();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (ClassNotFoundException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
			}

			if (isStarted() && !isPaused()) {
				music.stop();
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

	public boolean isPaused() {
		return paused;
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
		Vector<Message> messages_clone = (Vector<Message>) messages.clone();
		for (Iterator<Message> it = messages_clone.iterator(); it.hasNext();) {
			Message m = it.next();
			m.doLogic(delta);
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
		
		for(int i = 0;;i += 30) {
			int x = i % 810;
			int y = i / 810 * 30 + 30;
			if(y >= 630) {
				break;
			}
			g.drawImage(Animations.BACKGROUND, x, y, 30, 30, null);
		}
		
		
		g.setColor(Color.green);
		g.drawString("FPS: " + Long.toString(fps), 20, 10);
		gameInterface.drawObjects(g);
		if (painter != null) {
			for (ListIterator<Sprite> it = painter.listIterator(); it.hasNext();) {
				Sprite r = it.next();
				r.drawObjects(g);
			}
		}
		Vector<Message> messages_clone = (Vector<Message>) messages.clone();
		for(Iterator<Message> it = messages_clone.iterator(); it.hasNext();) {
			Message m = it.next();
			m.drawString(g);
		}
	}

	public boolean isStarted() {
		return started;
	}

	public void setStarted(boolean started) {
		System.out.println("setStarted called");
		this.started = started;
	}

	@SuppressWarnings("unchecked")
	public Vector<Sprite> getActors() {
		return (Vector<Sprite>) actors.clone();
	}

	public void nextMap() {
		paused = false;
		started = false;
		if(viewer != null) {
			viewer.setVisible(false);
			viewer = null;
		}
		actors.clear();
		painter.clear();
		currentMap++;
		doInitializations();
		started = true;
	}
	
	public void goShop() {
		started = false;
		actors.clear();
		player = null;
		painter.clear();
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

	public void throwSpell(boolean b) {
		player.throwSpell();
	}

	public void addActor(Sprite actor) {
		this.actors.add(actor);
	}

	public void removeActor(Sprite actor) {
		this.actors.remove(actor);
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

	public void removeMessage(Message message) {
		System.out.println("Message Size: " + messages.size());
		messages.remove(message);
		System.out.println("Message Size: " + messages.size());
	}
	
	public void addMessage(Message message) {
		messages.add(message);
	}
	
	private JMenuBar getMenuBar() {
		JMenuBar menu = new JMenuBar();
		JMenu file = new JMenu("File");
		JMenuItem newGame = new JMenuItem("New Game");
		JMenuItem save = new JMenuItem("Save");
		JMenuItem load = new JMenuItem("Load");
		ActionListener listener = new MenuActionListener(this);
		newGame.addActionListener(listener);
		save.addActionListener(listener);
		load.addActionListener(listener);
		file.add(newGame);
		file.add(save);
		file.add(load);
		menu.add(file);
		return menu;
	}

	public void save() {
		FileOutputStream fos = null;
		ObjectOutputStream out = null;
		try {
			File file = new File("save.ser");
			fos = new FileOutputStream(file);
			out = new ObjectOutputStream(fos);
//			SaveObject saveState = new SaveObject(actors, player);
//			out.writeObject(saveState);
			out.flush();
			System.out.println(file.getAbsolutePath());
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
				try {
					if (fos != null) fos.close();
					if (out != null) out.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
		}
	}

	public void load() {

	}

	public void addSwitch(PuzzleSwitch pSwitch) {
		if(puzzle == null) {
			puzzle = new SwitchPuzzle(this);
		}
		puzzle.addSwitch(pSwitch);
	}

	public void addBreakableWall(Mauer wall) {
		if(puzzle == null) {
			puzzle = new SwitchPuzzle(this);
		}
		puzzle.addWall(wall);	}

	public void setSwitch(PuzzleSwitch s) {
		puzzle.setSwitch(s);
	}

	public void clearPuzzle() {
		puzzle = null;
	}

	public SwitchPuzzle getPuzzle() {
		return puzzle;
	}

	public void setPuzzle(SwitchPuzzle savedPuzzle) {
		puzzle = savedPuzzle;
	}

	public void setStoryPic(String temp) {
		storyPic = temp;
	}

	public String getStoryPic() {
		return storyPic;
	}

	public void startStoryViewer(BufferedImage storyPic) {
		this.storyPic = "";
		paused  = true;
		viewer = new StoryPicViewer(this.getWidth(), this.getHeight(), storyPic);
		frame.add(viewer);
		this.setVisible(false);
		viewer.setVisible(true);
	}
	
	public void startStoryViewer(BufferedImage[] storyPic) {
		this.storyPic = "";
		paused  = true;
		viewer = new StoryPicViewer(this.getWidth(), this.getHeight(), storyPic);
		frame.add(viewer);
		this.setVisible(false);
		viewer.setVisible(true);
	}

	public boolean nextView() {
		return viewer.next();
	}

	public void setPaused(boolean b) {
		paused = false;
	}

	public boolean isViewerStarted() {
		return viewerStarted;
	}
}
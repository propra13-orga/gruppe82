package de.dc;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;
import java.util.ListIterator;
import java.util.Vector;


import javax.imageio.ImageIO;
import javax.swing.JFrame;
import javax.swing.JPanel;

import de.dc.menu.StartMenu;
import de.dc.sprites.Sprite;

public class Spielfeld extends JPanel implements Runnable,KeyListener {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	JFrame frame;
	
	
	
	long delta= 0; //zeit die fuer den letzten durchlauf benoetigt wurde
	long last= 0; //speicherung systemzeit
	long fps= 0; //fps anzeigen
	int currentMap = 1;
	
	Spieler player;
	Vector<Sprite> actors;
	Vector<Sprite> painter;
	Vector<Sprite> mauer;
	
	boolean started;
	boolean up;
	boolean down;
	boolean left;
	boolean right;
	double speed= 1000;
	
	StartMenu sMenu;
	private Ziel zielObject;
	
	public static void main(String[] args){
		new Spielfeld(810	,600);
	}
	
	public Spielfeld (int w, int h) {
		this.setPreferredSize(new Dimension(w,h));
//		this.setBackground(Color.BLUE);
		frame= new JFrame("GameDemo");
		frame.setLocation(50,0);  //Spielfeld setzen
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		sMenu = new StartMenu(w, h);
		frame.add(sMenu);
		frame.addKeyListener(this);
		sMenu.setVisible(true);
		this.setVisible(false);
		frame.pack();
		frame.setVisible(true);
		
		
		Thread th = new Thread(this);
		th.start();
	}
	private void doInitializations(){
		sMenu.setVisible(false);
		frame.add(this);
		this.setVisible(true);
		last = System.nanoTime();
		BufferedImage[] kreuz= loadPics("pics/spieler_gruen3.png",2);
		actors= new Vector<Sprite>();
		painter=new Vector<Sprite>();
		player= new Spieler("Spieler1",kreuz,0,30,100,this);
		actors.add(player);
		initwalls(currentMap);
		for (Sprite s: mauer){
			actors.add(s);
			
		}
		BufferedImage[] ziel=loadPics("pics/Goal.png",1);
		zielObject = new Ziel("Ziel",ziel, 810-60, 600-60, 100, this);
		actors.add(zielObject);

		

		
		
	}
private void initwalls(int mapnumber){
	mauer=new Vector<Sprite>();
	BufferedImage[] mauerbild=loadPics("pics/wall.png",1);
	if(mapnumber==2){
		mauer.add(new Mauer("Mauer",mauerbild,0,0,100,this));
		mauer.add(new Mauer("Mauer",mauerbild,30,0,100,this));
		mauer.add(new Mauer("Mauer",mauerbild,60,0,100,this));
		mauer.add(new Mauer("Mauer",mauerbild,90,0,100,this));
		mauer.add(new Mauer("Mauer",mauerbild,120,0,100,this));
		mauer.add(new Mauer("Mauer",mauerbild,150,0,100,this));
		mauer.add(new Mauer("Mauer",mauerbild,180,0,100,this));
		mauer.add(new Mauer("Mauer",mauerbild,210,0,100,this));
		mauer.add(new Mauer("Mauer",mauerbild,240,0,100,this));
		mauer.add(new Mauer("Mauer",mauerbild,270,0,100,this));
		mauer.add(new Mauer("Mauer",mauerbild,300,0,100,this));
		mauer.add(new Mauer("Mauer",mauerbild,330,0,100,this));
		mauer.add(new Mauer("Mauer",mauerbild,360,0,100,this));
		mauer.add(new Mauer("Mauer",mauerbild,390,0,100,this));
		mauer.add(new Mauer("Mauer",mauerbild,420,0,100,this));
		mauer.add(new Mauer("Mauer",mauerbild,450,0,100,this));
		mauer.add(new Mauer("Mauer",mauerbild,480,0,100,this));
		mauer.add(new Mauer("Mauer",mauerbild,510,0,100,this));
		mauer.add(new Mauer("Mauer",mauerbild,540,0,100,this));
		mauer.add(new Mauer("Mauer",mauerbild,570,0,100,this));
		mauer.add(new Mauer("Mauer",mauerbild,600,0,100,this));
		mauer.add(new Mauer("Mauer",mauerbild,630,0,100,this));
		mauer.add(new Mauer("Mauer",mauerbild,660,0,100,this));
		mauer.add(new Mauer("Mauer",mauerbild,690,0,100,this));
		mauer.add(new Mauer("Mauer",mauerbild,720,0,100,this));
		mauer.add(new Mauer("Mauer",mauerbild,750,0,100,this));
		mauer.add(new Mauer("Mauer",mauerbild,780,0,100,this));
		mauer.add(new Mauer("Mauer",mauerbild,780,30,100,this));
		mauer.add(new Mauer("Mauer",mauerbild,780,60,100,this));
		mauer.add(new Mauer("Mauer",mauerbild,780,90,100,this));
		mauer.add(new Mauer("Mauer",mauerbild,780,120,100,this));
		mauer.add(new Mauer("Mauer",mauerbild,780,150,100,this));
		mauer.add(new Mauer("Mauer",mauerbild,780,180,100,this));
		mauer.add(new Mauer("Mauer",mauerbild,780,210,100,this));
		mauer.add(new Mauer("Mauer",mauerbild,780,240,100,this));
		mauer.add(new Mauer("Mauer",mauerbild,780,270,100,this));
		mauer.add(new Mauer("Mauer",mauerbild,780,300,100,this));
		mauer.add(new Mauer("Mauer",mauerbild,780,330,100,this));
		mauer.add(new Mauer("Mauer",mauerbild,780,360,100,this));
		mauer.add(new Mauer("Mauer",mauerbild,780,390,100,this));
		mauer.add(new Mauer("Mauer",mauerbild,780,420,100,this));
		mauer.add(new Mauer("Mauer",mauerbild,780,450,100,this));
		mauer.add(new Mauer("Mauer",mauerbild,780,480,100,this));
		mauer.add(new Mauer("Mauer",mauerbild,780,510,100,this));
		mauer.add(new Mauer("Mauer",mauerbild,780,540,100,this));
		mauer.add(new Mauer("Mauer",mauerbild,780,570,100,this));
		mauer.add(new Mauer("Mauer",mauerbild,0,570,100,this));
		mauer.add(new Mauer("Mauer",mauerbild,30,570,100,this));
		mauer.add(new Mauer("Mauer",mauerbild,60,570,100,this));
		mauer.add(new Mauer("Mauer",mauerbild,90,570,100,this));
		mauer.add(new Mauer("Mauer",mauerbild,120,570,100,this));
		mauer.add(new Mauer("Mauer",mauerbild,150,570,100,this));
		mauer.add(new Mauer("Mauer",mauerbild,180,570,100,this));
		mauer.add(new Mauer("Mauer",mauerbild,210,570,100,this));
		mauer.add(new Mauer("Mauer",mauerbild,240,570,100,this));
		mauer.add(new Mauer("Mauer",mauerbild,270,570,100,this));
		mauer.add(new Mauer("Mauer",mauerbild,300,570,100,this));
		mauer.add(new Mauer("Mauer",mauerbild,330,570,100,this));
		mauer.add(new Mauer("Mauer",mauerbild,360,570,100,this));
		mauer.add(new Mauer("Mauer",mauerbild,390,570,100,this));
		mauer.add(new Mauer("Mauer",mauerbild,420,570,100,this));
		mauer.add(new Mauer("Mauer",mauerbild,450,570,100,this));
		mauer.add(new Mauer("Mauer",mauerbild,480,570,100,this));
		mauer.add(new Mauer("Mauer",mauerbild,510,570,100,this));
		mauer.add(new Mauer("Mauer",mauerbild,540,570,100,this));
		mauer.add(new Mauer("Mauer",mauerbild,570,570,100,this));
		mauer.add(new Mauer("Mauer",mauerbild,600,570,100,this));
		mauer.add(new Mauer("Mauer",mauerbild,630,570,100,this));
		mauer.add(new Mauer("Mauer",mauerbild,660,570,100,this));
		mauer.add(new Mauer("Mauer",mauerbild,690,570,100,this));
		mauer.add(new Mauer("Mauer",mauerbild,720,570,100,this));
		mauer.add(new Mauer("Mauer",mauerbild,750,570,100,this));
		mauer.add(new Mauer("Mauer",mauerbild,780,570,100,this));
		//Umrandung Oben-rechts-Unten
		mauer.add(new Mauer("Mauer",mauerbild,0,90,100,this));
		mauer.add(new Mauer("Mauer",mauerbild,0,120,100,this));
		mauer.add(new Mauer("Mauer",mauerbild,0,150,100,this));
		mauer.add(new Mauer("Mauer",mauerbild,0,180,100,this));
		mauer.add(new Mauer("Mauer",mauerbild,0,210,100,this));
		mauer.add(new Mauer("Mauer",mauerbild,0,240,100,this));
		mauer.add(new Mauer("Mauer",mauerbild,0,270,100,this));
		mauer.add(new Mauer("Mauer",mauerbild,0,300,100,this));
		mauer.add(new Mauer("Mauer",mauerbild,0,330,100,this));
		mauer.add(new Mauer("Mauer",mauerbild,0,360,100,this));
		mauer.add(new Mauer("Mauer",mauerbild,0,390,100,this));
		mauer.add(new Mauer("Mauer",mauerbild,0,420,100,this));
		mauer.add(new Mauer("Mauer",mauerbild,0,450,100,this));
		mauer.add(new Mauer("Mauer",mauerbild,0,480,100,this));
		mauer.add(new Mauer("Mauer",mauerbild,0,510,100,this));
		mauer.add(new Mauer("Mauer",mauerbild,0,540,100,this));
		mauer.add(new Mauer("Mauer",mauerbild,0,570,100,this));
		//Umrandung Komplett
		mauer.add(new Mauer("Mauer",mauerbild,30,90,100,this));
		mauer.add(new Mauer("Mauer",mauerbild,60,90,100,this));
		mauer.add(new Mauer("Mauer",mauerbild,90,90,100,this));
		mauer.add(new Mauer("Mauer",mauerbild,120,90,100,this));
		mauer.add(new Mauer("Mauer",mauerbild,150,90,100,this));
		mauer.add(new Mauer("Mauer",mauerbild,180,90,100,this));
		mauer.add(new Mauer("Mauer",mauerbild,210,90,100,this));
		mauer.add(new Mauer("Mauer",mauerbild,240,90,100,this));
		mauer.add(new Mauer("Mauer",mauerbild,270,90,100,this));
		mauer.add(new Mauer("Mauer",mauerbild,300,90,100,this));
		mauer.add(new Mauer("Mauer",mauerbild,420,90,100,this));
		mauer.add(new Mauer("Mauer",mauerbild,450,90,100,this));
		mauer.add(new Mauer("Mauer",mauerbild,480,90,100,this));
		mauer.add(new Mauer("Mauer",mauerbild,510,90,100,this));
		mauer.add(new Mauer("Mauer",mauerbild,540,90,100,this));
		mauer.add(new Mauer("Mauer",mauerbild,570,90,100,this));
		mauer.add(new Mauer("Mauer",mauerbild,600,90,100,this));
		mauer.add(new Mauer("Mauer",mauerbild,630,90,100,this));
		mauer.add(new Mauer("Mauer",mauerbild,660,90,100,this));
		mauer.add(new Mauer("Mauer",mauerbild,690,90,100,this));
		mauer.add(new Mauer("Mauer",mauerbild,720,90,100,this));
		mauer.add(new Mauer("Mauer",mauerbild,750,90,100,this));
		//
		mauer.add(new Mauer("Mauer",mauerbild,30,180,100,this));
		mauer.add(new Mauer("Mauer",mauerbild,60,180,100,this));
		mauer.add(new Mauer("Mauer",mauerbild,90,180,100,this));
		mauer.add(new Mauer("Mauer",mauerbild,120,180,100,this));
		mauer.add(new Mauer("Mauer",mauerbild,150,180,100,this));
		mauer.add(new Mauer("Mauer",mauerbild,180,180,100,this));
		mauer.add(new Mauer("Mauer",mauerbild,210,180,100,this));
		mauer.add(new Mauer("Mauer",mauerbild,240,180,100,this));
		mauer.add(new Mauer("Mauer",mauerbild,270,180,100,this));
		mauer.add(new Mauer("Mauer",mauerbild,300,180,100,this));
		mauer.add(new Mauer("Mauer",mauerbild,330,180,100,this));
		mauer.add(new Mauer("Mauer",mauerbild,360,180,100,this));
		mauer.add(new Mauer("Mauer",mauerbild,390,180,100,this));
		mauer.add(new Mauer("Mauer",mauerbild,420,180,100,this));
		mauer.add(new Mauer("Mauer",mauerbild,450,180,100,this));
		mauer.add(new Mauer("Mauer",mauerbild,480,180,100,this));
		mauer.add(new Mauer("Mauer",mauerbild,510,180,100,this));
		mauer.add(new Mauer("Mauer",mauerbild,540,180,100,this));
		mauer.add(new Mauer("Mauer",mauerbild,570,180,100,this));
		mauer.add(new Mauer("Mauer",mauerbild,600,180,100,this));
		mauer.add(new Mauer("Mauer",mauerbild,720,180,100,this));
		mauer.add(new Mauer("Mauer",mauerbild,750,180,100,this));
		//
		mauer.add(new Mauer("Mauer",mauerbild,30,270,100,this));
		mauer.add(new Mauer("Mauer",mauerbild,60,270,100,this));
		mauer.add(new Mauer("Mauer",mauerbild,90,270,100,this));
		mauer.add(new Mauer("Mauer",mauerbild,120,270,100,this));
		mauer.add(new Mauer("Mauer",mauerbild,150,270,100,this));
		mauer.add(new Mauer("Mauer",mauerbild,180,270,100,this));
		mauer.add(new Mauer("Mauer",mauerbild,210,270,100,this));
		mauer.add(new Mauer("Mauer",mauerbild,240,270,100,this));
		mauer.add(new Mauer("Mauer",mauerbild,270,270,100,this));
		mauer.add(new Mauer("Mauer",mauerbild,300,270,100,this));
		mauer.add(new Mauer("Mauer",mauerbild,330,270,100,this));
		mauer.add(new Mauer("Mauer",mauerbild,360,270,100,this));
		mauer.add(new Mauer("Mauer",mauerbild,390,270,100,this));
		mauer.add(new Mauer("Mauer",mauerbild,510,270,100,this));
		mauer.add(new Mauer("Mauer",mauerbild,540,270,100,this));
		mauer.add(new Mauer("Mauer",mauerbild,570,270,100,this));
		mauer.add(new Mauer("Mauer",mauerbild,600,270,100,this));
		mauer.add(new Mauer("Mauer",mauerbild,630,270,100,this));
		mauer.add(new Mauer("Mauer",mauerbild,660,270,100,this));
		mauer.add(new Mauer("Mauer",mauerbild,690,270,100,this));
		mauer.add(new Mauer("Mauer",mauerbild,720,270,100,this));
		mauer.add(new Mauer("Mauer",mauerbild,750,270,100,this));
		//
		mauer.add(new Mauer("Mauer",mauerbild,360,390,100,this));
		mauer.add(new Mauer("Mauer",mauerbild,390,390,100,this));
		mauer.add(new Mauer("Mauer",mauerbild,420,390,100,this));
		mauer.add(new Mauer("Mauer",mauerbild,450,390,100,this));
		mauer.add(new Mauer("Mauer",mauerbild,480,390,100,this));
		mauer.add(new Mauer("Mauer",mauerbild,510,390,100,this));
		mauer.add(new Mauer("Mauer",mauerbild,540,390,100,this));
		mauer.add(new Mauer("Mauer",mauerbild,570,390,100,this));
		mauer.add(new Mauer("Mauer",mauerbild,600,390,100,this));
		mauer.add(new Mauer("Mauer",mauerbild,630,390,100,this));
		mauer.add(new Mauer("Mauer",mauerbild,660,390,100,this));
		mauer.add(new Mauer("Mauer",mauerbild,690,390,100,this));
		mauer.add(new Mauer("Mauer",mauerbild,720,390,100,this));
		mauer.add(new Mauer("Mauer",mauerbild,750,390,100,this));
		mauer.add(new Mauer("Mauer",mauerbild,540,510,100,this));
		mauer.add(new Mauer("Mauer",mauerbild,540,540,100,this));
	
		
	}
	else if(mapnumber==3){
		mauer.add(new Mauer("Mauer",mauerbild,0,0,100,this));
		mauer.add(new Mauer("Mauer",mauerbild,30,0,100,this));
		mauer.add(new Mauer("Mauer",mauerbild,60,0,100,this));
		mauer.add(new Mauer("Mauer",mauerbild,90,0,100,this));
		mauer.add(new Mauer("Mauer",mauerbild,120,0,100,this));
		mauer.add(new Mauer("Mauer",mauerbild,150,0,100,this));
		mauer.add(new Mauer("Mauer",mauerbild,180,0,100,this));
		mauer.add(new Mauer("Mauer",mauerbild,210,0,100,this));
		mauer.add(new Mauer("Mauer",mauerbild,240,0,100,this));
		mauer.add(new Mauer("Mauer",mauerbild,270,0,100,this));
		mauer.add(new Mauer("Mauer",mauerbild,300,0,100,this));
		mauer.add(new Mauer("Mauer",mauerbild,330,0,100,this));
		mauer.add(new Mauer("Mauer",mauerbild,360,0,100,this));
		mauer.add(new Mauer("Mauer",mauerbild,390,0,100,this));
		mauer.add(new Mauer("Mauer",mauerbild,420,0,100,this));
		mauer.add(new Mauer("Mauer",mauerbild,450,0,100,this));
		mauer.add(new Mauer("Mauer",mauerbild,480,0,100,this));
		mauer.add(new Mauer("Mauer",mauerbild,510,0,100,this));
		mauer.add(new Mauer("Mauer",mauerbild,540,0,100,this));
		mauer.add(new Mauer("Mauer",mauerbild,570,0,100,this));
		mauer.add(new Mauer("Mauer",mauerbild,600,0,100,this));
		mauer.add(new Mauer("Mauer",mauerbild,630,0,100,this));
		mauer.add(new Mauer("Mauer",mauerbild,660,0,100,this));
		mauer.add(new Mauer("Mauer",mauerbild,690,0,100,this));
		mauer.add(new Mauer("Mauer",mauerbild,720,0,100,this));
		mauer.add(new Mauer("Mauer",mauerbild,750,0,100,this));
		mauer.add(new Mauer("Mauer",mauerbild,780,0,100,this));
		mauer.add(new Mauer("Mauer",mauerbild,780,30,100,this));
		mauer.add(new Mauer("Mauer",mauerbild,780,60,100,this));
		mauer.add(new Mauer("Mauer",mauerbild,780,90,100,this));
		mauer.add(new Mauer("Mauer",mauerbild,780,120,100,this));
		mauer.add(new Mauer("Mauer",mauerbild,780,150,100,this));
		mauer.add(new Mauer("Mauer",mauerbild,780,180,100,this));
		mauer.add(new Mauer("Mauer",mauerbild,780,210,100,this));
		mauer.add(new Mauer("Mauer",mauerbild,780,240,100,this));
		mauer.add(new Mauer("Mauer",mauerbild,780,270,100,this));
		mauer.add(new Mauer("Mauer",mauerbild,780,300,100,this));
		mauer.add(new Mauer("Mauer",mauerbild,780,330,100,this));
		mauer.add(new Mauer("Mauer",mauerbild,780,360,100,this));
		mauer.add(new Mauer("Mauer",mauerbild,780,390,100,this));
		mauer.add(new Mauer("Mauer",mauerbild,780,420,100,this));
		mauer.add(new Mauer("Mauer",mauerbild,780,450,100,this));
		mauer.add(new Mauer("Mauer",mauerbild,780,480,100,this));
		mauer.add(new Mauer("Mauer",mauerbild,780,510,100,this));
		mauer.add(new Mauer("Mauer",mauerbild,780,540,100,this));
		mauer.add(new Mauer("Mauer",mauerbild,780,570,100,this));
		mauer.add(new Mauer("Mauer",mauerbild,0,570,100,this));
		mauer.add(new Mauer("Mauer",mauerbild,30,570,100,this));
		mauer.add(new Mauer("Mauer",mauerbild,60,570,100,this));
		mauer.add(new Mauer("Mauer",mauerbild,90,570,100,this));
		mauer.add(new Mauer("Mauer",mauerbild,120,570,100,this));
		mauer.add(new Mauer("Mauer",mauerbild,150,570,100,this));
		mauer.add(new Mauer("Mauer",mauerbild,180,570,100,this));
		mauer.add(new Mauer("Mauer",mauerbild,210,570,100,this));
		mauer.add(new Mauer("Mauer",mauerbild,240,570,100,this));
		mauer.add(new Mauer("Mauer",mauerbild,270,570,100,this));
		mauer.add(new Mauer("Mauer",mauerbild,300,570,100,this));
		mauer.add(new Mauer("Mauer",mauerbild,330,570,100,this));
		mauer.add(new Mauer("Mauer",mauerbild,360,570,100,this));
		mauer.add(new Mauer("Mauer",mauerbild,390,570,100,this));
		mauer.add(new Mauer("Mauer",mauerbild,420,570,100,this));
		mauer.add(new Mauer("Mauer",mauerbild,450,570,100,this));
		mauer.add(new Mauer("Mauer",mauerbild,480,570,100,this));
		mauer.add(new Mauer("Mauer",mauerbild,510,570,100,this));
		mauer.add(new Mauer("Mauer",mauerbild,540,570,100,this));
		mauer.add(new Mauer("Mauer",mauerbild,570,570,100,this));
		mauer.add(new Mauer("Mauer",mauerbild,600,570,100,this));
		mauer.add(new Mauer("Mauer",mauerbild,630,570,100,this));
		mauer.add(new Mauer("Mauer",mauerbild,660,570,100,this));
		mauer.add(new Mauer("Mauer",mauerbild,690,570,100,this));
		mauer.add(new Mauer("Mauer",mauerbild,720,570,100,this));
		mauer.add(new Mauer("Mauer",mauerbild,750,570,100,this));
		mauer.add(new Mauer("Mauer",mauerbild,780,570,100,this));
		//Umrandung Oben-rechts-Unten
		mauer.add(new Mauer("Mauer",mauerbild,0,90,100,this));
		mauer.add(new Mauer("Mauer",mauerbild,0,120,100,this));
		mauer.add(new Mauer("Mauer",mauerbild,0,150,100,this));
		mauer.add(new Mauer("Mauer",mauerbild,0,180,100,this));
		mauer.add(new Mauer("Mauer",mauerbild,0,210,100,this));
		mauer.add(new Mauer("Mauer",mauerbild,0,240,100,this));
		mauer.add(new Mauer("Mauer",mauerbild,0,270,100,this));
		mauer.add(new Mauer("Mauer",mauerbild,0,300,100,this));
		mauer.add(new Mauer("Mauer",mauerbild,0,330,100,this));
		mauer.add(new Mauer("Mauer",mauerbild,0,360,100,this));
		mauer.add(new Mauer("Mauer",mauerbild,0,390,100,this));
		mauer.add(new Mauer("Mauer",mauerbild,0,420,100,this));
		mauer.add(new Mauer("Mauer",mauerbild,0,450,100,this));
		mauer.add(new Mauer("Mauer",mauerbild,0,480,100,this));
		mauer.add(new Mauer("Mauer",mauerbild,0,510,100,this));
		mauer.add(new Mauer("Mauer",mauerbild,0,540,100,this));
		mauer.add(new Mauer("Mauer",mauerbild,0,570,100,this));
		
		
		
		
		
		
		
		
	}
	else if(mapnumber==1){
		currentMap=1;
		mauer.add(new Mauer("Mauer",mauerbild,0,0,100,this));
		mauer.add(new Mauer("Mauer",mauerbild,30,0,100,this));
		mauer.add(new Mauer("Mauer",mauerbild,60,0,100,this));
		mauer.add(new Mauer("Mauer",mauerbild,90,0,100,this));
		mauer.add(new Mauer("Mauer",mauerbild,120,0,100,this));
		mauer.add(new Mauer("Mauer",mauerbild,150,0,100,this));
		mauer.add(new Mauer("Mauer",mauerbild,180,0,100,this));
		mauer.add(new Mauer("Mauer",mauerbild,210,0,100,this));
		mauer.add(new Mauer("Mauer",mauerbild,240,0,100,this));
		mauer.add(new Mauer("Mauer",mauerbild,270,0,100,this));
		mauer.add(new Mauer("Mauer",mauerbild,300,0,100,this));
		mauer.add(new Mauer("Mauer",mauerbild,330,0,100,this));
		mauer.add(new Mauer("Mauer",mauerbild,360,0,100,this));
		mauer.add(new Mauer("Mauer",mauerbild,390,0,100,this));
		mauer.add(new Mauer("Mauer",mauerbild,420,0,100,this));
		mauer.add(new Mauer("Mauer",mauerbild,450,0,100,this));
		mauer.add(new Mauer("Mauer",mauerbild,480,0,100,this));
		mauer.add(new Mauer("Mauer",mauerbild,510,0,100,this));
		mauer.add(new Mauer("Mauer",mauerbild,540,0,100,this));
		mauer.add(new Mauer("Mauer",mauerbild,570,0,100,this));
		mauer.add(new Mauer("Mauer",mauerbild,600,0,100,this));
		mauer.add(new Mauer("Mauer",mauerbild,630,0,100,this));
		mauer.add(new Mauer("Mauer",mauerbild,660,0,100,this));
		mauer.add(new Mauer("Mauer",mauerbild,690,0,100,this));
		mauer.add(new Mauer("Mauer",mauerbild,720,0,100,this));
		mauer.add(new Mauer("Mauer",mauerbild,750,0,100,this));
		mauer.add(new Mauer("Mauer",mauerbild,780,0,100,this));
		mauer.add(new Mauer("Mauer",mauerbild,780,30,100,this));
		mauer.add(new Mauer("Mauer",mauerbild,780,60,100,this));
		mauer.add(new Mauer("Mauer",mauerbild,780,90,100,this));
		mauer.add(new Mauer("Mauer",mauerbild,780,120,100,this));
		mauer.add(new Mauer("Mauer",mauerbild,780,150,100,this));
		mauer.add(new Mauer("Mauer",mauerbild,780,180,100,this));
		mauer.add(new Mauer("Mauer",mauerbild,780,210,100,this));
		mauer.add(new Mauer("Mauer",mauerbild,780,240,100,this));
		mauer.add(new Mauer("Mauer",mauerbild,780,270,100,this));
		mauer.add(new Mauer("Mauer",mauerbild,780,300,100,this));
		mauer.add(new Mauer("Mauer",mauerbild,780,330,100,this));
		mauer.add(new Mauer("Mauer",mauerbild,780,360,100,this));
		mauer.add(new Mauer("Mauer",mauerbild,780,390,100,this));
		mauer.add(new Mauer("Mauer",mauerbild,780,420,100,this));
		mauer.add(new Mauer("Mauer",mauerbild,780,450,100,this));
		mauer.add(new Mauer("Mauer",mauerbild,780,480,100,this));
		mauer.add(new Mauer("Mauer",mauerbild,780,510,100,this));
		mauer.add(new Mauer("Mauer",mauerbild,780,540,100,this));
		mauer.add(new Mauer("Mauer",mauerbild,780,570,100,this));
		mauer.add(new Mauer("Mauer",mauerbild,0,570,100,this));
		mauer.add(new Mauer("Mauer",mauerbild,30,570,100,this));
		mauer.add(new Mauer("Mauer",mauerbild,60,570,100,this));
		mauer.add(new Mauer("Mauer",mauerbild,90,570,100,this));
		mauer.add(new Mauer("Mauer",mauerbild,120,570,100,this));
		mauer.add(new Mauer("Mauer",mauerbild,150,570,100,this));
		mauer.add(new Mauer("Mauer",mauerbild,180,570,100,this));
		mauer.add(new Mauer("Mauer",mauerbild,210,570,100,this));
		mauer.add(new Mauer("Mauer",mauerbild,240,570,100,this));
		mauer.add(new Mauer("Mauer",mauerbild,270,570,100,this));
		mauer.add(new Mauer("Mauer",mauerbild,300,570,100,this));
		mauer.add(new Mauer("Mauer",mauerbild,330,570,100,this));
		mauer.add(new Mauer("Mauer",mauerbild,360,570,100,this));
		mauer.add(new Mauer("Mauer",mauerbild,390,570,100,this));
		mauer.add(new Mauer("Mauer",mauerbild,420,570,100,this));
		mauer.add(new Mauer("Mauer",mauerbild,450,570,100,this));
		mauer.add(new Mauer("Mauer",mauerbild,480,570,100,this));
		mauer.add(new Mauer("Mauer",mauerbild,510,570,100,this));
		mauer.add(new Mauer("Mauer",mauerbild,540,570,100,this));
		mauer.add(new Mauer("Mauer",mauerbild,570,570,100,this));
		mauer.add(new Mauer("Mauer",mauerbild,600,570,100,this));
		mauer.add(new Mauer("Mauer",mauerbild,630,570,100,this));
		mauer.add(new Mauer("Mauer",mauerbild,660,570,100,this));
		mauer.add(new Mauer("Mauer",mauerbild,690,570,100,this));
		mauer.add(new Mauer("Mauer",mauerbild,720,570,100,this));
		mauer.add(new Mauer("Mauer",mauerbild,750,570,100,this));
		mauer.add(new Mauer("Mauer",mauerbild,780,570,100,this));
		//Umrandung Oben-rechts-Unten
		mauer.add(new Mauer("Mauer",mauerbild,0,90,100,this));
		mauer.add(new Mauer("Mauer",mauerbild,0,120,100,this));
		mauer.add(new Mauer("Mauer",mauerbild,0,150,100,this));
		mauer.add(new Mauer("Mauer",mauerbild,0,180,100,this));
		mauer.add(new Mauer("Mauer",mauerbild,0,210,100,this));
		mauer.add(new Mauer("Mauer",mauerbild,0,240,100,this));
		mauer.add(new Mauer("Mauer",mauerbild,0,270,100,this));
		mauer.add(new Mauer("Mauer",mauerbild,0,300,100,this));
		mauer.add(new Mauer("Mauer",mauerbild,0,330,100,this));
		mauer.add(new Mauer("Mauer",mauerbild,0,360,100,this));
		mauer.add(new Mauer("Mauer",mauerbild,0,390,100,this));
		mauer.add(new Mauer("Mauer",mauerbild,0,420,100,this));
		mauer.add(new Mauer("Mauer",mauerbild,0,450,100,this));
		mauer.add(new Mauer("Mauer",mauerbild,0,480,100,this));
		mauer.add(new Mauer("Mauer",mauerbild,0,510,100,this));
		mauer.add(new Mauer("Mauer",mauerbild,0,540,100,this));
		mauer.add(new Mauer("Mauer",mauerbild,0,570,100,this));
		//Umrandung Komplett
		mauer.add(new Mauer("Mauer",mauerbild,30,90,100,this));
		mauer.add(new Mauer("Mauer",mauerbild,60,90,100,this));
		mauer.add(new Mauer("Mauer",mauerbild,90,90,100,this));
		mauer.add(new Mauer("Mauer",mauerbild,120,90,100,this));
		mauer.add(new Mauer("Mauer",mauerbild,150,90,100,this));
		mauer.add(new Mauer("Mauer",mauerbild,180,90,100,this));
		mauer.add(new Mauer("Mauer",mauerbild,210,90,100,this));
		mauer.add(new Mauer("Mauer",mauerbild,240,90,100,this));
		mauer.add(new Mauer("Mauer",mauerbild,270,90,100,this));
		mauer.add(new Mauer("Mauer",mauerbild,300,90,100,this));
		mauer.add(new Mauer("Mauer",mauerbild,330,90,100,this));
		mauer.add(new Mauer("Mauer",mauerbild,360,90,100,this));
		mauer.add(new Mauer("Mauer",mauerbild,390,90,100,this));
		mauer.add(new Mauer("Mauer",mauerbild,420,90,100,this));
		mauer.add(new Mauer("Mauer",mauerbild,450,90,100,this));
		mauer.add(new Mauer("Mauer",mauerbild,480,90,100,this));
		mauer.add(new Mauer("Mauer",mauerbild,510,90,100,this));
		mauer.add(new Mauer("Mauer",mauerbild,540,90,100,this));
		mauer.add(new Mauer("Mauer",mauerbild,570,90,100,this));
		mauer.add(new Mauer("Mauer",mauerbild,600,90,100,this));
		mauer.add(new Mauer("Mauer",mauerbild,630,90,100,this));
		mauer.add(new Mauer("Mauer",mauerbild,120,180,100,this));
		mauer.add(new Mauer("Mauer",mauerbild,150,180,100,this));
		mauer.add(new Mauer("Mauer",mauerbild,180,180,100,this));
		mauer.add(new Mauer("Mauer",mauerbild,210,180,100,this));
		mauer.add(new Mauer("Mauer",mauerbild,240,180,100,this));
		mauer.add(new Mauer("Mauer",mauerbild,270,180,100,this));
		mauer.add(new Mauer("Mauer",mauerbild,300,180,100,this));
		mauer.add(new Mauer("Mauer",mauerbild,330,180,100,this));
		mauer.add(new Mauer("Mauer",mauerbild,360,180,100,this));
		mauer.add(new Mauer("Mauer",mauerbild,390,180,100,this));
		mauer.add(new Mauer("Mauer",mauerbild,420,180,100,this));
		mauer.add(new Mauer("Mauer",mauerbild,450,180,100,this));
		mauer.add(new Mauer("Mauer",mauerbild,480,180,100,this));
		mauer.add(new Mauer("Mauer",mauerbild,510,180,100,this));
		mauer.add(new Mauer("Mauer",mauerbild,540,180,100,this));
		mauer.add(new Mauer("Mauer",mauerbild,570,180,100,this));
		mauer.add(new Mauer("Mauer",mauerbild,600,180,100,this));
		mauer.add(new Mauer("Mauer",mauerbild,630,180,100,this));
		mauer.add(new Mauer("Mauer",mauerbild,660,180,100,this));
		mauer.add(new Mauer("Mauer",mauerbild,690,180,100,this));
		mauer.add(new Mauer("Mauer",mauerbild,720,180,100,this));
		mauer.add(new Mauer("Mauer",mauerbild,750,180,100,this));
		//
		mauer.add(new Mauer("Mauer",mauerbild,30,270,100,this));
		mauer.add(new Mauer("Mauer",mauerbild,60,270,100,this));
		mauer.add(new Mauer("Mauer",mauerbild,90,270,100,this));
		mauer.add(new Mauer("Mauer",mauerbild,120,270,100,this));
		mauer.add(new Mauer("Mauer",mauerbild,150,270,100,this));
		mauer.add(new Mauer("Mauer",mauerbild,180,270,100,this));
		mauer.add(new Mauer("Mauer",mauerbild,210,270,100,this));
		mauer.add(new Mauer("Mauer",mauerbild,240,270,100,this));
		mauer.add(new Mauer("Mauer",mauerbild,270,270,100,this));
		mauer.add(new Mauer("Mauer",mauerbild,300,270,100,this));
		mauer.add(new Mauer("Mauer",mauerbild,330,270,100,this));
		mauer.add(new Mauer("Mauer",mauerbild,360,270,100,this));
		mauer.add(new Mauer("Mauer",mauerbild,390,270,100,this));
		mauer.add(new Mauer("Mauer",mauerbild,420,270,100,this));
		mauer.add(new Mauer("Mauer",mauerbild,450,270,100,this));
		mauer.add(new Mauer("Mauer",mauerbild,480,270,100,this));
		mauer.add(new Mauer("Mauer",mauerbild,510,270,100,this));
		mauer.add(new Mauer("Mauer",mauerbild,540,270,100,this));
		mauer.add(new Mauer("Mauer",mauerbild,570,270,100,this));
		mauer.add(new Mauer("Mauer",mauerbild,600,270,100,this));
		mauer.add(new Mauer("Mauer",mauerbild,630,270,100,this));
		//
		mauer.add(new Mauer("Mauer",mauerbild,120,360,100,this));
		mauer.add(new Mauer("Mauer",mauerbild,150,360,100,this));
		mauer.add(new Mauer("Mauer",mauerbild,180,360,100,this));
		mauer.add(new Mauer("Mauer",mauerbild,210,360,100,this));
		mauer.add(new Mauer("Mauer",mauerbild,240,360,100,this));
		mauer.add(new Mauer("Mauer",mauerbild,270,360,100,this));
		mauer.add(new Mauer("Mauer",mauerbild,300,360,100,this));
		mauer.add(new Mauer("Mauer",mauerbild,330,360,100,this));
		mauer.add(new Mauer("Mauer",mauerbild,360,360,100,this));
		mauer.add(new Mauer("Mauer",mauerbild,390,360,100,this));
		mauer.add(new Mauer("Mauer",mauerbild,420,360,100,this));
		mauer.add(new Mauer("Mauer",mauerbild,450,360,100,this));
		mauer.add(new Mauer("Mauer",mauerbild,480,360,100,this));
		mauer.add(new Mauer("Mauer",mauerbild,510,360,100,this));
		mauer.add(new Mauer("Mauer",mauerbild,540,360,100,this));
		mauer.add(new Mauer("Mauer",mauerbild,570,360,100,this));
		mauer.add(new Mauer("Mauer",mauerbild,600,360,100,this));
		mauer.add(new Mauer("Mauer",mauerbild,630,360,100,this));
		mauer.add(new Mauer("Mauer",mauerbild,660,360,100,this));
		mauer.add(new Mauer("Mauer",mauerbild,690,360,100,this));
		mauer.add(new Mauer("Mauer",mauerbild,720,360,100,this));
		mauer.add(new Mauer("Mauer",mauerbild,750,360,100,this));
		
		
		
		
		
	} else {
		gameOver();
	}
}


	private void gameOver() {
	started=false;
	actors.clear();
	player=null;
	mauer.clear();
	painter.clear();
	currentMap=1;
	
}

	@Override
	public void run() {
		
		while(frame.isVisible()){
			computeDelta();
			
			if(isStarted()){
				checkKeys();  //tastatureingabe
				
				moveObjects(); 
				doLogic();
				cloneVectors();
				repaint();

			}
			
			
			
			
			try{
				Thread.sleep(10); // 10 MS schlafen 
			}catch (InterruptedException e){} //Ausnahmefall
		}
		// TODO Auto-generated method stub
		
	}

	
	private void cloneVectors(){
		painter=(Vector<Sprite>) actors.clone();
	}
	private void moveObjects() {
		Vector<Sprite> temp=(Vector<Sprite>) actors.clone();
		for(ListIterator<Sprite>it=temp.listIterator();it.hasNext();){
			Sprite r=it.next();
			r.move(delta);
		}
		// TODO Auto-generated method stub
		
	}

	private void doLogic() {
		Vector<Sprite> temp=(Vector<Sprite>) actors.clone();
		for(ListIterator<Sprite> it=temp.listIterator();it.hasNext();){
			Sprite r=it.next();
			r.doLogic(delta);
		}
		// TODO Auto-generated method stub
		
	}

	private void checkKeys() {
		if(up){
			player.setVerticalSpeed(-speed);
		}
		
		if(down){
			player.setVerticalSpeed(speed);
		}
		if(right){
			player.setHorizontalSpeed(speed);
		}
		if(left){
			player.setHorizontalSpeed(-speed);
		}
		if(!up && !down){
			player.setVerticalSpeed(0);
		}
		if(!left && !right){
			player.setHorizontalSpeed(0);
		}
		// TODO Auto-generated method stub
		
	}

	private void computeDelta() {
		// TODO Auto-generated method stub
		
		delta= System.nanoTime()- last;
		last = System.nanoTime();
		fps=((long) 1e9)/delta; // 1/Sekunde ausgabe
		
	}
	
	@Override
	public void paintComponent(Graphics g){
		super.paintComponent(g);
		g.setColor(Color.green);
		g.drawString("FPS: " +Long.toString(fps),20,10);
		if(painter!=null){
			for(ListIterator<Sprite>it= painter.listIterator();it.hasNext();){
				Sprite r=it.next();
				r.drawObjects(g);
			}
				
		}
	}
	
	
private BufferedImage[] loadPics(String path, int pics){
	BufferedImage[] anim= new BufferedImage[pics];
	BufferedImage source=null;
	
	URL pic_url= getClass().getClassLoader().getResource(path);
	try{
	source=ImageIO.read(pic_url);
	}catch(IOException e){}
	
	for(int x=0; x <pics;x++){
		anim[x]= source.getSubimage(x*source.getWidth()/pics,0,source.getWidth()/pics,source.getHeight());
		
	}
	
	return anim;
}

@Override
public void keyTyped(KeyEvent e) {
	// TODO Auto-generated method stub
	
}
@Override
public void keyPressed(KeyEvent e){
	if(e.getKeyCode()==KeyEvent.VK_UP){
		up=true;
	}
	if(e.getKeyCode()==KeyEvent.VK_DOWN){
		down=true;
	}
	if(e.getKeyCode()==KeyEvent.VK_LEFT){
		left=true;
	}
	if(e.getKeyCode()==KeyEvent.VK_RIGHT){
		right=true;
	}
}

@Override
public void keyReleased(KeyEvent e) {
	// TODO Auto-generated method stub
	if(e.getKeyCode()==KeyEvent.VK_UP){
		up=false;
	}
	if(e.getKeyCode()==KeyEvent.VK_DOWN){
		down=false;
	}
	if(e.getKeyCode()==KeyEvent.VK_LEFT){
		left=false;
	}
	if(e.getKeyCode()==KeyEvent.VK_RIGHT){
		right=false;
		
	}
	if(e.getKeyCode()==KeyEvent.VK_ENTER){
		if(!isStarted()){
			doInitializations();
			setStarted(true);
		}
	}
	if(e.getKeyCode()==KeyEvent.VK_ESCAPE){
		if(isStarted()){
			setStarted(false);
		}else{
			frame.dispose();
		}
	}
}

public boolean isStarted(){
	return started;
}
public void setStarted(boolean started){
	this.started=started;
	
}

public Vector<Sprite> getActors() {
	return actors;
}

public void nextmap() {
	started=false;
	actors.clear();
	player=null;
	mauer.clear();
	painter.clear();
	currentMap++;
	doInitializations();
	started=true;
	
	// TODO Auto-generated method stub
	
}

}
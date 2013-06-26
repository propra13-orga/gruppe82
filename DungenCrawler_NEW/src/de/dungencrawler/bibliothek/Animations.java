package de.dungencrawler.bibliothek;

import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;

import javax.imageio.ImageIO;

public class Animations {
	public static final BufferedImage[] WALL=loadPics("pics/wall.png",1);
	public static final BufferedImage[] FIRE_TRAP = loadPics("pics/feuer.jpeg",4); // Falle
	public static final BufferedImage[] GOAL=loadPics("pics/Goal.png",1);
	public static final BufferedImage[] ENEMY1 = loadPics("pics/enemy.png", 1);
	public static final BufferedImage[] PLAYER = loadPics("pics/spieler_gruen.png", 2);
	public static final BufferedImage[] SLASH_RIGHT = loadPics("pics/slash_right_animation_2.png",3);
	public static final BufferedImage[] FIRE_BALL = loadPics("pics/feuerball.png", 5);
	public static final BufferedImage[] ICE_BALL = loadPics("pics/Eisball.png", 5);
	public static final BufferedImage[] FIRE_MONITOR = loadPics("pics/FeuerMonitor.png", 1);
	public static final BufferedImage[] ICE_MONITOR = loadPics("pics/EisMonitor.png", 1);
	public static final BufferedImage[] HEARTH = loadPics("pics/herz.png", 1);
	public static final BufferedImage[] MANA_POOL = loadPics("pics/manapool.png",1);
	public static final BufferedImage[] CONTINUES = loadPics("pics/continues.png",1);

	
	

	public static BufferedImage[] loadPics(String path, int pics) {
		BufferedImage[] anim = new BufferedImage[pics];
		BufferedImage source = null;

		URL pic_url = Animations.class.getClassLoader().getResource(path);
		try {
			source = ImageIO.read(pic_url);
		} catch (IOException e) {
		}

		for (int x = 0; x < pics; x++) {
			anim[x] = source.getSubimage(x * source.getWidth() / pics, 0,
					source.getWidth() / pics, source.getHeight());
		}
		return anim;
	}
}

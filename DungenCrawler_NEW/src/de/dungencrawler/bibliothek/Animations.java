package de.dungencrawler.bibliothek;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.Serializable;
import java.net.URL;
import java.util.Hashtable;

import javax.imageio.ImageIO;

import de.dungencrawler.Spielfeld;

public class Animations implements Serializable {

		public static final BufferedImage[] WALL=loadPics("pics/wall.png",1);
		public static final BufferedImage[] FIRE_TRAP = loadPics("pics/feuer.jpeg",4); // Falle
		public static final BufferedImage[] GOAL=loadPics("pics/Goal.png",1);
		public static final BufferedImage[] ENEMY1 = loadPics("pics/enemy.png", 1);
		public static final BufferedImage[] PLAYER = loadPics("pics/spieler_gruen.png", 2);
		public static final BufferedImage[] SLASH_RIGHT = loadPics("pics/slash_right_animation_2.png",3);
		public static final BufferedImage[] SLASH_LEFT = loadPics("pics/slash_left_animation.png",3);
		public static final BufferedImage[] SLASH_BOTTOM = loadPics("pics/slash_bottom_animation.png",3);
		public static final BufferedImage[] SLASH_TOP = loadPics("pics/slash_top_animation.png",3);
		public static final BufferedImage[] SLASH_RIGHT_TOP = loadPics("pics/slash_right_top_animation.png",3);
		public static final BufferedImage[] SLASH_LEFT_TOP = loadPics("pics/slash_left_top_animation.png",3);
		public static final BufferedImage[] SLASH_RIGHT_BOTTOM = loadPics("pics/slash_right_bottom_animation.png",3);
		public static final BufferedImage[] SLASH_LEFT_BOTTOM = loadPics("pics/slash_left_bottom_animation.png",3);
		public static final BufferedImage[] FIRE_BALL = loadPics("pics/Feuerball.png", 5);
		public static final BufferedImage[] ICE_BALL = loadPics("pics/Eisball.png", 5);
		public static final BufferedImage[] FIRE_MONITOR = loadPics("pics/FeuerMonitor.png", 1);
		public static final BufferedImage[] ICE_MONITOR = loadPics("pics/EisMonitor.png", 1);
		public static final BufferedImage[] HEARTH = loadPics("pics/herz.png", 1);
		public static final BufferedImage[] MANA_POOL = loadPics("pics/manapool.png",1);
		public static final BufferedImage[] CONTINUES = loadPics("pics/continues.png",1);
		public static final BufferedImage[] SHOP_RIGHT = loadPics("pics/Shop_Right.png",1);
		public static final BufferedImage[] SHOP_LEFT = loadPics("pics/Shop_Left.png",1);
		public static final BufferedImage[] SHOP_TOP = loadPics("pics/Shop_Top.png",1);
		public static final BufferedImage[] SHOP_BOTTOM = loadPics("pics/Shop_Bottom.png",1);
		public static final BufferedImage[] COIN = loadPics("pics/muenze.png", 3);
		public static final BufferedImage[] COIN_INTERFACE = loadPics("pics/muenze_interface.png", 1);
		public static final BufferedImage BACKGROUND = loadPic("pics/textur2.png");
		public static final BufferedImage[] REFIL_IMAGES = loadPics("pics/refilllife.png", 1);
		public static final BufferedImage[] ATTACK_DAMAGE_ICON = loadPics("pics/att_icon.png", 1);
		public static final BufferedImage[] MAGERY_ICON = loadPics("pics/magery_icon.png", 1);
		public static final BufferedImage[] ARMOR_ICON = loadPics("pics/armor_icon.png", 1);
		public static final BufferedImage[] MAGIC_RESISTANCE_ICON = loadPics("pics/magresi_icon.png", 1);
		public static final BufferedImage[] QUEST_NPC = loadPics("pics/questnpc.png", 2);
		public static final BufferedImage[] SWITCH = loadPics("pics/switch2.png", 1);
		public static final BufferedImage STORY1 = loadPic("pics/STORY1.jpg"); 
		public static final BufferedImage STORY2 = loadPic("pics/STORY2.jpg"); 
		public static final BufferedImage[] ICE_WALL = loadPics("pics/eismauer.jpeg",1);
		public static final BufferedImage[] ICE_TRAP = loadPics("pics/eisfalle.jpeg",1);
		public static final BufferedImage[] MATSCH_WALL = loadPics("pics/Matsch.jpeg",1);
		public static final BufferedImage[] MATSCH_TRAP = loadPics("pics/matschfalle.jpeg",1);




	public static BufferedImage[] loadPics(String path, int pics) {
		BufferedImage[] anim = new BufferedImage[pics];
		BufferedImage source = null;
		
		URL pic_url = Animations.class.getClassLoader().getResource(path);
		System.out.println("load" + pic_url.getPath());

		try {
			source = ImageIO.read(pic_url);
			
			for (int x = 0; x < pics; x++) {
				anim[x] = source.getSubimage(x * source.getWidth() / pics, 0,
						source.getWidth() / pics, source.getHeight());
			}
		} catch (IOException e) {
		}


		return anim;
	}
	
	public static BufferedImage getStoryPic(String str) {
		Hashtable<String, BufferedImage> table = new Hashtable<String, BufferedImage>();
		table.put("STORY1", STORY1);
		table.put("STORY2", STORY2);

		return table.get(str);
	}


	private static BufferedImage loadPic(String path) {
		URL pic_url = Animations.class.getClassLoader().getResource(path);
		System.out.println("load" + pic_url.getPath());
		BufferedImage img = null;
		try {
			img = ImageIO.read(pic_url);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return img;
	}
	
	public static BufferedImage[] getWallByMapType(String str) {
		Hashtable<String, BufferedImage[]> table = new Hashtable<String, BufferedImage[]>();
		if(str == null)
			str = "NORMAL";
		table.put("NORMAL", WALL);
		table.put("ICE", ICE_WALL);
		table.put("EARTH", MATSCH_WALL);
		
		BufferedImage[] result = table.get(str);
		if(result == null)
			result = WALL;
		return result;
	}
	
	public static BufferedImage[] getTrapByType(String str) {
		Hashtable<String, BufferedImage[]> table = new Hashtable<String, BufferedImage[]>();
		if(str == null)
			str = "NORMAL";
		table.put("NORMAL", FIRE_TRAP);
		table.put("ICE", ICE_TRAP);
		table.put("EARTH", MATSCH_TRAP);
		
		BufferedImage[] result = table.get(str);
		if(result == null)
			result = FIRE_TRAP;
		return result;
	}
}

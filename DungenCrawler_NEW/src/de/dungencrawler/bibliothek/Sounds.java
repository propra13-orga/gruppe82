package de.dungencrawler.bibliothek;

import java.applet.Applet;
import java.applet.AudioClip;

import java.net.URL;

public class Sounds {

	public static AudioClip CAST = loadSound("sounds/cast2.wav");
	public static AudioClip START_MUSIK = loadSound("sounds/einleitung.wav");

	private static AudioClip loadSound(String path) {
		AudioClip audio = null;
		URL url = Sounds.class.getClassLoader().getResource(path);
		System.out.println("laod Sound: " + url.getPath());
		audio =Applet.newAudioClip(url); 	
		return audio;
	} 
			

}

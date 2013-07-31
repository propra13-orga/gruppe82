package de.dungencrawler.bibliothek;

import java.applet.AudioClip;

public class PlaySound implements Runnable {

	private AudioClip sound;
	private boolean loop;
	private int delay;
	
	
	public PlaySound(AudioClip sound, boolean loop, int delay) {
		super();
		this.sound = sound;
		this.loop = loop;
		this.delay = delay;
		new Thread(this).start();
	}


	@Override
	public void run() {
		do {
//			System.out.println("SOund");
			sound.play();
			try {
				Thread.sleep(delay);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			sound.stop();
		} while(loop);
	}


	public void stop() {
		loop = false;
		sound.stop();
	}

}

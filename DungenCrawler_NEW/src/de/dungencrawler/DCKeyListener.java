package de.dungencrawler;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class DCKeyListener implements KeyListener {
	private Spielfeld parent;
	private boolean server = false;
	private boolean client = false;

	public DCKeyListener(Spielfeld parent) { //Konstrucktor
		this.parent = parent;
	}

	@Override
	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub
	}

	@Override
	public void keyPressed(KeyEvent e) {
		if (e.getKeyCode() == KeyEvent.VK_UP) {
			parent.setUp(true);
		}
		if (e.getKeyCode() == KeyEvent.VK_DOWN) {
			parent.setDown(true);
		}
		if (e.getKeyCode() == KeyEvent.VK_LEFT) {
			parent.setLeft(true);
		}
		if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
			parent.setRight(true);
		}
	}

	@Override
	public void keyReleased(KeyEvent e) {
		// TODO Auto-generated method stub
		if (e.getKeyCode() == KeyEvent.VK_UP) {
			parent.setUp(false);
		}
		if (e.getKeyCode() == KeyEvent.VK_DOWN) {
			parent.setDown(false);
		}
		if (e.getKeyCode() == KeyEvent.VK_LEFT) {
			parent.setLeft(false);
		}
		if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
			parent.setRight(false);

		}
		if (e.getKeyCode() == KeyEvent.VK_ENTER) {
			System.out.println("Started 1: " + parent.isStarted());
			if(parent.isStarted() && parent.isPaused()) {
				System.out.println("NextView or NextMap");
				if(!parent.nextView())
					parent.nextMap();
			}
			if (!parent.isStarted()) {
				if(parent.isPaused()) {
					if(!parent.isViewerStarted())
						parent.beginStory();
					if(!parent.nextView())
						parent.setPaused(false);
				} else if(parent.TEST_LEVEL_ON) {
					parent.doInitializationsTestLevel();
					parent.setStarted(true);
				} else {
					if(server ) {
						parent.startNetworkGameAsServer();
						parent.setStarted(true);
					} else if(client) {
						parent.startNetworkGameAsClient();
						parent.setStarted(true);
					} else {
						parent.doInitializations();
						parent.setStarted(true);
					}
				}
			}
			System.out.println("Started 2: " + parent.isStarted());
		}
		if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {
			if (parent.isStarted()) {
				parent.setStarted(false);
			} else {
				parent.frame.dispose();
			}
		}
		if (e.getKeyCode() == KeyEvent.VK_W) {
			parent.throwSpell(true); //Teilt dem Spiel, bei betätigen von "W", mit dass der Spieler den momentan gewählten Zauberspruch wirken soll.
		}
		if (e.getKeyCode() == KeyEvent.VK_Q) {
			parent.slash(); //Teilt dem Spiel, bei betätigen von "Q", mit dass der Spieler den normalen Angriff wirken soll.
		}
		if (e.getKeyCode() == KeyEvent.VK_E) {
			parent.switchSpell(); //Teilt dem Spiel, bei betätigen von E, mit dass der Zauberspruch gewechselt werden soll.
		}
		
		if (e.getKeyCode() == KeyEvent.VK_SPACE) {
			parent.interAction(); //Teilt dem Spiel, bei betätigen von SPACE, mit dass die Spielfigur mit etwas interagieren will.
		}
	}

}

package de.dungencrawler;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class DCKeyListener implements KeyListener {
	private Spielfeld parent;
	
	public DCKeyListener(Spielfeld parent) {
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
			if (!parent.isStarted()) {
				parent.doInitializations();
				parent.setStarted(true);
			}
		}
		if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {
			if (parent.isStarted()) {
				parent.setStarted(false);
			} else {
				parent.frame.dispose();
			}
		}
		if (e.getKeyCode() == KeyEvent.VK_W) {
			parent.throwFireBall(true);
		}
		if (e.getKeyCode() == KeyEvent.VK_Q) {
			parent.slash();
		}
		if (e.getKeyCode() == KeyEvent.VK_E) {
			parent.switchSpell();
		}
		
		if (e.getKeyCode() == KeyEvent.VK_SPACE) {
			parent.interAction();
		}
	}

}

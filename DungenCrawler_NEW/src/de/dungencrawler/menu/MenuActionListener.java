package de.dungencrawler.menu;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import de.dungencrawler.Spielfeld;

public class MenuActionListener implements ActionListener {
	
	private Spielfeld parent;
	
	

	public MenuActionListener(Spielfeld parent) {
		super();
		this.parent = parent;
	}



	@Override
	public void actionPerformed(ActionEvent arg0) {
		String action = arg0.getActionCommand();
		if(action.equals("Save")) {
			parent.save();
		} else if(action.equals("Load")) {
			parent.load();
		} else if(action.equals("New Game")) {
			parent.restartGame();
		}

	}

}

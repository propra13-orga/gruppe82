package de.dungencrawler.secrets;

import java.awt.Color;
import java.awt.Container;
import java.awt.Point;
import java.util.Vector;

import de.dungencrawler.Spielfeld;
import de.dungencrawler.message.Message;
import de.dungencrawler.sprites.Mauer;

public class SwitchPuzzle {
	private Vector<PuzzleSwitch> puzzelSwitches = new Vector<PuzzleSwitch>();
	private Vector<PuzzleSwitch> staticSwitches = new Vector<PuzzleSwitch>();
	private Vector<Mauer> walls = new Vector<Mauer>();
	private int actualSwitch = 1;
	private Spielfeld parent;
	private PuzzleSwitch lastSwitch;
	
	public SwitchPuzzle(Spielfeld parent) {
		super();
		this.parent = parent;
	}

	public void addSwitch(PuzzleSwitch pSwitch) {
		puzzelSwitches.add(pSwitch);
		staticSwitches.add(pSwitch);
	}

	public void addWall(Mauer wall) {
		walls.add(wall);
	}

	public void setSwitch(PuzzleSwitch s) {
		boolean temp = s != lastSwitch;
		if(puzzelSwitches.size() > 0) {
			if(s.getNr() == actualSwitch) {
				actualSwitch++;
				lastSwitch = s;
				puzzelSwitches.remove(s);
				if(puzzelSwitches.size() <= 0) {
					for(Mauer m : walls)
						parent.removeActor(m);
				}
			} else if(lastSwitch != null) {
				if(!s.equals(lastSwitch)) {
					lastSwitch = null;
					puzzelSwitches = (Vector<PuzzleSwitch>) staticSwitches.clone();
					actualSwitch = 1;
					Message m = new Message("Puzzle Neustart!", new Point((int)(s.getX()),
							(int)(s.getY() + 10)), new Color(0,180,40), 1000, parent);
					parent.addMessage(m);
				}
			}
			
			if(temp) {
				System.out.println("On Switch " + s.getNr());
				System.out.println("Restliche Schalter" + puzzelSwitches.size());
			}
		}

	}

}

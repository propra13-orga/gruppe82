package de.dungencrawler.items;

public class LeatherShoeArmor extends Shoe {
	private final static int ARMOR = 2; 
	
	@Override
	public int getArmor() {
		return ARMOR;
	}

	@Override
	public int getMagicResistance() {
		return 0;
	}

}

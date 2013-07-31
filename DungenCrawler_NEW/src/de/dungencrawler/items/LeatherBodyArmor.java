package de.dungencrawler.items;

public class LeatherBodyArmor extends BodyArmor {
	private final static int ARMOR = 10; 
	
	@Override
	public int getArmor() {
		return ARMOR;
	}

	@Override
	public int getMagicResistance() {
		return 0;
	}

}

package de.dungencrawler.items;

public class SmallSword extends Waffe {

	private static final int NORMAL_DAMAGE = 15;
	private static final int MAGIC_DAMAGE = 0;

	@Override
	public int getNormalDamage() {
		return NORMAL_DAMAGE;
	}

	@Override
	public int getMagicDamage() {
		// TODO Auto-generated method stub
		return MAGIC_DAMAGE;
	}

}

package de.dungencrawler.items;

public class SmallWard extends Waffe {

	private static final int NORMAL_DAMAGE = 0;
	private static final int MAGIC_DAMAGE = 15;

	@Override
	public int getNormalDamage() {
		return NORMAL_DAMAGE;
	}

	@Override
	public int getMagicDamage() {
		return MAGIC_DAMAGE;
	}
}

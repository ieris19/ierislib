package lib.ieris19.util.dice;

import java.util.Random;

public class Dice {
	private final int sides;

	public Dice(int sides) {
		this.sides = sides;
	}

	public int roll() {
		Random rng = new Random();
		return rng.nextInt(1, sides + 1);
	}
}

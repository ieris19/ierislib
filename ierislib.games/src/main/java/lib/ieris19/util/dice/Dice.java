package lib.ieris19.util.dice;

import java.util.Random;

/**
 * Class that represents a die
 */
public class Dice {
	/**
	 * The number of sides of the die
	 */
	private final int sides;

	/**
	 * Creates a new die with the specified number of sides
	 *
	 * @param sides The number of sides of the die
	 */
	public Dice(int sides) {
		this.sides = sides;
	}

	/**
	 * Rolls the die
	 *
	 * @return The result of the roll
	 */
	public int roll() {
		Random rng = new Random();
		return rng.nextInt(1, sides + 1);
	}
}

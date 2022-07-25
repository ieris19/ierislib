package lib.ieris19.util.dice;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class DiceTest {
	private static int sides = 12;
	private static Dice dice;

	@BeforeAll static void setUp() {
		dice = new Dice(sides);
	}

	@Test void rollTest() {
		for (int i = 0; i < 100000; i++) {
			int result = dice.roll();
			assert (1 <= result && result <= 12);
		}
	}
}
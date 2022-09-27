/*
 * Copyright 2021 Ieris19
 *
 *    Licensed under the Apache License, Version 2.0 (the "License");
 *    you may not use this file except in compliance with the License.
 *    You may obtain a copy of the License at
 *
 *        http://www.apache.org/licenses/LICENSE-2.0
 *
 *    Unless required by applicable law or agreed to in writing, software
 *    distributed under the License is distributed on an "AS IS" BASIS,
 *    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *    See the License for the specific language governing permissions and
 *    limitations under the License.
 *
 */

package lib.ieris19.games.dice;

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

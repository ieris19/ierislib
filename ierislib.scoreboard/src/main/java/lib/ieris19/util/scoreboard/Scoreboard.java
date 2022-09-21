package lib.ieris19.util.scoreboard;

import java.util.Arrays;

/**
 * A simple scoreboard that can be used to keep track of the scores.
 */
public class Scoreboard {
	/**
	 * Array of scores for each player
	 */
	int[] playerScores;

	/**
	 * Creates a new scoreboard with the given number of players.
	 *
	 * @param playerAmount amount of players to keep track of
	 */
	public Scoreboard(int playerAmount) {
		if (validNumber(playerAmount) && playerAmount != 0)
			this.playerScores = new int[playerAmount];
		else {
			throw new IllegalStateException("Scoreboard can't be created with less than 1 player");
		}
	}

	/**
	 * Sets the score of the given player index to the given score value.
	 *
	 * @param index number to check
	 * @param score score to check
	 *
	 * @throws IndexOutOfBoundsException if the player index is out of bounds
	 * @throws IllegalArgumentException  if the score is negative
	 */
	public void scoreSet(int index, int score) throws IndexOutOfBoundsException, IllegalArgumentException {
		if (validNumber(score))
			playerScores[index] = score;
		else
			throw new IllegalArgumentException("Can't set negative score");
	}

	/**
	 * Adds the given score to the score of the given player index.
	 *
	 * @param index  player index to change
	 * @param points amount of points to add
	 *
	 * @throws IndexOutOfBoundsException if the player index is out of bounds
	 * @throws IllegalArgumentException  if the score is negative
	 */
	public void scoreIncrease(int index, int points) throws IndexOutOfBoundsException, IllegalArgumentException {
		if (validNumber(points))
			playerScores[index] += points;
		else
			throw new IllegalArgumentException("Points needs to be a positive integer");
	}

	/**
	 * Subtracts the given score from the score of the given player index.
	 *
	 * @param index  player index to change
	 * @param points amount of points to subtract
	 *
	 * @throws IndexOutOfBoundsException if the player index is out of bounds
	 * @throws IllegalArgumentException  if the score is negative
	 */
	public void scoreDecrease(int index, int points) throws IndexOutOfBoundsException, IllegalArgumentException {
		if (validNumber(points))
			if (playerScores[index] - points > 0)
				playerScores[index] -= points;
			else
				throw new IllegalArgumentException("Points needs to be a positive integer");
	}

	/**
	 * Resets all scores to 0.
	 */
	public void resetScoreboard() {
		Arrays.fill(playerScores, 0);
	}

	/**
	 * Validates if the given number is a positive integer.
	 *
	 * @param n number to check
	 *
	 * @return true if the number is a positive integer
	 */
	private boolean validNumber(int n) {
		return n > 0;
	}

	/**
	 * Formats a string as a table with the scores of all players.
	 * @return scoreboard information in a string
	 */
	@Override public String toString() {
		StringBuilder builder = new StringBuilder("Scoreboard\n");
		for (int i = 0; i < playerScores.length; i++) {
			builder.append("Player ").append(i + 1).append(":").append(playerScores[i]).append('\n');
		}
		return builder.toString();
	}
}

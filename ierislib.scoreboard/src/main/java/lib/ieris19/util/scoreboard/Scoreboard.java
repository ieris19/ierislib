package lib.ieris19.util.scoreboard;

import java.util.Arrays;

public class Scoreboard {
	int[] playerScores;

	public Scoreboard(int playerAmount) {
		if (validNumber(playerAmount) && playerAmount != 0)
			this.playerScores = new int[playerAmount];
		else {
			throw new IllegalStateException("Scoreboard can't be created with less than 1 player");
		}
	}

	public void scoreSet(int index, int score) {
		if (validNumber(score))
			playerScores[index] = score;
		else
			throw new IllegalArgumentException("Can't set negative score");
	}

	public void scoreIncrease(int index, int points) {
		if (validNumber(points))
			playerScores[index] += points;
		else
			throw new IllegalArgumentException("Points needs to be a positive integer");
	}

	public void scoreDecrease(int index, int points) {
		if (validNumber(points))
			if (playerScores[index] - points > 0)
				playerScores[index] -= points;
			else
				throw new IllegalArgumentException("Points needs to be a positive integer");
	}

	public void resetScoreboard() {
		Arrays.fill(playerScores, 0);
	}

	private boolean validNumber(int n) {
		return n > 0;
	}

	@Override public String toString() {
		StringBuilder builder = new StringBuilder("Scoreboard\n");
		for (int i = 0; i < playerScores.length; i++) {
			builder.append("Player ").append(i + 1).append(":").append(playerScores[i]).append('\n');
		}
		return builder.toString();
	}
}

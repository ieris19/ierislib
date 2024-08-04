package com.ieris19.lib.util.scoreboard;

import com.ieris19.lib.common.cli.TextColor;
import com.ieris19.lib.util.console.Command;
import com.ieris19.lib.util.console.Console;

import java.util.ArrayList;

/**
 * A console to keep live track of scores while playing other games.
 */
public class ScoreConsole {
    /**
     * The console object
     */
    private final Console console;
    private final Scoreboard scoreboard;

    /**
     * A constructor for the ScoreConsole class.
     *
     * @param amountOfPlayers the amount of players that will be playing the game
     */
    public ScoreConsole(int amountOfPlayers) {
        this.console = Console.getInstance();
        console.setWelcomeMessage(
                "This is your scoreboard to keep track of the scores for " + amountOfPlayers + " players.");
        console.setPrompt("Scoreboard> ");
        this.scoreboard = new Scoreboard(amountOfPlayers);
        createCommands();
    }

    /**
     * Starts the console.
     */
    public void start() {
        console.launch();
    }

    /**
     * Creates the commands for the console.
     */
    private void createCommands() {
        ArrayList<Command> commands = new ArrayList<>();
        commands.add(new Command("set", "Sets the score of a player", this::parse));
        commands.add(new Command("add", "Adds a score to a player", this::parse));
        commands.add(new Command("sub", "Removes a score from a player", this::parse));
        commands.add(new Command("reset", "Resets the scoreboard", this::parse));
        commands.forEach(this.console::addCommand);
    }

    /**
     * Sets the score of a player.
     *
     * @param args the arguments
     */
    private void parse(String[] args) {
        try {
            int player = Integer.parseInt(args[1]);
            int score = Integer.parseInt(args[2]);
            modifyScore(player, score, args[0]);
            TextColor.println(scoreboard.toString(), TextColor.BLUE);
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException(e.getMessage());
        }
    }

    /**
     * Modifies the score of a player. This method is used by the console to process the arguments after they have been
     * parsed.
     *
     * @param player    player number that will be modified
     * @param score     score that will be added or subtracted
     * @param operation operation that will be performed
     */
    private void modifyScore(int player, int score, String operation) {
        switch (operation) {
            case "set" -> scoreboard.scoreSet(player, score);
            case "add" -> scoreboard.scoreIncrease(player, score);
            case "sub" -> scoreboard.scoreDecrease(player, score);
            case "reset" -> scoreboard.resetScoreboard();
        }
    }
}
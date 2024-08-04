package com.ieris19.lib.util.scoreboard;

import java.util.Scanner;

/**
 * This class is the entrypoint of the interactive scoreboard. It is responsible for creating the scoreboard and
 * starting the console.
 */
public class Main {
    /**
     * This class is static and shouldn't be instantiated
     */
    private Main() {
    }

    /**
     * The entrypoint of the interactive scoreboard.
     *
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("How many players are playing?");
        System.out.println("Enter a number: ");
        int amountOfPlayers = Integer.parseInt(scanner.nextLine());
        ScoreConsole console = new ScoreConsole(amountOfPlayers);
        console.start();
    }
}

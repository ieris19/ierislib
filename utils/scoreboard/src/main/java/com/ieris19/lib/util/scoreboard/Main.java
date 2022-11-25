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
	private Main() {}

	/**
	 * The entrypoint of the interactive scoreboard.
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

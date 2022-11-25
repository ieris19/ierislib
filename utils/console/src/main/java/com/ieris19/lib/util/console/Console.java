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

package com.ieris19.lib.util.console;

import com.ieris19.lib.common.Script;
import com.ieris19.lib.common.cli.TextColor;

import java.util.HashMap;
import java.util.InputMismatchException;
import java.util.Scanner;

import static com.ieris19.lib.common.cli.TextColor.*;

/**
 * The console class will require a little setup from the user. First you will need to add your own commands to the
 * console in some sort of initializer method. Once the console has been started it will enter an infinite loop of
 * parsing and executing commands.
 *
 * @see #addCommand(Command)
 * @see Script
 */
public class Console {
	/**
	 * Singleton instance of the console
	 */
	private static Console instance;
	/**
	 * Scanner reading user input from the CLI
	 */
	private final Scanner input;
	/**
	 * Map of the available commands. A command is identified by a unique id and a command object
	 */
	private final HashMap<String, Command> commandMap;
	/**
	 * Defines whether the console has started or not, in order to limit some functionality
	 */
	private boolean launched;
	/**
	 * Message used to welcome the user to the console
	 */
	private String welcomeMessage;
	/**
	 * Defines the preceding when asking the user for input
	 */
	private String prompt;

	/**
	 * Private constructor to avoid accidentally creating multiple instances of {@link Console}. Please refer to
	 * {@link #getInstance()} for more information
	 */
	private Console() {
		this.input = new Scanner(System.in);
		this.commandMap = new HashMap<>();
		this.welcomeMessage = """
													Welcome to the console\s
													 This command line application will let you control this program through some simple commands\s
													""";
		this.setPrompt("$");
		addCommand(new Command("help", "This command will print to the console a list of all available commands",
													 this::availableCommands));
		addCommand(new Command("exit",
													 "It will close the console by shutting down the program, it accepts an exit code, but will default to 1 if none is specified",
													 this::exit));
	}

	/**
	 * Adds a command to the console. Once added, the command can be called by typing its named and the needed arguments
	 * into the console
	 *
	 * @param command the command object to be added, in most cases it will be a new Command
	 *
	 * @throws IllegalArgumentException if the command name is already taken
	 * @throws IllegalStateException    if the console has already been launched
	 * @see Command
	 */
	public synchronized void addCommand(Command command) throws IllegalStateException, IllegalArgumentException {
		if (launched)
			throw new IllegalStateException("Console has already launched");
		if (commandMap.containsKey(command.getName()))
			throw new IllegalArgumentException("Command name already taken");
		commandMap.put(command.getName(), command);
	}

	/**
	 * Returns the only instance of the console that can exist during runtime. The first time it's called, it will create
	 * said instance, but any subsequent call will return the existing instance
	 *
	 * @return The only existing instance of this class
	 */
	public static synchronized Console getInstance() {
		if (instance == null) {
			instance = new Console();
		}
		return instance;
	}

	/**
	 * Starts the console with a short welcome message and starts the command line interface loop (CLI) <br>
	 *
	 * {@linkplain Console#parseCommand(String)} contains a list of all available commands
	 */
	public void launch() {
		println(getWelcomeMessage(), WHITE);
		launched = true;
		console();
	}

	/**
	 * Sets the message to welcome the user when the application is launched. It will automatically append "Type help to
	 * see a list of available commands" at the end. If this behavior is undesired, then use
	 * {@link #overrideWelcomeMessage}. Beware, the latter will not ensure that users know the help command
	 *
	 * @param message Welcome message to be set
	 */
	public void setWelcomeMessage(String message) {
		this.welcomeMessage = message + "\nType \"help\" to see a list of available commands";
	}

	/**
	 * Unsafely sets the welcome message. Use only if you know what you are doing. Otherwise, see
	 * {@link #setWelcomeMessage(String)}. This method will set the message exactly to the contents of its input. This
	 * does not ensure that users know about the help command
	 *
	 * @param message Exact welcome message
	 */
	public void overrideWelcomeMessage(String message) {
		this.welcomeMessage = message;
	}

	/**
	 * The welcome message is displayed to the user when the console is launched
	 *
	 * @return the welcome messaged previously set by the user or the default one if not set
	 */
	public String getWelcomeMessage() {
		return welcomeMessage;
	}

	/**
	 * Sets the prompt for this console. It will automatically add a space in the end if it doesn't already end in a blank
	 * space. This includes {@code space, newline, or horizontal tabulation}
	 *
	 * @param prompt The prompt to be set
	 */
	public void setPrompt(String prompt) {
		if (prompt.endsWith(" ") || prompt.endsWith("\n") || prompt.endsWith("\t"))
			this.prompt = prompt;
		else
			this.prompt = prompt + " ";
	}

	/**
	 * The prompt is displayed to the user in order to request input
	 *
	 * @return the prompt previously set by the user or the default one if not set
	 */
	public String getPrompt() {
		return prompt;
	}

	/**
	 * Runs an infinite loop where commands are entered and as a result, certain methods are executed, thus allows full
	 * control of the program with a small amount of commands.
	 *
	 * In the commands, you can use the following exceptions to handle the reply the console will give.
	 * <ul>
	 *   <li>No exception and after every exception it will loop back to the prompt. Catch your own exceptions to override
	 *   this behaviour</li>
	 *   <li>InvalidCommandException will let the user know that the command is not recognized by the console</li>
	 *   <li>IllegalArgumentException will let the user know that the command given contains illegal arguments</li>
	 *   <li>ArrayIndexOutOfBoundsException will let the user know that the command contains insufficient arguments. This
	 *   makes it safe to loop through arguments even if the user hasn't inputted</li>
	 *   <li>NullPointerException will automatically be thrown if the command is empty. It will display that the command
	 *   is empty</li>
	 *   <li>InputMismatchException will let the user know that there was an error parsing the command's arguments.</li>
	 *   <li>Any other exception will just display "Something went wrong"</li>
	 * </ul>
	 *
	 * Any exception given an error message will be displayed afterwards
	 */
	public void console() {
		String command;
		while (launched) {
			//The following variable stores any thrown exception to be handled in the "finally" block. It will be ignored or overwritten
			Exception expectedException = new Exception();

			TextColor.print(getPrompt(), YELLOW);
			command = input.nextLine();
			try {
				parseCommand(command);
			} catch (InvalidCommandException exception) {
				expectedException = exception;
				println('\"' + command + '\"' + " is not recognized by the console", RED);
				println("Try the command \"help\" if in doubt", BLUE);
			} catch (IllegalArgumentException exception) {
				expectedException = exception;
				println('\"' + command + '\"' + " contains illegal arguments", RED);
			} catch (ArrayIndexOutOfBoundsException exception) {
				expectedException = exception;
				println('\"' + command + '\"' + " contains insufficient arguments", YELLOW);
			} catch (NullPointerException exception) {
				expectedException = exception;
				println(exception.getMessage() + " string is not a valid command", MAGENTA);
			} catch (InputMismatchException exception) {
				expectedException = exception;
				println("There was an error parsing the command, please try again", YELLOW);
			} catch (Exception exception) {
				expectedException = exception;
				println("Something went wrong", RED);
			} finally {
				if (expectedException.getMessage() != null) {
					println(expectedException.getMessage(), MAGENTA);
				}
			}
		}
	}

	/**
	 * Removes forbidden characters "Form feed" and "Backspace" from the given String
	 *
	 * @param input string to be sanitized
	 *
	 * @return sanitized input
	 */
	private String sanitize(String input) {
		return input.replace("\f", "").replace("\b", "");
	}

	/**
	 * Checks whether a command exists and executes the corresponding method. By breaking up the command line into
	 * multiple tokens, it will execute the code with the specified parameters. The break between tokens is a blank space.
	 * It will ignore unnecessary parameters.<br><br>
	 *
	 * A list of commands can be found {@link Console#availableCommands(String[]) here}
	 *
	 * @param commandLine The command line that includes the command and the arguments, separated by spaces
	 *
	 * @throws InvalidCommandException An invalid command that is not in the command map
	 */
	public void parseCommand(String commandLine) throws InvalidCommandException {
		if (commandLine.equals(""))
			throw new NullPointerException("Empty");
		String cleanCommand = sanitize(commandLine);
		String[] arguments = cleanCommand.split(" ");
		Command command = commandMap.get(arguments[0]);
		if (command != null)
			command.execute(arguments);
		else
			throw new InvalidCommandException();
	}

	/**
	 * This method lists all available commands and their description
	 *
	 * @param arguments Parameter so that this method fits the type {@link Script#execute(String[])}
	 */
	public void availableCommands(String[] arguments) {
		for (String key : commandMap.keySet()) {
			println(key + ": " + commandMap.get(key).help(), GREEN);
		}
	}

	/**
	 * Closes the program, override to create special instructions on how to terminate the program
	 *
	 * @param arguments String of arguments, the second token will be used as an error code, any invalid values will
	 *                  default to one
	 */
	public void exit(String[] arguments) {
		try {
			print("The program will exit", MAGENTA);
			if (!arguments[1].equals("")) {
				print(": Exit Code -> " + arguments[1], MAGENTA);
			}
			launched = false;
		} catch (ArrayIndexOutOfBoundsException exception) {
			launched = false;
		}
	}
}

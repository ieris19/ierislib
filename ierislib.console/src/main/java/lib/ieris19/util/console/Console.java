package lib.ieris19.util.console;

import java.util.HashMap;
import java.util.InputMismatchException;
import java.util.Scanner;

import static lib.ieris19.util.cli.TextColor.*;

/**
 * The console class will require a little setup from the user. First you will need to add your own
 * commands to the console in some sort of initializer method. Once the console has rev
 *
 * @author Jason Abreu
 * @version 2.0
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
	 * Private constructor to avoid accidentally creating multiple instances of {@link Console}.
	 * Please refer to {@link #getInstance()} for more information
	 */
	private Console() {
		input = new Scanner(System.in);
		commandMap = new HashMap<>();
		addCommand(new Command("help",
													 "This command will print to the console a list of all available commands",
													 this::availableCommands));
		addCommand(new Command("exit",
													 "It will close the console by shutting down the program, it accepts an exit code, but will default to 1 if none is specified",
													 this::exit));
	}

	/**
	 * Adds a command to the console. Once added, the command can be called by typing its named and
	 * the needed arguments into the console
	 *
	 * @param command the command object to be added, in most cases it will be a new Command
	 *
	 * @see Command
	 */
	public synchronized void addCommand(Command command) {
		if (!launched)
			commandMap.put(command.getName(), command);
		else
			throw new IllegalStateException("Console has already launched");
	}

	/**
	 * Returns the only instance of the console that can exist during runtime. The first time it's
	 * called, it will create said instance, but any subsequent call will return the existing
	 * instance
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
	 * Starts the console with a short welcome message and starts the command line interface loop
	 * (CLI) <br>
	 *
	 * {@linkplain Console#parseCommand(String)} contains a list of all available commands
	 */
	public void launch() {
		println("Welcome to the console \n This command line application will let you control"
						+ " this program through some simple commands \n Type help to see a list of "
						+ "available commands", WHITE);
		launched = true;
		console();
	}

	/**
	 * Runs an infinite loop where commands are entered and as a result, certain methods are executed,
	 * thus allows full control of the program with a small amount of commands.
	 *
	 * @since 1.0
	 */
	public void console() {
		String command;
		while (true) {
			command = input.nextLine();
			try {
				parseCommand(command);
			} catch (InvalidCommandException exception) {
				println('\"' + command + '\"' + " is not recognized by the console", RED);
			} catch (IllegalArgumentException exception) {
				println('\"' + command + '\"' + " contains illegal arguments", RED);
				if (exception.getMessage() != null)
					println(exception.getMessage(), MAGENTA);
			} catch (ArrayIndexOutOfBoundsException exception) {
				println('\"' + command + '\"' + " contains insufficient arguments", YELLOW);
			} catch (NullPointerException exception) {
				println(exception.getMessage() + " string is not a valid command", MAGENTA);
			} catch (InputMismatchException exception) {
				println("There was an error parsing the command, please try again", YELLOW);
			}
			finally {
				println("Try the command \"help\" if in doubt", BLUE);
			}
		}
	}

	/**
	 * Checks whether a command exists and executes the corresponding method. By breaking up the
	 * command line into multiple tokens, it will execute the code with the specified parameters. The
	 * break between tokens is a blank space. It will ignore unnecessary parameters.<br><br>
	 *
	 * A list of commands can be found {@link Console#availableCommands(String[]) here}
	 *
	 * @param commandLine The command line that includes the command and the arguments, separated by
	 *                    spaces
	 *
	 * @throws InvalidCommandException  An invalid command that cannot be executed
	 * @throws IllegalArgumentException An illegal argument was passed in one of the commands
	 * @throws NullPointerException     A null value was entered as a command or a required parameter
	 *                                  is missing
	 * @throws InputMismatchException   When there's an error parsing the command (as in trying to
	 *                                  read incompatible primitive types)
	 * @since 1.0
	 */
	public void parseCommand(String commandLine)
	throws InvalidCommandException, IllegalArgumentException, NullPointerException,
				 InputMismatchException {
		if (commandLine.equals(""))
			throw new NullPointerException("Empty");
		String[] arguments = commandLine.split(" ");
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
	 * @param arguments String of arguments, the second token will be used as an error code, any
	 *                  invalid values will default to one
	 */
	public void exit(String[] arguments) {
		try {
			print("The program will exit", MAGENTA);
			System.exit(Integer.parseInt(arguments[1]));
		} catch (Exception exception) {
			System.exit(1);
		}
	}
}

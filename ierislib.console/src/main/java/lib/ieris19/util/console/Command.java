package lib.ieris19.util.console;

/**
 * A class used to define commands for use in the console, this class defines all that is needed for
 * the console to parse the command. It includes the amount of parameters the command has. The only
 * limitation is that all arguments must be Strings, thus, any parsing needed must be performed in
 * the runnable class
 */
public class Command {
	/**
	 * Command script designating the code to be called upon when the command is executed
	 */
	private Script script;
	/**
	 * A name to be used in order to call this command
	 */
	private String name;
	/**
	 * A detailed description of the command, its parameters and functioning, to be displayed through
	 * the {@code help} command
	 */
	private String description;

	/**
	 * Constructs a command object
	 *
	 * @param name        the name of the command is the instruction that will be used to call upon
	 *                    it
	 * @param description the description is the text that will be provided as help through the
	 *                    console
	 * @param script      the script is the method that will be run when the command is called.
	 *                    Preferably a lambda expression, but it can also be an anonymous or regular
	 *                    class
	 */
	public Command(String name, String description, Script script) {
		this.script = script;
		this.name = name;
		this.description = description;
	}

	/**
	 * Returns the name of the command
	 * @return the designated name of the command
	 */
	public String getName() {
		return name;
	}

	/**
	 * A method to be called in order to obtain the description of the command
	 * @return helpful text explaining the usage of the command
	 */
	public String help() {
		return description;
	}

	/**
	 * Executes whatever instructions are stored in this command
	 */
	public void execute(String[] args) {
		this.script.execute(args);
	}
}

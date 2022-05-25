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

	public Command(String name, String description, Script script) {
		this.script = script;
		this.name = name;
		this.description = description;
	}

	public String getName() {
		return name;
	}

	public String help() {
		return description;
	}

	public void execute(String[] args) {
		this.script.execute(args);
	}
}

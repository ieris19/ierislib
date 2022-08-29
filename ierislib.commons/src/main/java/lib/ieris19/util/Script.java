package lib.ieris19.util;

/**
 * Script is a {@link FunctionalInterface} designed to implement a command. The intended use is to
 * utilize a lambda expression in order to implement the command method whenever a new command is
 * created. This way, code is linked with the command. If needed, it can also be implemented by a
 * class for more complex commands
 */
@FunctionalInterface public interface Script {
	/**
	 * A concrete implementation of this method is the body of a command object
	 *
	 * @param arguments arguments to be used by the command
	 */
	void execute(String[] arguments);
}

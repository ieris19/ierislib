package lib.ieris19.util;

/**
 * Script is a {@link FunctionalInterface} designed to implement an executable class. The intended use is to utilize a
 * lambda expression in order to implement the {@link #execute(String...) execute} method whenever an executable script
 * is needed. This way, code is linked with a variable that can be executed at will by methods in a predetermined
 * library. If needed, it can also be implemented by a class for more complex executables, as class implementations
 * allow for both longer, more.
 */
@FunctionalInterface
public interface Script {
	/**
	 * A concrete implementation of this method is the body of a command object
	 *
	 * @param arguments arguments to be used by the command
	 */
	void execute(String... arguments);
}

package lib.ieris19.util.cards;

/**
 * Thrown by the card to let some games know that Jokers have non-standard value
 */
public class JokerValueException extends RuntimeException {
	/**
	 * Constructs a <code>JokerValueException</code> with no detail message. This is the only option
	 * because the purpose of this exception is to signal that the card is a Joker, and their value
	 * needs to be handled
	 */
	public JokerValueException() {
		super();
	}
}

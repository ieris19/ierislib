package lib.ieris19.util.cards;

/**
 * Card is a class that will handle each card within a {@link Deck} <br> Cards are defined by 3
 * variables, the <code>value</code>, the <code>suit</code> and <code>isRed</code> <br>
 * <br>
 * <code>suit</code> will store which of the 4 groups of cards it belongs to, if it belongs to none
 * of the groups it will be marked as a <i>Joker</i> <br> To prevent from messing up, an Array
 * constant with suit names (including Jokers)<br> <br>
 * <code>value</code> should track which type of card we are dealing with, whether the card is an 8
 * or an ace is determined by this instance variable <br>
 * <br>
 *
 * @author Jason
 * @version 1.0
 * @see Deck
 */
public class Card {
	/**
	 * A constant containing the valid suits a card can be
	 */
	public static final String[] SUITS = {"Spades", "Diamonds", "Hearts", "Clubs", "Joker"};
	/**
	 * The suit that this card belongs to
	 */
	private final String suit;
	/**
	 * The card value which is a number between 0-13, 0 represents a Joker and the rest represent the
	 * cards in the following order: Ace, 2, 3, 4, 5, 6, 7, 8, 9, 10, Jack, Queen, King
	 */
	private final int value;
	/**
	 * Whether the card is face down or face up
	 */
	private boolean faceDown;

	/**
	 * This is the constructor for a card, it will set all values to predefined values, anything
	 * outside them will be interpreted as a Joker
	 *
	 * @param value <b>Value</b> should track which type of card we are dealing with
	 * @param suit  <b>Suit</b> will store which of the 4 groups of cards it belongs to, if it
	 *              belongs to none of the groups it will be marked as a <i>Joker</i>
	 */
	public Card(int value, String suit) {
		if (value > 0 && value <= 13) {
			this.value = value;
		} else {
			this.value = 0;
		}
		/*
		 The value of the suit is parsed through a chain of if else statements.
		 If none of the acceptable values is recognized ignoring case, it will be assumed a joker.
		 */
		if (suit.equalsIgnoreCase(SUITS[0]))
			this.suit = SUITS[0];
		else if (suit.equalsIgnoreCase(SUITS[1]))
			this.suit = SUITS[1];
		else if (suit.equalsIgnoreCase(SUITS[2]))
			this.suit = SUITS[2];
		else if (suit.equalsIgnoreCase(SUITS[3]) || suit.equalsIgnoreCase("Clovers"))
			this.suit = SUITS[3];
		else
			this.suit = SUITS[4];
	}

	/**
	 * Determine the <code>color</code> of the card, it will check the <code>suit</code> and
	 * automatically detect the <code>color</code>. This means, if it belongs to the <i>"Hearts"</i>
	 * or <i>"Diamonds"</i> suit, the card will be <i>"Red"</i> otherwise it will be considered
	 * black.
	 *
	 * @return Whether the card is <i>"Red"</i> <br> A true value means it is <i>"Red"</i>, a false
	 * value means it is <i>"Black"</i>
	 */
	public boolean isRed() {
		return suit.equals(SUITS[1]) || suit.equals(SUITS[2]);
	}

	/**
	 * This method is the keystone to the whole class, it will determine the full name of  a card for
	 * identification. <br>
	 *
	 * @return A name consist of the following elements: <code>cardName</code> + <i>of</i> +
	 * <code>suit</code>
	 */
	public String getCardName() {
		if (getCardValue().equals(SUITS[4]) || suit.equals(SUITS[4]))
			return SUITS[4];
		return getCardValue() + " of " + suit;
	}

	/**
	 * A method that will give a name to all special values (J, Q, K, Ace and Joker)
	 *
	 * @return A cards name, for special values, it will be its name (J, Q, K, Ace or Joker), for the
	 * rest it will convert the <code>value</code> to a string
	 */
	public String getCardValue() {
		return switch (value) {
			case 1 -> "Ace";
			case 2 -> "2";
			case 3 -> "3";
			case 4 -> "4";
			case 5 -> "5";
			case 6 -> "6";
			case 7 -> "7";
			case 8 -> "8";
			case 9 -> "9";
			case 10 -> "10";
			case 11 -> "J";
			case 12 -> "Q";
			case 13 -> "K";
			default -> SUITS[4];
		};
	}

	/**
	 * Flips the card around, meaning that if the card is face up, it will make it face down and vice
	 * versa
	 */
	public void flip() {
		this.faceDown = !faceDown;
	}

	/**
	 * This method compares to Card objects and lets you know whether the objects are equal
	 *
	 * @param obj An object to be compared with
	 *
	 * @return Whether the object is the same as the one provided
	 */
	@Override public boolean equals(Object obj) {
		if (obj == null)
			return false;
		else if (!(obj instanceof Card other))
			return false;
		else {
			return this.value == other.value && this.suit.equals(other.suit);
		}
	}

	/**
	 * This method returns all the information from a card, from the <i>color</i> to the
	 * <code>suit</code>, including the <code>value</code>
	 *
	 * @return A string with the following structure: "<code>color</code>" + "<code>value</code>" + of
	 * + "<code>suit</code>"
	 */
	@Override public String toString() {
		if (getCardName().equals(SUITS[4]) || getCardValue().equals(SUITS[4]))
			return SUITS[4];
		String toString = "";
		if (isRed())
			toString += "Red ";
		else
			toString += "Black ";
		toString += getCardName();
		return toString;
	}
}

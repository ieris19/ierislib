package lib.ieris19.util.cards;

import java.util.ArrayList;
import java.util.Collections;

/**
 * A deck is a collection of {@linkplain Card Cards}. It represents a traditional 52 card poker
 * deck.
 * <br> It contains as many <i>Jokers</i> as the user specifies in the constructor. After being
 * created, you can draw cards, put them back and shuffle them at any point. This class should be
 * managed by the "game" and not instructions should not be executed manually
 * <br> <br>
 *
 * @author Jason
 * @version 1.0
 * @see Card
 */
public class Deck {
	/**
	 * A collection of cards. It can represent a traditional 52 card deck, a shoe of multiple 52 card
	 * decks or if processed, a custom deck with custom amounts of cards
	 */
	private ArrayList<Card> deck;

	/**
	 * This constructor will generate one set of each <code>suit</code> of {@linkplain Card} for a
	 * standard deck of 52 cards. <br> You also need to specify the amount of <i>Jokers</i>, which can
	 * be any integer number, however, any non-positive value will be automatically ignored.
	 *
	 * @param amountOfJokers Number of Joker cards to add to the standard deck
	 */
	public Deck(int amountOfJokers) {
		deck = new ArrayList<>();
		createSuit(Card.SUITS[0]);
		createSuit(Card.SUITS[1]);
		createSuit(Card.SUITS[2]);
		createSuit(Card.SUITS[3]);
		for (int i = 0; i < amountOfJokers; i++) {
			deck.add(new Card(0, Card.SUITS[4]));
		}
	}

	/**
	 * Creates a shoe instead of a regular deck for bigger card games. This means that instead of a
	 * standard 52 card deck, it will create a bigger deck containing multiple of these and add the
	 * indicated amount of jokers at the end
	 *
	 * @param amountOfJokers Total amount of Jokers in the whole shoe
	 * @param numberOfDecks  Amount of 52 cards decks to add to the shoe
	 */
	public Deck(int amountOfJokers, int numberOfDecks) {
		deck = new ArrayList<>();
		for (int i = 0; i < numberOfDecks; i++) {
			createSuit(Card.SUITS[0]);
			createSuit(Card.SUITS[1]);
			createSuit(Card.SUITS[2]);
			createSuit(Card.SUITS[3]);
		}
		for (int i = 0; i < amountOfJokers; i++) {
			deck.add(new Card(0, Card.SUITS[4]));
		}
	}

	/**
	 * This method will generate a set of {@linkplain Card Cards} of the provided <code>suit</code>
	 *
	 * @param suit The <code>suit</code> you'd want to generate a set of
	 */
	private void createSuit(String suit) {
		for (int i = 1; i <= 13; i++) {
			deck.add(new Card(i, suit));
		}
	}

	/**
	 * This method will shuffle the deck, whatever cards remain, it will put them in a random order
	 */
	public void shuffle() {
		Collections.shuffle(deck);
	}

	/**
	 * This method will remove a certain card from the deck, it is meant for game setup to remove
	 * cards that are not needed for that specific game.
	 *
	 * @param cardName Represents the name of the relevant {@linkplain Card} element inside the
	 *                 {@linkplain Deck}
	 *
	 * @see Card#getCardName()
	 */
	private void removeCard(String cardName) {
		if (cardName == null)
			throw new IllegalArgumentException("Illegal Argument: cardName is null");
		for (int i = 0; i < deck.size(); i++) {
			if (deck.get(i).getCardName().equals(cardName)) {
				deck.remove(i);
				return;
			}
		}
	}

	/**
	 * A method used to draw a card from the deck. It will take a card from the top of the deck
	 *
	 * @return The card that was situated at the start of the Array
	 */
	public Card drawTop() {
		return deck.remove(0);
	}

	/**
	 * Identical to {@link Deck#drawTop()} except it will take the last card
	 *
	 * @return The card that was situated at the end of the Array
	 */
	public Card drawBottom() {
		return deck.remove(deck.size() - 1);
	}

	/**
	 * <code>toString</code> method will return the whole array with one card per line. It will
	 * include not only the <code>value</code> and <code>suit</code> but also the color.
	 *
	 * @return A string with the following structure
	 */
	@Override public String toString() {
		String toString = "";
		for (Card card : deck) {
			toString += card.toString() + '\n';
		}
		return toString;
	}
}

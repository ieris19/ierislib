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

package lib.ieris19.games.cards;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Queue;

/**
 * A deck is a collection of {@linkplain Card Cards}. It represents a traditional 52 card poker deck.
 * <br> It contains as many <i>Jokers</i> as the user specifies in the constructor. After being
 * created, you can draw cards, put them back and shuffle them at any point. This class should be managed by the "game"
 * and not instructions should not be executed manually
 * <br> <br>
 *
 * @see Card
 */
public class Deck {
	/**
	 * A collection of cards. It can represent a traditional 52 card deck, a shoe of multiple 52 card decks or if
	 * processed, a custom deck with custom amounts of cards
	 */
	private Queue<Card> deck;

	/**
	 * This constructor will generate one set of each <code>suit</code> of {@linkplain Card} for a standard deck of 52
	 * cards. <br> You also need to specify the amount of <i>Jokers</i>, which can be any integer number, however, any
	 * non-positive value will be automatically ignored.
	 *
	 * @param amountOfJokers Number of Joker cards to add to the standard deck
	 */
	public Deck(int amountOfJokers) {
		this(amountOfJokers, 1);
	}

	/**
	 * Creates a shoe instead of a regular deck for bigger card games. This means that instead of a standard 52 card deck,
	 * it will create a bigger deck containing multiple of these and add the indicated amount of jokers at the end
	 *
	 * @param amountOfJokers Total amount of Jokers in the whole shoe
	 * @param numberOfDecks  Amount of 52 cards decks to add to the shoe
	 */
	public Deck(int amountOfJokers, int numberOfDecks) {
		deck = new ArrayDeque<>((52 * numberOfDecks) + amountOfJokers);
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
		ArrayList<Card> temp = new ArrayList<>(deck);
		Collections.shuffle(temp);
		deck = new ArrayDeque<>(temp);
	}

	/**
	 * This method will remove a certain card from the deck, it is meant for game setup to remove cards that are not
	 * needed for that specific game.
	 *
	 * @param cardName Represents the name of the relevant {@linkplain Card} element inside the {@linkplain Deck}
	 *
	 * @see Card#getCardName()
	 */
	private void removeCard(String cardName) {
		if (cardName == null)
			throw new IllegalArgumentException("Illegal Argument: cardName is null");
		deck.removeIf(card -> card.getCardName().equals(cardName));
	}

	/**
	 * A method used to draw a card from the deck. It will take a card from the top of the deck
	 *
	 * @return The card that was situated at the start of the queue
	 */
	public Card drawTop() {
		return deck.poll();
	}

	/**
	 * <code>toString</code> method will return the whole queue with one card per line. It will
	 * include not only the <code>value</code> and <code>suit</code> but also the color.
	 *
	 * @return A string with the following structure
	 */
	@Override public String toString() {
		StringBuilder temp = new StringBuilder();
		for (Card card : deck) {
			temp.append(card.toString()).append('\n');
		}
		return temp.toString();
	}
}

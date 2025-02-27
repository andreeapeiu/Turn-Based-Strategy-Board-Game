package org.poo.players;

import org.poo.cards.Card;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Represents a deck of cards used by a player in the game.
 * This class is final to prevent subclassing.
 */
public final class Deck {

    private List<Card> cards = new ArrayList<>();

    /**
     * Default constructor.
     */
    public Deck() { }

    /**
     * Constructs a deck with a specified list of cards.
     *
     * @param cards The list of cards to initialize the deck.
     */
    public Deck(final List<Card> cards) {
        this.cards = new ArrayList<>(cards); // Deep copy to avoid external modification
    }

    /**
     * Copy constructor to create a deep copy of another deck.
     *
     * @param other The deck to copy.
     */
    public Deck(final Deck other) {
        this.cards = new ArrayList<>(other.cards); // Deep copy to avoid external modification
    }

    /**
     * Adds a card to the deck.
     *
     * @param card The card to add.
     */
    public void addCard(final Card card) {
        cards.add(card);
    }

    /**
     * Draws the first card from the deck.
     *
     * @return The first card in the deck, or null if the deck is empty.
     */
    public Card drawCard() {
        if (cards.isEmpty()) {
            return null;
        }
        return cards.remove(0);
    }

    /**
     * Shuffles the deck of cards.
     */
    public void shuffleDeck() {
        Collections.shuffle(cards);
    }

    /**
     * Resets the deck with a new list of cards and shuffles it.
     *
     * @param newCards The new list of cards to reset the deck with.
     */
    public void resetDeck(final List<Card> newCards) {
        cards.clear();
        cards.addAll(newCards);
        shuffleDeck();
    }

    /**
     * Returns the number of cards left in the deck.
     *
     * @return The size of the deck.
     */
    public int getDeckSize() {
        return cards.size();
    }

    /**
     * Checks if the deck is empty.
     *
     * @return True if the deck is empty; false otherwise.
     */
    public boolean isEmpty() {
        return cards.isEmpty();
    }

    /**
     * Gets the list of cards in the deck.
     *
     * @return A list of cards in the deck.
     */
    public List<Card> getCards() {
        return new ArrayList<>(cards); // Return a copy to avoid external modification
    }

    /**
     * Sets the cards in the deck.
     *
     * @param newCards The new list of cards to set.
     */
    public void setCards(final List<Card> newCards) {
        this.cards = new ArrayList<>(newCards);
    }

    /**
     * Clears all cards from the deck.
     */
    public void clear() {
        cards.clear();
    }

    /**
     * Adds a list of cards to the deck.
     *
     * @param newCards The list of cards to add.
     */
    public void addAll(final List<Card> newCards) {
        cards.addAll(newCards);
    }

    /**
     * Removes a specific card from the deck.
     *
     * @param card The card to remove.
     */
    public void remove(final Card card) {
        cards.remove(card);
    }

    /**
     * Removes a card from the deck at the specified index.
     *
     * @param index The index of the card to remove.
     */
    public void remove(final int index) {
        cards.remove(index);
    }

    /**
     * Gets a card at a specific index.
     *
     * @param index The index of the card.
     * @return The card at the specified index.
     */
    public Card get(final int index) {
        return cards.get(index);
    }

    /**
     * Adds a single card to the deck.
     *
     * @param card The card to add.
     */
    public void add(final Card card) {
        cards.add(card);
    }
}

package org.poo.players;

import org.poo.cards.Card;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Represents a player's hand in the game, holding a list of cards.
 */
public class Hand {

    private final List<Card> cards = new ArrayList<>();

    /**
     * Default constructor for creating an empty hand.
     */
    public Hand() { }

    /**
     * Copy constructor to create a deep copy of another hand.
     *
     * @param other the hand to copy from
     */
    public Hand(final Hand other) {
        for (Card card : other.cards) {
            cards.add(new Card(card));
        }
    }

    /**
     * Adds a card to the hand.
     *
     * @param card the card to add
     */
    public void addCard(final Card card) {
        cards.add(card);
    }

    /**
     * Removes a card from the hand at the specified index.
     *
     * @param index the index of the card to remove
     */
    public void removeCard(final int index) {
        if (index >= 0 && index < cards.size()) {
            cards.remove(index);
        }
    }

    /**
     * Retrieves a card from the hand at the specified index.
     *
     * @param index the index of the card to retrieve
     * @return the card at the specified index, or null if the index is invalid
     */
    public Card getCard(final int index) {
        return index >= 0 && index < cards.size() ? cards.get(index) : null;
    }

    /**
     * Returns a list of playable cards based on the given mana limit.
     *
     * @param currentMana the current mana available
     * @return an unmodifiable list of playable cards
     */
    public List<Card> getPlayableCards(final int currentMana) {
        List<Card> playableCards = new ArrayList<>();
        for (Card card : cards) {
            if (card.getMana() <= currentMana) {
                playableCards.add(card);
            }
        }
        return Collections.unmodifiableList(playableCards);
    }

    /**
     * Returns the number of cards in the hand.
     *
     * @return the size of the hand
     */
    public int getSize() {
        return cards.size();
    }

    /**
     * Clears all cards from the hand.
     */
    public void clearHand() {
        cards.clear();
    }

    /**
     * Returns the list of cards in the hand.
     *
     * @return the list of cards
     */
    public List<Card> getCards() {
        return cards;
    }
}

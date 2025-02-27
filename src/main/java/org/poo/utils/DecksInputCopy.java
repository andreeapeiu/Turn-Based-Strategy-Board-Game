package org.poo.utils;

import org.poo.fileio.DecksInput;
import java.util.ArrayList;
import java.util.List;

/**
 * A copy of DecksInput containing the number of cards per deck,
 * the number of decks, and a list of copied decks.
 */
public final class DecksInputCopy {
    private final int nrCardsInDeck;
    private final int nrDecks;
    private final List<List<CardInputCopy>> decks;

    /**
     * Constructs a DecksInputCopy from an existing DecksInput instance.
     *
     * @param original the original DecksInput to copy
     */
    public DecksInputCopy(final DecksInput original) {
        this.nrCardsInDeck = original.getNrCardsInDeck();
        this.nrDecks = original.getNrDecks();
        this.decks = new ArrayList<>();
        for (var deck : original.getDecks()) {
            List<CardInputCopy> copiedDeck = new ArrayList<>();
            for (var card : deck) {
                copiedDeck.add(new CardInputCopy(card));
            }
            this.decks.add(copiedDeck);
        }
    }

    /**
     * Gets the number of cards in each deck.
     *
     * @return the number of cards in each deck
     */
    public int getNrCardsInDeck() {
        return nrCardsInDeck;
    }

    /**
     * Gets the number of decks.
     *
     * @return the number of decks
     */
    public int getNrDecks() {
        return nrDecks;
    }

    /**
     * Gets the list of decks, each represented as a list of CardInputCopy instances.
     *
     * @return the list of decks
     */
    public List<List<CardInputCopy>> getDecks() {
        return decks;
    }
}

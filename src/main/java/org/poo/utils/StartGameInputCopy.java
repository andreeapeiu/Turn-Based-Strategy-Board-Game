package org.poo.utils;

import org.poo.fileio.StartGameInput;

/**
 * A copy of the StartGameInput class containing information about the game's starting settings.
 */
public final class StartGameInputCopy {
    private final int playerOneDeckIdx;
    private final int playerTwoDeckIdx;
    private final int shuffleSeed;
    private final CardInputCopy playerOneHero;
    private final CardInputCopy playerTwoHero;
    private final int startingPlayer;

    /**
     * Constructs a StartGameInputCopy from an existing StartGameInput instance.
     *
     * @param original the original StartGameInput to copy
     */
    public StartGameInputCopy(final StartGameInput original) {
        this.playerOneDeckIdx = original.getPlayerOneDeckIdx();
        this.playerTwoDeckIdx = original.getPlayerTwoDeckIdx();
        this.shuffleSeed = original.getShuffleSeed();
        this.playerOneHero = new CardInputCopy(original.getPlayerOneHero());
        this.playerTwoHero = new CardInputCopy(original.getPlayerTwoHero());
        this.startingPlayer = original.getStartingPlayer();
    }

    /**
     * Gets the deck index of player one.
     *
     * @return the index of player one's deck
     */
    public int getPlayerOneDeckIdx() {
        return playerOneDeckIdx;
    }

    /**
     * Gets the deck index of player two.
     *
     * @return the index of player two's deck
     */
    public int getPlayerTwoDeckIdx() {
        return playerTwoDeckIdx;
    }

    /**
     * Gets the shuffle seed for deck randomization.
     *
     * @return the shuffle seed
     */
    public int getShuffleSeed() {
        return shuffleSeed;
    }

    /**
     * Gets the hero card of player one.
     *
     * @return a CardInputCopy instance representing player one's hero
     */
    public CardInputCopy getPlayerOneHero() {
        return playerOneHero;
    }

    /**
     * Gets the hero card of player two.
     *
     * @return a CardInputCopy instance representing player two's hero
     */
    public CardInputCopy getPlayerTwoHero() {
        return playerTwoHero;
    }

    /**
     * Gets the starting player index.
     *
     * @return the starting player index
     */
    public int getStartingPlayer() {
        return startingPlayer;
    }
}

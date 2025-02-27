package org.poo.utils;

import org.poo.fileio.Input;
import java.util.ArrayList;
import java.util.List;

/**
 * A copy of the Input class that contains player deck information and game data.
 */
public final class InputCopy {
    private final DecksInputCopy playerOneDecks;
    private final DecksInputCopy playerTwoDecks;
    private final List<GameInputCopy> games;

    /**
     * Constructs an InputCopy from an existing Input instance.
     *
     * @param original the original Input to copy
     */
    public InputCopy(final Input original) {
        this.playerOneDecks = new DecksInputCopy(original.getPlayerOneDecks());
        this.playerTwoDecks = new DecksInputCopy(original.getPlayerTwoDecks());
        this.games = new ArrayList<>();
        for (var game : original.getGames()) {
            this.games.add(new GameInputCopy(game));
        }
    }

    /**
     * Gets the deck information for player one.
     *
     * @return a DecksInputCopy instance representing player one's decks
     */
    public DecksInputCopy getPlayerOneDecks() {
        return playerOneDecks;
    }

    /**
     * Gets the deck information for player two.
     *
     * @return a DecksInputCopy instance representing player two's decks
     */
    public DecksInputCopy getPlayerTwoDecks() {
        return playerTwoDecks;
    }

    /**
     * Gets the list of games included in the input.
     *
     * @return a list of GameInputCopy instances representing the games
     */
    public List<GameInputCopy> getGames() {
        return games;
    }

    /**
     * Gets the number of games in the input.
     *
     * @return the total number of games
     */
    public int getNumberOfGames() {
        return games.size();
    }
}

package org.poo.utils;

import org.poo.fileio.GameInput;
import java.util.ArrayList;
import java.util.List;

/**
 * A copy of GameInput containing the initial game settings and a list of actions.
 */
public final class GameInputCopy {
    private final StartGameInputCopy startGame;
    private final List<ActionsInputCopy> actions;

    /**
     * Constructs a GameInputCopy from an existing GameInput instance.
     *
     * @param original the original GameInput to copy
     */
    public GameInputCopy(final GameInput original) {
        this.startGame = new StartGameInputCopy(original.getStartGame());
        this.actions = new ArrayList<>();
        for (var action : original.getActions()) {
            this.actions.add(new ActionsInputCopy(action));
        }
    }

    /**
     * Gets the initial game settings.
     *
     * @return a StartGameInputCopy representing the initial game settings
     */
    public StartGameInputCopy getStartGame() {
        return startGame;
    }

    /**
     * Gets the list of actions in the game.
     *
     * @return a list of ActionsInputCopy instances representing the actions in the game
     */
    public List<ActionsInputCopy> getActions() {
        return actions;
    }
}

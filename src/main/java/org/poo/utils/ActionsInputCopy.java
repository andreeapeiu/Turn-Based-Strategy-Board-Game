package org.poo.utils;

import org.poo.fileio.ActionsInput;
import org.poo.fileio.Coordinates;
import org.poo.cards.Card;
import org.poo.players.Player;

/**
 * A copy of the ActionsInput class, storing all necessary attributes from the original input.
 */
public class ActionsInputCopy {

    private static final int FRONT_ROW_PLAYER_1 = 2;
    private static final int BACK_ROW_PLAYER_1 = 3;
    private static final int FRONT_ROW_PLAYER_2 = 1;
    private static final int BACK_ROW_PLAYER_2 = 0;

    private final String command;
    private final int handIdx;
    private final Coordinates cardAttacker;
    private final Coordinates cardAttacked;
    private final int affectedRow;
    private final int playerIdx;
    private final int x;
    private final int y;

    /**
     * Constructs an ActionsInputCopy from an existing ActionsInput instance.
     *
     * @param original the original ActionsInput to copy
     */
    public ActionsInputCopy(final ActionsInput original) {
        this.command = original.getCommand();
        this.handIdx = original.getHandIdx();
        this.cardAttacker = original.getCardAttacker();
        this.cardAttacked = original.getCardAttacked();
        this.affectedRow = original.getAffectedRow();
        this.playerIdx = original.getPlayerIdx();
        this.x = original.getX();
        this.y = original.getY();
    }

    /**
     * Gets the command of the action.
     *
     * @return the command string
     */
    public String getCommand() {
        return command;
    }

    /**
     * Gets the hand index.
     *
     * @return the hand index
     */
    public int getHandIdx() {
        return handIdx;
    }

    /**
     * Gets the coordinates of the attacking card.
     *
     * @return the coordinates of the attacking card
     */
    public Coordinates getCardAttacker() {
        return cardAttacker;
    }

    /**
     * Gets the coordinates of the attacked card.
     *
     * @return the coordinates of the attacked card
     */
    public Coordinates getCardAttacked() {
        return cardAttacked;
    }

    /**
     * Gets the affected row.
     *
     * @return the affected row
     */
    public int getAffectedRow() {
        return affectedRow;
    }

    /**
     * Gets the player index.
     *
     * @return the player index
     */
    public int getPlayerIdx() {
        return playerIdx;
    }

    /**
     * Gets the x-coordinate.
     *
     * @return the x-coordinate
     */
    public int getX() {
        return x;
    }

    /**
     * Gets the y-coordinate.
     *
     * @return the y-coordinate
     */
    public int getY() {
        return y;
    }

    /**
     * Determines the target row on the board for a specific card based on the player and card type.
     *
     * @param cardToPlace   the card to place
     * @param currentPlayer the current player
     * @return the target row index, or -1 if the card type is unrecognized
     */
    public int getTargetRow(final Card cardToPlace, final Player currentPlayer) {
        int playerId = currentPlayer.getPlayerIdx();

        if (playerId == 1) {
            switch (cardToPlace.getName()) {
                case "Sentinel":
                case "Berserker":
                case "The Cursed One":
                case "Disciple":
                    return BACK_ROW_PLAYER_1;
                case "Goliath":
                case "Warden":
                case "The Ripper":
                case "Miraj":
                    return FRONT_ROW_PLAYER_1;
                default:
                    return -1;
            }
        } else if (playerId == 2) {
            switch (cardToPlace.getName()) {
                case "Sentinel":
                case "Berserker":
                case "The Cursed One":
                case "Disciple":
                    return BACK_ROW_PLAYER_2;
                case "Goliath":
                case "Warden":
                case "The Ripper":
                case "Miraj":
                    return FRONT_ROW_PLAYER_2;
                default:
                    return -1;
            }
        }

        return -1;
    }
}

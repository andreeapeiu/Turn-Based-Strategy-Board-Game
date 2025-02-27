package org.poo.fileio;

public final class ActionHelper {

    private final ActionsInput action;
    private final int cardIndex;
    private final int row;

    /**
     * Constructor for ActionHelper, which accepts an ActionsInput and additional values.
     *
     * @param action    the action input
     * @param cardIndex the index of the card
     * @param row       the row of the action
     */
    public ActionHelper(final ActionsInput action, final int cardIndex, final int row) {
        this.action = action;
        this.cardIndex = cardIndex;
        this.row = row;
    }

    /**
     * Gets the ActionsInput.
     *
     * @return the ActionsInput instance
     */
    public ActionsInput getAction() {
        return action;
    }

    /**
     * Gets the index of the card.
     *
     * @return the card index
     */
    public int getCardIndex() {
        return cardIndex;
    }

    /**
     * Gets the row of the action.
     *
     * @return the row number
     */
    public int getRow() {
        return row;
    }

    /**
     * Retrieves the command from the ActionsInput.
     *
     * @return the command as a String
     */
    public String getCommand() {
        return action.getCommand();
    }

    /**
     * Retrieves the player index from the ActionsInput.
     *
     * @return the player index
     */
    public int getPlayerIdx() {
        return action.getPlayerIdx();
    }
}

package org.poo.players;

import org.poo.cards.Card;
import com.fasterxml.jackson.databind.node.ObjectNode;

import java.util.ArrayList;
import java.util.List;

/**
 * Represents the game board where cards are placed.
 */
public class GameBoard {

    private static final int ROW_COUNT = 4;
    private static final int MAX_CARDS_PER_ROW = 5;

    private final List<List<Card>> board;

    /**
     * Constructs a new GameBoard with predefined rows.
     */
    public GameBoard() {
        board = new ArrayList<>(ROW_COUNT);
        for (int i = 0; i < ROW_COUNT; i++) {
            board.add(new ArrayList<>(MAX_CARDS_PER_ROW));
        }
    }

    /**
     * Adds a card to the specified row.
     *
     * @param rowIndex the index of the row.
     * @param card     the card to add.
     */
    public void addCardToRow(final int rowIndex, final Card card) {
        board.get(rowIndex).add(card);
    }

    /**
     * Gets the cards in the specified row.
     *
     * @param rowIndex the index of the row.
     * @return the list of cards in the row.
     */
    public List<Card> getRow(final int rowIndex) {
        return board.get(rowIndex);
    }

    /**
     * Checks if a card can be placed on the specified row.
     *
     * @param rowIndex the index of the row.
     * @return true if a card can be placed; false otherwise.
     */
    public boolean canPlaceCardOnRow(final int rowIndex) {
        return board.get(rowIndex).size() < MAX_CARDS_PER_ROW;
    }

    /**
     * Places a card on the specified row if there is space.
     *
     * @param rowIndex the index of the row.
     * @param card     the card to place.
     * @param output   the output node to store messages if placement fails.
     * @return true if the card was placed; false otherwise.
     */
    public boolean placeCard(final int rowIndex, final Card card, final ObjectNode output) {

        List<Card> row = getRow(rowIndex);

        if (row.size() >= MAX_CARDS_PER_ROW) {
            output.put("command", "placeCard");
            output.put("HandIdx", rowIndex);
            output.put("message", "Cannot place card on table since row is full");
            return false;
        } else {
            row.add(card);
            return true;
        }
    }

    /**
     * Removes a card from the specified position in the row.
     *
     * @param rowIndex  the index of the row.
     * @param cardIndex the index of the card within the row.
     */
    public void removeCardFromRow(final int rowIndex, final int cardIndex) {
        if (rowIndex < board.size()) {
            if (cardIndex < board.get(rowIndex).size()) {
                board.get(rowIndex).remove(cardIndex);
            }
        }
    }

    /**
     * Gets a card from the specified position in the row.
     *
     * @param rowIndex  the index of the row.
     * @param cardIndex the index of the card within the row.
     * @return the card at the specified position, or null if not found.
     */
    public Card getCardFromRow(final int rowIndex, final int cardIndex) {
        if (rowIndex < board.size()) {
            if (cardIndex < board.get(rowIndex).size()) {
                return board.get(rowIndex).get(cardIndex);
            }
        }
        return null;
    }

    /**
     * Resets the game board, clearing all cards from each row.
     */
    public void resetBoard() {
        for (List<Card> row : board) {
            row.clear();
        }
    }

    /**
     * Returns the game board as a list of rows.
     *
     * @return the list of rows in the game board.
     */
    public List<List<Card>> getBoard() {
        return board;
    }
}

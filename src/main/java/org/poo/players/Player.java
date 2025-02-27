package org.poo.players;

import org.poo.cards.Hero;
import org.poo.cards.Card;
import com.fasterxml.jackson.databind.node.ObjectNode;

/**
 * Represents a player in the game, holding details like hand, deck, hero, and game statistics.
 */
public class Player {

    private static final int MAX_MANA = 10;
    private static final int INITIAL_HEALTH = 30;

    private int playerIdx;
    private Hand hand;
    private Deck deck;
    private Hero hero;
    private int mana = 1;
    private int maxMana = MAX_MANA;
    private boolean isTurn;
    private int health = INITIAL_HEALTH;
    private int gamesWon;
    private int gamesPlayed;

    /**
     * Constructs a new Player with a specified hand, deck, hero, and index.
     *
     * @param hand      the hand of the player
     * @param deck      the deck of the player
     * @param hero      the hero of the player
     * @param playerIdx the index of the player
     */
    public Player(final Hand hand, final Deck deck, final Hero hero, final int playerIdx) {
        this.hand = hand;
        this.deck = deck;
        this.hero = hero;
        this.playerIdx = playerIdx;
    }

    /**
     * Constructs a new Player with specified attributes.
     *
     * @param hand         the hand of the player
     * @param deck         the deck of the player
     * @param hero         the hero of the player
     * @param isTurn       whether it's this player's turn
     * @param gamesWon     number of games won by this player
     * @param gamesPlayed  number of games played by this player
     * @param playerIdx    the index of the player
     */
    public Player(final Hand hand, final Deck deck, final Hero hero,
                  final boolean isTurn, final int gamesWon,
                  final int gamesPlayed, final int playerIdx) {
        this.hand = hand;
        this.deck = deck;
        this.hero = hero;
        this.isTurn = isTurn;
        this.gamesWon = gamesWon;
        this.gamesPlayed = gamesPlayed;
        this.playerIdx = playerIdx;
    }

    /**
     * Copy constructor to create a deep copy of another player.
     *
     * @param otherPlayer the player to copy from
     */
    public Player(final Player otherPlayer) {
        this.hand = new Hand(otherPlayer.hand);
        this.deck = new Deck(otherPlayer.deck);
        this.hero = new Hero(otherPlayer.hero);
        this.mana = otherPlayer.mana;
        this.maxMana = otherPlayer.maxMana;
        this.isTurn = otherPlayer.isTurn;
        this.health = otherPlayer.health;
        this.gamesWon = otherPlayer.gamesWon;
        this.gamesPlayed = otherPlayer.gamesPlayed;
        this.playerIdx = otherPlayer.playerIdx;
    }

    /**
     * Checks if it is the player's turn.
     *
     * @return true if it is the player's turn, false otherwise.
     */
    public boolean isTurn() {
        return isTurn;
    }

    /**
     * Sets whether it is the player's turn.
     *
     * @param setisTurn true if it is the player's turn, false otherwise.
     */
    public void setTurn(final boolean setisTurn) {
        this.isTurn = setisTurn;
    }

    /**
     * Gets the player's current mana.
     *
     * @return the current mana.
     */
    public int getMana() {
        return mana;
    }

    /**
     * Sets the player's mana.
     *
     * @param mana the new mana value.
     */
    public void setMana(final int mana) {
        this.mana = mana;
    }

    /**
     * Gets the player's index.
     *
     * @return the player's index.
     */
    public int getPlayerIdx() {
        return playerIdx;
    }

    /**
     * Sets the player's index.
     *
     * @param playerIdx the new index value.
     */
    public void setPlayerIdx(final int playerIdx) {
        this.playerIdx = playerIdx;
    }

    /**
     * Gets the player's health.
     *
     * @return the current health of the player.
     */
    public int getHealth() {
        return health;
    }

    /**
     * Sets the player's health.
     *
     * @param health the new health value.
     */
    public void setHealth(final int health) {
        this.health = health;
    }

    /**
     * Gets the number of games won by the player.
     *
     * @return the number of games won.
     */
    public int getGamesWon() {
        return gamesWon;
    }

    /**
     * Sets the number of games won by the player.
     *
     * @param gamesWon the number of games won.
     */
    public void setGamesWon(final int gamesWon) {
        this.gamesWon = gamesWon;
    }

    /**
     * Gets the number of games played by the player.
     *
     * @return the number of games played.
     */
    public int getGamesPlayed() {
        return gamesPlayed;
    }

    /**
     * Sets the number of games played by the player.
     *
     * @param gamesPlayed the new number of games played.
     */
    public void setGamesPlayed(final int gamesPlayed) {
        this.gamesPlayed = gamesPlayed;
    }

    /**
     * Gets the player's hand.
     *
     * @return the player's hand.
     */
    public Hand getHand() {
        return hand;
    }

    /**
     * Sets the player's hand.
     *
     * @param hand the new hand.
     */
    public void setHand(final Hand hand) {
        this.hand = hand;
    }

    /**
     * Gets the player's deck.
     *
     * @return the player's deck.
     */
    public Deck getDeck() {
        return deck;
    }

    /**
     * Sets the player's deck.
     *
     * @param deck the new deck.
     */
    public void setDeck(final Deck deck) {
        this.deck = deck;
    }

    /**
     * Gets the player's hero.
     *
     * @return the player's hero.
     */
    public Hero getHero() {
        return hero;
    }

    /**
     * Sets the player's hero.
     *
     * @param hero the new hero.
     */
    public void setHero(final Hero hero) {
        this.hero = hero;
    }

    /**
     * Gets the maximum mana the player can have.
     *
     * @return the maximum mana value.
     */
    public int getMaxMana() {
        return maxMana;
    }

    /**
     * Sets the maximum mana the player can have.
     *
     * @param maxMana the new maximum mana value.
     */
    public void setMaxMana(final int maxMana) {
        this.maxMana = maxMana;
    }

    /**
     * Draws a card from the deck and adds it to the player's hand.
     */
    public void drawCard() {
        Card card = deck.drawCard();
        if (card != null) {
            hand.addCard(card);
        }
    }

    /**
     * Plays a card from the hand at the specified index onto the specified row of the game board.
     *
     * @param index      the index of the card in the hand
     * @param row        the row on the board to place the card
     * @param outputNode the output node for any resulting messages
     * @param board      the game board
     */
    public void playCard(final int index, final int row,
                         final ObjectNode outputNode, final GameBoard board) {
        Card card = hand.getCard(index);
        if (card == null) {
            outputNode.put("error", "Card not found in hand.");
            return;
        }
        if (board.placeCard(row, card, outputNode)) {
            decreaseMana(card.getMana());
            hand.removeCard(index);
        }
    }

    /**
     * Increases the player's mana based on the round number.
     *
     * @param roundNumber the current round number.
     */
    public void increaseManaForRound(final int roundNumber) {
        this.mana = Math.min(this.mana + roundNumber, MAX_MANA);
    }

    /**
     * Decreases the player's mana by the specified amount.
     *
     * @param manaCost the amount of mana to decrease.
     */
    public void decreaseMana(final int manaCost) {
        this.mana -= manaCost;
    }

    /**
     * Resets the player to the initial game state.
     */
    public void resetPlayer() {
        hand.clearHand();
        mana = 1;
        health = INITIAL_HEALTH;
    }
}

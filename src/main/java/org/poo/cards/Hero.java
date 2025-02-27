package org.poo.cards;

import com.fasterxml.jackson.databind.node.ObjectNode;
import org.poo.players.GameBoard;
import org.poo.players.Player;
import java.util.List;
import java.util.ArrayList;

public class Hero extends Card {

    private static final int PLAYER_ONE_IDX = 1;
    private static final int ROW_FRONT_PLAYER_ONE = 0;
    private static final int ROW_BACK_PLAYER_ONE = 1;
    private static final int ROW_FRONT_PLAYER_TWO = 2;
    private static final int ROW_BACK_PLAYER_TWO = 3;

    private String heroType; // Lord Royce, Empress Thorina, King Mudface, General Kocioraw
    private String heroAbility; // special ability of the hero

    /**
     * Default constructor for Hero.
     */
    public Hero() {
    }

    /**
     * Parameterized constructor for Hero.
     *
     * @param mana         the mana cost of the hero
     * @param health       the health of the hero
     * @param attackDamage the attack damage of the hero
     * @param description  the description of the hero
     * @param name         the name of the hero
     * @param colors       the colors of the hero
     * @param type         the type of the hero
     * @param heroType     the specific type of hero (e.g., Lord Royce, Empress Thorina)
     * @param heroAbility  the special ability of the hero
     */
    public Hero(final int mana, final int health, final int attackDamage, final String description,
                final String name, final ArrayList<String> colors, final String type,
                final String heroType, final String heroAbility) {
        super(mana, health, attackDamage, description, name, colors, type);
        this.heroType = heroType;
        this.heroAbility = heroAbility;
    }

    /**
     * Copy constructor for Hero.
     *
     * @param other the Hero object to copy
     */
    public Hero(final Hero other) {
        super(other.getMana(), other.getHealth(), other.getAttackDamage(), other.getDescription(),
                other.getName(), other.getColors(), other.getType());
        this.heroType = other.getHeroType();
        this.heroAbility = other.getHeroAbility();
    }

    /**
     * Uses the hero's special ability on a specified row of the game board,
     * affecting either the enemy or the current player.
     *
     * @param hero        the hero using the ability
     * @param output      the output node for reporting errors or success
     * @param affectedRow the row number that the ability affects
     * @param board       the game board
     * @param playerOne   player one in the game
     * @param playerTwo   player two in the game
     */
    public void usePower(final Hero hero, final ObjectNode output, final int affectedRow,
                         final GameBoard board, final Player playerOne, final Player playerTwo) {
        Player currentPlayer = playerOne.isTurn() ? playerOne : playerTwo;

        // Check if the player has enough mana to use the hero's ability
        if (currentPlayer.getMana() < hero.getMana()) {
            output.put("command", "useHeroAbility");
            output.put("affectedRow", affectedRow);
            output.put("error", "Not enough mana to use hero's ability.");
            return;
        }

        // Check if the hero has already used their ability this turn
        if (hero.getHasUsedAbility(hero)) {
            output.put("command", "useHeroAbility");
            output.put("affectedRow", affectedRow);
            output.put("error", "Hero has already attacked this turn.");
            return;
        }

        // Checks for the hero type and ensures the correct row is selected based on the type
        if (hero.getName().equals("Lord Royce") || hero.getName().equals("Empress Thorina")) {
            if (currentPlayer.getPlayerIdx() == PLAYER_ONE_IDX) {
                if (affectedRow == ROW_FRONT_PLAYER_TWO || affectedRow == ROW_BACK_PLAYER_TWO) {
                    output.put("command", "useHeroAbility");
                    output.put("affectedRow", affectedRow);
                    output.put("error", "Selected row does not belong to the enemy.");
                    return;
                }
            } else {
                if (affectedRow == ROW_FRONT_PLAYER_ONE || affectedRow == ROW_BACK_PLAYER_ONE) {
                    output.put("command", "useHeroAbility");
                    output.put("affectedRow", affectedRow);
                    output.put("error", "Selected row does not belong to the enemy.");
                    return;
                }
            }
        }

        if (hero.getName().equals("General Kocioraw") || hero.getName().equals("King Mudface")) {
            if (currentPlayer.getPlayerIdx() == PLAYER_ONE_IDX) {
                if (affectedRow == ROW_FRONT_PLAYER_ONE || affectedRow == ROW_BACK_PLAYER_ONE) {
                    output.put("command", "useHeroAbility");
                    output.put("affectedRow", affectedRow);
                    output.put("error", "Selected row does not belong to the current player.");
                    return;
                }
            } else {
                if (affectedRow == ROW_FRONT_PLAYER_TWO || affectedRow == ROW_BACK_PLAYER_TWO) {
                    output.put("command", "useHeroAbility");
                    output.put("affectedRow", affectedRow);
                    output.put("error", "Selected row does not belong to the current player.");
                    return;
                }
            }
        }

        switch (hero.getName()) {
            case "Lord Royce":
                lordRoyceAbility(affectedRow, board);
                break;
            case "Empress Thorina":
                empressThorinaAbility(affectedRow, board);
                break;
            case "King Mudface":
                kingMudfaceAbility(affectedRow, board);
                break;
            case "General Kocioraw":
                generalKociorawAbility(affectedRow, board);
                break;
            default:
                break;
        }

        currentPlayer.setMana(currentPlayer.getMana() - hero.getMana());
        hero.setHasUsedAbility(true);
    }

    /**
     * Applies General Kocioraw's ability, increasing the attack damage by 1
     * for all cards in the specified row.
     *
     * @param affectedRow the row affected by the ability
     * @param board       the game board
     */
    private void generalKociorawAbility(final int affectedRow, final GameBoard board) {
        List<Card> row = board.getRow(affectedRow);
        for (Card card : row) {
            card.setAttackDamage(card.getAttackDamage() + 1);
        }
    }

    /**
     * Applies Lord Royce's ability, freezing all cards in the specified row.
     *
     * @param affectedRow the row affected by the ability
     * @param board       the game board
     */
    private void lordRoyceAbility(final int affectedRow, final GameBoard board) {
        List<Card> row = board.getRow(affectedRow);
        for (Card card : row) {
            card.freeze(card);
        }
    }

    /**
     * Applies Empress Thorina's ability, destroying the card with the highest health
     * in the specified row. If multiple cards have the same highest health,
     * the leftmost card is destroyed.
     *
     * @param affectedRow the row affected by the ability
     * @param board       the game board
     */
    private void empressThorinaAbility(final int affectedRow, final GameBoard board) {
        List<Card> row = board.getRow(affectedRow);
        Card cardToDestroy = null;
        int maxHealth = 0;
        for (Card card : row) {
            if (card.getHealth() > maxHealth) {
                maxHealth = card.getHealth();
                cardToDestroy = card;
            }
        }
        if (cardToDestroy != null) {
            row.remove(cardToDestroy);
        }
    }

    /**
     * Applies King Mudface's ability, increasing the health by 1
     * for all cards in the specified row.
     *
     * @param affectedRow the row affected by the ability
     * @param board       the game board
     */
    private void kingMudfaceAbility(final int affectedRow, final GameBoard board) {
        List<Card> row = board.getRow(affectedRow);
        for (Card card : row) {
            card.setHealth(card.getHealth() + 1);
        }
    }

    /**
     * Returns the hero type.
     *
     * @return hero type
     */
    public String getHeroType() {
        return heroType;
    }

    /**
     * Returns the hero ability.
     *
     * @return hero ability
     */
    public String getHeroAbility() {
        return heroAbility;
    }
}

package org.poo.cards;

import java.util.ArrayList;

public class Minion extends Card {

    private boolean isFrozen;
    private boolean hasAttacked;
    private int row = -1; // pe ce rand se va pune cartea
    private boolean hasAbilities;

    /**
     * Default constructor for Minion.
     */
    public Minion() {
    }

    /**
     * Parameterized constructor for Minion.
     *
     * @param mana         the mana cost of the minion
     * @param health       the health of the minion
     * @param attackDamage the attack damage of the minion
     * @param description  the description of the minion
     * @param name         the name of the minion
     * @param colors       the colors of the minion
     * @param type         the type of the minion
     * @param isFrozen     the frozen status of the minion
     * @param hasAttacked  the attack status of the minion
     */
    public Minion(final int mana, final int health, final int attackDamage,
                  final String description,
                  final String name, final ArrayList<String> colors, final String type,
                  final boolean isFrozen, final boolean hasAttacked) {
        super(mana, health, attackDamage, description, name, colors, type);
        this.isFrozen = false;
        this.hasAttacked = false;
        this.hasAbilities = checkHasAbilities(name);
    }

    /**
     * Checks if the minion has specific abilities based on its name.
     *
     * @param name the name of the minion
     * @return true if the minion has abilities, false otherwise
     */
    private boolean checkHasAbilities(final String name) {
        return name.equals("The Ripper") || name.equals("Miraj")
                || name.equals("The Cursed One") || name.equals("Disciple");
    }

    /**
     * Returns whether the minion has abilities.
     *
     * @return true if the minion has abilities, false otherwise
     */
    public final boolean hasAbilities() {
        return hasAbilities;
    }

    /**
     * Freezes the minion, preventing it from attacking.
     */
    public final void freeze() {
        this.isFrozen = true;
    }

    /**
     * Unfreezes the minion, allowing it to attack.
     */
    public final void unfreeze() {
        this.isFrozen = false;
    }

    /**
     * Sets the row of the minion.
     *
     * @param row the row to set for the minion
     */
    public final void setRow(final int row) {
        this.row = row;
    }

    /**
     * Gets the row of the minion.
     *
     * @return the row of the minion
     */
    public final int getRow() {
        return row;
    }

    /**
     * Checks if the minion is frozen.
     *
     * @return true if the minion is frozen, false otherwise
     */
    public final boolean isFrozen() {
        return isFrozen;
    }

    /**
     * Checks if the minion has attacked.
     *
     * @return true if the minion has attacked, false otherwise
     */
    public final boolean hasAttacked() {
        return hasAttacked;
    }

    /**
     * Sets whether the minion has attacked.
     *
     * @param hasAttacked the attack status to set
     */
    public final void setHasAttacked(final boolean hasAttacked) {
        this.hasAttacked = hasAttacked;
    }
}

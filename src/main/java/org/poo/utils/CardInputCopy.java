package org.poo.utils;

import org.poo.fileio.CardInput;
import java.util.ArrayList;
import java.util.List;

/**
 * A copy of CardInput containing card properties such as mana,
 * attack damage, health, description, colors, and name.
 */
public final class CardInputCopy {
    private final int mana;
    private final int attackDamage;
    private final int health;
    private final String description;
    private final List<String> colors;
    private final String name;

    /**
     * Constructs a CardInputCopy from an existing CardInput instance.
     *
     * @param original the original CardInput to copy
     */
    public CardInputCopy(final CardInput original) {
        this.mana = original.getMana();
        this.attackDamage = original.getAttackDamage();
        this.health = original.getHealth();
        this.description = original.getDescription();
        this.colors = new ArrayList<>(original.getColors());
        this.name = original.getName();
    }

    /**
     * Copy constructor for creating a new CardInputCopy instance from another CardInputCopy.
     *
     * @param other the CardInputCopy instance to copy
     */
    public CardInputCopy(final CardInputCopy other) {
        this.mana = other.mana;
        this.attackDamage = other.attackDamage;
        this.health = other.health;
        this.description = other.description;
        this.colors = new ArrayList<>(other.colors);
        this.name = other.name;
    }

    /**
     * Gets the mana cost of the card.
     *
     * @return the mana cost
     */
    public int getMana() {
        return mana;
    }

    /**
     * Gets the attack damage of the card.
     *
     * @return the attack damage
     */
    public int getAttackDamage() {
        return attackDamage;
    }

    /**
     * Gets the health of the card.
     *
     * @return the health
     */
    public int getHealth() {
        return health;
    }

    /**
     * Gets the description of the card.
     *
     * @return the description
     */
    public String getDescription() {
        return description;
    }

    /**
     * Gets the colors associated with the card.
     *
     * @return the list of colors
     */
    public List<String> getColors() {
        return colors;
    }

    /**
     * Gets the name of the card.
     *
     * @return the name
     */
    public String getName() {
        return name;
    }
}

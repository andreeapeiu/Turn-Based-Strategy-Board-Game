package org.poo.cards;

import java.util.ArrayList;

/**
 * Card class represents a card with attributes like mana, health,
 * attack damage, and other properties.
 */
public class Card {
    private int mana;
    private int health;
    private int attackDamage;
    private String description;
    private String name;
    private ArrayList<String> colors;
    private String type; // hero, special, tank
    private boolean isFrozen = false;
    private boolean hasAttacked = false;
    private boolean hasUsedAbility = false;

    /**
     * Default constructor for Card.
     */
    public Card() {
    }

    /**
     * Parameterized constructor for Card.
     *
     * @param mana         the mana cost of the card
     * @param health       the health of the card
     * @param attackDamage the attack damage of the card
     * @param description  the description of the card
     * @param name         the name of the card
     * @param colors       the colors of the card
     * @param type         the type of the card
     */
    public Card(final int mana, final int health, final int attackDamage, final String description,
                final String name, final ArrayList<String> colors, final String type) {
        this.mana = mana;
        this.health = health;
        this.attackDamage = attackDamage;
        this.description = description;
        this.name = name;
        this.colors = colors;
        this.type = type;
    }

    /**
     * Copy constructor for Card.
     *
     * @param other the Card object to copy
     */
    public Card(final Card other) {
        this.mana = other.getMana();
        this.health = other.getHealth();
        this.attackDamage = other.getAttackDamage();
        this.description = other.getDescription();
        this.name = other.getName();
        this.colors = other.getColors();
        this.type = other.getType();
        this.isFrozen = other.getIsFrozen(other);
        this.hasAttacked = other.getHasAttacked(other);
        this.hasUsedAbility = other.getHasUsedAbility(other);
    }

    /**
     * Returns the mana cost of the card.
     *
     * @return mana cost
     */
    public int getMana() {
        return mana;
    }

    /**
     * Returns the health of the card.
     *
     * @return health
     */
    public int getHealth() {
        return health;
    }

    /**
     * Returns the attack damage of the card.
     *
     * @return attack damage
     */
    public int getAttackDamage() {
        return attackDamage;
    }

    /**
     * Returns the description of the card.
     *
     * @return description
     */
    public String getDescription() {
        return description;
    }

    /**
     * Returns the name of the card.
     *
     * @return name
     */
    public String getName() {
        return name;
    }

    /**
     * Returns the colors of the card.
     *
     * @return colors
     */
    public ArrayList<String> getColors() {
        return colors;
    }

    /**
     * Returns the type of the card.
     *
     * @return type
     */
    public String getType() {
        return type;
    }

    /**
     * Returns whether the card is frozen.
     *
     * @param card the card to check
     * @return true if the card is frozen, false otherwise
     */
    public boolean getIsFrozen(final Card card) {
        return card.isFrozen;
    }

    /**
     * Returns whether the card has attacked.
     *
     * @return true if the card has attacked, false otherwise
     */
    public boolean getHasAttacked(final Card card) {
        return card.hasAttacked;
    }

    /**
     * Returns whether the card has used its ability.
     *
     * @return true if the card has used its ability, false otherwise
     */
    public boolean getHasUsedAbility(final Card card) {
        return card.hasUsedAbility;
    }

    /**
     * Checks if the card is a tank type.
     *
     * @return true if the card is a tank, false otherwise
     */
    public boolean isTank() {
        return "Goliath".equals(name) || "Warden".equals(name);
    }

    /**
     * Checks if the specified card is a hero type.
     *
     * @param card the card to check
     * @return true if the card is a hero, false otherwise
     */
    public boolean isHero(final Card card) {
        return "Lord Royce".equals(card.getName()) || "Empress Thorina".equals(card.getName())
                || "King Mudface".equals(card.getName())
                || "General Kocioraw".equals(card.getName());
    }

    /**
     * Freezes the specified card.
     *
     * @param card the card to freeze
     */
    public void freeze(final Card card) {
        card.setIsFrozen(true);
    }

    /**
     * Sets the frozen status of the card.
     *
     * @param isFrozen the frozen status to set
     */
    public void setIsFrozen(final boolean isFrozen) {
        this.isFrozen = isFrozen;
    }

    /**
     * Unfreezes the specified card.
     *
     * @param card the card to unfreeze
     */
    public void unfreeze(final Card card) {
        card.setIsFrozen(false);
    }

    /**
     * Reduces the health of the card by the specified damage amount.
     *
     * @param damage the amount of health to reduce
     */
    public void reduceHealth(final int damage) {
        this.health -= damage;
    }

    /**
     * Sets whether the card has attacked.
     *
     * @param hasAttackedStatus the attacked status to set
     */
    public void setHasAttacked(final Card card, final boolean hasAttackedStatus) {
        this.hasAttacked = hasAttackedStatus;
    }


    /**
     * Sets whether the card has used its ability.
     *
     * @param hasUsedAbility the ability usage status to set
     */
    public void setHasUsedAbility(final boolean hasUsedAbility) {
        this.hasUsedAbility = hasUsedAbility;
    }

    /**
     * Sets the health of the card.
     *
     * @param health the health to set
     */
    public void setHealth(final int health) {
        this.health = health;
    }

    /**
     * Sets the attack damage of the card.
     *
     * @param attack the attack damage to set
     */
    public void setAttackDamage(final int attack) {
        this.attackDamage = attack;
    }

    /**
     * Returns a string representation of the card.
     *
     * @return string representation of the card
     */
    @Override
    public String toString() {
        return "Card{"
                + "mana=" + mana
                + ", health=" + health
                + ", attackDamage=" + attackDamage
                + ", description='" + description + '\''
                + ", name='" + name + '\''
                + ", colors=" + colors
                + '}';
    }
}

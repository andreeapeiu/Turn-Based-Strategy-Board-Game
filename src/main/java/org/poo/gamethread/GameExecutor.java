package org.poo.gamethread;

import org.poo.players.Player;
import org.poo.players.Hand;
import org.poo.players.Deck;
import org.poo.cards.Card;
import org.poo.cards.Hero;
import org.poo.cards.Minion;
import org.poo.utils.ActionsInputCopy;
import org.poo.utils.InputCopy;
import org.poo.utils.CardInputCopy;
import org.poo.utils.StartGameInputCopy;
import org.poo.utils.GameInputCopy;
import org.poo.fileio.Coordinates;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.poo.players.GameBoard;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

/**
 * GameExecutor is responsible for executing the game logic, managing players, actions,
 * game rounds, and processing game input and output.
 */
public class GameExecutor {

    private static final int INITIAL_HEALTH = 30;
    private static final int BOARD_ROWS = 4;
    private static final int ZERO = 0;
    private static final int ONE = 1;
    private static final int TWO = 2;
    private static final int THREE = 3;
    private static final int TEN = 10;
    private final InputCopy inputData;
    private int currentRound = 1;
    private Player playerOne;
    private Player playerTwo;
    private ArrayNode output;
    private GameBoard board = new GameBoard();
    private int gamesPlayed = 1;
    private int gamesWonPlayerOne = 0;
    private int gamesWonPlayerTwo = 0;

    /**
     * Constructs a GameExecutor instance with input data and output array.
     *
     * @param inputData The input data for the game.
     * @param output The ArrayNode to store the output results of game actions.
     */
    public GameExecutor(final InputCopy inputData, final ArrayNode output) {
        this.inputData = inputData;
        this.output = output;
    }

    /**
     * Executes the game based on input data, initializing players and managing each round.
     */
    public void executeGame() {
        ObjectMapper objectMapper = new ObjectMapper();

        for (GameInputCopy game : inputData.getGames()) {
            StartGameInputCopy startGame = game.getStartGame();
            List<CardInputCopy> playerOneDeckInput
                = inputData.getPlayerOneDecks().getDecks().get(startGame.getPlayerOneDeckIdx());
            List<CardInputCopy> playerTwoDeckInput
                = inputData.getPlayerTwoDecks().getDecks().get(startGame.getPlayerTwoDeckIdx());

            List<CardInputCopy> playerOneDeckInputCopy = new ArrayList<>();
            for (CardInputCopy card : playerOneDeckInput) {
                playerOneDeckInputCopy.add(new CardInputCopy(card));
            }

            List<CardInputCopy> playerTwoDeckInputCopy = new ArrayList<>();
            for (CardInputCopy card : playerTwoDeckInput) {
                playerTwoDeckInputCopy.add(new CardInputCopy(card));
            }

            shuffleDecks(playerOneDeckInputCopy, playerTwoDeckInputCopy,
                    startGame.getShuffleSeed());
            initializePlayers(playerOneDeckInputCopy, playerTwoDeckInputCopy, startGame);
            resetInitialGameState();

            for (ActionsInputCopy action : game.getActions()) {
                ObjectNode outputNode = objectMapper.createObjectNode();
                processAction(action, outputNode, startGame);
                if (!outputNode.isEmpty()) {
                    output.add(outputNode);
                }
            }

            resetStateForNewGame();
        }
    }

    private void shuffleDecks(final List<CardInputCopy> playerOneDeck,
                              final List<CardInputCopy> playerTwoDeck, final long seed) {
        Collections.shuffle(playerOneDeck, new Random(seed));
        Collections.shuffle(playerTwoDeck, new Random(seed));
    }

    private void resetInitialGameState() {
        currentRound = 1;
        playerOne.drawCard();
        playerTwo.drawCard();
        playerOne.setMana(1);
        playerTwo.setMana(1);
        playerOne.getHero().setHealth(INITIAL_HEALTH);
        playerTwo.getHero().setHealth(INITIAL_HEALTH);
    }

    private void processAction(final ActionsInputCopy action, final ObjectNode outputNode,
                               final StartGameInputCopy startGame) {
        switch (action.getCommand()) {
            case "getPlayerDeck":
                handleGetPlayerDeck(action, outputNode);
                break;
            case "getPlayerHero":
                handleGetPlayerHero(action, outputNode);
                break;
            case "getPlayerTurn":
                handleGetPlayerTurn(outputNode);
                break;
            case "placeCard":
                handlePlaceCard(action, outputNode);
                break;
            case "endPlayerTurn":
                handleEndPlayerTurn(startGame, outputNode);
                break;
            case "getPlayerMana":
                handleGetPlayerMana(action, outputNode);
                break;
            case "getCardsInHand":
                handleGetCardsInHand(action, outputNode);
                break;
            case "getCardsOnTable":
                handleGetCardsOnTable(outputNode);
                break;
            case "cardUsesAttack":
                handleCardUsesAttack(action, outputNode);
                break;
            case "cardUsesAbility":
                handleCardUsesAbility(action, outputNode);
                break;
            case "useHeroAbility":
                handleUseHeroAbility(action, outputNode);
                break;
            case "useAttackHero":
                handleUseAttackHero(action, outputNode);
                break;
            case "getCardAtPosition":
                handleGetCardAtPosition(action, outputNode);
                break;
            case "getFrozenCardsOnTable":
                handleGetFrozenCardsOnTable(outputNode);
                break;
            case "getPlayerOneWins":
                handleGetPlayerOneWins(outputNode);
                break;
            case "getPlayerTwoWins":
                handleGetPlayerTwoWins(outputNode);
                break;
            case "getTotalGamesPlayed":
                handleGetTotalGamesPlayed(outputNode);
                break;
            default:
                break;
        }
    }

    private void handleGetPlayerOneWins(final ObjectNode outputNode) {
        outputNode.put("command", "getPlayerOneWins");
        outputNode.put("output", gamesWonPlayerOne);
    }

    private void handleGetPlayerTwoWins(final ObjectNode outputNode) {
        outputNode.put("command", "getPlayerTwoWins");
        outputNode.put("output", gamesWonPlayerTwo);
    }

    private void handleGetTotalGamesPlayed(final ObjectNode outputNode) {
        outputNode.put("command", "getTotalGamesPlayed");
        outputNode.put("output", gamesPlayed);
    }

    private void handleGetFrozenCardsOnTable(final ObjectNode outputNode) {
        outputNode.put("command", "getFrozenCardsOnTable");

        ArrayNode frozenCards = outputNode.putArray("output");

        for (int row = 0; row < BOARD_ROWS; row++) {
            List<Card> rowCards = board.getRow(row);

            for (Card card : rowCards) {
                if (card.getIsFrozen(card)) {
                    ObjectNode cardNode = frozenCards.addObject();
                    cardNode.put("mana", card.getMana());
                    cardNode.put("attackDamage", card.getAttackDamage());
                    cardNode.put("health", card.getHealth());
                    cardNode.put("description", card.getDescription());

                    ArrayNode colorsNode = cardNode.putArray("colors");
                    for (String color : card.getColors()) {
                        colorsNode.add(color);
                    }
                    cardNode.put("name", card.getName());
                }
            }
        }
    }

    private void handleUseAttackHero(final ActionsInputCopy action, final ObjectNode outputNode) {
        int playerIdx = playerOne.isTurn() ? 1 : 2;
        Coordinates attacker = action.getCardAttacker();

        int attackerRow = attacker.getX();
        int attackerColumn = attacker.getY();

        Card attackerCard = board.getCardFromRow(attackerRow, attackerColumn);

        if (attackerCard == null) {
            return;
        }

        if (attackerCard.getIsFrozen(attackerCard)) {
            outputNode.put("command", "useAttackHero");
            ObjectNode cardAttackerNode = outputNode.putObject("cardAttacker");
            cardAttackerNode.put("x", attackerRow);
            cardAttackerNode.put("y", attackerColumn);
            outputNode.put("error", "Attacker card is frozen.");
            return;
        }

        if (attackerCard.getHasAttacked(attackerCard)
                || attackerCard.getHasUsedAbility(attackerCard)) {
            outputNode.put("command", "useAttackHero");
            ObjectNode cardAttackerNode = outputNode.putObject("cardAttacker");
            cardAttackerNode.put("x", attackerRow);
            cardAttackerNode.put("y", attackerColumn);
            outputNode.put("error", "Attacker card has already attacked this turn.");
            return;
        }

        if (hasTankOnEnemyRows(playerIdx)) {
            outputNode.put("command", "useAttackHero");
            ObjectNode cardAttackerNode = outputNode.putObject("cardAttacker");
            cardAttackerNode.put("x", attackerRow);
            cardAttackerNode.put("y", attackerColumn);
            outputNode.put("error", "Attacked card is not of type 'Tank'.");
            return;
        }

        if (playerOne.isTurn()) {
            playerTwo.getHero().reduceHealth(attackerCard.getAttackDamage());
        } else {
            playerOne.getHero().reduceHealth(attackerCard.getAttackDamage());
        }

        attackerCard.setHasAttacked(attackerCard, true);

        if (playerOne.getHero().getHealth() <= 0) {
            outputNode.put("gameEnded", "Player two killed the enemy hero.");
            gamesWonPlayerTwo++;
        } else if (playerTwo.getHero().getHealth() <= 0) {
            outputNode.put("gameEnded", "Player one killed the enemy hero.");
            gamesWonPlayerOne++;
        }
    }

    private void handleUseHeroAbility(final ActionsInputCopy action, final ObjectNode outputNode) {
        int afectedRow = action.getAffectedRow();
        Hero hero = (playerOne.isTurn()) ? playerOne.getHero() : playerTwo.getHero();

        hero.usePower(hero, outputNode, afectedRow, board, playerOne, playerTwo);

    }

    private void handleCardUsesAbility(final ActionsInputCopy action, final ObjectNode outputNode) {
        int playerIdx = playerOne.isTurn() ? 1 : 2;  // Use class-level playerOne and playerTwo
        Coordinates attacker = action.getCardAttacker();
        Coordinates attacked = action.getCardAttacked();

        int attackerRow = attacker.getX();
        int attackerColumn = attacker.getY();

        int attackedRow = attacked.getX();
        int attackedColumn = attacked.getY();

        Card attackerCard;
        Card attackedCard;

        if (board.getCardFromRow(attackedRow, attackedColumn) == null
                || board.getCardFromRow(attackerRow, attackerColumn) == null) {
            return;
        }

        attackerCard = board.getCardFromRow(attackerRow, attackerColumn);
        attackedCard = board.getCardFromRow(attackedRow, attackedColumn);

        if (attackerCard.getIsFrozen(attackerCard)) {
            outputNode.put("command", "cardUsesAbility");
            ObjectNode cardAttackerNode = outputNode.putObject("cardAttacker");
            cardAttackerNode.put("x", attackerRow);
            cardAttackerNode.put("y", attackerColumn);
            ObjectNode cardAttackedNode = outputNode.putObject("cardAttacked");
            cardAttackedNode.put("x", attackedRow);
            cardAttackedNode.put("y", attackedColumn);
            outputNode.put("error", "Attacker card is frozen.");
            return;
        }

        if (attackerCard.getHasAttacked(attackerCard)
                || attackerCard.getHasUsedAbility(attackerCard)) {
            outputNode.put("command", "cardUsesAbility");
            ObjectNode cardAttackerNode = outputNode.putObject("cardAttacker");
            cardAttackerNode.put("x", attackerRow);
            cardAttackerNode.put("y", attackerColumn);
            ObjectNode cardAttackedNode = outputNode.putObject("cardAttacked");
            cardAttackedNode.put("x", attackedRow);
            cardAttackedNode.put("y", attackedColumn);
            outputNode.put("error", "Attacker card has already attacked this turn.");
            return;
        }

        if (attackerCard.getName().equals("Disciple")) {
            if (playerIdx == ONE && (attackedRow == ZERO || attackedRow == ONE)
                    || playerIdx == TWO && (attackedRow == TWO || attackedRow == THREE)) {
                outputNode.put("command", "cardUsesAbility");
                ObjectNode cardAttackerNode = outputNode.putObject("cardAttacker");
                cardAttackerNode.put("x", attackerRow);
                cardAttackerNode.put("y", attackerColumn);
                ObjectNode cardAttackedNode = outputNode.putObject("cardAttacked");
                cardAttackedNode.put("x", attackedRow);
                cardAttackedNode.put("y", attackedColumn);
                outputNode.put("error", "Attacked card does not belong to the current player.");
                return;
            }
            discipleusesability(attackedCard);
            attackerCard.setHasUsedAbility(true);
            return;
        }

        if (attackerCard.getName().equals("The Ripper") || attackerCard.getName().equals("Miraj")
                || attackerCard.getName().equals("The Cursed One")) {
            if (playerIdx == ONE && (attackedRow == TWO || attackedRow == THREE)
                    || playerIdx == TWO && (attackedRow == ZERO || attackedRow == ONE)) {
                outputNode.put("command", "cardUsesAbility");
                ObjectNode cardAttackerNode = outputNode.putObject("cardAttacker");
                cardAttackerNode.put("x", attackerRow);
                cardAttackerNode.put("y", attackerColumn);
                ObjectNode cardAttackedNode = outputNode.putObject("cardAttacked");
                cardAttackedNode.put("x", attackedRow);
                cardAttackedNode.put("y", attackedColumn);
                outputNode.put("error", "Attacked card does not belong to the enemy.");
                return;
            }
        }

        if (hasTankOnEnemyRows(playerIdx) && !attackedCard.isTank()) {
            outputNode.put("command", "cardUsesAbility");
            ObjectNode cardAttackerNode = outputNode.putObject("cardAttacker");
            cardAttackerNode.put("x", attackerRow);
            cardAttackerNode.put("y", attackerColumn);
            ObjectNode cardAttackedNode = outputNode.putObject("cardAttacked");
            cardAttackedNode.put("x", attackedRow);
            cardAttackedNode.put("y", attackedColumn);
            outputNode.put("error", "Attacked card is not of type 'Tank'.");
            return;
        }

        switch (attackerCard.getName()) {
            case "The Ripper":
                theripperusesability(attackedCard);
                break;
            case "Miraj":
                mirajusesability(attackerCard, attackedCard);
                break;
            case "The Cursed One":
                cursedusesability(attackedCard);
                break;
            default:
                break;
        }

        attackerCard.setHasUsedAbility(true);

        if (attackedCard.getHealth() <= 0) {
            board.removeCardFromRow(attackedRow, attackedColumn);
        }
    }

    private boolean hasTankOnEnemyRows(final int playerIdx) {
        int startRow = (playerIdx == ONE) ? ZERO : TWO;
        int endRow = (playerIdx == ONE) ? ONE : THREE;

        for (int i = startRow; i <= endRow; i++) {
            List<Card> row = board.getRow(i);
            for (Card card : row) {
                if (card.isTank()) {
                    return true;
                }
            }
        }
        return false;
    }

    private void discipleusesability(final Card attackedCard) {
        attackedCard.setHealth(attackedCard.getHealth() + 2);
    }

    private void theripperusesability(final Card attackedCard) {
        if (attackedCard.getAttackDamage() < 2) {
            attackedCard.setAttackDamage(0);
        } else {
            attackedCard.setAttackDamage(attackedCard.getAttackDamage() - 2);
        }
    }

    private void mirajusesability(final Card attackerCard, final Card attackedCard) {
        int aux = attackerCard.getHealth();
        attackerCard.setHealth(attackedCard.getHealth());
        attackedCard.setHealth(aux);
    }

    private void cursedusesability(final Card attackedCard) {
        int aux = attackedCard.getAttackDamage();
        attackedCard.setAttackDamage(attackedCard.getHealth());
        attackedCard.setHealth(aux);
    }

    private void handleGetCardAtPosition(final ActionsInputCopy action,
                                         final ObjectNode outputNode) {
        outputNode.put("command", "getCardAtPosition");
        int row = action.getX();
        int column = action.getY();
        outputNode.put("x", row);
        outputNode.put("y", column);
        Card card = board.getCardFromRow(row, column);
        if (card == null) {
            outputNode.put("output", "No card available at that position.");
            return;
        }

        ObjectNode cardNode = outputNode.putObject("output");
        cardNode.put("mana", card.getMana());
        cardNode.put("attackDamage", card.getAttackDamage());
        cardNode.put("health", card.getHealth());
        cardNode.put("description", card.getDescription());

        ArrayNode colorsNode = cardNode.putArray("colors");
        for (String color : card.getColors()) {
            colorsNode.add(color);
        }
        cardNode.put("name", card.getName());
    }

    private void handleGetPlayerTurn(final ObjectNode outputNode) {
        outputNode.put("command", "getPlayerTurn");
        if (playerOne.isTurn()) {
            outputNode.put("output", 1);
        } else {
            outputNode.put("output", 2);
        }
    }

    private void handleCardUsesAttack(final ActionsInputCopy action, final ObjectNode outputNode) {
        int playerIdx = playerOne.isTurn() ? 1 : 2;
        Coordinates attacker = action.getCardAttacker();
        Coordinates attacked = action.getCardAttacked();

        int attackerRow = attacker.getX();
        int attackerColumn = attacker.getY();

        int attackedRow = attacked.getX();
        int attackedColumn = attacked.getY();

        if (playerIdx == ONE) {
            if (attackedRow == TWO || attackedRow == THREE) {
                outputNode.put("command", "cardUsesAttack");
                ObjectNode cardAttackerNode = outputNode.putObject("cardAttacker");
                cardAttackerNode.put("x", attackerRow);
                cardAttackerNode.put("y", attackerColumn);
                ObjectNode cardAttackedNode = outputNode.putObject("cardAttacked");
                cardAttackedNode.put("x", attackedRow);
                cardAttackedNode.put("y", attackedColumn);
                outputNode.put("error", "Attacked card does not belong to the enemy.");
                return;
            }
        } else {
            if (attackedRow == 0 || attackedRow == 1) {
                outputNode.put("command", "cardUsesAttack");
                ObjectNode cardAttackerNode = outputNode.putObject("cardAttacker");
                cardAttackerNode.put("x", attackerRow);
                cardAttackerNode.put("y", attackerColumn);
                ObjectNode cardAttackedNode = outputNode.putObject("cardAttacked");
                cardAttackedNode.put("x", attackedRow);
                cardAttackedNode.put("y", attackedColumn);
                outputNode.put("error", "Attacked card does not belong to the enemy.");
                return;
            }
        }
        Card attackerCard = null;
        Card attackedCard = null;
        if (board.getCardFromRow(attackedRow, attackedColumn) == null
                || board.getCardFromRow(attackerRow, attackerColumn) == null) {
            return;
        }

        attackerCard =  board.getCardFromRow(attackerRow, attackerColumn);
        attackedCard = board.getCardFromRow(attackedRow, attackedColumn);
        if (attackerCard.getHasAttacked(attackerCard)) {
            outputNode.put("command", "cardUsesAttack");
            ObjectNode cardAttackerNode = outputNode.putObject("cardAttacker");
            cardAttackerNode.put("x", attackerRow);
            cardAttackerNode.put("y", attackerColumn);
            ObjectNode cardAttackedNode = outputNode.putObject("cardAttacked");
            cardAttackedNode.put("x", attackedRow);
            cardAttackedNode.put("y", attackedColumn);
            outputNode.put("error", "Attacker card has already attacked this turn.");
            return;
        }

        if (attackerCard.getIsFrozen(attackerCard)) {
            outputNode.put("command", "cardUsesAttack");
            ObjectNode cardAttackerNode = outputNode.putObject("cardAttacker");
            cardAttackerNode.put("x", attackerRow);
            cardAttackerNode.put("y", attackerColumn);
            ObjectNode cardAttackedNode = outputNode.putObject("cardAttacked");
            cardAttackedNode.put("x", attackedRow);
            cardAttackedNode.put("y", attackedColumn);
            outputNode.put("error", "Attacker card is frozen.");
            return;
        }

        List<Card> attackedRowList;

        if (playerIdx == 1) {
            attackedRowList = board.getRow(1);
        } else {
            attackedRowList = board.getRow(2);
        }

        if (hasTankOnEnemyRows(playerIdx)) {
            if (!(attackedCard.isTank())) {
                outputNode.put("command", "cardUsesAttack");
                ObjectNode cardAttackerNode = outputNode.putObject("cardAttacker");
                cardAttackerNode.put("x", attackerRow);
                cardAttackerNode.put("y", attackerColumn);
                ObjectNode cardAttackedNode = outputNode.putObject("cardAttacked");
                cardAttackedNode.put("x", attackedRow);
                cardAttackedNode.put("y", attackedColumn);
                outputNode.put("error", "Attacked card is not of type 'Tank'.");
                return;
            }
        }

        attackerCard.setHasAttacked(attackerCard, true);
        attackedCard.reduceHealth(attackerCard.getAttackDamage());

        if (attackedCard.getHealth() <= 0) {
            board.removeCardFromRow(attackedRow, attackedColumn);
        }
    }

    private void handleGetPlayerDeck(final ActionsInputCopy action, final ObjectNode outputNode) {
        outputNode.put("command", "getPlayerDeck");
        outputNode.put("playerIdx", action.getPlayerIdx());
        ArrayNode deckOutput = outputNode.putArray("output");

        List<Card> selectedDeck = (action.getPlayerIdx() == 1) ? playerOne.getDeck().getCards()
                : playerTwo.getDeck().getCards();
        for (Card card : selectedDeck) {
            ObjectNode cardNode = deckOutput.addObject();
            cardNode.put("mana", card.getMana());
            cardNode.put("attackDamage", card.getAttackDamage());
            cardNode.put("health", card.getHealth());
            cardNode.put("description", card.getDescription());

            ArrayNode colorsNode = cardNode.putArray("colors");
            for (String color : card.getColors()) {
                colorsNode.add(color);
            }
            cardNode.put("name", card.getName());
        }
    }

    private void handleGetPlayerHero(final ActionsInputCopy action, final ObjectNode outputNode) {
        Hero hero = (action.getPlayerIdx() == 1) ? playerOne.getHero() : playerTwo.getHero();
        outputNode.put("command", "getPlayerHero");
        outputNode.put("playerIdx", action.getPlayerIdx());

        ObjectNode heroNode = outputNode.putObject("output");
        heroNode.put("mana", hero.getMana());
        heroNode.put("description", hero.getDescription());

        ArrayNode heroColors = heroNode.putArray("colors");
        for (String color : hero.getColors()) {
            heroColors.add(color);
        }
        heroNode.put("name", hero.getName());
        heroNode.put("health", hero.getHealth());
    }

    private void handleEndPlayerTurn(final StartGameInputCopy startGame,
                                     final ObjectNode outputNode) {
        resetHeroConsequences();
        toggleTurns();
        if ((playerOne.isTurn() && startGame.getStartingPlayer() == 1)
                || (playerTwo.isTurn() && startGame.getStartingPlayer() == 2)) {
            currentRound++;
            incrementMana();
            playerOne.drawCard();
            playerTwo.drawCard();
            resetHasAttacked();
            resetHasUsedAbility();
            resetHeroState();
        }

    }

    private void resetHeroState() {
        playerOne.getHero().setHasUsedAbility(false);
        playerTwo.getHero().setHasUsedAbility(false);
    }

    private void resetHeroConsequences() {
        int playerIdx = playerOne.isTurn() ? 1 : 2;
        if (playerIdx == 1) {
            for (int i = TWO; i < BOARD_ROWS; i++) {
                List<Card> row = board.getRow(i);
                for (Card card : row) {
                    card.unfreeze(card);
                }
            }
        } else {
            for (int i = 0; i < 2; i++) {
                List<Card> row = board.getRow(i);
                for (Card card : row) {
                    card.unfreeze(card);
                }
            }
        }
    }

    private void resetHasUsedAbility() {
        for (int i = 0; i < BOARD_ROWS; i++) {
            List<Card> row = board.getRow(i);
            for (Card card : row) {
                card.setHasUsedAbility(false);
            }
        }
    }

    private void resetHasAttacked() {
        for (int i = 0; i < BOARD_ROWS; i++) {
            List<Card> row = board.getRow(i);
            for (Card card : row) {
                card.setHasAttacked(card, false);
            }
        }
    }

    private void toggleTurns() {
        playerOne.setTurn(!playerOne.isTurn());
        playerTwo.setTurn(!playerTwo.isTurn());
    }

    private void handleGetPlayerMana(final ActionsInputCopy action, final ObjectNode outputNode) {
        outputNode.put("command", "getPlayerMana");
        outputNode.put("playerIdx", action.getPlayerIdx());
        outputNode.put("output", (action.getPlayerIdx() == 1) ? playerOne.getMana()
                : playerTwo.getMana());
    }

    private void handleGetCardsInHand(final ActionsInputCopy action, final ObjectNode outputNode) {
        outputNode.put("command", "getCardsInHand");
        outputNode.put("playerIdx", action.getPlayerIdx());
        ArrayNode handOutput = outputNode.putArray("output");

        Hand hand = (action.getPlayerIdx() == 1) ? playerOne.getHand() : playerTwo.getHand();
        for (Card card : hand.getCards()) {
            ObjectNode cardNode = handOutput.addObject();
            cardNode.put("mana", card.getMana());
            cardNode.put("attackDamage", card.getAttackDamage());
            cardNode.put("health", card.getHealth());
            cardNode.put("description", card.getDescription());

            ArrayNode colorsNode = cardNode.putArray("colors");
            for (String color : card.getColors()) {
                colorsNode.add(color);
            }
            cardNode.put("name", card.getName());
        }
    }

    private void handleGetCardsOnTable(final ObjectNode outputNode) {
        outputNode.put("command", "getCardsOnTable");
        ArrayNode tableArray = outputNode.putArray("output");

        for (int row = 0; row < BOARD_ROWS; row++) {
            List<Card> rowCards = board.getRow(row);

            ArrayNode rowArray = tableArray.addArray();
            if (!rowCards.isEmpty()) {
                for (Card card : rowCards) {
                    ObjectNode cardNode = rowArray.addObject();
                    cardNode.put("mana", card.getMana());
                    cardNode.put("attackDamage", card.getAttackDamage());
                    cardNode.put("health", card.getHealth());
                    cardNode.put("description", card.getDescription());

                    ArrayNode colorsNode = cardNode.putArray("colors");
                    for (String color : card.getColors()) {
                        colorsNode.add(color);
                    }
                    cardNode.put("name", card.getName());
                }
            }
        }
    }

    private void handlePlaceCard(final ActionsInputCopy action, final ObjectNode outputNode) {
        Player currentPlayer;

        if (playerOne.isTurn()) {
            currentPlayer = playerOne;
        } else {
            currentPlayer = playerTwo;
        }

        int handIndex = action.getHandIdx();
        Card cardToPlace = currentPlayer.getHand().getCard(handIndex);

        if (cardToPlace == null) {
            outputNode.put("command", "placeCard");
            outputNode.put("error", "Card not found in hand.");
            outputNode.put("HandIdx", handIndex);
            return;
        }

        if (cardToPlace.getMana() > currentPlayer.getMana()) {
            outputNode.put("command", "placeCard");
            outputNode.put("error", "Not enough mana to place card on table.");
            outputNode.put("handIdx", handIndex);
            return;
        }

        int targetRow = action.getTargetRow(cardToPlace, currentPlayer);

        if (targetRow < 0 || targetRow >= BOARD_ROWS) {
            outputNode.put("command", "placeCard");
            outputNode.put("error", "Invalid row index for card placement. Row: " + targetRow);
            outputNode.put("HandIdx", handIndex);
            return;
        }

        currentPlayer.playCard(handIndex, targetRow, outputNode, board);

    }

    private void initializePlayers(final List<CardInputCopy> playerOneDeckInput,
                                   final List<CardInputCopy> playerTwoDeckInput,
                                   final StartGameInputCopy startGame) {
        Hand playerOneHand = new Hand();
        Hand playerTwoHand = new Hand();

        List<Card> playerOneDeckCards = convertToCardList(playerOneDeckInput);
        List<Card> playerTwoDeckCards = convertToCardList(playerTwoDeckInput);

        Deck playerOneDeck = new Deck(playerOneDeckCards);
        Deck playerTwoDeck = new Deck(playerTwoDeckCards);

        Hero heroOne = new Hero(startGame.getPlayerOneHero().getMana(), INITIAL_HEALTH, 0,
                startGame.getPlayerOneHero().getDescription(),
                startGame.getPlayerOneHero().getName(),
                new ArrayList<>(startGame.getPlayerOneHero().getColors()),
                "hero", startGame.getPlayerOneHero().getName(), "Ability");

        Hero heroTwo = new Hero(startGame.getPlayerTwoHero().getMana(),
                INITIAL_HEALTH, 0,
                startGame.getPlayerTwoHero().getDescription(),
                startGame.getPlayerTwoHero().getName(),
                new ArrayList<>(startGame.getPlayerTwoHero().getColors()),
                "hero", startGame.getPlayerTwoHero().getName(), "Ability");

        playerOne = new Player(playerOneHand, playerOneDeck, heroOne, 1);
        playerTwo = new Player(playerTwoHand, playerTwoDeck, heroTwo, 2);

        if (startGame.getStartingPlayer() == 1) {
            playerOne.setTurn(true);
        } else {
            playerTwo.setTurn(true);
        }
    }

    private List<Card> convertToCardList(final List<CardInputCopy> cardInputCopies) {
        List<Card> cards = new ArrayList<>();
        for (CardInputCopy input : cardInputCopies) {
            cards.add(createCardFromInput(input));
        }
        return cards;
    }

    private Card createCardFromInput(final CardInputCopy input) {
        if (input.getName().equals("Lord Royce") || input.getName().equals("Empress Thorina")
                || input.getName().equals("King Mudface")
                || input.getName().equals("General Kocioraw")) {
            return new Hero(input.getMana(), INITIAL_HEALTH, 0, input.getDescription(),
                    input.getName(), new ArrayList<>(input.getColors()), "hero",
                    input.getName(), "Ability");
        } else if (input.getName().equals("The Ripper") || input.getName().equals("Miraj")
                || input.getName().equals("The Cursed One") || input.getName().equals("Disciple")) {
            return new Minion(input.getMana(), input.getHealth(), input.getAttackDamage(),
                    input.getDescription(), input.getName(),
                    new ArrayList<>(input.getColors()), "minion",
                    false, false);
        } else {
            return new Card(input.getMana(), input.getHealth(), input.getAttackDamage(),
                    input.getDescription(), input.getName(),
                    new ArrayList<>(input.getColors()), "minion");
        }
    }

    private void incrementMana() {

        playerOne.setMana(playerOne.getMana() + Math.min(currentRound, TEN));
        playerTwo.setMana(playerTwo.getMana() + Math.min(currentRound, TEN));
    }

    private void resetStateForNewGame() {
        currentRound = 1;
        playerOne.resetPlayer();
        playerTwo.resetPlayer();

        gamesPlayed++;

        board.resetBoard();
    }

    /**
     * Gets the Player object representing Player One.
     *
     * @return the Player object for Player One
     */
    public Player getPlayerOne() {
        return playerOne;
    }

    /**
     * Gets the Player object representing Player Two.
     *
     * @return the Player object for Player Two
     */
    public Player getPlayerTwo() {
        return playerTwo;
    }

    /**
     * Gets the GameBoard object representing the current game board.
     *
     * @return the GameBoard object for the current game
     */
    public GameBoard getBoard() {
        return board;
    }

    /**
     * Gets the total number of games played.
     *
     * @return the number of games played
     */
    public int getGamesPlayed() {
        return gamesPlayed;
    }

    /**
     * Gets the number of games won by Player One.
     *
     * @return the number of games won by Player One
     */
    public int getGamesWonPlayerOne() {
        return gamesWonPlayerOne;
    }

    /**
     * Gets the number of games won by Player Two.
     *
     * @return the number of games won by Player Two
     */
    public int getGamesWonPlayerTwo() {
        return gamesWonPlayerTwo;
    }

}

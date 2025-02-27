# GwentStone Lite Project

## Project Overview
Every match in GwentStone Lite begins with a strategically shuffled deck, ensuring  
fairness and unpredictability through a predetermined seed. As players take their  
turns, they engage in a series of calculated actions—selecting cards, positioning  
them strategically on the game board, and making tactical use of their available  
mana. Each move has the potential to shift the balance of the game, rewarding  
foresight and adaptability.

The board is designed as a list of lists, resembling a grid. This design choice  
simplifies the interaction with the game space, allowing players to place cards as  
if strategically positioning units on a digital battlefield.

As the game progresses, adaptability becomes key. Players must respond to the  
evolving board, utilizing their hero’s unique abilities at critical moments to  
influence the course of battle. These hero abilities can alter the state of play,  
offering strategic depth and surprise elements that challenge the players.

Victory is achieved by reducing the opposing hero's health to zero. However, the  
journey to this endpoint is filled with strategic choices, driven by the dynamic  
interactions facilitated by the game board's grid-like structure.

GwentStone Lite is a strategic card game that blends elements from famous games  
like Hearthstone and Gwent. It is implemented in Java and designed to facilitate  
learning in object-oriented programming, debugging skills, and adhering to design  
patterns and coding standards.

## Technical Specifications
The game is structured for two players using card decks and heroes that represent  
each player on a 4x5 matrix game board. Players alternate turns, during which they  
draw cards, manage mana, and strategically place cards on the board to defeat the  
opponent's hero.

## Running the Game
Clone the repository: [GitHub Link](#)  
(**https://github.com/andreeapeiu/tema1_poo.git**).  
Execute `Main.java` to start the game simulation.

## Game Mechanics

### Initialization
- Decks are shuffled using a provided seed at the start to ensure fair gameplay.

### Gameplay
- Each round begins with players drawing cards from their decks.
- On their turn, players can place cards on the board, provided they have enough  
  mana.
- Each card has unique abilities and specific placement rules.

### Card Types
- **Minions**: Regular attack and defense cards.
- **Heroes**: Special cards that represent the players, equipped with unique  
  abilities.
- **Cards with special abilities**: Possess unique powers that can be used during  
  the game.

### Win Condition
- The game concludes when a hero's health is depleted to zero.

## Code Structure and Logic
The `GameExecutor` class is central to the game's functionality, handling game  
states, player actions, and enforcement of game rules.

### Key Functions
- `shuffleDecks()`: Shuffles both players' decks at game start.
- `processAction()`: Processes and executes actions from input JSON files.
- `handleCardUsesAttack()`: Manages attacks between cards or against heroes.
- `resetStateForNewGame()`: Resets the game state for a new match.

### Player Turns and Actions
- Iterates over actions from input JSON, modifying the game state accordingly.

### Card Management
- Manages interactions through card objects that can be drawn, placed, and activated.

### Hero Abilities
- Special actions from hero cards significantly affect the board state.

### Utility Classes
- Classes for parsing JSON (`InputCopy`), managing cards (`Card`), and handling  
  player actions (`Player`).

## Testing and Debugging
The game includes comprehensive debugging commands to verify game state at  
various points, crucial for ensuring correct game logic execution.

## Future Possible Improvements
- **AI Enhancement**: To provide more strategic depth.
- **GUI Implementation**: For a more interactive and visually engaging experience.
- **New Card Types**: Introduce new abilities and card dynamics to enhance  
  strategic options.

## Development Environment
- **IDE**: IntelliJ IDEA
- **JDK Version**: JDK 11
- **Dependencies**: Uses Jackson for JSON processing.

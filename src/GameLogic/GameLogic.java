package gamelogic;

import view.SendMessage;
import dice.DiceController;
import deck.Deck;
import dice.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;


/**
 * The GameLogic class initiates game attributes and routes game decision during player turns.
 **/
public class GameLogic {
    private ArrayList<Monster> monsters;
    private Scanner scanner;
    private SendMessage sendMessage;
    private DiceController diceController;
    private WinCondition winCondition;
    HashMap<Dice, Integer> result;
    Deck deck;

    public GameLogic(ArrayList<Monster> monsters) {
        this.monsters = monsters;
        initGameLogic();
    }

    private void initGameLogic() {
        Monster kong = new Monster("Kong");
        Monster gigazaur = new Monster("Gigazaur");
        Monster alien = new Monster("Alienoid");
        this.monsters.add(kong);
        this.monsters.add(gigazaur);
        this.monsters.add(alien);
        scanner = new Scanner(System.in);
        result = new HashMap<Dice, Integer>();
        sendMessage = new SendMessage(monsters, scanner);
        diceController = new DiceController(monsters, sendMessage, result, deck);
        winCondition = new WinCondition(monsters, sendMessage);

        deck = new Deck(sendMessage);
    }

    public void playerTurn() {
        // Collections.shuffle(monsters);
        /*
         * Game loop: pre: Award a monster in Tokyo 1 star 1. Roll 6 dice 2. Decide
         * which dice to keep 3. Reroll remaining dice 4. Decide which dice to keep 5.
         * Reroll remaining dice 6. Sum up totals 6a. Hearts = health (max 10 unless a
         * cord increases it) 6b. 3 hearts = power-up 6c. 3 of a number = victory points
         * 6d. claws = attack (if in Tokyo attack everyone, else attack monster in
         * Tokyo) 6e. If you were outside, then the monster inside tokyo may decide to
         * leave Tokyo 6f. energy = energy tokens 7. Decide to buy things for energy 7a.
         * Play "DISCARD" cards immediately 8. Check victory conditions
         */
        for (int i = 0; i < monsters.size(); i++) {
            Monster currentMonster = monsters.get(i);
            if (currentMonster.getCurrentHealth() <= 0) {
                currentMonster.setInTokyo(false);
                continue;
            }

            gainStarinTokyo(currentMonster);
            playerStatus(currentMonster, i);
            diceController.diceController(i);
            diceController.diceResult.diceResult(i, currentMonster, deck);
        }
        winCondition.winCondition();
    }

    private void gainStarinTokyo(Monster currentMonster) {
        if (currentMonster.getInTokyo()) {
            currentMonster.increaseStars(1);
        }
    }

    private void playerStatus(Monster currentMonster, int i) {
        String statusUpdate = "You are " + currentMonster.getName() + " and it is your turn. Here are the stats";
        for (int count = 0; count < 3; count++) {
            statusUpdate += ":" + monsters.get(count).getName()
                    + (monsters.get(count).getInTokyo() ? " is in Tokyo " : " is not in Tokyo ");
            statusUpdate += "with " + monsters.get(count).getCurrentHealth() + " health, " + monsters.get(count).getStars()
                    + " stars, ";
            statusUpdate += monsters.get(count).getEnergy() + " energy, and owns the following cards:";
            statusUpdate += monsters.get(count).cardsToString();
        }
        sendMessage.sendMessage(i, statusUpdate + "\n");
    }
}
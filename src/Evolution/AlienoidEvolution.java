package evolution;

import java.util.ArrayList;
import java.util.Random;

import dice.Dice;
import dice.diceresult.DiceResult;
import gamelogic.Monster;
import view.SendMessage;

public class AlienoidEvolution {
    private SendMessage sendMessage;
    private ArrayList<Monster> monsters;
    private static Random random;
    private int result;
    DiceResult diceResult;

    public AlienoidEvolution(ArrayList<Monster> monsters, SendMessage sendMessage) {
        random = new Random();
        this.result = 0;
        this.sendMessage = sendMessage;
        this.monsters = monsters;

    }

    public void alienoidPowerUp(int i, Monster currentMonster) {
        result = getRandomNumberInRange(0, 1);

        if (result == 0) {
            alienScourge(i, currentMonster);
        } else if (result == 1) {
            funnyLookingButDangerous(i);
        }
    }

    private static int getRandomNumberInRange(int min, int max) throws IllegalArgumentException {
        if (min >= max) {
            throw new IllegalArgumentException("Error: Upper bound must be greater than lower bound!");
        }

        return random.nextInt((max - min) + 1) + min;
    }

    // Gain 2 STAR.
    private void alienScourge(int i, Monster currentMonster) {
        sendMessage.sendMessage(i, "POWERUP: Receive 2 stars\n");
        currentMonster.increaseStars(2);
    }

    // If you roll at least TWO TWO TWO each other Monster loses 1 HEART.
    private void funnyLookingButDangerous(int i) {
        try {
            if (diceResult.getResult().get(new Dice(2)).intValue() >= 3) {
                sendMessage.sendMessage(i, "POWERUP: All other Monsters lose 1 STAR\n");
                for (int mon = 0; mon < monsters.size(); mon++) {
                    if (mon != i) {
                        monsters.get(mon).decreaseStars(1);
                    }
                }
            }
        } catch (Exception Error) {
            sendMessage.sendMessage(i, "FAILED: Alienoid does not have enough TWO's for POWERUP\n");
        }
    }
}
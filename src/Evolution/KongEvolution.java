package evolution;

import java.util.ArrayList;
import java.util.Random;

import dice.diceresult.DiceResult;
import dice.Dice;
import gamelogic.*;
import view.SendMessage;

/**
 * @throws IllegalArgumentException 
 */
public class KongEvolution {
    private SendMessage sendMessage;
    private ArrayList<Monster> monsters;
    private static Random random;
    private int result;
    DiceResult diceResult;

    public KongEvolution(ArrayList<Monster> monsters, SendMessage sendMessage) {
        random = new Random();
        this.result = 0;
        this.sendMessage = sendMessage;
        this.monsters = monsters;

    }

    public void kongPowerUp(int i, Monster currentMonster) {
        // result = getRandomNumberInRange(0, 1);

        redDawn(i);
        // if (result == 0) {
        //     redDawn(i);
        // }
    }

    private static int getRandomNumberInRange(int min, int max) throws IllegalArgumentException {
        if (min >= max) {
            throw new IllegalArgumentException("Error: Upper bound must be greater than lower bound!");
        }

        return random.nextInt((max - min) + 1) + min;
    }

    // All other Monsters lose 2 HEART.
    private void redDawn(int i) {
        sendMessage.sendMessage(i, "POWERUP: Deal 2 damage to all others\n");
        for (int mon = 0; mon < monsters.size(); mon++) {
            if (mon != i) {
                monsters.get(mon).decreaseCurrentHealth(2);
            }
        }
    }
}
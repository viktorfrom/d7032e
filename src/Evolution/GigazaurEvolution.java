package evolution;

import java.util.ArrayList;
import java.util.Random;

import gamelogic.Monster;
import view.SendMessage;

public class GigazaurEvolution {
    private SendMessage sendMessage;
    private ArrayList<Monster> monsters;
    private static Random random;
    private int result;

    public GigazaurEvolution(ArrayList<Monster> monsters, SendMessage sendMessage) {
        random = new Random();
        this.result = 0;
        this.sendMessage = sendMessage;
        this.monsters = monsters;

    }

    public void gigazaurPowerUp(int i, Monster currentMonster) {
        result = getRandomNumberInRange(0, 2);

        if (result == 0) {
            radioactiveWaste(i, currentMonster);
        } else if (result == 1) {
            primalBellow(i);
        } else if (result == 2) {
            defenderOfTokyo(i, currentMonster);
        }
    }

    private static int getRandomNumberInRange(int min, int max) throws IllegalArgumentException {
        if (min >= max) {
            throw new IllegalArgumentException("Error: Upper bound must be greater than lower bound!");
        }

        return random.nextInt((max - min) + 1) + min;
    }

    // Gain 2 ENERGY and 1 HEART.
    private void radioactiveWaste(int i, Monster currentMonster) {
        sendMessage.sendMessage(i, "POWERUP: Receive 2 energy and 1 health\n");
        currentMonster.increaseEnergy(2);
        if (currentMonster.getCurrentHealth() + 1 >= currentMonster.getMaxHealth()) {
            currentMonster.setCurrentHealth(currentMonster.getMaxHealth());
        } else {
            currentMonster.increaseCurrentHealth(1);
        }
    }

    // All other Monsters lose 2 STAR.
    private void primalBellow(int i) {
        sendMessage.sendMessage(i, "POWERUP: All other Monsters lose 2 STAR\n");
        for (int mon = 0; mon < monsters.size(); mon++) {
            if (mon != i) {
                monsters.get(mon).decreaseStars(2);
            }
        }
    }

    // If you start your turn in Tokyo, all other Monsters lose 1 STAR.
    private void defenderOfTokyo(int i, Monster currentMonster) {
        sendMessage.sendMessage(i, "POWERUP: All other Monsters lose 2 STAR\n");
        if (currentMonster.getInTokyo() == true) {
            for (int mon = 0; mon < monsters.size(); mon++) {
                if (mon != i) {
                    monsters.get(mon).decreaseStars(1);
                }
            }
        }
    }
}
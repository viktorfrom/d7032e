package evolution;

import view.SendMessage;
import gamelogic.Monster;

import java.util.ArrayList;

public class EvolutionCard {
    private SendMessage sendMessage;
    private ArrayList<Monster> monsters;

    public EvolutionCard(ArrayList<Monster> monsters, SendMessage sendMessage) {
        this.sendMessage = sendMessage;
        this.monsters = monsters;
    }

    public void powerUp(int i, Monster currentMonster) {
        if (currentMonster.name.equals("Kong")) {
            kongPowerUp(i, currentMonster);
        }

        if (currentMonster.name.equals("Gigazaur")) {
            gigazaurPowerUp(i, currentMonster);
        }

        if (currentMonster.name.equals("Alienoid")) {
            alienoidPowerUp(i, currentMonster);
        }
    }

    private void kongPowerUp(int i, Monster currentMonster) {
        // Deal a power-up card to the currentMonster
        // Todo: Add support for more cards.
        // Current support is only for the Red Dawn card
        // Add support for keeping it secret until played
        sendMessage.sendMessage(i, "POWERUP: Deal 2 damage to all others\n");
        for (int mon = 0; mon < monsters.size(); mon++) {
            if (mon != i) {
                monsters.get(mon).currentHealth -= 2;
            }
        }
    }

    private void gigazaurPowerUp(int i, Monster currentMonster) {
        // Todo: Add support for more cards.
        // Current support is only for the Radioactive Waste
        // Add support for keeping it secret until played
        sendMessage.sendMessage(i, "POWERUP: Receive 2 energy and 1 health\n");
        currentMonster.energy += 2;
        if (currentMonster.currentHealth + 1 >= currentMonster.maxHealth) {
            currentMonster.currentHealth = currentMonster.maxHealth;
        } else {
            currentMonster.currentHealth += 1;
        }
    }

    private void alienoidPowerUp(int i, Monster currentMonster) {
        // Todo: Add support for more cards.
        // Current support is only for the Alien Scourge
        // Add support for keeping it secret until played
        sendMessage.sendMessage(i, "POWERUP: Receive 2 stars\n");
        currentMonster.stars += 2;
    }
}
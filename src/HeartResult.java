import java.util.ArrayList;
import java.util.HashMap;

public class HeartResult {
    private Dice aHeart;
    private ArrayList<Monster> monsters;
    private SendMessage sendMessage;
    private HashMap<Dice, Integer> result;

    public HeartResult(ArrayList<Monster> monsters, SendMessage sendMessage, HashMap<Dice, Integer> result) {
        aHeart = new Dice(Dice.getHEART());
        this.sendMessage = sendMessage;
        this.monsters = monsters;
        this.result = result;
    }

    public void heartResult(int i, Monster currentMonster) {
        if (result.containsKey(this.aHeart)) { // +1 currentHealth per heart, up to maxHealth
            if (currentMonster.currentHealth + result.get(this.aHeart).intValue() >= currentMonster.maxHealth) {
                currentMonster.currentHealth = currentMonster.maxHealth;
            } else {
                currentMonster.currentHealth += result.get(this.aHeart).intValue();
            }
            // 6b. 3 hearts = power-up
            if (result.get(this.aHeart).intValue() >= 3) {
                // Deal a power-up card to the currentMonster
                if (currentMonster.name.equals("Kong")) {
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
                if (currentMonster.name.equals("Gigazaur")) {
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
                if (currentMonster.name.equals("Alienoid")) {
                    // Todo: Add support for more cards.
                    // Current support is only for the Alien Scourge
                    // Add support for keeping it secret until played
                    sendMessage.sendMessage(i, "POWERUP: Receive 2 stars\n");
                    currentMonster.stars += 2;
                }
            }
        }
    }
}
import java.util.ArrayList;
import java.util.HashMap;

public class DiceResult {
    private ArrayList<Monster> monsters;
    private SendMessage sendMessage;
    private HashMap<Dice, Integer> result;
    private Store store;

    public DiceResult(ArrayList<Monster> monsters, SendMessage sendMessage, HashMap<Dice, Integer> result) {
        this.sendMessage = sendMessage;
        this.monsters = monsters;
        this.result = result;
    }

    public void diceResult(int i, Monster currentMonster, Deck deck) {
        // 6a. Hearts = health (max 10 unless a cord increases it)
        // 6c. 3 of a number = victory points
        heartResult(i, currentMonster);

        numberResult(i, currentMonster);
        clawResult(i, currentMonster);
        energyResult(currentMonster);
        // 7. Decide to buy things for energy
        deck.getStore().storeWindow(i, currentMonster, deck.getDeck().remove(0));

    }

    private void heartResult(int i, Monster currentMonster) {
        Dice aHeart = new Dice(Dice.getHEART());

        if (result.containsKey(aHeart)) { // +1 currentHealth per heart, up to maxHealth
            if (currentMonster.currentHealth + result.get(aHeart).intValue() >= currentMonster.maxHealth) {
                currentMonster.currentHealth = currentMonster.maxHealth;
            } else {
                currentMonster.currentHealth += result.get(aHeart).intValue();
            }
            // 6b. 3 hearts = power-up
            if (result.get(aHeart).intValue() >= 3) {
                // Deal a power-up card to the currentMonster
                if (currentMonster.name.equals("Kong")) {
                    // Todo: Add support for more cards.
                    // Current support is only for the Red Dawn card
                    // Add support for keeping it secret until played
                    sendMessage.sendMessage(i, "POWERUP: Deal 2 damage to all others\n");
                    for (int mon = 0; mon < monsters.size(); mon++) {
                        if (mon != i) {
                            monsters.get(mon).currentHealth += -2;
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

    private void numberResult(int i, Monster currentMonster) {
        for (int num = 1; num < 4; num++) {
            if (result.containsKey(new Dice(num)))
                if (result.get(new Dice(num)).intValue() >= 3)
                    currentMonster.stars += num + (result.get(new Dice(num)).intValue() - 3);
        }
    }

    private void clawResult(int i, Monster currentMonster) {
        // 6d. claws = attack (if in Tokyo attack everyone, else attack monster in
        // Tokyo)
        Dice aClaw = new Dice(Dice.getCLAWS());
        if (result.containsKey(aClaw)) {
            currentMonster.stars += currentMonster.cardEffect("starsWhenAttacking"); // Alpha Monster
            if (currentMonster.inTokyo) {
                for (int mon = 0; mon < monsters.size(); mon++) {
                    int moreDamage = currentMonster.cardEffect("moreDamage"); // Acid Attack
                    int totalDamage = result.get(aClaw).intValue() + moreDamage;
                    if (mon != i && totalDamage > monsters.get(mon).cardEffect("armor")) { // Armor Plating
                        monsters.get(mon).currentHealth += -totalDamage;
                    }
                }
            } else {
                boolean monsterInTokyo = false;
                for (int mon = 0; mon < monsters.size(); mon++) {
                    if (monsters.get(mon).inTokyo) {
                        monsterInTokyo = true;
                        int moreDamage = currentMonster.cardEffect("moreDamage"); // Acid Attack
                        int totalDamage = result.get(aClaw).intValue() + moreDamage;
                        if (totalDamage > monsters.get(mon).cardEffect("armor")) // Armor Plating
                            monsters.get(mon).currentHealth += -totalDamage;
                        // 6e. If you were outside, then the monster inside tokyo may decide to leave
                        // Tokyo
                        String answer = sendMessage.sendMessage(mon,
                                "ATTACKED:You have " + monsters.get(mon).currentHealth
                                        + " health left. Do you wish to leave Tokyo? [YES/NO]\n");
                        if (answer.equalsIgnoreCase("YES")) {
                            monsters.get(mon).inTokyo = false;
                            monsterInTokyo = false;
                        }
                    }
                }
                if (!monsterInTokyo) {
                    currentMonster.inTokyo = true;
                    currentMonster.stars += 1;
                }
            }
        }
    }

    private void energyResult(Monster currentMonster) {
        // 6f. energy = energy tokens
        Dice anEnergy = new Dice(Dice.getENERGY());
        if (result.containsKey(anEnergy))
            currentMonster.energy += result.get(anEnergy).intValue();
    }
    public HashMap<Dice, Integer> getResult(){
        return this.result;
    }
}
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Scanner;

public class KingTokyoPowerUpServer {

    /**
     * @param args the command line arguments
     * @throws IOException
     */
    public static void main(String[] args) throws IOException {
        // TODO code application logic here
        // https://www.youtube.com/watch?v=HqdOaAzPtek
        // https://boardgamegeek.com/thread/1408893/categorizing-cards
        new KingTokyoPowerUpServer();
    }


    private ArrayList<Monster> monsters;
    private Scanner scanner;
    private Deck deck;
    private SendMessage sendMessage;
    private DiceController diceController;
    private HashMap<Dice, Integer> result;
    private ServerConnection serverConnection;
    private WinCondition winCondition;

    public KingTokyoPowerUpServer() throws IOException {
        Monster kong = new Monster("Kong");
        Monster gigazaur = new Monster("Gigazaur");
        Monster alien = new Monster("Alienoid");
        monsters = new ArrayList<Monster>();
        monsters.add(kong);
        monsters.add(gigazaur);
        monsters.add(alien);
        serverConnection = new ServerConnection(monsters);
        result = new HashMap<Dice, Integer>();
        deck = new Deck();
        scanner = new Scanner(System.in);
        sendMessage = new SendMessage(monsters, scanner);
        diceController = new DiceController(sendMessage, result);
        winCondition = new WinCondition(monsters, sendMessage);

        // Shuffle which player is which monster
        Collections.shuffle(monsters);
        // Server stuffs
        serverConnection.connectToClient();
        // Shuffle the starting order
        Collections.shuffle(monsters);
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
        while (true) {
            for (int i = 0; i < monsters.size(); i++) {
                Monster currentMonster = monsters.get(i);
                if (currentMonster.currentHealth <= 0) {
                    currentMonster.inTokyo = false;
                    continue;
                }

                // pre: Award a monster in Tokyo 1 star
                if (currentMonster.inTokyo) {
                    currentMonster.stars += 1;
                }

                String statusUpdate = "You are " + currentMonster.name + " and it is your turn. Here are the stats";
                for (int count = 0; count < 3; count++) {
                    statusUpdate += ":" + monsters.get(count).name
                            + (monsters.get(count).inTokyo ? " is in Tokyo " : " is not in Tokyo ");
                    statusUpdate += "with " + monsters.get(count).currentHealth + " health, "
                            + monsters.get(count).stars + " stars, ";
                    statusUpdate += monsters.get(count).energy + " energy, and owns the following cards:";
                    statusUpdate += monsters.get(count).cardsToString();
                }

                sendMessage.sendMessage(i, statusUpdate + "\n");

                diceController.diceLogic(i);

                sendMessage.sendMessage(i, "ROLLED:You rolled " + result + " Press [ENTER]\n");
                // 6a. Hearts = health (max 10 unless a cord increases it)
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
                            sendMessage.sendMessage(i, "POWERUP:Deal 2 damage to all others\n");
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
                            sendMessage.sendMessage(i, "POWERUP:Receive 2 energy and 1 health\n");
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
                            sendMessage.sendMessage(i, "POWERUP:Receive 2 stars\n");
                            currentMonster.stars += 2;
                        }
                    }
                }
                // 6c. 3 of a number = victory points
                for (int num = 1; num < 4; num++) {
                    if (result.containsKey(new Dice(num)))
                        if (result.get(new Dice(num)).intValue() >= 3)
                            currentMonster.stars += num + (result.get(new Dice(num)).intValue() - 3);
                }
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
                                String answer = sendMessage.sendMessage(mon, "ATTACKED:You have " + monsters.get(mon).currentHealth
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
                // 6f. energy = energy tokens
                Dice anEnergy = new Dice(Dice.getENERGY());
                if (result.containsKey(anEnergy))
                    currentMonster.energy += result.get(anEnergy).intValue();
                // 7. Decide to buy things for energy
                String msg = "PURCHASE:Do you want to buy any of the cards from the store? (you have "
                        + currentMonster.energy + " energy) [#/-1]:" + deck + "\n";
                String answer = sendMessage.sendMessage(i, msg);
                int buy = Integer.parseInt(answer);
                if (buy > 0 && (currentMonster.energy >= (deck.getStoreCard(buy).getCost()
                        - currentMonster.cardEffect("cardsCostLess")))) { // Alien Metabolism
                    if (deck.getStoreCard(buy).getDiscard()) {
                        // 7a. Play "DISCARD" cards immediately
                        currentMonster.stars += deck.getStoreCard(buy).getEffect().getStars();
                    } else
                        currentMonster.cards.add(deck.getStoreCard(buy));
                    // Deduct the cost of the card from energy
                    currentMonster.energy += -(deck.getStoreCard(buy).getCost() - currentMonster.cardEffect("cardsCostLess")); // Alient
                                                                                                                   // Metabolism
                    // Draw a new card from the deck to replace the card that was bought
                    // deck.store[buy] = deck.deck.remove(0);
                    deck.removeStoreCard(buy);
                }
                winCondition.winCondition();
            }
        }
    }
}

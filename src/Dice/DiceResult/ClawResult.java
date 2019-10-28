package dice.diceresult;

import gamelogic.Monster;
import view.SendMessage;
import dice.Dice;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * The ClawResult class handles game claw dice result and game logic of players being
 * attacked outside or inside of Tokyo.
 **/
public class ClawResult {
    private ArrayList<Monster> monsters;
    private SendMessage sendMessage;
    private HashMap<Dice, Integer> result;

    public ClawResult(ArrayList<Monster> monsters, SendMessage sendMessage, HashMap<Dice, Integer> result) {
        this.sendMessage = sendMessage;
        this.monsters = monsters;
        this.result = result;
    }

    public void clawResult(int i, Monster currentMonster) {
        Dice aClaw = new Dice(Dice.getCLAWS());
        if (result.containsKey(aClaw)) {
            currentMonster.increaseStars(currentMonster.cardEffect("starsWhenAttacking")); 
            if (currentMonster.getInTokyo()) {
                for (int mon = 0; mon < monsters.size(); mon++) {
                    int moreDamage = currentMonster.cardEffect("moreDamage"); 
                    int totalDamage = result.get(aClaw).intValue() + moreDamage;
                    if (mon != i && totalDamage > monsters.get(mon).cardEffect("armor")) { 
                        monsters.get(mon).decreaseCurrentHealth(totalDamage);
                    }
                }
            } else {
                boolean monsterInTokyo = false;
                for (int mon = 0; mon < monsters.size(); mon++) {
                    if (monsters.get(mon).getInTokyo()) {
                        monsterInTokyo = true;
                        int moreDamage = currentMonster.cardEffect("moreDamage");
                        int totalDamage = result.get(aClaw).intValue() + moreDamage;
                        if (totalDamage > monsters.get(mon).cardEffect("armor")) 
                            monsters.get(mon).decreaseCurrentHealth(totalDamage);

                        String answer = sendMessage.sendMessage(mon,
                                "ATTACKED:You have " + monsters.get(mon).getCurrentHealth()
                                        + " health left. Do you wish to leave Tokyo? [YES/NO]\n");
                        if (answer.equalsIgnoreCase("YES")) {
                            monsters.get(mon).setInTokyo(false);
                            monsterInTokyo = false;
                        }
                    }
                }
                if (!monsterInTokyo) {
                    sendMessage.sendMessage(i, "No monster is currently in Tokyo, " + currentMonster.getName()
                            + " will wreck havoc in town! RAWR!\n");
                    currentMonster.setInTokyo(true);
                    currentMonster.increaseStars(1);
                }
            }
        }
    }
}
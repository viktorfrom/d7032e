import java.util.ArrayList;
import java.util.HashMap;

/**
 * If current player is in Tokyo, attack everyone, else player attacks monster
 * in Tokyo.
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
            currentMonster.stars += currentMonster.cardEffect("starsWhenAttacking"); // Alpha Monster
            monsterOutsideTokyo(aClaw, i, currentMonster);
        }
    }

    private void monsterOutsideTokyo(Dice aClaw, int i, Monster currentMonster) {
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
            attackMonsterInTokyo(aClaw, currentMonster, monsterInTokyo);
            tokyoEmpty(currentMonster, monsterInTokyo);
        }
    }

    private void attackMonsterInTokyo(Dice aClaw, Monster currentMonster, Boolean monsterInTokyo) {
        for (int mon = 0; mon < monsters.size(); mon++) {
            if (monsters.get(mon).inTokyo) {
                monsterInTokyo = true;
                int moreDamage = currentMonster.cardEffect("moreDamage"); // Acid Attack
                int totalDamage = result.get(aClaw).intValue() + moreDamage;
                if (totalDamage > monsters.get(mon).cardEffect("armor")) // Armor Plating
                    monsters.get(mon).currentHealth += -totalDamage;
                // 6e. If you were outside, then the monster inside tokyo may decide to leave
                // Tokyo
                monsterInTokyoLeave(mon, currentMonster, monsterInTokyo);
            }
        }
    }

    private void monsterInTokyoLeave(int mon, Monster currentMonster, Boolean monsterInTokyo) {

        String answer = sendMessage.sendMessage(mon, "ATTACKED:You have " + monsters.get(mon).currentHealth
                + " health left. Do you wish to leave Tokyo? [YES/NO]\n");
        if (answer.equalsIgnoreCase("YES")) {
            monsters.get(mon).inTokyo = false;
            monsterInTokyo = false;
        }
    }

    private void tokyoEmpty(Monster currentMonster, Boolean monsterInTokyo) {
        if (!monsterInTokyo) {
            currentMonster.inTokyo = true;
            currentMonster.stars += 1;
        }
    }
}

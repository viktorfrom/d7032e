package dice.diceresult;

import dice.Dice;
import gamelogic.Monster;
import view.SendMessage;
import evolution.*;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * The HeartResult class handles game energy dice result.
 **/
public class HeartResult {
    private Dice aHeart;
    private HashMap<Dice, Integer> result;
    private EvolutionCard evolutionCard;

    public HeartResult(ArrayList<Monster> monsters, SendMessage sendMessage, HashMap<Dice, Integer> result) {
        evolutionCard = new EvolutionCard(monsters, sendMessage);
        aHeart = new Dice(Dice.getHEART());
        this.result = result;
    }

    public void heartResult(int i, Monster currentMonster) {
        if (result.containsKey(this.aHeart)) {
            if (!currentMonster.getInTokyo()) {
                if (currentMonster.getCurrentHealth() + result.get(this.aHeart).intValue() >= currentMonster
                        .getMaxHealth()) {
                    currentMonster.setCurrentHealth(currentMonster.getMaxHealth());
                } else {
                    currentMonster.increaseCurrentHealth(result.get(this.aHeart).intValue());
                }
            }
            threeHearts(i, currentMonster);
        }
    }

    private void threeHearts(int i, Monster currentMonster) {
        if (result.get(this.aHeart).intValue() >= 3) {
            evolutionCard.powerUp(i, currentMonster);
        }
    }
}
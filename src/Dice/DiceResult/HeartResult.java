package Dice.DiceResult;

import Dice.Dice;
import GameLogic.Monster;
import View.SendMessage;
import Evolution.*;

import java.util.ArrayList;
import java.util.HashMap;

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
        if (result.containsKey(this.aHeart)) { // +1 currentHealth per heart, up to maxHealth
            if (currentMonster.currentHealth + result.get(this.aHeart).intValue() >= currentMonster.maxHealth) {
                currentMonster.currentHealth = currentMonster.maxHealth;
            } else {
                currentMonster.currentHealth += result.get(this.aHeart).intValue();
            }
            threeHearts(i, currentMonster);
        }
    }

    private void threeHearts(int i, Monster currentMonster) {
        // 6b. 3 hearts = power-up
        if (result.get(this.aHeart).intValue() >= 3) {
            evolutionCard.powerUp(i, currentMonster);
        }
    }
}
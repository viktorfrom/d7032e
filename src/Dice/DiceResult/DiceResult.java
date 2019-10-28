package dice.diceresult;

import dice.Dice;
import gamelogic.Monster;
import view.SendMessage;
import deck.Deck;

import java.util.ArrayList;
import java.util.HashMap;

public class DiceResult {
    private HashMap<Dice, Integer> result;
    private HeartResult heartResult;
    private NumberResult numberResult;
    private ClawResult clawResult;
    private EnergyResult energyResult;


    public DiceResult(ArrayList<Monster> monsters, SendMessage sendMessage, HashMap<Dice, Integer> result) {
        heartResult = new HeartResult(monsters, sendMessage, result);
        numberResult = new NumberResult(result);
        clawResult = new ClawResult(monsters, sendMessage, result);
        energyResult = new EnergyResult(result);
        this.result = result;
    }

    public void diceResult(int i, Monster currentMonster, Deck deck) {
        heartResult.heartResult(i, currentMonster);

        numberResult.numberResult(i, currentMonster);
        clawResult.clawResult(i, currentMonster);
        energyResult.energyResult(currentMonster);

        deck.getStore().storeWindow(i, currentMonster, deck.getDeck().remove(0));

    }

    public HashMap<Dice, Integer> getResult(){
        return this.result;
    }

}
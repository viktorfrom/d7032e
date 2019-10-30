package dice.diceresult;

import dice.Dice;
import gamelogic.Monster;

import java.util.HashMap;

/**
 * The NumberResult class handles game number dice result.
 **/
public class NumberResult {
    private HashMap<Dice, Integer> result;

    public NumberResult(HashMap<Dice, Integer> result) {
        this.result = result;
    }

    public void numberResult(int i, Monster currentMonster) {
        for (int num = 1; num < 4; num++) {
        System.out.println("test containsKey " + result.containsKey(new Dice(num))) ;
            if (result.containsKey(new Dice(num))) {
                threeOfNum(num, currentMonster);
            }
        }
    }

    public void threeOfNum(int num, Monster currentMonster) {
        if (result.get(new Dice(num)).intValue() >= 3) {
            currentMonster.increaseStars(num + (result.get(new Dice(num)).intValue() - 3));
        }
    }

}

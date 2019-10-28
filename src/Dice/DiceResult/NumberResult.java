package Dice.DiceResult;

import Dice.Dice;
import GameLogic.Monster;

import java.util.HashMap;

public class NumberResult {
    private HashMap<Dice, Integer> result;

    public NumberResult(HashMap<Dice, Integer> result) {
        this.result = result;
    }

    public void numberResult(int i, Monster currentMonster) {
        for (int num = 1; num < 4; num++) {
            if (result.containsKey(new Dice(num)))
                if (result.get(new Dice(num)).intValue() >= 3)
                    currentMonster.stars += num + (result.get(new Dice(num)).intValue() - 3);
        }
    }
}

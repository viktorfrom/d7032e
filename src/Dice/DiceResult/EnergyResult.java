package dice.diceresult;

import dice.Dice;
import gamelogic.Monster;

import java.util.HashMap;

/**
 * The EnergyResult class handles game energy dice result.
 **/
public class EnergyResult {
    private Dice anEnergy;
    private HashMap<Dice, Integer> result;

    public EnergyResult(HashMap<Dice, Integer> result) {
        anEnergy = new Dice(Dice.getENERGY());
        this.result = result;
    }

    public void energyResult(Monster currentMonster) {
        if (result.containsKey(this.anEnergy))
            currentMonster.increaseEnergy(result.get(anEnergy).intValue());
    }
}

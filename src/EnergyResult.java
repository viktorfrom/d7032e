import java.util.HashMap;

public class EnergyResult {
    private Dice anEnergy;
    private HashMap<Dice, Integer> result;

    public EnergyResult(HashMap<Dice, Integer> result) {
        anEnergy = new Dice(Dice.getENERGY());
        this.result = result;
    }

    public void energyResult(Monster currentMonster) {
        if (result.containsKey(this.anEnergy))
            currentMonster.energy += result.get(anEnergy).intValue();
    }
}

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Random;

public class DiceController {
    private ArrayList<Dice> dice;
    private static Random random = new Random();
    private SendMessage sendMessage;
    private String rolledDice;
    private String[] reroll;
    private HashMap<Dice, Integer> result;

    public DiceController(SendMessage sendMessage, HashMap<Dice, Integer> result) {
        this.sendMessage = sendMessage;
        this.result = result;
        this.rolledDice = "";
    }

    public void diceLogic(int i) {
        this.dice = diceRoll(6);
        rolledDice();
        rerollDice(i);
        this.dice.addAll(diceRoll(6 - this.dice.size()));

        rolledDice();
        rerollDice(i);
        this.dice.addAll(diceRoll(6 - this.dice.size()));
        diceResult();
        Collections.sort(dice);
    }

    private void rerollDice(int i) {
        this.rolledDice += ":Choose which dice to reroll, separate with comma and in decending order (e.g. 5,4,1   0 to skip)\n";
        reroll = sendMessage.sendMessage(i, rolledDice).split(",");
        if (Integer.parseInt(reroll[0]) != 0)
            for (int j = 0; j < reroll.length; j++) {
                dice.remove(Integer.parseInt(reroll[j]) - 1);
            }
    }

    private void diceResult() {
        for (Dice unique : new HashSet<Dice>(getDice())) {
            this.result.put(unique, Collections.frequency(getDice(), unique));
        }
    }

    private void rolledDice() {
        this.rolledDice = "ROLLED:You rolled:\t[1]\t[2]\t[3]\t[4]\t[5]\t[6]:";
        for (int allDice = 0; allDice < dice.size(); allDice++) {
            this.rolledDice += "\t[" + dice.get(allDice) + "]";
        }
    }

    public static ArrayList<Dice> diceRoll(int nrOfDice) {
        ArrayList<Dice> dice = new ArrayList<Dice>();
        for (int i = 0; i < nrOfDice; i++) {
            dice.add(new Dice(random.nextInt(6)));
        }
        return dice;
    }

    public ArrayList<Dice> getDice() {
        return this.dice;
    }
}
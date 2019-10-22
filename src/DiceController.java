import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

public class DiceController {
    private ArrayList<Dice> dice;
    private static Random random = new Random();
    private SendMessage sendMessage;

    public DiceController(SendMessage sendMessage) {
       this.sendMessage = sendMessage; 
    }

    public void diceLogic() {
        for (int i = 0; i < 1; i++) {
        // 1. Roll 6 dice
        // ArrayList<Dice> dice = new ArrayList<Dice>();
        dice = diceRoll(6);
        // 2. Decide which dice to keep
        String rolledDice = "ROLLED:You rolled:\t[1]\t[2]\t[3]\t[4]\t[5]\t[6]:";
        for (int allDice = 0; allDice < dice.size(); allDice++) {
            rolledDice += "\t[" + dice.get(allDice) + "]";
        }
        rolledDice += ":Choose which dice to reroll, separate with comma and in decending order (e.g. 5,4,1   0 to skip)\n";
        String[] reroll = sendMessage.sendMessage(i, rolledDice).split(",");
        if (Integer.parseInt(reroll[0]) != 0)
            for (int j = 0; j < reroll.length; j++) {
                dice.remove(Integer.parseInt(reroll[j]) - 1);
            }
        // 3. Reroll remaining dice
        dice.addAll(diceRoll(6 - dice.size()));
        // 4. Decide which dice to keep
        rolledDice = "ROLLED:You rolled:\t[1]\t[2]\t[3]\t[4]\t[5]\t[6]:";
        for (int allDice = 0; allDice < dice.size(); allDice++) {
            rolledDice += "\t[" + dice.get(allDice) + "]";
        }
        rolledDice += ":Choose which dice to reroll, separate with comma and in decending order (e.g. 5,4,1   0 to skip)\n";
        reroll = sendMessage.sendMessage(i, rolledDice).split(",");
        if (Integer.parseInt(reroll[0]) != 0)
            for (int j = 0; j < reroll.length; j++) {
                dice.remove(Integer.parseInt(reroll[j]) - 1);
            }
        // 5. Reroll remaining dice
        dice.addAll(diceRoll(6 - dice.size()));
        // 6. Sum up totals
        Collections.sort(dice);
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
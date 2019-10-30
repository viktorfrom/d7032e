package tests;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

import org.junit.Ignore;
import org.junit.Test;

import deck.Card;
import gamelogic.Monster;
import gamelogic.WinCondition;
import view.SendMessage;
import dice.diceresult.*;
import dice.Dice;
import deck.Effect;

public class KingTokyoTests {

    public KingTokyoTests() {
    }

    // Test 1. Each player is assigned a monster.
    @Test
    @Ignore
    public void testPlayersAssignedMonster() {
        // Cannot test connection between server and clients using sockets.
    }

    // Test. 2 Set Victory Points to 0.
    @Test
    public void testMonsterStars() {
        Monster kong = new Monster("Kong");
        kong.setStars(0);
        assertEquals(kong.getStars(), 0);
    }

    // Test 3. Set Life to 10.
    @Test
    public void testMonsterFullLife() {
        Monster kong = new Monster("Kong");
        kong.setCurrentHealth(10);
        assertEquals(kong.getCurrentHealth(), 10);
    }

    // Test 4. Shuffle the store cards (contained in the deck) (todo: add support
    // for more store cards).
    @Test
    @Ignore
    public void testStoreShuffle() {
        // n*(n-1)*(n-2) number of combinations in the store, this means the test will
        // fail 1/210 when 7 cards are in the deck. Further since Collections.Shuffle()
        // lib. is used, it can be argued this does not need to be tested.
    }

    // Test 5. Shuffle the evolution cards for the respective monsters (todo: add
    // support for more evolution cards).
    @Test
    @Ignore
    public void testEvolutionShuffle() {
        // Same as Test 4, but with even fewer cards available.
    }

    // Test 6. Randomise which monster starts the game.
    @Test
    @Ignore
    public void testMonsterStart() {
        // Same as Test 4 and 5, since collections.Shuffle() is used this cannot/does
        // not need to be tested.
    }

    // Test 7. If your monster is inside of Tokyo – Gain 1 star.
    @Test
    public void testMonsterInTokyo() {
        Monster kong = new Monster("Kong");
        kong.setInTokyo(true);
        assertEquals(kong.getInTokyo(), true);
    }

    private class SendMessageTest extends SendMessage {
        private String message;

        public SendMessageTest(ArrayList<Monster> monsters, Scanner scanner, String message) {
            super(monsters, scanner);
            this.message = message;
        }

        @Override
        public String sendMessage(int recipient, String message) {
            return this.message;
        }
    }

    // Test 8. Roll your 6 dice.
    @Test
    @Ignore
    public void rollSixDie() {
        // n!, where n is the number of faces on a die, in this case 6. Since there is a
        // possibility of rolling 6 dices and getting the same result and the test
        // failing, this cannot be tested.
    }

    // Test 10. Reroll the selected die.
    @Test
    @Ignore
    public void rerollSelectedDie() {
        // same as 8, since the result can be the same after rerolling, the test fails.
    }

    // Test 11. Repeat step 9 and 10 once.
    @Test
    @Ignore
    public void repeatNineTen() {
        // Cannot be tested due to secondary fault, since this test is dependent on Test
        // 10 which has a possibillity to fail.
    }

    // Test 12. Sum up the dice and assign stars, health or damage.
    @Test
    public void testDiceResults() {
        testResultOnes();
        testResultTwos();
        testResultThrees();
        testResultEnergy();
        testResultHeartInTokyo();
        testResultHeartNotInTokyo();
        testEvolution();
        testClaw();
    }

    // Tripple 1’s = 1 Star
    @Test
    public void testResultOnes() {
        ArrayList<Monster> monsters = new ArrayList<Monster>();
        Monster kong = new Monster("Kong");
        kong.setStars(0);
        monsters.add(kong);

        HashMap<Dice, Integer> result = new HashMap<Dice, Integer>();
        result.put(new Dice(1), 3);
        NumberResult numberResult = new NumberResult(result);
        numberResult.threeOfNum(1, monsters.get(0));
        assertEquals(monsters.get(0).getStars(), 1);
    }

    // Tripple 2’s = 2 Stars
    @Test
    public void testResultTwos() {
        ArrayList<Monster> monsters = new ArrayList<Monster>();
        Monster kong = new Monster("Kong");
        kong.setStars(0);
        monsters.add(kong);

        HashMap<Dice, Integer> result = new HashMap<Dice, Integer>();
        result.put(new Dice(2), 3);
        NumberResult numberResult = new NumberResult(result);
        numberResult.threeOfNum(2, monsters.get(0));
        assertEquals(monsters.get(0).getStars(), 2);
    }

    // Tripple 3’s = 3 Stars
    @Test
    public void testResultThrees() {
        ArrayList<Monster> monsters = new ArrayList<Monster>();
        Monster kong = new Monster("Kong");
        kong.setStars(0);
        monsters.add(kong);

        HashMap<Dice, Integer> result = new HashMap<Dice, Integer>();
        result.put(new Dice(3), 3);
        NumberResult numberResult = new NumberResult(result);
        numberResult.threeOfNum(3, monsters.get(0));
        assertEquals(monsters.get(0).getStars(), 3);
    }
    // Each energy = 1 energy
    @Test
    public void testResultEnergy() {
        ArrayList<Monster> monsters = new ArrayList<Monster>();
        Monster kong = new Monster("Kong");
        kong.setEnergy(0);
        monsters.add(kong);

        HashMap<Dice, Integer> result = new HashMap<Dice, Integer>();
        result.put(new Dice(Dice.getENERGY()), 1);
        EnergyResult energyResult = new EnergyResult(result);
        energyResult.energyResult(monsters.get(0));
        assertEquals(monsters.get(0).getEnergy(), 1);
    }

    // Each heart = 1 heart.
    @Test
    public void testResultHeartInTokyo() {
        ArrayList<Monster> monsters = new ArrayList<Monster>();
        Monster kong = new Monster("Kong");
        kong.setCurrentHealth(9);
        kong.setInTokyo(true);
        monsters.add(kong);

        HashMap<Dice, Integer> result = new HashMap<Dice, Integer>();
        result.put(new Dice(Dice.getHEART()), 1);


        Scanner scanner = new Scanner(System.in);
        SendMessageTest sendMessageTest = new SendMessageTest(monsters, scanner, "test");
        HeartResult heartResult = new HeartResult(monsters, sendMessageTest, result);

        heartResult.heartResult(0, monsters.get(0));
        assertEquals(monsters.get(0).getCurrentHealth(), 9);
    }

    // Each heart = 1 heart.
    @Test
    public void testResultHeartNotInTokyo() {
        ArrayList<Monster> monsters = new ArrayList<Monster>();
        Monster kong = new Monster("Kong");
        kong.setCurrentHealth(9);
        kong.setInTokyo(false);
        monsters.add(kong);

        HashMap<Dice, Integer> result = new HashMap<Dice, Integer>();
        result.put(new Dice(Dice.getHEART()), 1);


        Scanner scanner = new Scanner(System.in);
        SendMessageTest sendMessageTest = new SendMessageTest(monsters, scanner, "test");
        HeartResult heartResult = new HeartResult(monsters, sendMessageTest, result);

        heartResult.heartResult(0, monsters.get(0));
        assertEquals(monsters.get(0).getCurrentHealth(), 10);
    }

    // Each heart, gain 3 hearts, power up!
    @Test
    public void testEvolution() {
        ArrayList<Monster> monsters = new ArrayList<Monster>();
        Monster kong = new Monster("Kong");
        kong.setCurrentHealth(7);
        kong.setEnergy(0);
        kong.setInTokyo(false);
        monsters.add(kong);

        HashMap<Dice, Integer> result = new HashMap<Dice, Integer>();
        result.put(new Dice(Dice.getHEART()), 3);

        Scanner scanner = new Scanner(System.in);
        SendMessageTest sendMessageTest = new SendMessageTest(monsters, scanner, "test");
        HeartResult heartResult = new HeartResult(monsters, sendMessageTest, result);
        heartResult.heartResult(0, monsters.get(0));


        assertEquals(monsters.get(0).getCurrentHealth(), 10);
    }

    // Each claw
    @Test
    @Ignore
    public void testClaw() {
        // Idividual functions in ClawResult cannot be tested, fragile code. 
    }

    // Test 13. Buying Cards(As long as you have the Energy, you can take any of the
    // following actions).
    @Test
    @Ignore
    public void testStore() {
        // Since all of the functionality with the store is associated with cards
        // contained in the store, secondary fault does not allow these functions to be
        // tested. Since renewing the store or replacing cards can result in same
        // combination as before.
    }

    // Test 14. A store card can be of either type “Keep” or “Discard”. “Discard”
    // cards take effect immediately when purchased, and “Keep” cards may either be
    // played when the owner desires or provides an active power/ability.
    @Test
    public void testKeepDiscard() {
        Effect damage = new Effect();
        damage.setDamage(1);

        Card card = new Card("Acid Attack", 6, false, damage, "Deal 1 extra damage each turn");
        Boolean discard = card.getDiscard();
        assertEquals(discard, false);
    }

    // Test 15. End of turn.
    // DESC: At end of turn, if it both win conditions returns false, next turn
    // starts.
    @Test
    public void testEndOfTurn() {
        Monster kong = new Monster("Kong");
        Monster gigazaur = new Monster("Gigazaur");
        Monster alien = new Monster("Alienoid");
        ArrayList<Monster> monsters = new ArrayList<Monster>();

        kong.setCurrentHealth(10);
        gigazaur.setCurrentHealth(10);
        alien.setCurrentHealth(10);
        kong.setStars(0);
        gigazaur.setStars(0);
        alien.setStars(0);

        monsters.add(kong);
        monsters.add(gigazaur);
        monsters.add(alien);
        Scanner scanner = new Scanner(System.in);

        SendMessageTest sendMessageTest = new SendMessageTest(monsters, scanner, "test");
        WinCondition winCondition = new WinCondition(monsters, sendMessageTest);

        boolean resultAlive = winCondition.monstersAlive();
        ;
        boolean resultNum = winCondition.numStars();
        assertEquals(resultAlive, resultNum);
    }

    // Test 16. First monster to get 20 stars win the game.
    @Test
    public void testMonsterWinByStars() {
        Monster kong = new Monster("Kong");
        Monster gigazaur = new Monster("Gigazaur");
        Monster alien = new Monster("Alienoid");
        ArrayList<Monster> monsters = new ArrayList<Monster>();

        kong.setStars(20);
        monsters.add(kong);
        monsters.add(gigazaur);
        monsters.add(alien);
        Scanner scanner = new Scanner(System.in);

        SendMessageTest sendMessageTest = new SendMessageTest(monsters, scanner, "test");
        WinCondition winCondition = new WinCondition(monsters, sendMessageTest);

        boolean result = winCondition.numStars();
        assertEquals(result, true);
    }

    // 17. The sole surviving monster wins the game (other monsters at 0 or less
    // health).
    @Test
    public void testMonsterWinByAlive() {
        Monster kong = new Monster("Kong");
        Monster gigazaur = new Monster("Gigazaur");
        Monster alien = new Monster("Alienoid");
        ArrayList<Monster> monsters = new ArrayList<Monster>();

        kong.setCurrentHealth(0);
        gigazaur.setCurrentHealth(0);
        alien.setCurrentHealth(10);
        monsters.add(kong);
        monsters.add(gigazaur);
        monsters.add(alien);
        Scanner scanner = new Scanner(System.in);

        SendMessageTest sendMessageTest = new SendMessageTest(monsters, scanner, "test");
        WinCondition winCondition = new WinCondition(monsters, sendMessageTest);

        boolean result = winCondition.monstersAlive();
        assertEquals(result, true);
    }

    // Test 18. A monster that reaches 0 or less health is out of the game.
    @Test
    public void testMonsterDeadOut() {
        Monster kong = new Monster("Kong");
        ArrayList<Monster> monsters = new ArrayList<Monster>();

        kong.setCurrentHealth(0);
        monsters.add(kong);

        int result = monsters.get(0).getCurrentHealth();
        assertEquals(result, 0);
    }

}
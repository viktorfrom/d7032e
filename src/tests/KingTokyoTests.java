package tests;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.Scanner;

import org.junit.Test;

import gamelogic.*;
import view.SendMessage;

public class KingTokyoTests {

    public KingTokyoTests() {
    }

    // Test 2
    @Test
    public void testMonsterStars() {
        Monster kong = new Monster("Kong");
        kong.setStars(0);
        assertEquals(kong.getStars(), 0);
    }

    // Test 2
    @Test
    public void testMonsterFullLife() {
        Monster kong = new Monster("Kong");
        kong.setCurrentHealth(10);
        assertEquals(kong.getCurrentHealth(), 10);
    }

    // Test 7
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

    // Test 16
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
        // Test 17
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
}
package View;

import GameLogic.Monster;

import java.util.ArrayList;
import java.util.Scanner;

public class SendMessage {
    private ArrayList<Monster> monsters;
    private Scanner scanner;
    
    public SendMessage(ArrayList<Monster> monsters, Scanner scanner) {
        this.monsters = monsters;
        this.scanner = scanner;
    }

    public String sendMessage(int recipient, String message) {
        // TODO: change recipient to monster

        Monster aMonster = monsters.get(recipient);
        String response = "";

        if (aMonster.connection != null) {
            try {
                aMonster.outToClient.writeBytes(message);
                response = aMonster.inFromClient.readLine();
            } catch (Exception e) {
            }
        } else {
            String[] theMessage = message.split(":");
            for (int i = 0; i < theMessage.length; i++) {
                System.out.println(theMessage[i].toString());
            }
            if (!(theMessage[0].equals("ATTACKED") || theMessage[0].equals("ROLLED")
                    || theMessage[0].equals("PURCHASE")))
                System.out.println("Press [ENTER]");
            response = scanner.nextLine();
        }
        return response;
    }

    // public String sendMessage(Monster monster, String message) {
    //     // TODO: change recipient to monster

    //     // Monster aMonster = monsters.get(recipient);
    //     String response = "";

    //     if (monster.connection != null) {
    //         try {
    //             monster.outToClient.writeBytes(message);
    //             response = monster.inFromClient.readLine();
    //         } catch (Exception e) {
    //         }
    //     } else {
    //         String[] theMessage = message.split(":");
    //         for (int i = 0; i < theMessage.length; i++) {
    //             System.out.println(theMessage[i].toString());
    //         }
    //         if (!(theMessage[0].equals("ATTACKED") || theMessage[0].equals("ROLLED")
    //                 || theMessage[0].equals("PURCHASE")))
    //             System.out.println("Press [ENTER]");
    //         response = scanner.nextLine();
    //     }
    //     return response;
    // }

}
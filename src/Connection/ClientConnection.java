package connection;

import java.util.*;
import java.io.*;
import java.util.Scanner;

/**
 * The ClientConnection class sets up a client and handles the client communication.
 * @throws IOException
 **/
public class ClientConnection {
    private boolean bot;
    private Scanner scanner = new Scanner(System.in);

    public ClientConnection(boolean bot) throws IOException {
        this.bot = bot;
    }

    public void serverCommunication(Random rnd, DataOutputStream outToServer, BufferedReader inFromServer)
            throws IOException {
        while (true) {
            String[] message = inFromServer.readLine().split(":");
            for (int i = 0; i < message.length; i++) {
                System.out.println(message[i].toString());
            }
            if (message[0].equalsIgnoreCase("VICTORY")) {
                outToServer.writeBytes("Bye!\n");
            } else if (message[0].equalsIgnoreCase("ATTACKED")) {
                attackedClient(outToServer);
            } else if (message[0].equalsIgnoreCase("ROLLED")) {
                rerollClient(outToServer);
            } else if (message[0].equalsIgnoreCase("PURCHASE")) {
                purchaseClient(outToServer);
            } else {
                noChoiceClient(outToServer);
            }
            System.out.println("\n");
        }
    }

    private void attackedClient(DataOutputStream outToServer) throws IOException {
        if (bot)
            outToServer.writeBytes("YES\n");
        else {
            outToServer.writeBytes(scanner.nextLine() + "\n");
        }
    }

    private void rerollClient(DataOutputStream outToServer) throws IOException {
        if (bot)
            outToServer.writeBytes("YES\n");
        else {
            outToServer.writeBytes(scanner.nextLine() + "\n");
        }
    }

    private void purchaseClient(DataOutputStream outToServer) throws IOException {
        if (bot)
            outToServer.writeBytes("-1\n");
        else
            outToServer.writeBytes(scanner.nextLine() + "\n");
    }

    private void noChoiceClient(DataOutputStream outToServer) throws IOException {
        if (bot)
            outToServer.writeBytes("OK\n");
        else {
            System.out.println("Press [ENTER]");
            scanner.nextLine();
            outToServer.writeBytes("OK\n");
        }
    }
}
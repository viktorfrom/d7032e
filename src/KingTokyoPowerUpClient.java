import java.util.*;
import java.io.*;
import java.net.*;
import java.util.concurrent.ThreadLocalRandom;
import java.util.Scanner;

public class KingTokyoPowerUpClient {
    private boolean bot;
    private Scanner scanner = new Scanner(System.in);

    public KingTokyoPowerUpClient(boolean bot) throws IOException {
        this.bot = bot;
        connectToServer();
    }

    private void connectToServer() throws IOException {
        String name = "";
        Random rnd = ThreadLocalRandom.current();

        try {
            Socket aSocket = new Socket("localhost", 2048);
            DataOutputStream outToServer = new DataOutputStream(aSocket.getOutputStream());
            BufferedReader inFromServer = new BufferedReader(new InputStreamReader(aSocket.getInputStream()));
            name = inFromServer.readLine();
            System.out.println("Connection established to server! " + name);
            serverCommunication(rnd, outToServer, inFromServer);

        } catch (IOException Error) {
            System.out.println("Error: Could not establish connection to server." + Error);

        }
    }

    private void serverCommunication(Random rnd, DataOutputStream outToServer, BufferedReader inFromServer)
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

    public static void main(String argv[]) throws IOException {
        if (argv.length != 0)
            new KingTokyoPowerUpClient(true);
        else
            new KingTokyoPowerUpClient(false);
    }
}
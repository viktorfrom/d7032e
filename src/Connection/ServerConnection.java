package connection;

import gamelogic.Monster;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

/**
 * The ServerConnection class sets up a server requiring a minimum of two
 * clients to connect before the game can start.
 **/
public class ServerConnection {
    private ArrayList<Monster> monsters;

    public ServerConnection(ArrayList<Monster> monsters) {
        this.monsters = monsters;
    }

    public void connectToClient() throws IOException {
        System.out.println(
                "Server up and running, atleast two clients needs to connect to start the game. Trying to establish connection to client(s)...");
        try {
            ServerSocket aSocket = new ServerSocket(2048);
            // assume two online clients
            for (int onlineClient = 0; onlineClient < 2; onlineClient++) {
                Socket connectionSocket = aSocket.accept();
                BufferedReader inFromClient = new BufferedReader(
                        new InputStreamReader(connectionSocket.getInputStream()));
                DataOutputStream outToClient = new DataOutputStream(connectionSocket.getOutputStream());
                outToClient.writeBytes("You are the monster: " + this.monsters.get(onlineClient).getName() + "\n");

                this.monsters.get(onlineClient).setConnection(connectionSocket);
                this.monsters.get(onlineClient).setInFromClient(inFromClient);
                this.monsters.get(onlineClient).setOutToClient(outToClient);

                System.out.println("Eastablished connection to " + this.monsters.get(onlineClient).getName());
            }
            aSocket.close();

        } catch (IOException Error) {
            System.out.println("Error: Could not establish connection to client(s)." + Error);

        }
    }
}
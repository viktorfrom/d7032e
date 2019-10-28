package Connection;

import java.util.*;
import java.io.*;
import java.net.*;
import java.util.concurrent.ThreadLocalRandom;

public class PowerUpClient {
    private ClientConnection clientConnection;

    public PowerUpClient(boolean bot) throws IOException {
        clientConnection = new ClientConnection(bot);
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
            clientConnection.serverCommunication(rnd, outToServer, inFromServer);

            aSocket.close();
        } catch (IOException Error) {
            System.out.println("Error: Could not establish connection to server." + Error);

        }
    }

    public static void main(String argv[]) throws IOException {
        if (argv.length != 0) {
            new PowerUpClient(true);
        } else {
            new PowerUpClient(false);
        }
    }

}
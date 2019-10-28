package connection;

import gamelogic.Monster;
import gamelogic.GameLogic;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;

/**
 * The PowerUpServer class initiates server details and handles communication
 * between clients.
 **/
public class PowerUpServer {
    private ArrayList<Monster> monsters;
    private ServerConnection serverConnection;
    private GameLogic gameLogic;

    public PowerUpServer() throws IOException {
        monsters = new ArrayList<Monster>();
        gameLogic = new GameLogic(monsters);
        serverConnection = new ServerConnection(monsters);
        Collections.shuffle(monsters);
        serverConnection.connectToClient();
        Collections.shuffle(monsters);

        while (true) {
            gameLogic.playerTurn();
        }
    }

    /**
     * @param args the command line arguments
     * @throws IOException
     */
    public static void main(String[] args) throws IOException {
        new PowerUpServer();
    }
}

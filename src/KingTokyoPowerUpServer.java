import java.io.IOException;
import java.util.ArrayList;

public class KingTokyoPowerUpServer {
    private ArrayList<Monster> monsters;
    private ServerConnection serverConnection;
    private GameLogic gameLogic;

    public KingTokyoPowerUpServer() throws IOException {
        monsters = new ArrayList<Monster>();
        gameLogic = new GameLogic(monsters);
        serverConnection = new ServerConnection(monsters);
        serverConnection.connectToClient();

        while (true) {
            gameLogic.playerTurn();
        }
    }

    /**
     * @param args the command line arguments
     * @throws IOException
     */
    public static void main(String[] args) throws IOException {
        new KingTokyoPowerUpServer();
    }
}

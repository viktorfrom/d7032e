import java.net.Socket;
import java.io.BufferedReader;
import java.util.ArrayList;
import java.io.DataOutputStream;

/**
    The Monster class initiates player avatars at the start of the game.
**/
public class Monster {
    ArrayList<Card> cards = new ArrayList<Card>();
    public String name;
    public int energy, stars, maxHealth, currentHealth;
    public boolean inTokyo;
    public Socket connection;
    public BufferedReader inFromClient;
    public DataOutputStream outToClient;

    public Monster(String name) {
        this.name = name;
        this.energy = 0;
        this.stars = 0;
        this.maxHealth = 10;
        this.currentHealth = 10;
        this.inTokyo = false;
        this.connection = null;
        this.inFromClient = null;
        this.outToClient = null;
    }

    // search all available cards and return the effect value of an effect
    public int cardEffect(String effectName) {
        for (int i = 0; i < cards.size(); i++) {
            try {
                // Find variable by "name"
                if (Effect.class.getField(effectName).getInt(cards.get(i).getEffect()) > 0) {
                    return Effect.class.getField(effectName).getInt(cards.get(i).getEffect());
                }
            } catch (Exception e) {
            }
        }
        return 0;
    }

    public String cardsToString() {
        String returnString = "";
        if (cards.size() == 0)
            return "[NO CARDS]:";
        for (int i = 0; i < cards.size(); i++) {
            returnString += "\t[" + i + "] " + cards.get(i) + ":";
        }
        return returnString;
    }
}
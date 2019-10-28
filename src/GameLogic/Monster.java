package gamelogic;

import deck.*;

import java.net.Socket;
import java.io.BufferedReader;
import java.util.ArrayList;
import java.io.DataOutputStream;

/**
 * The Monster class initiates player avatars at the start of the game.
 **/
public class Monster {
    public ArrayList<Card> cards = new ArrayList<Card>();
    private String name;
    private int energy, stars, maxHealth, currentHealth;
    private boolean inTokyo;
    private Socket connection;
    private BufferedReader inFromClient;
    private DataOutputStream outToClient;

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

    public int cardEffect(String effectName) {
        for (int i = 0; i < cards.size(); i++) {
            try {
                if (Effect.class.getField(effectName).getInt(cards.get(i).getEffect()) > 0) {
                    return Effect.class.getField(effectName).getInt(cards.get(i).getEffect());
                }
            } catch (Exception Error) {
            }
        }
        return 0;
    }

    // The toString() method returns the string representation of the object.
    public String cardsToString() {
        String returnString = "";
        if (cards.size() == 0)
            return "[NO CARDS]:";
        for (int i = 0; i < cards.size(); i++) {
            returnString += "\t[" + i + "] " + cards.get(i) + ":";
        }
        return returnString;
    }

    public void setCurrentHealth(int health) {
        this.currentHealth = health;
    }

    public void setInTokyo(boolean bool) {
        this.inTokyo =  bool;
    }

    public void setConnection(Socket socket) {
        this.connection = socket;
    }    
    public void setInFromClient(BufferedReader buffer) {
        this.inFromClient = buffer; 
    }

    public void setOutToClient(DataOutputStream data) {
        this.outToClient = data; 
    } 

    public void increaseStars(int stars) {
        this.stars += stars;
    }

    public void increaseEnergy(int energy) {
        this.energy += energy;
    }

    public void increaseCurrentHealth(int health) {
        this.currentHealth += health;
    }

    public void decreaseEnergy(int energy) {
        this.energy -= energy;
    }

    public void decreaseCurrentHealth(int health) {
        this.currentHealth -= health;
    }

    public String getName() {
        return this.name;
    }

    public int getEnergy() {
        return this.energy;
    }

    public int getStars() {
        return this.stars;
    }

    public int getMaxHealth() {
        return this.maxHealth;
    }

    public int getCurrentHealth() {
        return this.currentHealth;
    }
    
    public boolean getInTokyo() {
        return this.inTokyo;
    }  

    public Socket getConnection() {
        return this.connection;
    }    
    public BufferedReader getInFromClient() {
        return this.inFromClient;
    }

    public DataOutputStream getOutToClient() {
        return this.outToClient;
    } 
}
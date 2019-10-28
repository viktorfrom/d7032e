package view;

import java.util.ArrayList;

import deck.Card;
import gamelogic.Monster;

/**
 * The Store class draws 3 random cards from the Card class and draws a simple interface for the store.
 **/
public class Store {
    private Card[] store;
    private SendMessage sendMessage;

    public Store(SendMessage sendMessage) {
        this.store = new Card[3];
        this.sendMessage = sendMessage;
    }

    public void storeWindow(int i, Monster currentMonster, ArrayList<Card> deck) {
        String msg = "PURCHASE: Do you want to buy any of the cards from the store? (you have "
                + currentMonster.getEnergy() + " energy) [#/-1]:" + this + "\n";
        String answer = sendMessage.sendMessage(i, msg);
        int num = Integer.parseInt(answer);
        if (num > 0 && (currentMonster
                .getEnergy() >= (store[num].getCost() - currentMonster.cardEffect("cardsCostLess")))) { 
            if (store[num].getDiscard()) {
                currentMonster.increaseStars(store[num].getEffect().getStars());
            } else
                currentMonster.cards.add(store[num]);
            currentMonster.decreaseEnergy(store[num].getCost() - currentMonster.cardEffect("cardsCostLess"));

            // Draw a new card from the deck to replace the card that was bought
            setStoreCard(num, deck.remove(0));
        }

    }

    public Card getStoreCard(int card) {
        return this.store[card];
    }

    public void setStoreCard(int cardIndex, Card card) {
        this.store[cardIndex] = card;
    }

    // The toString() method returns the string representation of the object.
    public String toString() {
        String returnString = "";
        for (int i = 0; i < 3; i++) {
            returnString += "\t[" + i + "] " + this.store[i] + ":";
        }
        return returnString;
    }
}
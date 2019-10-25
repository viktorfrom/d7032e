
public class Store {
    // private Card[] store = new Card[3];
    private Card[] store;
    private SendMessage sendMessage;

    public Store(SendMessage sendMessage) {
        this.store = new Card[3];
        this.sendMessage = sendMessage;
    }

    public void storeWindow(int i, Monster currentMonster, Card card) {
        String msg = "PURCHASE: Do you want to buy any of the cards from the store? (you have " + currentMonster.energy
                + " energy) [#/-1]:" + this + "\n";
        String answer = sendMessage.sendMessage(i, msg);
        int num = Integer.parseInt(answer);
        if (num > 0 && (currentMonster.energy >= (store[num].getCost() - currentMonster.cardEffect("cardsCostLess")))) { // Alien
                                                                                                                         // Metabolism
            if (store[num].getDiscard()) {
                // 7a. Play "DISCARD" cards immediately
                currentMonster.stars += store[num].getEffect().getStars();
            } else
                currentMonster.cards.add(store[num]);
            // Deduct the cost of the card from energy
            currentMonster.energy -= (store[num].getCost() - currentMonster.cardEffect("cardsCostLess")); // Alient

            // Draw a new card from the deck to replace the card that was bought
            setStoreCard(num, card);
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
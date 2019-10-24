import java.util.ArrayList;
import java.util.Collections;

public class Deck {
    private ArrayList<Card> deck = new ArrayList<Card>();
    private Card[] store = new Card[3];

    private Effect damage = new Effect();
    private Effect cardPrice = new Effect();
    private Effect starsOnAttack = new Effect();
    private Effect armor = new Effect();
    private Effect gainOneStars = new Effect();
    private Effect gainTwoStars = new Effect();
    private Effect gainThreeStars = new Effect();

    public Deck() {
        damage.setDamage(1);
        cardPrice.setCardPrice(1);
        starsOnAttack.setStarsOnAttack(1);
        armor.setArmor(1);
        gainOneStars.setStars(1);
        gainTwoStars.setStars(2);
        gainThreeStars.setStars(3);

        initDeck();
    }

    private void initDeck() {
        deck.add(new Card("Acid Attack", 6, false, damage, "Deal 1 extra damage each turn"));
        deck.add(new Card("Alien Metabolism", 3, false, cardPrice, "Buying cards costs you 1 less"));
        deck.add(new Card("Alpha Monster", 5, false, starsOnAttack, "Gain 1 star when you attack"));
        deck.add(new Card("Apartment Building", 5, true, gainThreeStars, "+3 stars"));
        deck.add(new Card("Armor Plating", 4, false, armor, "Ignore damage of 1"));
        deck.add(new Card("Commuter Train", 4, true, gainTwoStars, "+2 stars"));
        deck.add(new Card("Corner Stone", 3, true, gainOneStars, "+1 stars"));
        // Todo: Add more cards
        Collections.shuffle(deck);
        // Start the game with 3 cards face up in the store
        for (int i = 0; i < 3; i++) {
            store[i] = deck.remove(0);
        }
    }

    // Print the store
    public String toString() {
        String returnString = "";
        for (int i = 0; i < 3; i++) {
            returnString += "\t[" + i + "] " + store[i] + ":";
        }
        return returnString;
    }
    public Effect getDamage() {
        return this.damage;
    }

    public void removeStoreCard(int card) {
        store[card] = deck.remove(0);
    }

    public Card getStoreCard(int card) {
        return this.store[card];
    }

    public ArrayList<Card> getDeck() {
        return this.deck;
    }
}
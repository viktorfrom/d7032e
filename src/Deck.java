import java.util.ArrayList;
import java.util.Collections;

class Deck {
    public ArrayList<Card> deck = new ArrayList<Card>();
    public Card[] store = new Card[3];

    public Deck() {
        // Effect moreDamage, cardPrice, starsOnAttack, armor, stars1, stars2, stars3 = new Effect();

        Effect moreDamage = new Effect();
        Effect cardsCostLess = new Effect();
        Effect starsWhenAttacking = new Effect();
        Effect stars3 = new Effect();
        Effect armor = new Effect();
        Effect stars2 = new Effect();
        Effect stars1 = new Effect();

        moreDamage.setDamage(1);
        cardsCostLess.setCardPrice(1);
        starsWhenAttacking.setStarsOnAttack(1);
        stars3.setStars(3);
        armor.setArmor(1);
        stars2.setStars(2);
        stars1.setStars(1);

        deck.add(new Card("Acid Attack", 6, false, moreDamage, "Deal 1 extra damage each turn"));
        deck.add(new Card("Alien Metabolism", 3, false, cardsCostLess, "Buying cards costs you 1 less"));
        deck.add(new Card("Alpha Monster", 5, false, starsWhenAttacking, "Gain 1 star when you attack"));
        deck.add(new Card("Apartment Building", 5, true, stars3, "+3 stars"));
        deck.add(new Card("Armor Plating", 4, false, armor, "Ignore damage of 1"));
        deck.add(new Card("Commuter Train", 4, true, stars2, "+2 stars"));
        deck.add(new Card("Corner Stone", 3, true, stars1, "+1 stars"));
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
}
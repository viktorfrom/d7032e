package Deck;

import View.SendMessage;
import View.Store;

import java.util.ArrayList;
import java.util.Collections;

public class Deck {
    private ArrayList<Card> deck = new ArrayList<Card>();
    private Store store;

    private Effect damage = new Effect();
    private Effect cardPrice = new Effect();
    private Effect starsOnAttack = new Effect();
    private Effect armor = new Effect();
    private Effect gainOneStars = new Effect();
    private Effect gainTwoStars = new Effect();
    private Effect gainThreeStars = new Effect();

    public Deck(SendMessage sendMessage) {
        store = new Store(sendMessage);
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
            store.setStoreCard(i, deck.remove(0));
        }
    }

    public Store getStore() {
        return this.store;
    }

    public Effect getDamage() {
        return this.damage;
    }

    public ArrayList<Card> getDeck() {
        return this.deck;
    }
}
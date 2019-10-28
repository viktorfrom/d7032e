package Deck;
    
/**
    The Effect class initiates values of card effects and can be modified using setters.
**/
public class Effect {
    private int damage;
    private int cardPrice; 
    private int starsOnAttack; 
    private int stars; 
    private int armor; 
    
    public Effect() {
        this.damage = 0;
        this.cardPrice = 0;
        this.starsOnAttack = 0;
        this.stars = 0;
        this.armor = 0;
    }

    public void setDamage(int damage) {
        this.damage = damage;
    }
    
    public void setCardPrice(int price) {
        this.cardPrice = price;
    }

    public void setStarsOnAttack(int stars) {
        this.starsOnAttack = stars;
    }

    public void setStars(int stars) {
        this.stars = stars;
    }

    public void setArmor(int armor) {
        this.armor = armor;
    }
    public int getDamage() {
        return this.damage;
    }

    public int getCardsPrice() {
        return this.cardPrice;
    }

    public int getStarOnAttack() {
        return this.starsOnAttack;
    }
    
    public int getStars() {
        return this.stars;
    }

    public int getArmor() {
        return this.armor;
    }
}
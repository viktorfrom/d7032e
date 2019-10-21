public class Card {
    private String name;
    private int cost;
    private boolean discard;
    private Effect effect;
    private String description;

    public Card(String name, int cost, boolean discard, Effect effect, String description) {
        this.name = name;
        this.cost = cost;
        this.discard = discard;
        this.effect = effect;
        this.description = description;
    }
    public String toString() {
        return name + ", Cost " + cost + ", " + (discard ? "DISCARD" : "KEEP") + ", Effect " + description;
    }

    public String getName() {
        return this.name;
    }
    
    public int getCost() {
        return this.cost;
    }

    public Boolean getDiscard() {
        return this.discard;
    }

    public Effect getEffect() {
        return this.effect;
    }

    public String getDescription() {
        return this.description;
    }
}
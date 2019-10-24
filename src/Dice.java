public class Dice implements Comparable<Dice> {
    private static final int HEART = 0;
    private static final int ENERGY = 4;
    private static final int CLAWS = 5;
    private int value = -1;

    public Dice(int value) {
        this.value = value;
    }

    // ENERGY, SHOVES DICE RESULTS IF WRITTEN WITH AN EXTRA "E"
    public String toString() {
        if (value == HEART) {
            return "HEART";
        } else if ( value == ENERGY) {
            return "ENRGY";
        } else if (value == CLAWS) {
            return "CLAWS";
        } else if (value == 1) {
            return "ONE";
        } else if (value == 2) {
            return "TWO";
        } else {
            return "THREE";
        }
    }

    @Override
    public int compareTo(Dice dice) throws NullPointerException  {
        if (value < dice.value) {
            return -1;
        } else if ( value == dice.value) {
            return 0;
        } else {
            return 1;
        } 
    }

    public boolean equals(Object dice) {
        return value == ((Dice) dice).value;
    }

    public int hashCode() {
        return toString().hashCode();
    }

    public static int getHEART() {
        return HEART;
    }

    public static int getENERGY() {
        return ENERGY;
    }

    public static int getCLAWS() {
        return CLAWS;
    }
}
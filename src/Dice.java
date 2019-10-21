
public class Dice implements Comparable<Dice> {
    private static final int HEART = 0;
    private static final int ENERGY = 4;
    private static final int CLAWS = 5;
    private int value = -1;

    public Dice(int value) {
        this.value = value;
    }

    public String toString() {
        return (value == HEART ? "HEART"
                : value == ENERGY ? "ENRGY"
                        : value == CLAWS ? "CLAWS" : value == 1 ? "ONE" : value == 2 ? "TWO" : "THREE");
    }

    @Override
    public int compareTo(Dice o) {
        return value < o.value ? -1 : value == o.value ? 0 : 1;
    }

    public boolean equals(Object o) {
        return value == ((Dice) o).value;
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
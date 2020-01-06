package risk;

public abstract class Dice implements Comparable<Dice> {

    protected Integer value;

    public Dice() {
        this.value = null;
    }

    public Integer getValue() {
        return value;
    }

    public void setValue(Integer value) {
        this.value = value;
    }

    @Override
    public int compareTo(Dice otherDice) {
        return this.value.compareTo(otherDice.value);
    }

    @Override
    public String toString() {
        return "Dice value: " + this.value;
    }

    public abstract Integer roll();

}

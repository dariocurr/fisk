package risk;

import java.util.*;

public class ClassicDice implements Comparable<ClassicDice> {

    protected Integer value;

    public ClassicDice() {
        this.value = null;
    }

    public Integer getValue() {
        return value;
    }

    public void setValue(Integer value) {
        this.value = value;
    }

    public Integer roll() {
        Random random = new Random();
        this.value = random.nextInt(6);
        return ++this.value;
    }

    @Override
    public int compareTo(ClassicDice otherClassicDice) {
        return this.value.compareTo(otherClassicDice.value);
    }

    @Override
    public String toString() {
        return "Dice value: " + this.value;
    }

}

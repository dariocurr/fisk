package risk;

/**
    Classe che modella un dado.
*/

public abstract class Dice implements Comparable<Dice> {

    protected Integer value;

    public Dice() {
        this.value = null;
    }

    /**
        Restituisce il valore di un dado.
        @return valore di questo dado
    */
    public Integer getValue() {
        return value;
    }

    /**
        Setta il valore di questo dado.
        @param value valore per questo dado
    */
    public void setValue(Integer value) {
        this.value = value;
    }

    /**
        Confronto basato sul valore del dado.
    */
    @Override
    public int compareTo(Dice otherDice) {
        return this.value.compareTo(otherDice.value);
    }

    @Override
    public String toString() {
        return "Dice value: " + this.value;
    }

    /**
        Lancia un dado, settandone quindi il valore.
    */
    public abstract Integer roll();

}

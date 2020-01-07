package risk;

/**
 * Classe che modella un dado.
 */
public abstract class Dice implements Comparable<Dice> {

    protected Integer value;

    /**
     * Istanzia un dado
     */
    public Dice() {
        this.value = null;
    }

    /**
     * Restituisce il valore del dado.
     *
     * @return il valore del dado
     */
    public Integer getValue() {
        return value;
    }

    /**
     * Setta il valore del dado.
     *
     * @param value valore del dado
     */
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

    /**
     * Lancia il dado, settandone quindi il valore.
     *
     * @return il valore del dado
     */
    public abstract Integer roll();

}

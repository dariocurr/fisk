package fisk.card;

/**
 * Classe parametrica che modella un tipo di carta.
 *
 * @param <T> tipo della carta
 */
public abstract class Card<T> {

    protected T card;

    /**
     * Istanzia un tipo di carta.
     *
     * @param card carta da istanziare
     */
    public Card(T card) {
        this.card = card;
    }

    /**
     * Restituisce questa carta.
     *
     * @return questa carta
     */
    public T getCard() {
        return this.card;
    }

    @Override
    public String toString() {
        return this.card.toString();
    }

}

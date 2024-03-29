package fisk.deck;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import fisk.card.Card;

/**
 * Classe che modella un mazzo di carte.
 *
 * @param <T> tipo di carte contenute nel mazzo
 */
public abstract class Deck<T extends Card<?>> implements Iterable<T> {

    protected List<T> deck;

    /**
     * Istanzia un mazzo di carte.
     */
    public Deck() {
        this.deck = new ArrayList<>();
    }

    @Override
    public String toString() {
        return "card deck: " + this.deck.toString();
    }

    /**
     * Restituisce, rimuovendo, la prima carta.
     *
     * @return carta estratta
     */
    public T removeCard() {
        return this.deck.remove(0);
    }

    /**
     * Aggiunge una carta al mazzo.
     *
     * @param card carta da aggiungere
     */
    public void addCard(T card) {
        this.deck.add(card);
    }

    /**
     * Verifica se il mazzo è vuoto.
     *
     * @return true se il mazzo è vuoto, false altrimenti.
     */
    public boolean isEmpty() {
        return this.deck.isEmpty();
    }

    @Override
    public Iterator<T> iterator() {
        return this.deck.iterator();
    }

}

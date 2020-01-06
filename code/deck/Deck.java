package risk;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public abstract class Deck<T> implements Iterable<T> {

    protected List<T> deck;

    public Deck() {
        this.deck = new ArrayList<>();
    }

    @Override
    public String toString() {
        return "card deck: " + this.deck.toString();
    }

    public T removeCard() {
        return this.deck.remove(0);
    }

    public void addCard(T card) {
        this.deck.add(card);
    }

    public boolean isEmpty() {
        return this.deck.isEmpty();
    }

    @Override
    public Iterator<T> iterator() {
        return this.deck.iterator();
    }

}

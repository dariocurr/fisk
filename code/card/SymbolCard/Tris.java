package risk;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

public abstract class Tris implements Iterable<SymbolCard> {

    protected final List<SymbolCard> cards;

    public Tris(SymbolCard firstCard, SymbolCard secondCard, SymbolCard thirdCard) {
        this.cards = new ArrayList<>(3);
        this.cards.add(firstCard);
        this.cards.add(secondCard);
        this.cards.add(thirdCard);
        Collections.sort(this.cards);
    }

    @Override
    public String toString() {
        return "Tris: " + this.cards;
    }

    public List<SymbolCard> getCards() {
        return this.cards;
    }

    @Override
    public Iterator<SymbolCard> iterator() {
        return this.cards.iterator();
    }
    
    @Override
    public abstract boolean equals(Object obj);

}

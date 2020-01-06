package risk;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

/**
    Classe che rappresenta un tris.
*/

public abstract class Tris implements Iterable<SymbolCard> {

    protected final List<SymbolCard> cards;

    /**
        Costruttore del tris, che prende a parametro la prima, la seconda e la terza carta.
        @param firstCard prima carta
        @param secondCard seconda carta
        @param thirdCard terza carta
    */
    public Tris(SymbolCard firstCard, SymbolCard secondCard, SymbolCard thirdCard) {
        this.cards = new ArrayList<>(3);
        this.cards.add(firstCard);
        this.cards.add(secondCard);
        this.cards.add(thirdCard);
        Collections.sort(this.cards);
    }

    /**
        Restituisce una stringa che rappresenta un tris.
    */
    @Override
    public String toString() {
        return "Tris: " + this.cards;
    }

    /**
        Restituisce la lista con le tre carte che compongono il tris.
        @return lista delle carte
    */
    public List<SymbolCard> getCards() {
        return this.cards;
    }

    /**
        Implementa l'interfaccia iterable.
    */
    @Override
    public Iterator<SymbolCard> iterator() {
        return this.cards.iterator();
    }

    @Override
    public abstract boolean equals(Object obj);

}

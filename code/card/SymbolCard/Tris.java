package risk;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Tris {

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

    @Override
    public boolean equals(Object obj) {
        if (obj != null) {
            if (obj instanceof Tris) {
                Tris otherTris = (Tris) obj;
                return this.cards.equals(otherTris.cards);
            }
        }
        return false;
    }

    public List<SymbolCard> getCards() {
        return this.cards;
    }

}


/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package risk;

import java.util.*;

/**
 *
 * @author dario
 */
public class Tris {

    private final List<SymbolCard> cards;

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

    public SymbolCard getFirstCard() {
        return this.cards.get(0);
    }

    public SymbolCard getSecondCard() {
        return this.cards.get(1);
    }

    public SymbolCard getThirdCard() {
        return this.cards.get(2);
    }

    public List<SymbolCard> getCards() {
        return this.cards;
    }

}

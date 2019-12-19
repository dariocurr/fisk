/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package risk;

/**
 *
 * @author dario
 */
public class SymbolCard extends Card<Symbol> implements Comparable<SymbolCard> {

    public SymbolCard(Symbol symbol) {
        super(symbol);
    }

    @Override
    public boolean equals(Object obj) {
        if (obj != null) {
            if (obj instanceof SymbolCard) {
                SymbolCard otherSymbolCard = (SymbolCard) obj;
                return this.card.equals(otherSymbolCard.card);
            }
        }
        return false;
    }

    @Override
    public int compareTo(SymbolCard otherSymbolCard) {
        return this.card.compareTo(otherSymbolCard.card);
    }

}

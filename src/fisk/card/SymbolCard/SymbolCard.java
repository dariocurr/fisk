package fisk.card.SymbolCard;

import fisk.card.Card;

/**
 * Classe che rappresenta una carta simbolo.
 */
public abstract class SymbolCard extends Card<Symbol> implements Comparable<SymbolCard> {

    /**
     * Istanzia una carta simbolo.
     *
     * @param symbol simbolo della carta
     */
    public SymbolCard(Symbol symbol) {
        super(symbol);
    }

    @Override
    public String toString() {
        return super.toString();
    }

    @Override
    public int compareTo(SymbolCard otherSymbolCard) {
        return this.card.compareTo(otherSymbolCard.card);
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

}

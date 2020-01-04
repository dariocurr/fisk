package risk;

public abstract class SymbolCard extends Card<Symbol> implements Comparable<SymbolCard> {

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
    public abstract boolean equals(Object obj);

}

package risk;

public class ConcreteSymbolCard extends SymbolCard {

    public ConcreteSymbolCard(Symbol symbol) {
        super(symbol);
    }

    @Override
    public boolean equals(Object obj) {
        if (obj != null) {
            if (obj instanceof ConcreteSymbolCard) {
                ConcreteSymbolCard otherSymbolCard = (ConcreteSymbolCard) obj;
                return this.card.equals(otherSymbolCard.card);
            }
        }
        return false;
    }

}

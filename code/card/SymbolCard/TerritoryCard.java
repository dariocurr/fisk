package risk;

public class TerritoryCard extends SymbolCard {

    protected final Territory territory;

    public TerritoryCard(Symbol symbol, Territory territory) {
        super(symbol);
        this.territory = territory;
    }

    public Territory getTerritory() {
        return this.territory;
    }

    @Override
    public String toString() {
        return "Territory " + super.toString() + " " + this.territory.toString();
    }

}

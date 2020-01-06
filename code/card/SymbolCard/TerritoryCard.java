package risk;

/**
    Classe che rappresenta una carta dei territori.
*/

public abstract class TerritoryCard extends SymbolCard {

    protected final Territory territory;

    /**
        Costruttore della carta del territorio specificato a parametro.
        @param symbol simbolo della carta
        @param territory territorio della carta
    */
    public TerritoryCard(Symbol symbol, Territory territory) {
        super(symbol);
        this.territory = territory;
    }

    /**
        Restituisce il territorio associato a questa carta.
        @return il territorio di questa carta
    */
    public Territory getTerritory() {
        return this.territory;
    }

    @Override
    public String toString() {
        return super.toString() + " " + this.territory.getName();
    }

    @Override
    public abstract boolean equals(Object obj);

}

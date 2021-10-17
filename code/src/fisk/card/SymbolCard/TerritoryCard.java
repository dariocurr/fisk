package fisk.card.SymbolCard;

import fisk.territory.Territory;

/**
 * Classe che rappresenta una carta territorio.
 */
public abstract class TerritoryCard extends SymbolCard {

    protected final Territory territory;

    /**
     * Istanzia una carta territorio.
     *
     * @param symbol    simbolo della carta
     * @param territory territorio della carta
     */
    public TerritoryCard(Symbol symbol, Territory territory) {
        super(symbol);
        this.territory = territory;
    }

    /**
     * Restituisce il territorio associato a questa carta.
     *
     * @return il territorio di questa carta
     */
    public Territory getTerritory() {
        return this.territory;
    }

    @Override
    public String toString() {
        return super.toString() + " " + this.territory.getName();
    }

}

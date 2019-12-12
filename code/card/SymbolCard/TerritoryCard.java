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
public class TerritoryCard extends SymbolCard {

    private Territory territory;
    
    public TerritoryCard(Symbol symbol, Territory territory) {
        super(symbol);
        this.territory = territory;
    }

    public Territory getTerritory() {
        return this.territory;
    }

    @Override
    public String toString() {
        return super.toString() + " " + this.territory;
    }
    
}

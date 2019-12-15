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
public class SymbolDeck extends TerritoryDeck {

    public SymbolDeck(List<Territory> territories, int numJolly) {
        super(territories);
        for(int i = 0; i < numJolly; i++) {
            this.deck.add(new SymbolCard(Symbol.JOLLY));
        }
        Collections.shuffle(this.deck);
    }
    
    @Override
    public String toString() {
        return "Symbol&" + super.toString();
    }
    
}

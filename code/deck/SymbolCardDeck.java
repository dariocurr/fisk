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
public class SymbolCardDeck extends TerritoryCardDeck {

    public SymbolCardDeck(List<Territory> territory, int numJolly) {
        super(territory);
        for(int i = 0; i < numJolly; i++) {
            this.deck.add(new SymbolCard(Symbol.JOLLY));
        }
        Collections.shuffle(this.deck);
    }
    
}

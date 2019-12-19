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
public class SymbolDeck extends Deck<SymbolCard> {

    public SymbolDeck(TerritoryDeck territoryDeck) {
        for (TerritoryCard territoryCard : territoryDeck.deck) {
            this.deck.add(territoryCard);
        }
        for (int i = 0; i < 2; i++) {
            this.deck.add(new SymbolCard(Symbol.JOKER));
        }
        Collections.shuffle(this.deck);
    }

    @Override
    public String toString() {
        return "Symbol&" + super.toString();
    }

}

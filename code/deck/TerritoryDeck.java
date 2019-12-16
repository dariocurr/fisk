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
public class TerritoryDeck extends Deck<TerritoryCard> {

    public TerritoryDeck(List<Territory> territories) {
        super();
        for(int i = 0; i < territories.size();) {
            this.deck.add(new TerritoryCard(Symbol.BISHOP, territories.get(i++)));
            this.deck.add(new TerritoryCard(Symbol.CANNON, territories.get(i++)));
            this.deck.add(new TerritoryCard(Symbol.KNIGHT, territories.get(i++)));
        }
        Collections.shuffle(this.deck);
    }

    @Override
    public String toString() {
        return "TerritoryCardDeck: " + super.toString();
    }
    
}

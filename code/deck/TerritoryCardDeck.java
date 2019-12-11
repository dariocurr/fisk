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
public class TerritoryCardDeck extends Deck<TerritoryCard> {

    public TerritoryCardDeck(List<Territory> territory) {
        super();
        for(int i = 0; i < territory.size();) {
            this.deck.add(new TerritoryCard(Symbol.BISHOP, territory.get(i++)));
            this.deck.add(new TerritoryCard(Symbol.CANNON, territory.get(i++)));
            this.deck.add(new TerritoryCard(Symbol.KNIGHT, territory.get(i++)));
        }
        Collections.shuffle(deck);
    }
    
}

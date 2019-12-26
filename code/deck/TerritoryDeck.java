package risk;

import java.util.*;

public class TerritoryDeck extends Deck<TerritoryCard> {

    public TerritoryDeck(List<Territory> territories) {
        super();
        for (int i = 0; i < territories.size();) {
            this.deck.add(new TerritoryCard(Symbol.BISHOP, territories.get(i++)));
            this.deck.add(new TerritoryCard(Symbol.CANNON, territories.get(i++)));
            this.deck.add(new TerritoryCard(Symbol.KNIGHT, territories.get(i++)));
        }
        Collections.shuffle(this.deck);
    }

    @Override
    public String toString() {
        return "Territory " + super.toString();
    }

}

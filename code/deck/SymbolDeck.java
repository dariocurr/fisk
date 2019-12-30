package risk;

import java.util.Collections;

public class SymbolDeck extends Deck<SymbolCard> {

    public SymbolDeck(TerritoryDeck territoryDeck) {
        territoryDeck.deck.forEach((territoryCard) -> this.deck.add(territoryCard));
        for (int i = 0; i < 2; i++) {
            this.deck.add(new SymbolCard(Symbol.JOKER));
        }
        Collections.shuffle(this.deck);
    }

    @Override
    public String toString() {
        return "Symbol " + super.toString();
    }

}

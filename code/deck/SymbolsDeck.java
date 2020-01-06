package risk;

import java.util.Collections;

/**
    Classe che rappresenta il mazzo delle carte dei simboli.
*/

public abstract class SymbolsDeck extends Deck<SymbolCard> {

    public SymbolsDeck(TerritoriesDeck territoryDeck, Integer numberOfJokers) {
        for (TerritoryCard territoryCard : territoryDeck) {
            this.deck.add(territoryCard);
        }
        for (int i = 0; i < numberOfJokers; i++) {
            this.deck.add(new ConcreteSymbolCard(Symbol.JOKER));
        }
        Collections.shuffle(this.deck);
    }

    @Override
    public String toString() {
        return "Symbols " + super.toString();
    }

}

package fisk.deck;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import fisk.card.SymbolCard.ConcreteTerritoryCard;
import fisk.card.SymbolCard.Symbol;
import fisk.card.SymbolCard.TerritoryCard;
import fisk.territory.Territory;

/**
 * Classe che rappresenta il mazzo di carte territorio.
 */
public abstract class TerritoriesDeck extends Deck<TerritoryCard> {

    /**
     * Istanzia un mazzo di carte territorio.
     *
     * @param territories lista di territori da associare alle carte
     */
    public TerritoriesDeck(List<Territory> territories) {
        super();
        List<Symbol> symbols = new ArrayList<>();
        symbols.add(Symbol.BISHOP);
        symbols.add(Symbol.CANNON);
        symbols.add(Symbol.KNIGHT);
        for (int i = 0; i < territories.size(); i++) {
            this.deck.add(new ConcreteTerritoryCard(symbols.get(i % symbols.size()), territories.get(i)));
        }
        Collections.shuffle(this.deck);
    }

    @Override
    public String toString() {
        return "Territories " + super.toString();
    }

}

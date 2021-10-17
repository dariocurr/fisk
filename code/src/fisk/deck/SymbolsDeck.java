package fisk.deck;

import java.util.Collections;

import fisk.card.SymbolCard.ConcreteSymbolCard;
import fisk.card.SymbolCard.Symbol;
import fisk.card.SymbolCard.SymbolCard;
import fisk.card.SymbolCard.TerritoryCard;

/**
 * Classe che rappresenta il mazzo delle carte simbolo.
 */
public abstract class SymbolsDeck extends Deck<SymbolCard> {

    /**
     * Istanzia un mazzo di carte simbolo
     *
     * @param territoryDeck  il mazzo di carte territorio contenuto nel mazzo di
     *                       carte simbolo
     * @param numberOfJokers numero di jolly da aggiungere al mazzo di carte
     *                       territorio
     */
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

package fisk.deck;

/**
 * Concretizzazione del mazzo delle carte dei simboli.
 */
public class ConcreteSymbolsDeck extends SymbolsDeck {

    public ConcreteSymbolsDeck(TerritoriesDeck territoryDeck) {
        super(territoryDeck, 2);
    }

}

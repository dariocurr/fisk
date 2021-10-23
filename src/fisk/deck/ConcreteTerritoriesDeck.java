package fisk.deck;

import java.util.List;

import fisk.territory.Territory;

/**
 * Concretizzazione del mazzo delle carte dei territori.
 */
public class ConcreteTerritoriesDeck extends TerritoriesDeck {

    public ConcreteTerritoriesDeck(List<Territory> territories) {
        super(territories);
    }

}

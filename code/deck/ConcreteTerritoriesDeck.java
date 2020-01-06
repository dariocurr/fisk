package risk;

import java.util.Collections;
import java.util.List;

public class ConcreteTerritoriesDeck extends TerritoriesDeck {

    public ConcreteTerritoriesDeck(List<Territory> territories) {
        super();
        for (int i = 0; i < territories.size();) {
            this.deck.add(new ConcreteTerritoryCard(Symbol.BISHOP, territories.get(i++)));
            this.deck.add(new ConcreteTerritoryCard(Symbol.CANNON, territories.get(i++)));
            this.deck.add(new ConcreteTerritoryCard(Symbol.KNIGHT, territories.get(i++)));
        }
        Collections.shuffle(this.deck);
    }

}

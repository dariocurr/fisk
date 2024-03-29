package fisk.deck;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

import fisk.player.RiskColor;
import fisk.territory.Continent;

/**
 * Concretizzazione del mazzo delle carte degli obiettivi.
 */
public class ConcreteGoalsDeck extends GoalsDeck {

    public ConcreteGoalsDeck(List<Continent> continents, Integer numberOfTerritories) {
        super(numberOfTerritories);
        continents.stream().map((continent) -> {
            List<Continent> continentsToConquer = new ArrayList<>();
            continentsToConquer.add(continent);
            Random random = new Random();
            Continent otherContinent = continents.get(random.nextInt(continents.size()));
            while (continent.equals(otherContinent)) {
                otherContinent = continents.get(random.nextInt(continents.size()));
            }
            continentsToConquer.add(otherContinent);
            return continentsToConquer;
        }).forEach((goal) -> this.deck.add(this.goalCardFactory.createGoal(goal)));
        for (RiskColor color : RiskColor.values()) {
            this.deck.add(this.goalCardFactory.createGoal(color));
        }
        Collections.shuffle(this.deck);
    }

}

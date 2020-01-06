package risk;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class ConcreteGoalsDeck extends GoalsDeck {

    public ConcreteGoalsDeck(List<Continent> continents, List<Territory> territories) {
        this.deck.add(new NumberOfTerritoriesGoalCard(territories.size() / 2));
        this.deck.add(new NumberOfTerritoriesGoalCard(territories.size() / 7 * 3));
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
        }).forEach(ContinentsGoalCard::new);
        for (RiskColor color : RiskColor.values()) {
            this.deck.add(new KillGoalCard(color));
        }
        Collections.shuffle(this.deck);
    }

}

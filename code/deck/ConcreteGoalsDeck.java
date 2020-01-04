package risk;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class ConcreteGoalsDeck extends GoalsDeck{
    
    public ConcreteGoalsDeck(List<Continent> continents, List<Territory> territories) {
        this.deck.add(new NumberOfTerritoriesGoalCard(territories.size() / 2));
        this.deck.add(new NumberOfTerritoriesGoalCard(territories.size() / 7 * 3));
        continents.stream().map((continent) -> {
            List<Continent> temp = new ArrayList<>();
            temp.add(continent);
            Random random = new Random();
            Continent otherContinent = continents.get(random.nextInt(continents.size()));
            while (continent.equals(otherContinent)) {
                otherContinent = continents.get(random.nextInt(continents.size()));
            }
            temp.add(otherContinent);
            return temp;
        }).forEachOrdered((temp) -> {
            this.deck.add(new ContinentsGoalCard(temp));
        });
        for (RiskColor color : RiskColor.values()) {
            this.deck.add(new KillGoalCard(color));
        }
        Collections.shuffle(this.deck);
    }
    
}

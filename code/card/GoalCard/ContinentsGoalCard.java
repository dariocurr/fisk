package risk;

import java.util.List;

public class ContinentsGoalCard extends GoalCard<List<Continent>> {

    public ContinentsGoalCard(List<Continent> continents) {
        super(continents);
    }

    @Override
    public String toString() {
        return "Continents to conquer " + super.toString();
    }

}

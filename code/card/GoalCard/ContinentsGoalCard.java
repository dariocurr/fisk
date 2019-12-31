package risk;

import java.util.List;

public class ContinentsGoalCard extends GoalCard<List<Continent>> {

    public ContinentsGoalCard(List<Continent> continents) {
        super(continents);
    }

    @Override
    public String toString() {
        String goal = "Continents to conquer goal card: ";
        goal = this.card.stream().map((continent) -> continent.getName() + ", ").reduce(goal, String::concat);
        return goal.substring(0, goal.lastIndexOf(","));
    }

}

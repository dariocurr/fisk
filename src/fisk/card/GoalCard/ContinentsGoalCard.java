package fisk.card.GoalCard;

import java.util.List;

import fisk.territory.Continent;

/**
 * Carta obiettivo dei continenti da conquistare.
 */
public class ContinentsGoalCard extends GoalCard<List<Continent>> {

    /**
     * Istanzia una carta obiettivo dei continenti da conquistare.
     *
     * @param continents continenti da conquistare
     */
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

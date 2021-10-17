package fisk.card.GoalCard;

import java.util.List;

import fisk.player.RiskColor;
import fisk.territory.Continent;

/**
 * Classe che concretizza il concetto di Factory per creare le armate.
 */
@SuppressWarnings("unchecked")
public class ConcreteGoalCardFactory implements GoalCardFactory {

    /**
     * Istanzia una Factory di carte obiettivo.
     */
    public ConcreteGoalCardFactory() {
    }

    @Override
    public GoalCard<?> createGoal(Object goal) {
        if (goal instanceof List) {
            List<Continent> continentsToConquer = (List<Continent>) goal;
            return new ContinentsGoalCard(continentsToConquer);
        } else if (goal instanceof RiskColor) {
            RiskColor colorToKill = (RiskColor) goal;
            return new KillGoalCard(colorToKill);
        } else if (goal instanceof Integer) {
            Integer numberOfTerritoriesToConquer = (Integer) goal;
            return new NumberOfTerritoriesGoalCard(numberOfTerritoriesToConquer);
        }
        return null;
    }

}

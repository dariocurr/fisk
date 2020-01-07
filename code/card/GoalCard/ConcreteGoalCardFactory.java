package risk;

import java.util.List;

/**
 * Classe che concretizza il concetto di Factory per creare le armate.
 */
public class ConcreteGoalCardFactory implements GoalCardFactory {

    /**
     * Istanzia una Factory di carte obiettivo.
     */
    public ConcreteGoalCardFactory() {
    }

    @Override
    public GoalCard createGoal(Object goal) {
        if (goal instanceof List) {
            List continentsToConquer = (List) goal;
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

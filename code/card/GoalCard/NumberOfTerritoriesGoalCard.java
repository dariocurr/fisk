package risk;

/**
 * Carta obiettivo del numero di territori da conquistare.
 */
public class NumberOfTerritoriesGoalCard extends GoalCard<Integer> {

    /**
     * Istanzia una carta obiettivo del numero di territori da conquistare.
     *
     * @param numberOfTerritoriesToConquer numero di territori da conquistare
     */
    public NumberOfTerritoriesGoalCard(Integer numberOfTerritoriesToConquer) {
        super(numberOfTerritoriesToConquer);
    }

    @Override
    public String toString() {
        return "Number of territories to conquer " + super.toString();
    }

}

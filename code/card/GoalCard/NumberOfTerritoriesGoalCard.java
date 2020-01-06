package risk;

/**
	Carte degli obiettivi del numero di territori da conquistare.
*/

public class NumberOfTerritoriesGoalCard extends GoalCard<Integer> {

    public NumberOfTerritoriesGoalCard(Integer num) {
        super(num);
    }

    @Override
    public String toString() {
        return "Number of territories to conquer " + super.toString();
    }

}

package risk;

public class NumberOfTerritoriesGoalCard extends GoalCard<Integer> {

    public NumberOfTerritoriesGoalCard(Integer num) {
        super(num);
    }

    @Override
    public String toString() {
        return "Number of territories to conquer " + super.toString();
    }

}

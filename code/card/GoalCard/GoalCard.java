package risk;

public abstract class GoalCard<T> extends Card<T> {

    public GoalCard(T goal) {
        super(goal);
    }

    @Override
    public String toString() {
        return "goal " + super.toString();
    }
    
}

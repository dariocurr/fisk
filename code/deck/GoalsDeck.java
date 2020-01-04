package risk;

public abstract class GoalsDeck extends Deck<GoalCard> {

    public GoalsDeck() {
        super();
    }

    @Override
    public String toString() {
        return "Goals " + super.toString();
    }

}

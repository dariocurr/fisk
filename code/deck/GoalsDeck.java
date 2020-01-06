package risk;

/**
	Classe che rappresenta il mazzo delle carte degli obiettivi.
*/

public abstract class GoalsDeck extends Deck<GoalCard> {

    public GoalsDeck() {
        super();
    }

    @Override
    public String toString() {
        return "Goals " + super.toString();
    }

}

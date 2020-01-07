package risk;

import java.util.Collections;

/**
 * Classe che rappresenta il mazzo delle carte obiettivo.
 */
public abstract class GoalsDeck extends Deck<GoalCard> {

    protected GoalCardFactory goalCardFactory;

    /**
     * Istanzia un mazzo di carte obiettivo.
     *
     * @param numberOfTerritories numero di territori
     */
    public GoalsDeck(Integer numberOfTerritories) {
        super();
        this.goalCardFactory = new ConcreteGoalCardFactory();
        this.deck.add(this.goalCardFactory.createGoal(numberOfTerritories / 7 * 4));
        this.deck.add(this.goalCardFactory.createGoal(numberOfTerritories / 7 * 3));
        Collections.shuffle(this.deck);
    }

    @Override
    public String toString() {
        return "Goals " + super.toString();
    }

}

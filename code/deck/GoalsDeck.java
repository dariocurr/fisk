package risk;

import java.util.Collections;

/**
 * Classe che rappresenta il mazzo delle carte obiettivo.
 */
public abstract class GoalsDeck extends Deck<GoalCard> {

    /**
     * Istanzia un mazzo di carte obiettivo.
     *
     * @param numberOfTerritories numero di territori
     */
    public GoalsDeck(Integer numberOfTerritories) {
        super();
        this.deck.add(new NumberOfTerritoriesGoalCard(numberOfTerritories / 2));
        this.deck.add(new NumberOfTerritoriesGoalCard(numberOfTerritories / 7 * 3));
        Collections.shuffle(this.deck);
    }

    @Override
    public String toString() {
        return "Goals " + super.toString();
    }

}

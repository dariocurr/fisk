package fisk.card.GoalCard;

import fisk.card.Card;

/**
 * Classe che rappresenta una carta dell'obiettivo.
 *
 * @param <T> tipo di carta obiettivo
 */
public abstract class GoalCard<T> extends Card<T> {

    /**
     * Istanzia una carta obiettivo.
     *
     * @param goal obuiettivo
     */
    public GoalCard(T goal) {
        super(goal);
    }

    @Override
    public String toString() {
        return "goal: " + super.toString();
    }

}

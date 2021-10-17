package fisk.card.GoalCard;

import fisk.player.RiskColor;

/**
 * Carta obiettivo del nemico da uccidere.
 */
public class KillGoalCard extends GoalCard<RiskColor> {

    /**
     * Istanzia una carta obiettivo del nemico da uccidere.
     *
     * @param riskcolor colore da uccidere
     */
    public KillGoalCard(RiskColor riskcolor) {
        super(riskcolor);
    }

    @Override
    public String toString() {
        return "Player to destroy " + super.toString();
    }

}

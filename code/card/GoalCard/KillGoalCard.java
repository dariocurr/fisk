package risk;

/**
	Carte degli obiettivi dei nemici da uccidere.
*/

public class KillGoalCard extends GoalCard<RiskColor> {

    public KillGoalCard(RiskColor riskcolor) {
        super(riskcolor);
    }

    @Override
    public String toString() {
        return "Player to destroy " + super.toString();
    }

}

package risk;

import java.util.List;

public class ReinforcementStage extends Stage {

    public ReinforcementStage(RiskMediator mediator) {
        super(mediator);
    }

    @Override
    public void play(List<Territory> involvedTerritories) {
        if (involvedTerritories.size() == 1) {
            this.mediator.putTank(involvedTerritories.get(0));
            this.mediator.getFacade().clearInvolvedTerritories();
            if (this.checkEndStage()) {
                this.mediator.nextStage();
            }
        }
    }

    @Override
    public void setAvailableTerritories() {
        this.mediator.getFacade().setAvailableTerritories(this.mediator.getCurrentPlayer().getTerritories());
    }

    @Override
    public String toString() {
        return "Reinforcement " + super.toString();
    }

    public boolean checkEndStage() {
        return this.mediator.getCurrentPlayer().getFreeTanks().isEmpty();
    }

}

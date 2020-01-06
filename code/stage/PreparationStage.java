package risk;

import java.util.List;

public class PreparationStage extends Stage {

    protected int counter = 0;

    public PreparationStage(RiskMediator mediator) {
        super(mediator);
    }

    @Override
    public void play(List<Territory> involvedTerritories) {
        if (involvedTerritories.size() == 1) {
            this.counter++;
            this.mediator.putTank(involvedTerritories.get(0));
            this.mediator.getFacade().clearInvolvedTerritories();
        }
        if (this.checkEndStage()) {
            this.counter = 0;
            this.mediator.nextPlayerPreparationStage();
        }
    }

    public boolean checkEndStage() {
        return (this.counter == 3) || (this.mediator.getCurrentPlayer().getFreeTanks().isEmpty());
    }

    @Override
    public void setAvailableTerritories() {
        this.mediator.getFacade().setAvailableTerritories(this.mediator.getCurrentPlayer().getTerritories());
    }

    @Override
    public String toString() {
        return "Preparation " + super.toString();
    }

}

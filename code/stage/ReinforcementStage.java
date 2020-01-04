package risk;

import java.util.*;

public class ReinforcementStage extends Stage {

    public ReinforcementStage(Mediator mediator) {
        super(mediator);
    }

    public void play(List<Territory> clickedTerritories) {
        if (this.mediator.getCurrentPlayer().getFreeTanks().size() > 0 && clickedTerritories.size() == 1) {
            clickedTerritories.get(0).getTanks().add(this.mediator.getCurrentPlayer().getFreeTanks().remove(0));
            this.mediator.getFacade().updateLog(this.mediator.getCurrentPlayer() + " places a tank in " + clickedTerritories.get(0).getName() + 
                                                ", " + this.mediator.getCurrentPlayer().getFreeTanks().size() + " still to place");
            this.mediator.getFacade().updateLabelsTerritories(clickedTerritories);
            this.mediator.getFacade().updatePlayerData(this.mediator.getCurrentPlayer().getTerritories().size(), this.mediator.getCurrentPlayer().getFreeTanks().size(), this.mediator.getCurrentStage().toString());
            this.mediator.getFacade().clearClickedTerritories();
            if (this.checkEndStage()) {
                this.mediator.nextStage();
            }
        }
    }

    @Override
    public List<Territory> setAvailableTerritories() {
        return this.mediator.getCurrentPlayer().getTerritories();
    }

    @Override
    public String toString() {
        return "Reinforcement " + super.toString();
    }

    @Override
    public boolean checkEndStage() {
        return this.mediator.getCurrentPlayer().getFreeTanks().isEmpty();
    }

}

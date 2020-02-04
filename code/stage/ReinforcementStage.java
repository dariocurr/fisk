package risk;

import java.util.ArrayList;
import java.util.List;

/**
 * Classe che implementa la fase di rinforzo di un turno di gioco.
 */
public class ReinforcementStage extends Stage {

    public ReinforcementStage(RiskMediator mediator) {
        super(mediator);
    }

    /**
     * Permette di posizionare un'armata in un proprio territorio.
     */
    @Override
    public void play(List<Territory> involvedTerritories) {
        if (involvedTerritories.size() == 1) {
            this.mediator.putTank(involvedTerritories.get(0));
            this.mediator.getFacade().clearInvolvedTerritories();
            if (this.checkEndStage()) {
                //this.mediator.nextStage();
            }
        }
    }

    /**
     * Rende cliccabili solo i territori del player corrente.
     */
    @Override
    public void setAvailableTerritories() {
        if (!this.checkEndStage()) {
            this.mediator.getFacade().setAvailableTerritories(this.mediator.getCurrentPlayer().getTerritories());
        }
    }

    @Override
    public String toString() {
        return "Reinforcement " + super.toString();
    }

    /**
     * Verifica se è possibile passare alla fase successiva di gioco.
     *
     * @return true se tutti i tank sono stati posizionati, false altrimenti
     */
    public boolean checkEndStage() {
        return this.mediator.getCurrentPlayer().getFreeTanks().isEmpty();
    }

}

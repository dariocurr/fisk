package risk;

import java.util.List;

/**
    Classe che implementa la fase di preparazione del gioco, prima del suo inizio.
*/

public class PreparationStage extends Stage {

    protected int counter = 0;

    public PreparationStage(RiskMediator mediator) {
        super(mediator);
    }

    /**
        Permette di posizionare le proprie armate a tre a tre fino ad esaurimento,
        a turno per ogni giocatore.
    */
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

    /**
        Verifica se è necessario passare il turno al prossimo giocatore o meno.
        @return restituisce true se il giocatore corrente ha posizionate le tre armate o se le ha terminate,
        false altrimenti.
    */
    public boolean checkEndStage() {
        return (this.counter == 3) || (this.mediator.getCurrentPlayer().getFreeTanks().isEmpty());
    }

    /**
        Rende cliccabili solo i territori posseduti dal giocatore corrente.
    */
    @Override
    public void setAvailableTerritories() {
        this.mediator.getFacade().setAvailableTerritories(this.mediator.getCurrentPlayer().getTerritories());
    }

    @Override
    public String toString() {
        return "Preparation " + super.toString();
    }

}

package risk;

import java.util.ArrayList;
import java.util.List;

/**
 * Classe che implementa la fase di attacco di un turno di gioco.
 */
public class AttackStage extends TwoActionsStage {

    public AttackStage(RiskMediator mediator) {
        super(mediator);
    }

    /**
     * Permette di attaccare un territorio da uno posseduto dal giocatore
     * corrente.
     *
     * @param involvedTerritories territori coninvolti
     */
    @Override
    public void play(List<Territory> involvedTerritories) {
        if (involvedTerritories.size() == 2) {
            Territory from = involvedTerritories.get(0);
            Territory to = involvedTerritories.get(1);
            this.mediator.handleAttack(from, to);
            if (to.getTanks().isEmpty()) {
                this.mediator.changeOwnerTerritory(to);
                if (!this.mediator.IsEnded()) {
                    this.mediator.moveTanks(from, to, this.getNumberOfTanksToMove(involvedTerritories));
                }
            }
            this.mediator.getFacade().clearInvolvedTerritories();
            this.mediator.getFacade().enableEndStage();
            this.setAvailableTerritories();
        } else if (involvedTerritories.size() == 1) {
            this.setAvailableTerritoriesAfterFirstAction(involvedTerritories.get(0));
            this.mediator.getFacade().disableEndStage();
        }
    }

    protected Integer getNumberOfTanksToMove(List<Territory> involvedTerritories) {
        Territory from = involvedTerritories.get(0);
        Territory to = involvedTerritories.get(1);
        Integer numberOfTanksToMove;
        if (from.getTanks().size() < 5) {
            numberOfTanksToMove = from.getTanks().size() - 1;
        } else {
            if (this.mediator.getCurrentPlayer() instanceof AIPlayer) {
                AIPlayer aiPlayer = (AIPlayer) this.mediator.getCurrentPlayer();
                numberOfTanksToMove = aiPlayer.getNumberOfTanksToMove(involvedTerritories);
            } else {
                numberOfTanksToMove = this.mediator.getFacade().askNumberOfTanksToMove(to, 3, from.getTanks().size() - 1);
            }
        }
        return numberOfTanksToMove;
    }

    @Override
    public String toString() {
        return "Attack " + super.toString();
    }

    @Override
    public void setAvailableTerritories() {
        List<Territory> availableTerritories = new ArrayList<>();
        for (Territory territory : this.mediator.getCurrentPlayer().getTerritories()) {
            if (territory.getTanks().size() > 1) {
                boolean hasTerritoryToAttack = false;
                List<Territory> neighboringTerritories = territory.getNeighboringTerritories();
                for (int i = 0; i < neighboringTerritories.size() && !hasTerritoryToAttack; i++) {
                    if (!this.mediator.getCurrentPlayer().getTerritories().contains(neighboringTerritories.get(i))) {
                        hasTerritoryToAttack = true;
                    }
                }
                if (hasTerritoryToAttack) {
                    availableTerritories.add(territory);
                }
            }
        }
        this.mediator.getFacade().setAvailableTerritories(availableTerritories);
    }

    @Override
    public void setAvailableTerritoriesAfterFirstAction(Territory InvolvedTerritory) {
        List<Territory> availableTerritories = new ArrayList<>();
        InvolvedTerritory.getNeighboringTerritories()
                .stream()
                .filter((neighboringTerritory) -> (!(neighboringTerritory.getOwnerPlayer().equals(this.mediator.getCurrentPlayer()))))
                .forEach(availableTerritories::add);
        this.mediator.getFacade().setAvailableTerritories(availableTerritories);
    }

}

package fisk.stage;

import java.util.ArrayList;
import java.util.List;

import fisk.mediator.RiskMediator;
import fisk.player.AIPlayer;
import fisk.territory.Territory;

/**
 * Classe che implementa la fase di spostamento di un turno di gioco.
 */
public class MovingStage extends TwoActionsStage {

    public MovingStage(RiskMediator mediator) {
        super(mediator);
    }

    /**
     * Permette di spostare un certo numero di armate da un territorio posseduto ad
     * un altro ad esso confinante.
     *
     * @param involvedTerritories territori coninvolti
     */
    @Override
    public void play(List<Territory> involvedTerritories) {
        if (involvedTerritories.size() == 2) {
            Territory from = involvedTerritories.get(0);
            Territory to = involvedTerritories.get(1);
            this.mediator.moveTanks(from, to, this.getNumberOfTanksToMove(involvedTerritories));
            this.mediator.getFacade().clearInvolvedTerritories();
            this.mediator.nextStage();
        } else if (involvedTerritories.size() == 1) {
            this.setAvailableTerritoriesAfterFirstAction(involvedTerritories.get(0));
            this.mediator.getFacade().disableEndStage();
        }
    }

    protected Integer getNumberOfTanksToMove(List<Territory> involvedTerritories) {
        Territory from = involvedTerritories.get(0);
        Territory to = involvedTerritories.get(1);
        Integer numberOfTanksToMove;
        if (from.getTanks().size() == 2) {
            numberOfTanksToMove = 1;
        } else {
            if (this.mediator.getCurrentPlayer() instanceof AIPlayer) {
                AIPlayer aiPlayer = (AIPlayer) this.mediator.getCurrentPlayer();
                numberOfTanksToMove = aiPlayer.getNumberOfTanksToMove(involvedTerritories);
            } else {
                numberOfTanksToMove = this.mediator.getFacade().askNumberOfTanksToMove(to, 1,
                        from.getTanks().size() - 1);
            }
        }
        return numberOfTanksToMove;
    }

    @Override
    public String toString() {
        return "Moving " + super.toString();
    }

    @Override
    public void setAvailableTerritories() {
        List<Territory> availableTerritories = new ArrayList<>();
        for (Territory territory : this.mediator.getCurrentPlayer().getTerritories()) {
            if (territory.getTanks().size() > 1) {
                boolean hasNeighboringTerritory = false;
                List<Territory> neighboringTerritories = territory.getNeighboringTerritories();
                for (int i = 0; i < neighboringTerritories.size() && !hasNeighboringTerritory; i++) {
                    if (this.mediator.getCurrentPlayer().getTerritories().contains(neighboringTerritories.get(i))) {
                        hasNeighboringTerritory = true;
                    }
                }
                if (hasNeighboringTerritory) {
                    availableTerritories.add(territory);
                }
            }
        }
        this.mediator.getFacade().setAvailableTerritories(availableTerritories);
    }

    @Override
    public void setAvailableTerritoriesAfterFirstAction(Territory involvedterritory) {
        List<Territory> availableTerritories = new ArrayList<>();
        involvedterritory.getNeighboringTerritories().stream().filter((neighboringTerritory) -> (neighboringTerritory
                .getOwnerPlayer().equals(this.mediator.getCurrentPlayer()))).forEach(availableTerritories::add);
        this.mediator.getFacade().setAvailableTerritories(availableTerritories);
    }

}

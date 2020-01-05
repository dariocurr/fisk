package risk;

import java.util.ArrayList;
import java.util.List;

public class MovingStage extends TwoActionsStage {

    public MovingStage(RiskMediator mediator) {
        super(mediator);
    }

    /*public void play(List<Territory> clickedTerritories, Integer numberOfTanksToMove) {
        if (clickedTerritories.size() == 2) {
            Territory from = clickedTerritories.get(0);
            Territory to = clickedTerritories.get(1);
            if (this.mediator.getCurrentPlayer().getTerritories().contains(from) && from.getNeighboringTerritories().contains(to)) {
                for (int i = 0; i < numberOfTanksToMove; i++) {
                    to.getTanks().add(from.getTanks().remove(0));
                }
                this.mediator.getFacade().updateLog(this.mediator.getCurrentPlayer().getName() + " moved " + numberOfTanksToMove + " from " + 
                                                    clickedTerritories.get(0).getName() + " to " + clickedTerritories.get(1).getName());
                this.mediator.getFacade().updatePlayerData(this.mediator.getCurrentPlayer().getTerritories().size(), this.mediator.getCurrentPlayer().getFreeTanks().size(), this.mediator.getCurrentStage().toString());
                this.mediator.getFacade().updateLabelsTerritories(clickedTerritories);
                this.mediator.endStage();
            } else {
                this.mediator.getFacade().notifyError("The involved territories in moving must be adjoining");
            }
            this.mediator.getFacade().clearClickedTerritories();
            this.mediator.getFacade().setNumberOfTanksToMove(null);
        }
    }

    public void play(List<Territory> clickedTerritories) {
        if (clickedTerritories.size() == 2) {
            if (this.mediator.getFacade().getNumberOfTanksToMove() == null) {
                if (clickedTerritories.get(0).getTanks().size() == 2) {
                    this.play(clickedTerritories, 1);
                } else {
                    this.mediator.getFacade().askNumberOfTanks(clickedTerritories.get(0), clickedTerritories.get(0).getTanks().size() - 1);
                }
            } else {
                this.play(clickedTerritories, this.mediator.getFacade().getNumberOfTanksToMove());
            }
        } else if (clickedTerritories.size() == 1) {
            if (clickedTerritories.get(0).getTanks().size() == 1) {
                    this.mediator.getFacade().notifyError("You must choice a territory with more than 1 tank");
                    this.mediator.getFacade().clearClickedTerritories();
            } else {
                List<Territory> clickableTerritories = new ArrayList<>();
                for(Territory neighboringTerritory: clickedTerritories.get(0).getNeighboringTerritories()) {
                    if(this.mediator.getCurrentPlayer().getColor().equals(neighboringTerritory.getOwnerPlayer().getColor())) {
                        clickableTerritories.add(neighboringTerritory);
                    }
                }
                this.mediator.getFacade().setClickableTerritories(clickableTerritories);
            }        
        }
    }*/
    @Override
    public void play(List<Territory> involvedTerritories) {
        if (involvedTerritories.size() == 2) {
            Territory from = involvedTerritories.get(0);
            Territory to = involvedTerritories.get(1);
            this.mediator.moveTanks(from, to, this.getNumberOfTanksToMove(involvedTerritories));
            this.mediator.getFacade().clearInvolvedTerritories();
            this.mediator.getFacade().enableEndStage();
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
                numberOfTanksToMove = this.mediator.getFacade().askNumberOfTanksToMove(to, 1, from.getTanks().size() - 1);
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
        involvedterritory.getNeighboringTerritories()
                .stream()
                .filter((neighboringTerritory) -> (neighboringTerritory.getOwnerPlayer().equals(this.mediator.getCurrentPlayer())))
                .forEach(availableTerritories::add);
        this.mediator.getFacade().setAvailableTerritories(availableTerritories);
    }

}

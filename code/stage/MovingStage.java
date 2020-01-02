package risk;

import java.util.*;

public class MovingStage extends Stage {

    public MovingStage(Mediator mediator) {
        super(mediator);
    }

    public void play(List<Territory> clickedTerritories, Integer numberOfTanksToMove) {
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
    }

    @Override
    public String toString() {
        return "Moving " + super.toString();
    }

    @Override
    public List<Territory> setClickableTerritories() {
        return this.mediator.getCurrentPlayer().getTerritories();
    }

    @Override
    public boolean checkEndStage() {
        if (this.mediator.getCurrentPlayerWinsTerritory()) {
            if (!this.mediator.getSymbolDeck().isEmpty()) {
                SymbolCard draw = this.mediator.getSymbolDeck().removeCard();
                this.mediator.getCurrentPlayer().getCards().add(draw);
            }            
        }
        return true;
    }
}

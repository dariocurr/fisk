package risk;

import java.util.*;
import java.io.*;

class MovingStage extends Stage {

    public MovingStage(Mediator mediator) {
        super(mediator);
    }

    public void play(List<Territory> clickedTerritories, Integer numberOfTanksToMove) {
        if (clickedTerritories.size() == 2) {
            Territory from = clickedTerritories.get(0);
            Territory to = clickedTerritories.get(1);
            if (this.mediator.getCurrentPlayer().getTerritories().contains(from) && from.getNeighboringTerritories().contains(to)) {
                if (numberOfTanksToMove < from.getTanks().size()) {
                    for (int i = 0; i < numberOfTanksToMove; i++) {
                        to.getTanks().add(from.getTanks().remove(0));
                    }

                    this.mediator.getFacade().updatePlayerData(this.mediator.getCurrentPlayer().getTerritories().size(), this.mediator.getCurrentPlayer().getFreeTanks().size(), this.mediator.getCurrentStage().toString());
                    this.mediator.updateColorTerritoryButton();
                    this.mediator.updateLabelTerritoryButton(clickedTerritories);
                    this.mediator.getFacade().clearClickedTerritories();
                    this.mediator.nextStage();

                } else {
                    this.mediator.getFacade().updateLog("Errore: numero di tanks troppo alto");
                    return;
                }
            } else {
                this.mediator.getFacade().updateLog("I territori cliccati devono essere confinanti.");
                this.mediator.getFacade().clearClickedTerritories();
                this.mediator.getFacade().setNumberOfTanksToMove(null);
            }
        }
    }

    public void play(List<Territory> clickedTerritories) {
        if (clickedTerritories.size() == 2) {
            if (this.mediator.getFacade().getNumberOfTanksToMove() == null) {
                this.mediator.getFacade().askNumberOfTanks(clickedTerritories.get(0), clickedTerritories.get(0).getTanks().size() - 1);
            } else {
                this.play(clickedTerritories, this.mediator.getFacade().getNumberOfTanksToMove());
            }
        } else if (clickedTerritories.size() > 2) {
            this.mediator.getFacade().clearClickedTerritories();
        }
    }

    /*
	
	************************************************************************************************************************************************************
	IMPORTANTE
	I System.out.println("") vanno sostituiti con dei messageDialog nella gui
	************************************************************************************************************************************************************

     */
    @Override
    public String toString() {
        return "Moving " + super.toString();
    }

    public List<Territory> setClickableTerritories() {
        return this.mediator.getCurrentPlayer().getTerritories();
    }

    @Override
    public boolean checkEndStage() {
        if (this.mediator.getCurrentPlayerWinsTerritory()) {
            if (this.mediator.getCurrentPlayerWinsTerritory()) {
                SymbolCard draw = this.mediator.getSymbolDeck().removeCard();
                this.mediator.getCurrentPlayer().getCards().add(draw);
            }
            if (this.mediator.getSymbolDeck().isEmpty()) {
                for (Player s : this.mediator.getPlayers()) {
                    s.getCards().clear();
                }
            }
            this.mediator.createNewSymbolDeck();
        }
        return true;
    }
}

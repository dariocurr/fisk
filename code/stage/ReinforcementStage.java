package risk;

import java.util.*;
import java.io.*;

class ReinforcementStage extends Stage {

    public ReinforcementStage(Mediator mediator) {
        super(mediator);
    }

    public void play(List<Territory> clickedTerritories) {
        if (this.mediator.getCurrentPlayer().getFreeTanks().size() > 0 && clickedTerritories.size() == 1) {
            clickedTerritories.get(0).getTanks().add(this.mediator.getCurrentPlayer().getFreeTanks().remove(0));
            this.mediator.getFacade().updateLog(this.mediator.getCurrentPlayer() + " places a tank in " + clickedTerritories.get(0).getName() + 
                                                ", " + this.mediator.getCurrentPlayer().getFreeTanks().size() + " still to place");
            this.mediator.getFacade().updateBoard(clickedTerritories);
            this.mediator.getFacade().clearClickedTerritories();
            this.mediator.getFacade().updatePlayerData(this.mediator.getCurrentPlayer().getTerritories().size(), this.mediator.getCurrentPlayer().getFreeTanks().size(), this.mediator.getCurrentStage().toString());
        }
    }

    // scambiare un tris play ( Tris )
    public void play(Tris t) {
    }

    /*

	************************************************************************************************************************************************************
	IMPORTANTE
	Questo metodo permette di scambiare i tris:
	lo devo implementare qui o da facade chiami direttamente il metodo changeTris in mediator?
	************************************************************************************************************************************************************

     */
    public List<Territory> setClickableTerritories() {
        return this.mediator.getCurrentPlayer().getTerritories();
    }

    @Override
    public String toString() {
        return "Reinforcement " + super.toString();
    }

    @Override
    public boolean checkEndStage() {
        if (this.mediator.getCurrentPlayer().getFreeTanks().isEmpty()) {
            return true;
        } else {
            this.mediator.getFacade().updateLog("Posizionare tutti i tank disponibili");
            return false;
        }
    }

}

package risk;

import java.util.Collections;
import java.util.List;

public class PreparationStage extends Stage {

    protected int counter = 0;

    public PreparationStage(Mediator mediator) {
        super( mediator );
    }

    @Override
    public void play ( List<Territory> clickedTerritories ){
        if ( this.mediator.getCurrentPlayer().getFreeTanks().size() > 0 && clickedTerritories.size() == 1 ){
            this.counter++;
            clickedTerritories.get(0).getTanks().add( this.mediator.getCurrentPlayer().getFreeTanks().remove(0) );
            this.mediator.getFacade().updateLog(this.mediator.getCurrentPlayer() + " places a tank in " + clickedTerritories.get(0).getName() + 
                                                ", " + this.mediator.getCurrentPlayer().getFreeTanks().size() + " still to place");
            this.mediator.getFacade().updateLabelsTerritories(clickedTerritories);
            this.mediator.getFacade().updatePlayerData(this.mediator.getCurrentPlayer().getTerritories().size(), this.mediator.getCurrentPlayer().getFreeTanks().size(), this.mediator.getCurrentStage().toString() );
            this.mediator.getFacade().clearClickedTerritories();
        }
        if ( this.checkEndStage() ){
            this.counter = 0;
            this.mediator.nextPlayerPreparationStage();
        }
    }

    @Override 
    public boolean checkEndStage (){
        return (counter == 3) || (this.mediator.getCurrentPlayer().getFreeTanks().isEmpty());
    }

    @Override
    public List<Territory> setAvailableTerritories (){
        return this.mediator.getCurrentPlayer().getTerritories();
    }

    public void init() {
        Collections.shuffle(this.mediator.getPlayers());
        this.releaseGoal();
        this.checkGoals();
        this.releaseTerritory();
        this.releaseTanks();
        this.initTerritory();
    }

    protected void initTerritory() {
        for (Player p : this.mediator.getPlayers()) {
            this.initTerritoryToPlayer(p);
        }
    }

    protected void initTerritoryToPlayer(Player p) {
        for (Territory t : p.getTerritories()) {
            t.getTanks().add(p.getFreeTanks().remove(0));
        }
    }

    protected void releaseTanks() {
        Integer numberOfTanks;
        if (this.mediator.getPlayers().size() == 3) {
            numberOfTanks = 35;
        } else if (this.mediator.getPlayers().size() == 4) {
            numberOfTanks = 30;
        } else if (this.mediator.getPlayers().size() == 5) {
            numberOfTanks = 25;
        } else {
            numberOfTanks = 20;
        }
        for (Player p : this.mediator.getPlayers()) {
            this.releaseTanksToPlayer(p, numberOfTanks);
        }
    }

    protected void releaseTanksToPlayer(Player p, Integer numberOfTanks) {
        for (int i = 0; i < numberOfTanks; i++) {
            p.getFreeTanks().add(this.mediator.getGame().getTanksPool(p.getColor()).releaseTank());
        }
    }

    protected void releaseTerritory() {
        TerritoryCard drawCard;
        boolean ended = false;
        while (!this.mediator.getGame().getTerritoriesDeck().isEmpty()) {
            for (int i = 0; i < this.mediator.getPlayers().size() && !ended; i++) {
                Player p = this.mediator.getPlayers().get(i);
                drawCard = this.mediator.getGame().getTerritoriesDeck().removeCard();
                p.getTerritories().add(drawCard.getTerritory());
                drawCard.getTerritory().setOwnerPlayer(p);
                if(this.mediator.getGame().getTerritoriesDeck().isEmpty()) {
                    ended = true;
                }
            }
        }
    }

    protected void releaseGoal() {
        for (Player p : this.mediator.getPlayers()) {
            p.setGoal(this.mediator.getGame().getGoalsDeck().removeCard());
        }
    }

    protected void checkGoals() {
        for (Player player : this.mediator.getPlayers()) {
            if (player.getGoal() instanceof KillGoalCard) {
                boolean found = false;
                Player playerToKill = null;
                RiskColor colorToKill = (RiskColor) player.getGoal().getCard();
                for (int i = 0; i < this.mediator.getPlayers().size() && !found; i++) {
                    if (this.mediator.getPlayers().get(i).getColor() == colorToKill) {
                        playerToKill = this.mediator.getPlayers().get(i);
                        found = true;
                    }
                }
                if (found) {
                    if (player.equals(playerToKill)) {
                        player.setGoal(new NumberOfTerritoriesGoalCard(24));
                    }
                } else {
                    player.setGoal(new NumberOfTerritoriesGoalCard(24));
                }
            }
        }
    }
    
    @Override
    public String toString() {
        return "Preparation " + super.toString();
    }

}
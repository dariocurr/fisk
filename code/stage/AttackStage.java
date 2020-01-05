package risk;

import java.util.ArrayList;
import java.util.List;

public class AttackStage extends TwoActionsStage {

    public AttackStage(RiskMediator mediator) {
        super(mediator);
    }

    /*public void play(List<Territory> clickedTerritories) {
        if (clickedTerritories.size() == 2) {
            Territory[] involvedTerritory = new Territory[2];
            involvedTerritory[0] = clickedTerritories.get(0);
            involvedTerritory[1] = clickedTerritories.get(1);
            this.mediator.getFacade().updateLog(this.mediator.getCurrentPlayer().getName() + " attack " + involvedTerritory[1].getOwnerPlayer().getName() + 
                                                " from " + involvedTerritory[0].getName() + " to " + involvedTerritory[1].getName());
            if (this.checkTerritoriesForAttack(this.mediator.getCurrentPlayer(), involvedTerritory)) {
                Player attackedPlayer = involvedTerritory[1].getOwnerPlayer();
                int numberOfRollAttackingPlayer = Math.max(Math.min(involvedTerritory[0].getTanks().size() - 1, 3), 1);
                this.mediator.getFacade().updateLog("Number of dice attacking " + numberOfRollAttackingPlayer);
                int numberOfRollAttackedPlayer = Math.min(involvedTerritory[1].getTanks().size(), 3);
                this.mediator.getFacade().updateLog("Number of dice defensing " + numberOfRollAttackedPlayer);
                this.mediator.getCurrentPlayer().rollDice(this.mediator.getGame().getAttackDice(), numberOfRollAttackingPlayer);
                attackedPlayer.rollDice(this.mediator.getGame().getDefenseDice(), numberOfRollAttackedPlayer);
                this.mediator.getFacade().showDice();
                Arrays.sort(this.mediator.getGame().getAttackDice(), Collections.reverseOrder());
                Arrays.sort(this.mediator.getGame().getDefenseDice(), Collections.reverseOrder());
                int numberOfComparison = Math.min(numberOfRollAttackedPlayer, numberOfRollAttackingPlayer);
                //this.mediator.getFacade().updateLog("Numero di scontri " + numberOfComparison);
                int[] result = this.diceComparison(numberOfComparison);
                this.mediator.getFacade().updateLog("Result of the clash: " + this.mediator.getCurrentPlayer().getName() + " lost " + result[0] + " tank(s), " +
                                                    involvedTerritory[1].getOwnerPlayer().getName() + " " + result[1]);
                //this.mediator.getFacade().updateLog("Numero armate perse da " + involvedTerritory[0].getOwnerPlayer().getName() + ": " + result[0]);
                //this.mediator.getFacade().updateLog("Numero armate perse da " + involvedTerritory[1].getOwnerPlayer().getName() + ": " + result[1]);
                this.removeTanksFromTerritory(involvedTerritory, result);
                if (involvedTerritory[1].getTanks().isEmpty()) {
                    for (int i = 0; i < numberOfComparison; i++) {
                        involvedTerritory[1].getTanks().add(involvedTerritory[0].getTanks().remove(0));
                    }
                    involvedTerritory[1].getOwnerPlayer().getTerritories().remove(involvedTerritory[1]);
                    involvedTerritory[1].setOwnerPlayer(this.mediator.getCurrentPlayer());
                    this.mediator.getCurrentPlayer().getTerritories().add(involvedTerritory[1]);
                    this.mediator.setCurrentPlayerWinsTerritory(true);
                    this.mediator.getFacade().updateLog(this.mediator.getCurrentPlayer().getName() + " wins " + involvedTerritory[1].toString());
                }
                this.mediator.getFacade().updatePlayerData(this.mediator.getCurrentPlayer().getTerritories().size(),
                                                            this.mediator.getCurrentPlayer().getFreeTanks().size(),
                                                            this.mediator.getCurrentStage().toString());
                this.mediator.getFacade().updateColorsTerritories(clickedTerritories);
                this.mediator.getFacade().updateLabelsTerritories(clickedTerritories);
            }
            this.mediator.getFacade().clearClickedTerritories();
            this.mediator.getFacade().setClickableTerritories(this.setClickableTerritories());
        } else if (clickedTerritories.size() > 2) {
            this.mediator.getFacade().clearClickedTerritories();
        } else if (clickedTerritories.size() == 1) {
            List<Territory> clickableTerritories = new ArrayList<>();
            for(Territory neighboringTerritory: clickedTerritories.get(0).getNeighboringTerritories()) {
                if(!this.mediator.getCurrentPlayer().getColor().equals(neighboringTerritory.getOwnerPlayer().getColor())) {
                    clickableTerritories.add(neighboringTerritory);
                }
            }
            this.mediator.getFacade().setClickableTerritories(clickableTerritories);
        }
    }*/
    @Override
    public void play(List<Territory> involvedTerritories) {
        if (involvedTerritories.size() == 2) {
            Territory from = involvedTerritories.get(0);
            Territory to = involvedTerritories.get(1);
            this.mediator.handleAttack(from, to);
            if (to.getTanks().isEmpty()) {
                this.mediator.changeOwnerOfTerritory(to);
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

    /*
    private void checkContinent ( Territory from, Territory to ){
    	this.conqueredContinent(from.getOwnerPlayer(), to);
    	this.loosedContinent(to.getOwnerPlayer(), to);
    }

    private void conqueredContinent (Player p, Territory to){
    	List<Continent> continents = this.mediator.getGame().getContinents();
    	for ( Continent c : continents ){
    		if ( c.getTerritories().contains( to ) ){
    			if ( p.getTerritories().containsAll( c.getTerritories() ) )
    				p.getContinents().add( c );
    		}
    	}
    }

    private void loosedContinent ( Player p, Territory to ){
    	List<Continent> continents = this.mediator.getGame().getContinents();
    	for ( Continent c : continents ){
    		if ( c.getTerritories().contains( to ) ){
    			if ( p.getContinents().contains( c ) )
    				p.getContinents().remove( c );
    		}
    	}
    }
     */
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

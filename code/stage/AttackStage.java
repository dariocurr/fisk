package risk;

import java.util.*;

public class AttackStage extends Stage {

    public AttackStage(Mediator mediator) {
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

    public void play(List<Territory> clickedTerritories) {
        if (clickedTerritories.size() == 2) {
            Territory[] involvedTerritory = new Territory[2];
            involvedTerritory[0] = clickedTerritories.get(0);
            involvedTerritory[1] = clickedTerritories.get(1);
            this.mediator.getFacade().updateLog(this.mediator.getCurrentPlayer().getName() + " attack " + involvedTerritory[1].getOwnerPlayer().getName() + 
                                                " from " + involvedTerritory[0].getName() + " to " + involvedTerritory[1].getName());
            Player attackedPlayer = involvedTerritory[1].getOwnerPlayer();
            int numberOfRollAttackingPlayer = Math.max(Math.min(involvedTerritory[0].getTanks().size() - 1, 3), 1);
            this.mediator.getFacade().updateLog("Number of dice attacking " + numberOfRollAttackingPlayer);
            int numberOfRollAttackedPlayer = Math.min(involvedTerritory[1].getTanks().size(), 3);
            this.mediator.getFacade().updateLog("Number of dice defensing " + numberOfRollAttackedPlayer);
            this.mediator.getCurrentPlayer().rollDice(this.mediator.getGame().getAttackDice(), numberOfRollAttackingPlayer);
            attackedPlayer.rollDice(this.mediator.getGame().getDefenseDice(), numberOfRollAttackedPlayer);
            Arrays.sort(this.mediator.getGame().getAttackDice(), Collections.reverseOrder());
            Arrays.sort(this.mediator.getGame().getDefenseDice(), Collections.reverseOrder());
            int numberOfComparison = Math.min(numberOfRollAttackedPlayer, numberOfRollAttackingPlayer);
            if ( !(involvedTerritory[0].getOwnerPlayer() instanceof AIPlayer) || !(involvedTerritory[1].getOwnerPlayer() instanceof AIPlayer) ){
                this.mediator.showDice(numberOfComparison);
            }
            int[] result = this.diceComparison(numberOfComparison);
            this.mediator.getFacade().updateLog("Result of the clash: " + this.mediator.getCurrentPlayer().getName() + " lost " + result[0] + " tank(s), " +
                                                involvedTerritory[1].getOwnerPlayer().getName() + " " + result[1]);
            this.removeTanksFromTerritories(involvedTerritory, result);
            if (involvedTerritory[1].getTanks().isEmpty()) {
                this.mediator.assignTerritoryToCurrentPlayer(involvedTerritory[1]);
                this.moveTanks( clickedTerritories);
                this.checkContinent( involvedTerritory[0], involvedTerritory[1] );
                if ( !this.mediator.checkEnd() ){
                    if ( involvedTerritory[1].getOwnerPlayer().getTerritories().isEmpty() ){
                        this.mediator.removePlayer( involvedTerritory[1].getOwnerPlayer() );
                    }
                }
            }
            this.mediator.getFacade().updatePlayerData(this.mediator.getCurrentPlayer().getTerritories().size(),
                                                       this.mediator.getCurrentPlayer().getFreeTanks().size(),
                                                       this.mediator.getCurrentStage().toString());
            this.mediator.getFacade().updateColorsTerritories(clickedTerritories);
            this.mediator.getFacade().clearClickedTerritories();
            this.mediator.getFacade().setAvailableTerritories(this.setAvailableTerritories());
        } else if (clickedTerritories.size() > 2) {
            this.mediator.getFacade().clearClickedTerritories();
        } else if (clickedTerritories.size() == 1) {
            this.setAvailableTerritoriesAfterFirstAction(clickedTerritories.get(0));
        }
    }

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

    private void moveTanks ( List<Territory> clickedTerritories){
        int numberOfTanksToMove = 0;
        if ( this.mediator.getCurrentPlayer() instanceof AIPlayer ){
            AIPlayer aiPlayer = (AIPlayer) this.mediator.getCurrentPlayer();
            numberOfTanksToMove = aiPlayer.getNumberOfTanksToMove(clickedTerritories);
        } else {
            if(clickedTerritories.get(0).getTanks().size() < 5) {
                numberOfTanksToMove = clickedTerritories.get(0).getTanks().size() - 1;
            } else {
                this.mediator.getFacade().askNumberOfTanks(clickedTerritories.get(1), 
                                                            Math.min(clickedTerritories.get(0).getTanks().size() - 1, 3),
                                                            clickedTerritories.get(0).getTanks().size()-1); 
                numberOfTanksToMove = this.mediator.getFacade().getNumberOfTanksToMove();
            }
        }
        this.mediator.moveTanks(clickedTerritories.get(0), clickedTerritories.get(1), numberOfTanksToMove);
        this.mediator.getFacade().setNumberOfTanksToMove(null);
    }

    private void removeTanksFromTerritories(Territory[] involvedTerritory, int[] result) {
        this.mediator.removeTanks(involvedTerritory[0], result[0]);
        this.mediator.removeTanks(involvedTerritory[1], result[1]);
    }

    private int[] diceComparison(int numberOfComparison) {
        int howManyLoseAttacking = 0;
        int howManyLoseAttacked = 0;
        for (int i = 0; i < numberOfComparison; i++) {
            if (this.mediator.getGame().getAttackDice()[i].compareTo(this.mediator.getGame().getDefenseDice()[i]) > 0) {
                howManyLoseAttacked++;
            } else {
                howManyLoseAttacking++;
            }
        }
        int[] result = new int[2];
        result[0] = howManyLoseAttacking;
        result[1] = howManyLoseAttacked;
        return result;
    }

    /*
    private boolean checkTerritoriesForAttack(Player p, Territory[] involvedTerritory) {
        if (involvedTerritory == null) {
            return false;
        }
        if (p.getTerritories().contains(involvedTerritory[0])) {
            if (involvedTerritory[0].getNeighboringTerritories().contains(involvedTerritory[1])) {
                if (!p.getTerritories().contains(involvedTerritory[1])) {
                    if (involvedTerritory[0].getTanks().size() > 1) {
                        return true;
                    } else {
                        this.mediator.getFacade().updateLog("Il territorio da cui attaccare deve possedere almeno due armate.");
                        return false;
                    }
                } else {
                    this.mediator.getFacade().updateLog("Il territorio da attaccare deve essere posseduto da un altro avversario.");
                    return false;
                }
            } else {
                this.mediator.getFacade().updateLog("Il territorio da attaccare deve essere confinante ad uno posseduto.");
                return false;
            }
        } else {
            this.mediator.getFacade().updateLog("Il primo territorio è il territorio da cui si attacca.");
            return false;
        }
    }
    */

    @Override
    public String toString() {
        return "Attack " + super.toString();
    }

    @Override
    public List<Territory> setAvailableTerritories() {
        List<Territory> availableTerritories = new ArrayList<>();
        for (Territory territory : this.mediator.getCurrentPlayer().getTerritories()) {
            if(territory.getTanks().size() > 1) {
                boolean hasTerritoryToAttack = false;
                List<Territory> neighboringTerritories = territory.getNeighboringTerritories();
                for(int i = 0; i < neighboringTerritories.size() && !hasTerritoryToAttack; i++) {
                    if(!this.mediator.getCurrentPlayer().getTerritories().contains(neighboringTerritories.get(i))) {
                        hasTerritoryToAttack = true;
                    }
                }
                if (hasTerritoryToAttack) {
                    availableTerritories.add(territory);
                }
            } 
        }
        return availableTerritories;
    }
    
    public void setAvailableTerritoriesAfterFirstAction(Territory clickedTerritory) {
        List<Territory> availableTerritories = new ArrayList<>();
        for (Territory neighboringTerritory : clickedTerritory.getNeighboringTerritories()) {
            if(!(neighboringTerritory.getOwnerPlayer().equals(this.mediator.getCurrentPlayer()))) {
                availableTerritories.add(neighboringTerritory);
            }
        }
        this.mediator.getFacade().setAvailableTerritories(availableTerritories);
    }

    @Override
    public boolean checkEndStage() {
        return true;
    }

}

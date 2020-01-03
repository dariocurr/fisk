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
            if (this.checkTerritoriesForAttack(this.mediator.getCurrentPlayer(), involvedTerritory)) {
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
                this.removeTanksFromTerritory(involvedTerritory, result);

                if (involvedTerritory[1].getTanks().isEmpty()) {
                    this.mediator.getCurrentPlayer().getTerritories().add(involvedTerritory[1]);
                    this.moveTanks( clickedTerritories, numberOfComparison );
                    involvedTerritory[1].getOwnerPlayer().getTerritories().remove(involvedTerritory[1]);
                    involvedTerritory[1].setOwnerPlayer(this.mediator.getCurrentPlayer());
                    this.mediator.getCurrentPlayer().getTerritories().add(involvedTerritory[1]);
                    this.mediator.setCurrentPlayerWinsTerritory(true);
                    this.mediator.getFacade().updateLog(this.mediator.getCurrentPlayer().getName() + " wins " + involvedTerritory[1].toString());
                    System.out.println("ciao");
                    this.checkContinent( involvedTerritory[0], involvedTerritory[1] );
                    if ( involvedTerritory[1].getOwnerPlayer().getTerritories().size() == 0 ){
                        this.mediator.removePlayer( involvedTerritory[1].getOwnerPlayer() );
                        this.mediator.showMessage ( "The player " + involvedTerritory[1].getOwnerPlayer().getName() + " has been killed!!" );
                    }
                    /*if ( this.checkEnd() ){
                        this.mediator.showMessage ( "The player " + involvedTerritory[0].getOwnerPlayer().getName() + " wins!!!" );
                        this.mediator.endGame();
                    }*/
                }
                this.mediator.getFacade().updatePlayerData(this.mediator.getCurrentPlayer().getTerritories().size(),
                                                            this.mediator.getCurrentPlayer().getFreeTanks().size(),
                                                            this.mediator.getCurrentStage().toString());
                this.mediator.getFacade().updateColorsTerritories(clickedTerritories);
                this.mediator.getFacade().updateLabelsTerritories(clickedTerritories);
            }
            this.mediator.getFacade().clearClickedTerritories();
        } else if (clickedTerritories.size() > 2) {
            this.mediator.getFacade().clearClickedTerritories();
        }
    }

    private void checkContinent ( Territory from, Territory to ){
    	this.conqueredContinent(from.getOwnerPlayer(), to);
    	this.loosedContinent(to.getOwnerPlayer(), to);
    	System.out.println( from.getOwnerPlayer().getContinents() );
    	System.out.println( to.getOwnerPlayer().getContinents() );
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

    private void moveTanks ( List<Territory> clickedTerritories, int numberOfComparison ){
        int numberOfTanksToMove = 0;
        if ( this.mediator.getCurrentPlayer() instanceof AIPlayer ){
            AIPlayer aiPlayer = (AIPlayer) this.mediator.getCurrentPlayer();
            numberOfTanksToMove = aiPlayer.getNumberOfTanksToMove(clickedTerritories);
        }
        else {
            System.out.println( this.mediator.getFacade().askNumberOfTanks(clickedTerritories.get(0), clickedTerritories.get(0).getTanks().size() - 1, numberOfComparison ));
            System.out.println( this.mediator.getFacade().getNumberOfTanksToMove() );
            numberOfTanksToMove = this.mediator.getFacade().getNumberOfTanksToMove();
        }
        for (int i = 0; i < numberOfTanksToMove; i++) {
            clickedTerritories.get(1).getTanks().add(clickedTerritories.get(0).getTanks().remove(0));
        }
        this.mediator.getFacade().setNumberOfTanksToMove(null);
    }

    private boolean checkEnd() {
        for (Player player : this.mediator.getPlayers()) {
            GoalCard temp = player.getGoal();
            if (temp instanceof ContinentsGoalCard) {
                ContinentsGoalCard goalCard = (ContinentsGoalCard) temp;
                return player.getContinents().containsAll(goalCard.card);
            } else if (temp instanceof NumberOfTerritoriesGoalCard) {
                NumberOfTerritoriesGoalCard goalCard = (NumberOfTerritoriesGoalCard) temp;
                return (player.getTerritories().size() >= goalCard.card);
            } else if (temp instanceof KillGoalCard) {
                KillGoalCard goalCard = (KillGoalCard) temp;
                boolean found = false;
                Integer indexPlayerToKill = null;
                for (int i = 0; i < this.mediator.getPlayers().size() && !found; i++) {
                    if (this.mediator.getPlayers().get(i).getColor() == goalCard.card) {
                        found = true;
                        indexPlayerToKill = i;
                    }
                }
                return (this.mediator.getPlayers().get(indexPlayerToKill).getTerritories().isEmpty());
            }
        }
        return false;
    }

    private void removeTanksFromTerritory(Territory[] involvedTerritory, int[] result) {
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

    @Override
    public String toString() {
        return "Attack " + super.toString();
    }

    public List<Territory> setClickableTerritories() {
        List<Territory> clickableTerritory = new ArrayList<>();
        for (Territory t : this.mediator.getCurrentPlayer().getTerritories()) {
            clickableTerritory.add(t);
            clickableTerritory.addAll(t.getNeighboringTerritories());
        }
        return clickableTerritory;
    }

    @Override
    public boolean checkEndStage() {
        return true;
    }

}

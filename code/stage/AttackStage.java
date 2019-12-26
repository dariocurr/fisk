package risk;

import java.util.*;
import java.io.*;

class AttackStage implements Stage {

    private Mediator mediator;

    public AttackStage(Mediator mediator) {
        this.mediator = mediator;
    }

    public void play(List<Territory> clickedTerritories) {
        if (clickedTerritories.size() == 2) {
            Territory[] involvedTerritory = new Territory[2];
            involvedTerritory[0] = clickedTerritories.get(0);
            involvedTerritory[1] = clickedTerritories.get(1);
            this.mediator.getFacade().updateLog("" + clickedTerritories.get(0).toString() + " attacca " + clickedTerritories.get(1).toString());
            if (this.checkTerritoriesForAttack(this.mediator.getCurrentPlayer(), involvedTerritory)) {
                Player attackedPlayer = involvedTerritory[1].getOwnerPlayer();
                int numberOfRollAttackingPlayer = Math.max(Math.min(involvedTerritory[0].getTanks().size() - 1, 3), 1);
                this.mediator.getFacade().updateLog("Numero di tank dell' attancante " + numberOfRollAttackingPlayer);
                int numberOfRollAttackedPlayer = Math.min(involvedTerritory[1].getTanks().size(), 3);
                this.mediator.getFacade().updateLog("Numero di tank del difensore " + numberOfRollAttackedPlayer);
                this.mediator.getCurrentPlayer().rollDice(this.mediator.getGame().getAttackDice(), numberOfRollAttackingPlayer);
                attackedPlayer.rollDice(this.mediator.getGame().getDefenseDice(), numberOfRollAttackedPlayer);
                this.mediator.getFacade().showDice();
                Arrays.sort(this.mediator.getGame().getAttackDice(), Collections.reverseOrder());
                Arrays.sort(this.mediator.getGame().getDefenseDice(), Collections.reverseOrder());
                int numberOfComparison = Math.min(numberOfRollAttackedPlayer, numberOfRollAttackingPlayer);
                this.mediator.getFacade().updateLog("Numero di scontri " + numberOfComparison);
                int[] result = this.diceComparison(numberOfComparison);
                this.mediator.getFacade().updateLog("Esito scontro:");
                this.mediator.getFacade().updateLog("Numero armate perse da " + involvedTerritory[0].getOwnerPlayer().getName() + ": " + result[0]);
                this.mediator.getFacade().updateLog("Numero armate perse da " + involvedTerritory[1].getOwnerPlayer().getName() + ": " + result[1]);
                this.removeTanksFromTerritory(involvedTerritory, result);
                if (involvedTerritory[1].getTanks().size() == 0) {
                    this.mediator.getCurrentPlayer().getTerritories().add(involvedTerritory[1]);
                    for (int i = 0; i < numberOfComparison; i++) {
                        involvedTerritory[1].getTanks().add(involvedTerritory[0].getTanks().remove(0));
                    }
                    involvedTerritory[1].setOwnerPlayer(this.mediator.getCurrentPlayer());
                    this.mediator.setCurrentPlayerWinsTerritory(true);
                    this.mediator.getFacade().updateLog("" + involvedTerritory[0].getOwnerPlayer().getName() + " vince " + involvedTerritory[1].toString());
                }
                this.mediator.getFacade().updatePlayerData(this.mediator.getCurrentPlayer().getTerritories().size(),
                        this.mediator.getCurrentPlayer().getFreeTanks().size(),
                        this.mediator.getCurrentStage().toString());
                this.mediator.updateColorTerritoryButton();
                this.mediator.updateLabelTerritoryButton(null);
                this.mediator.getFacade().clearClickedTerritories();
            } else {
                this.mediator.getFacade().clearClickedTerritories();
            }
        } else if (clickedTerritories.size() > 2) {
            this.mediator.getFacade().clearClickedTerritories();
        }

        return;

        /*
	
		************************************************************************************************************************************************************
		IMPORTANTE
		I System.out.println("") vanno sostituiti con dei messageDialog nella gui
		************************************************************************************************************************************************************

         */
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
        for (int i = 0; i < result[0]; i++) {
            involvedTerritory[0].getTanks().remove(0);
        }
        for (int i = 0; i < result[1]; i++) {
            involvedTerritory[1].getTanks().remove(0);
        }
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
                if (p.getTerritories().contains(involvedTerritory[0]) && !p.getTerritories().contains(involvedTerritory[1])) {
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
        return "Fase di Attacco";
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

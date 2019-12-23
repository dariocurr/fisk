/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package risk;

import java.util.*;
import java.util.Map.Entry;

/**
 *
 * @author dario
 */
public abstract class RiskStrategy {
    
    public Territory addTank(Player player) {
        List<Territory> territoriesWithMinimumTanks = this.getTerritoriesWithMinumumTanks(player.getTerritories());
        if(territoriesWithMinimumTanks.size() == 1) {
            return territoriesWithMinimumTanks.get(0);
        } else {
            return addTankByGoal(player.getGoal(), territoriesWithMinimumTanks);
        }
    }
    
    protected List<Territory> getTerritoriesWithMinumumTanks(List<Territory> territories) {
        List<Territory> territoriesWithMinumumTanks = new ArrayList<>();
        int min = Integer.MAX_VALUE;
        for(Territory territory: territories) {
            int territoryTanks = territory.getTanks().size();
            if(territoryTanks < min) {
                territoriesWithMinumumTanks.clear();
                territoriesWithMinumumTanks.add(territory);
                min = territoryTanks;
            } else if(territoryTanks == min) {
                territoriesWithMinumumTanks.add(territory);
            }
        }
        return territoriesWithMinumumTanks;
    }
    
    protected Territory addTankByGoal(GoalCard goal, List<Territory> territoriesWithMinimumTanks) {
        if(goal instanceof ContinentsGoalCard) {
            ContinentsGoalCard goalCard = (ContinentsGoalCard) goal;
            for(Territory territory: territoriesWithMinimumTanks) {
                for(Continent continent: goalCard.getCard()) {
                    if(continent.getTerritories().contains(territory)) {
                        return territory;
                    }
                }
            }
        } else if(goal instanceof KillGoalCard) {
            KillGoalCard goalCard = (KillGoalCard) goal;
            for(Territory territory: territoriesWithMinimumTanks) {
                for(Territory nearTerritory: territory.getNeighboringTerritories()) {
                    if(nearTerritory.getOwnerPlayer().getColor().equals(goalCard.getCard())) {
                        return territory;
                    }
                }
            }
        }
        return territoriesWithMinimumTanks.get(0);
    }
    
    public Territory[] attack(Player player) {
        Territory territoriesInvolvedAttack[] = new Territory[2];
        Map<Territory, Territory> bestAttacks = this.getPossibleBestAttacks(player.getTerritories());
        Entry<Territory, Territory>[] entries = (Entry<Territory, Territory>[])bestAttacks.entrySet().toArray();
        int delta = entries[0].getKey().getTanks().size() - entries[0].getValue().getTanks().size();
        if(this.wantToAttack(delta, entries[0].getValue().getTanks().size())) {
            if(bestAttacks.size() == 1) {
                territoriesInvolvedAttack[0] = entries[0].getKey();
                territoriesInvolvedAttack[1] = entries[0].getValue();
            } else {
                return this.attackByGoal(player.getGoal(), bestAttacks);
            }
            return territoriesInvolvedAttack;
        } else {
            return null;
        }  
    }
    
    protected Map<Territory, Territory> getPossibleBestAttacks(List<Territory> territories) {
        int maxDelta = Integer.MIN_VALUE;
        Map<Territory, Territory> bestAttacks = new HashMap<>();
        for(Territory fromTerritory: territories) {
            for(Territory toTerritory: fromTerritory.getNeighboringTerritories()) {
                if(!territories.contains(toTerritory)) {
                    int delta = fromTerritory.getTanks().size() - toTerritory.getTanks().size();
                    if(delta > maxDelta) {
                        bestAttacks.clear();
                        bestAttacks.put(fromTerritory, toTerritory);
                        maxDelta = delta;
                    } else if(delta == maxDelta) {
                        bestAttacks.put(fromTerritory, toTerritory);
                    }
                }
            }
        }
        return bestAttacks;
    }
    
    protected Territory[] attackByGoal(GoalCard goal, Map<Territory, Territory> bestAttacks) {
        Territory territoriesInvolvedAttack[] = new Territory[2];
        if(goal instanceof ContinentsGoalCard) {
            ContinentsGoalCard goalCard = (ContinentsGoalCard) goal;
            for(Entry<Territory, Territory> entry: bestAttacks.entrySet()) {
                for(Continent continent: goalCard.getCard()) {
                    if(continent.getTerritories().contains(entry.getValue())) {
                        territoriesInvolvedAttack[0] = entry.getKey();
                        territoriesInvolvedAttack[1] = entry.getValue();
                    }
                }
            } 
        } else if (goal instanceof KillGoalCard) {
            KillGoalCard goalCard = (KillGoalCard) goal;
            for(Entry<Territory, Territory> entry: bestAttacks.entrySet()) {
                if(entry.getValue().getOwnerPlayer().getColor().equals(goalCard.getCard())) {
                    territoriesInvolvedAttack[0] = entry.getKey();
                    territoriesInvolvedAttack[1] = entry.getValue();
                }
            }
        } else {
            Entry<Territory, Territory>[] entries = (Entry<Territory, Territory>[])bestAttacks.entrySet().toArray();
            territoriesInvolvedAttack[0] = entries[0].getKey();
            territoriesInvolvedAttack[1] = entries[0].getValue();
        }   
        return territoriesInvolvedAttack;
    }
    
    protected abstract boolean wantToAttack(Integer delta, Integer numberOfTanksToAttack);
    
    public Territory[] moveTank(Player player) {
        Territory territoriesInvolvedMoving[] = new Territory[2];
        Map<Territory, Territory> bestMovings = this.getPossibleBestMovings(player.getTerritories());
        Entry<Territory, Territory>[] entries = (Entry<Territory, Territory>[])bestMovings.entrySet().toArray();
        int delta = entries[0].getKey().getTanks().size() - entries[0].getValue().getTanks().size();
        if(delta > 1) {
            if(bestMovings.size() == 1) {
                territoriesInvolvedMoving[0] = entries[0].getKey();
                territoriesInvolvedMoving[1] = entries[0].getValue();
            } else {
                return this.moveByGoal(player.getGoal(), bestMovings);
            }
            return territoriesInvolvedMoving;
        } else {
            return null;
        } 
    }
    
    protected Map<Territory, Territory> getPossibleBestMovings(List<Territory> territories) {
        int maxDelta = Integer.MIN_VALUE;
        Map<Territory, Territory> bestMovings = new HashMap<>();
        for(Territory fromTerritory: territories) {
            for(Territory toTerritory: fromTerritory.getNeighboringTerritories()) {
                if(territories.contains(toTerritory)) {
                    int delta = fromTerritory.getTanks().size() - toTerritory.getTanks().size();
                    if(delta > maxDelta) {
                        bestMovings.clear();
                        bestMovings.put(fromTerritory, toTerritory);
                        maxDelta = delta;
                    } else if(delta == maxDelta) {
                        bestMovings.put(fromTerritory, toTerritory);
                    }
                }
            }
        }
        return bestMovings;
    }
    
    protected Territory[] moveByGoal(GoalCard goal, Map<Territory, Territory> bestMovings) {
        Territory territoriesInvolvedMoving[] = new Territory[2];
        if(goal instanceof ContinentsGoalCard) {
            ContinentsGoalCard goalCard = (ContinentsGoalCard) goal;
            for(Entry<Territory, Territory> entry: bestMovings.entrySet()) {
                for(Continent continent: goalCard.getCard()) {
                    if(continent.getTerritories().contains(entry.getValue())) {
                        territoriesInvolvedMoving[0] = entry.getKey();
                        territoriesInvolvedMoving[1] = entry.getValue();
                    }
                }
            } 
        } else {
            Entry<Territory, Territory>[] entries = (Entry<Territory, Territory>[])bestMovings.entrySet().toArray();
            territoriesInvolvedMoving[0] = entries[0].getKey();
            territoriesInvolvedMoving[1] = entries[0].getValue();
        }   
        return territoriesInvolvedMoving;
    }
    
}

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
public class AIPlayer extends Player {
    
    public final static Set<String> NAMES_SET = initNamesSet();
    
    public AIPlayer(String name, RiskColor color) {
        super(name, color);
    }
    
    private static Set<String> initNamesSet() {
        Set<String> temp = new HashSet<>();
        temp.add("Dario");
        temp.add("Riccardo");
        temp.add("Domenico");
        temp.add("Emanuele");
        temp.add("Salvatore");
        temp.add("Antonino");
        return temp;
    }
    
    @Override
    public Territory addTank() {
        List<Territory> territoriesWithMinimumTanks = this.getTerritoriesWithMinumumTanks();
        if(territoriesWithMinimumTanks.size() == 1) {
            return territoriesWithMinimumTanks.get(0);
        } else {
            return addTankByGoal(territoriesWithMinimumTanks);
        }
    }
    
    private List<Territory> getTerritoriesWithMinumumTanks() {
        List<Territory> territoriesWithMinumumTanks = new ArrayList<>();
        int min = Integer.MAX_VALUE;
        for(Territory territory: this.territories) {
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
    
    private Territory addTankByGoal(List<Territory> territoriesWithMinimumTanks) {
        if(this.goal instanceof ContinentsGoalCard) {
            ContinentsGoalCard goalCard = (ContinentsGoalCard) this.goal;
            for(Territory territory: territoriesWithMinimumTanks) {
                for(Continent continent: goalCard.getCard()) {
                    if(continent.getTerritories().contains(territory)) {
                        return territory;
                    }
                }
            }
        }
        if(this.goal instanceof KillGoalCard) {
            KillGoalCard goalCard = (KillGoalCard) this.goal;
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
    
    @Override
    public Territory[] attack() {
        Territory territoriesInvolvedInAttack[] = new Territory[2];
        Map<Territory, Territory> bestAttacks = this.getPossibleBestAttacks();
        Entry<Territory, Territory>[] entries = (Entry<Territory, Territory>[])bestAttacks.entrySet().toArray();
        int delta = entries[0].getKey().getTanks().size() - entries[0].getValue().getTanks().size();
        if(delta > 0) {
            if(bestAttacks.size() == 1) {
                territoriesInvolvedInAttack[0] = entries[0].getKey();
                territoriesInvolvedInAttack[1] = entries[0].getValue();
            } else {
                return this.attackByGoal(bestAttacks);
            }
            return territoriesInvolvedInAttack;
        } else {
            return null;
        }     
    }
    
    private Map<Territory, Territory> getPossibleBestAttacks() {
        int maxDelta = Integer.MIN_VALUE;
        Map<Territory, Territory> bestAttacks = new HashMap<>();
        for(Territory fromTerritory: this.territories) {
            for(Territory toTerritory: fromTerritory.getNeighboringTerritories()) {
                if(!toTerritory.getOwnerPlayer().equals(this)) {
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
    
    private Territory[] attackByGoal(Map<Territory, Territory> bestAttacks) {
        Territory territoriesInvolvedInAttack[] = new Territory[2];
        if(this.goal instanceof ContinentsGoalCard) {
            ContinentsGoalCard goalCard = (ContinentsGoalCard) this.goal;
            for(Entry<Territory, Territory> entry: bestAttacks.entrySet()) {
                for(Continent continent: goalCard.getCard()) {
                    if(continent.getTerritories().contains(entry.getValue())) {
                        territoriesInvolvedInAttack[0] = entry.getKey();
                        territoriesInvolvedInAttack[1] = entry.getValue();
                    }
                }
            } 
        } else if (this.goal instanceof KillGoalCard) {
            KillGoalCard goalCard = (KillGoalCard) this.goal;
            for(Entry<Territory, Territory> entry: bestAttacks.entrySet()) {
                if(entry.getValue().getOwnerPlayer().getColor().equals(goalCard.getCard())) {
                    territoriesInvolvedInAttack[0] = entry.getKey();
                    territoriesInvolvedInAttack[1] = entry.getValue();
                }
            }
        } else {
            Entry<Territory, Territory>[] entries = (Entry<Territory, Territory>[])bestAttacks.entrySet().toArray();
            territoriesInvolvedInAttack[0] = entries[0].getKey();
            territoriesInvolvedInAttack[1] = entries[0].getValue();
        }   
        return territoriesInvolvedInAttack;
    }
    
}

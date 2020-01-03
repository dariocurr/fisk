package risk;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

public abstract class RiskStrategy {

    public Territory addTank(Player player) {
        List<Territory> territoriesWithMinimumTanks = this.getTerritoriesWithMinumumTanks(player.getTerritories());
        if (territoriesWithMinimumTanks.size() == 1) {
            return territoriesWithMinimumTanks.get(0);
        } else {
            return addTankByGoal(player.getGoal(), territoriesWithMinimumTanks);
        }
    }
    
    protected abstract String getStrategyName();

    protected List<Territory> getTerritoriesWithMinumumTanks(List<Territory> territories) {
        List<Territory> territoriesWithMinumumTanks = new ArrayList<>();
        int min = Integer.MAX_VALUE;
        for (Territory territory : territories) {
            int territoryTanks = territory.getTanks().size();
            if (territoryTanks < min) {
                territoriesWithMinumumTanks.clear();
                territoriesWithMinumumTanks.add(territory);
                min = territoryTanks;
            } else if (territoryTanks == min) {
                territoriesWithMinumumTanks.add(territory);
            }
        }
        return territoriesWithMinumumTanks;
    }

    protected Territory addTankByGoal(GoalCard goal, List<Territory> territoriesWithMinimumTanks) {
        if (goal instanceof ContinentsGoalCard) {
            ContinentsGoalCard goalCard = (ContinentsGoalCard) goal;
            for (Territory territory : territoriesWithMinimumTanks) {
                for (Continent continent : goalCard.getCard()) {
                    if (continent.getTerritories().contains(territory)) {
                        return territory;
                    }
                }
            }
        } else if (goal instanceof KillGoalCard) {
            KillGoalCard goalCard = (KillGoalCard) goal;
            for (Territory territory : territoriesWithMinimumTanks) {
                for (Territory nearTerritory : territory.getNeighboringTerritories()) {
                    if (nearTerritory.getOwnerPlayer().getColor().equals(goalCard.getCard())) {
                        return territory;
                    }
                }
            }
        }
        return territoriesWithMinimumTanks.get(0);
    }

    public List<Territory> attack(Player player) {
        List<Territory> territoriesInvolvedAttack = new ArrayList<>();
        Map<Territory, Territory> bestAttacks = this.getPossibleBestAttacks(player.getTerritories());
        List<Entry<Territory, Territory>> entries = new ArrayList<>();
        entries.addAll(bestAttacks.entrySet());
        int delta = entries.get(0).getKey().getTanks().size() - entries.get(0).getValue().getTanks().size();
        if (this.wantToAttack(delta, entries.get(0).getValue().getTanks().size())) {
            if (bestAttacks.size() == 1) {
                territoriesInvolvedAttack.add(entries.get(0).getKey());
                territoriesInvolvedAttack.add(entries.get(0).getValue());
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
        for (Territory fromTerritory : territories) {
            for (Territory toTerritory : fromTerritory.getNeighboringTerritories()) {
                if (!territories.contains(toTerritory)) {
                    int delta = fromTerritory.getTanks().size() - toTerritory.getTanks().size();
                    if (delta > maxDelta) {
                        bestAttacks.clear();
                        bestAttacks.put(fromTerritory, toTerritory);
                        maxDelta = delta;
                    } else if (delta == maxDelta) {
                        bestAttacks.put(fromTerritory, toTerritory);
                    }
                }
            }
        }
        return bestAttacks;
    }

    protected List<Territory> attackByGoal(GoalCard goal, Map<Territory, Territory> bestAttacks) {
        List<Territory> territoriesInvolvedAttack = new ArrayList<>();
        boolean found = false;
        if (goal instanceof ContinentsGoalCard) {
            ContinentsGoalCard goalCard = (ContinentsGoalCard) goal;
            for(Entry<Territory, Territory> entry: bestAttacks.entrySet()) {
                for (Continent continent : goalCard.getCard()) {
                    if (continent.getTerritories().contains(entry.getValue())) {
                        territoriesInvolvedAttack.clear();
                        territoriesInvolvedAttack.add(entry.getKey());
                        territoriesInvolvedAttack.add(entry.getValue());
                        found = true;
                    }
                }
            }
        } else if (goal instanceof KillGoalCard) {
            KillGoalCard goalCard = (KillGoalCard) goal;
            for (Entry<Territory, Territory> entry : bestAttacks.entrySet()) {
                if (entry.getValue().getOwnerPlayer().getColor().equals(goalCard.getCard())) {
                    territoriesInvolvedAttack.clear();
                    territoriesInvolvedAttack.add(entry.getKey());
                    territoriesInvolvedAttack.add(entry.getValue());
                    found = true;
                }
            }
        } 
        if (!found) {
            List<Entry<Territory, Territory>> entries = new ArrayList<>();
            entries.addAll(bestAttacks.entrySet());
            territoriesInvolvedAttack.clear();
            territoriesInvolvedAttack.add(entries.get(0).getKey());
            territoriesInvolvedAttack.add(entries.get(0).getValue());
        }
        return territoriesInvolvedAttack;
    }

    protected abstract boolean wantToAttack(Integer delta, Integer numberOfTanksToAttack);

    public List<Territory> moveTanks(Player player) {
        List<Territory> territoriesInvolvedMoving = new ArrayList<>();
        Map<Territory, Territory> bestMovings = this.getPossibleBestMovings(player.getTerritories());
        if(!bestMovings.isEmpty()) {
            List<Entry<Territory, Territory>> entries = new ArrayList<>();
            entries.addAll(bestMovings.entrySet());
            int delta = entries.get(0).getKey().getTanks().size() - entries.get(0).getValue().getTanks().size();
            if (delta > 1) {
                if (bestMovings.size() == 1) {
                    territoriesInvolvedMoving.add(entries.get(0).getKey());
                    territoriesInvolvedMoving.add(entries.get(0).getValue());
                } else {
                    return this.moveByGoal(player.getGoal(), bestMovings);
                }
                return territoriesInvolvedMoving;
            } else {
                return null;
            }
        } else {
            return null;
        }
    }

    protected Map<Territory, Territory> getPossibleBestMovings(List<Territory> territories) {
        Integer maxDelta = Integer.MIN_VALUE;
        Map<Territory, Territory> bestMovings = new HashMap<>();
        for (Territory fromTerritory : territories) {
            for (Territory toTerritory : fromTerritory.getNeighboringTerritories()) {
                if (territories.contains(toTerritory)) {
                    int delta = fromTerritory.getTanks().size() - toTerritory.getTanks().size();
                    if (delta > maxDelta) {
                        bestMovings.clear();
                        bestMovings.put(fromTerritory, toTerritory);
                        maxDelta = delta;
                    } else if (delta == maxDelta) {
                        bestMovings.put(fromTerritory, toTerritory);
                    }
                }
            }
        }
        return bestMovings;
    }

    protected List<Territory> moveByGoal(GoalCard goal, Map<Territory, Territory> bestMovings) {
        List<Territory> territoriesInvolvedMoving = new ArrayList<>();
        boolean found = false;
        if (goal instanceof ContinentsGoalCard) {
            ContinentsGoalCard goalCard = (ContinentsGoalCard) goal;
            for(Entry<Territory, Territory> entry: bestMovings.entrySet()) {
                for (Continent continent : goalCard.getCard()) {
                    if (continent.getTerritories().contains(entry.getValue())) {
                        territoriesInvolvedMoving.clear();
                        territoriesInvolvedMoving.add(entry.getKey());
                        territoriesInvolvedMoving.add(entry.getValue());
                        found = true;
                    }
                }
            }
        }
        if (!found) {
            List<Entry<Territory, Territory>> entries = new ArrayList<>();
            entries.addAll(bestMovings.entrySet());
            territoriesInvolvedMoving.clear();
            territoriesInvolvedMoving.add(entries.get(0).getKey());
            territoriesInvolvedMoving.add(entries.get(0).getValue());
        }
        return territoriesInvolvedMoving;
    }
    
    public Integer getNumberOfTanksToMove(List<Territory> territories) {
        if (territories.size() == 2) {
            return (territories.get(0).getTanks().size() - territories.get(1).getTanks().size()) / 2;
        } else {
            return null;
        }
    }

    @Override
    public String toString() {
        return this.getStrategyName() + " strategy";
    }

}

package fisk.strategy;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import fisk.card.GoalCard.ContinentsGoalCard;
import fisk.card.GoalCard.GoalCard;
import fisk.card.GoalCard.KillGoalCard;
import fisk.player.Player;
import fisk.territory.Continent;
import fisk.territory.Territory;

/**
 * Classe che implementa le strategie di gioco dei player virtuali.
 */
public abstract class RiskStrategy {

    public RiskStrategy() {
    }

    /**
     * Dato il giocatore corrente, restituisce il territorio su cui posizionare un'
     * armata. Se il territorio con il minimo numero di armate è unico allora verrà
     * scelto come territorio su cui posizionare la prossima armata; altrimenti
     * verrà scelto il territorio più affine all'obiettivo da raggiungere.
     *
     * @param player player corrente
     * @return territorio su cui posizionare un armata
     */
    public Territory addTank(Player player) {
        List<Territory> territoriesWithMinimumTanks = this.getTerritoriesWithMinumumTanks(player.getTerritories());
        if (territoriesWithMinimumTanks.size() == 1) {
            return territoriesWithMinimumTanks.get(0);
        } else {
            return addTankByGoal(player.getGoal(), territoriesWithMinimumTanks);
        }
    }

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

    protected Territory addTankByGoal(GoalCard<?> goal, List<Territory> territoriesWithMinimumTanks) {
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

    /**
     * Metodo che dato il player corrente restituisce la lista dei territorio
     * coinvoti nell'attacco. Il primo territorio è il territorio da cui attaccherà,
     * il secondo sarà quello da attaccare. La scelta dei due territori è basata
     * sulla differenza di armate poste nel territorio posseduto e nel territorio
     * nemico. Se per una ed una sola certa coppia di territori tale differenza è
     * massima allora verrà scelta tale coppia, altrimenti la scelta verrà
     * effettuata in base all'affinità con l'obiettivo da perseguire.
     *
     * @param player giocatore corrente
     * @return lista dei territori coinvolti nell'attacco
     */
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

    protected List<Territory> attackByGoal(GoalCard<?> goal, Map<Territory, Territory> bestAttacks) {
        List<Territory> territoriesInvolvedAttack = new ArrayList<>();
        boolean found = false;
        if (goal instanceof ContinentsGoalCard) {
            ContinentsGoalCard goalCard = (ContinentsGoalCard) goal;
            for (Entry<Territory, Territory> entry : bestAttacks.entrySet()) {
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

    /**
     * Metodo che dato il player corrente restituisce la lista dei territori
     * coinvoti nello spostamento. Il primo territorio è il territorio da cui
     * verranno spostate le armate, il secondo sarà quello di destinazione. La
     * scelta dei due territori è basata sulla differenza di armate poste nel
     * territorio di partenza e nel territorio di arrivo. Se per una ed una sola
     * certa coppia di territori tale differenza è massima allora verrà scelta tale
     * coppia, altrimenti la scelta verrà effettuata in base all'affinità con
     * l'obiettivo da perseguire.
     *
     * @param player giocatore corrente
     * @return lista dei territori coinvolti nello spostamento
     */
    public List<Territory> moveTanks(Player player) {
        List<Territory> territoriesInvolvedMoving = new ArrayList<>();
        Map<Territory, Territory> bestMovings = this.getPossibleBestMovings(player.getTerritories());
        if (!bestMovings.isEmpty()) {
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

    protected List<Territory> moveByGoal(GoalCard<?> goal, Map<Territory, Territory> bestMovings) {
        List<Territory> territoriesInvolvedMoving = new ArrayList<>();
        boolean found = false;
        if (goal instanceof ContinentsGoalCard) {
            ContinentsGoalCard goalCard = (ContinentsGoalCard) goal;
            for (Entry<Territory, Territory> entry : bestMovings.entrySet()) {
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

    /**
     * Dati i territori di partenza e di arrivo, restituisce il numero di armate da
     * spostare.
     *
     * @param territories lista dei territori coinvolti nello spostamento
     * @return numero di armate da spostare
     */
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

    /**
     * Restituisce il nome della fase corrente.
     *
     * @return il nome della fase corrente
     */
    protected abstract String getStrategyName();

    /**
     * Restituisce true se il player virtuale vuole attaccare, false altrimenti.
     *
     * @param delta                 delta di armate tra territorio attacante e
     *                              territorio attacato
     * @param numberOfTanksToAttack numero di armate da attaccare
     * @return true se il player virtuale vuole attaccare, false altrimenti
     */
    protected abstract Boolean wantToAttack(Integer delta, Integer numberOfTanksToAttack);

}

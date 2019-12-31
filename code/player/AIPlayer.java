package risk;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map.Entry;
import java.util.Set;
import java.util.Map;

public class AIPlayer extends Player {

    public static final Set<String> NAMES_SET = AIPlayer.initNamesSet();
    public static final RiskStrategy[] ALL_POSSIBLE_STRATEGIES = AIPlayer.initStrategies();
    protected static Map<Tris, Integer> TRIS_BONUS;
    protected RiskStrategy strategy;

    public AIPlayer(String name, RiskColor color, RiskStrategy strategy) {
        super(name, color);
        this.strategy = strategy;
    }

    public static void setTrisBonus(Map<Tris, Integer> TRIS_BONUS) {
        AIPlayer.TRIS_BONUS = TRIS_BONUS;
    }

    public void setStrategy(RiskStrategy strategy) {
        this.strategy = strategy;
    }

    public Territory addTank() {
        return this.strategy.addTank(this);
    }
    
    public List<Territory> attack() {
        return this.strategy.attack(this);
    }
    
    public Entry<List<Territory>, Integer> moveTanks() {
        return this.strategy.moveTanks(this);
    }

    protected static Set<String> initNamesSet() {
        Set<String> temp = new HashSet<>();
        temp.add("Dario");
        temp.add("Riccardo");
        temp.add("Lucia");
        temp.add("Emanuele");
        temp.add("Giulia");
        temp.add("Eleonora");
        return temp;
    }

    protected static RiskStrategy[] initStrategies() {
        RiskStrategy[] temp = new RiskStrategy[3];
        temp[0] = new AggressiveRiskStrategy();
        temp[1] = new BalancedRiskStrategy();
        temp[2] = new ConservativeRiskStrategy();
        return temp;
    }

    public Tris changeTris() {
        if (this.cards.size() < 3) {
            return null;
        } else {
            List<Tris> allCombinationsOfCards = this.generateAllCombinationsOfCards();
            List<Tris> allPossibleTris = this.generateAllCombinationsOfCards();
            allCombinationsOfCards
                    .stream()
                    .filter((tris) -> (AIPlayer.TRIS_BONUS.keySet().contains(tris)))
                    .forEachOrdered(allPossibleTris::add);
            if (allPossibleTris.isEmpty()) {
                return null;
            } else if (allPossibleTris.size() == 1) {
                return allPossibleTris.get(1);
            } else {
                Integer maxBonus = Integer.MIN_VALUE;
                Tris bestTris = null;
                for (Tris tris : allPossibleTris) {
                    Integer bonus = AIPlayer.TRIS_BONUS.get(tris);
                    /*for (SymbolCard symbolCard : tris.getCards()) {
                        if (symbolCard instanceof TerritoryCard) {
                        TerritoryCard territoryCard = (TerritoryCard) symbolCard;
                        if (this.territories.contains(territoryCard.getTerritory())) {
                            bonus += 2;
                        }
                    }
                    }*/
                    bonus = tris.getCards()
                            .stream()
                            .filter((symbolCard) -> (symbolCard instanceof TerritoryCard))
                            .map((symbolCard) -> (TerritoryCard) symbolCard)
                            .filter((territoryCard) -> (this.territories.contains(territoryCard.getTerritory())))
                            .mapToInt((territory) -> 2)
                            .sum();
                    if (bonus > maxBonus) {
                        bestTris = tris;
                    }
                }
                return bestTris;
            }
        }
    }

    protected List<Tris> generateAllCombinationsOfCards() {
        List<Tris> allCombinationsOfCards = new ArrayList<>();
        for (int i = 0; i < this.cards.size(); i++) {
            for (int j = i + 1; j < this.cards.size(); j++) {
                for (int k = j + 1; k < this.cards.size(); k++) {
                    allCombinationsOfCards.add(new Tris(this.cards.get(i), this.cards.get(j), this.cards.get(k)));
                }
            }
        }
        return allCombinationsOfCards;
    }

}

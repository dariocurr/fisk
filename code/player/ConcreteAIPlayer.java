package risk;

import java.util.ArrayList;
import java.util.List;

public class ConcreteAIPlayer extends AIPlayer {

    public ConcreteAIPlayer(String name, RiskColor color, RiskStrategy strategy) {
        super(name, color, strategy);
    }

    @Override
    public Tris exchangeTris() {
        if (this.cards.size() < 3) {
            return null;
        } else {
            List<Tris> allCombinationsOfCards = this.generateAllCombinationsOfCards();
            List<Tris> allPossibleTris = new ArrayList<>();
            /*
            for(Tris possibleTris: allCombinationsOfCards) {
                for(Tris validTris: AIPlayer.trisBonus.keySet()) {
                    if(validTris.equals(possibleTris)) {
                        allPossibleTris.add(possibleTris);
                    }
                }
            }
             */
            allCombinationsOfCards.forEach((possibleTris) -> {
                AIPlayer.trisBonus.keySet()
                        .stream()
                        .filter((validTris) -> (validTris.equals(possibleTris)))
                        .forEach((tris) -> allPossibleTris.add(tris));
            });
            if (allPossibleTris.isEmpty()) {
                return null;
            } else if (allPossibleTris.size() == 1) {
                return allPossibleTris.get(0);
            } else {
                Integer maxBonus = Integer.MIN_VALUE;
                Tris bestTris = null;
                for (Tris tris : allPossibleTris) {
                    Integer bonus = AIPlayer.trisBonus.get(tris);
                    /*
                    for (SymbolCard symbolCard : tris.getCards()) {
                        if (symbolCard instanceof TerritoryCard) {
                            TerritoryCard territoryCard = (TerritoryCard) symbolCard;
                            if (this.territories.contains(territoryCard.getTerritory())) {
                                bonus += 2;
                            }
                        }
                    }
                     */
                    bonus = tris.getCards()
                            .stream()
                            .filter((symbolCard) -> (symbolCard instanceof TerritoryCard))
                            .map((symbolCard) -> (TerritoryCard) symbolCard)
                            .filter((territoryCard) -> (this.territories.contains(territoryCard.getTerritory())))
                            .mapToInt((territory) -> 2)
                            .sum();
                    if (bonus > maxBonus) {
                        bestTris = tris;
                        maxBonus = bonus;
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
                    allCombinationsOfCards.add(new ConcreteTris(this.cards.get(i), this.cards.get(j), this.cards.get(k)));
                }
            }
        }
        return allCombinationsOfCards;
    }

}

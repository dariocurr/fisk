package risk;

public class ConcreteTerritoryCard extends TerritoryCard {

    public ConcreteTerritoryCard(Symbol symbol, Territory territory) {
        super(symbol, territory);
    }

    @Override
    public boolean equals(Object obj) {
        if (obj != null) {
            if (obj instanceof ConcreteTerritoryCard) {
                ConcreteTerritoryCard otherTerritoryCard = (ConcreteTerritoryCard) obj;
                return this.card.equals(otherTerritoryCard.card) && this.territory.equals(otherTerritoryCard.territory);
            }
        }
        return false;
    }

}

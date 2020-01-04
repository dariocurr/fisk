package risk;

public abstract class TerritoriesDeck extends Deck<TerritoryCard> {

    public TerritoriesDeck() {
        super();
    }

    @Override
    public String toString() {
        return "Territories " + super.toString();
    }

}

package risk;

import java.util.ArrayList;
import java.util.List;

public class Territory {

    protected final List<Territory> neighboringTerritories;
    protected final String name;
    protected final List<Tank> tanks;
    protected Continent continent;
    protected Player ownerPlayer;

    public Territory(String territoryName) {
        this.name = territoryName;
        this.neighboringTerritories = new ArrayList<>();
        this.tanks = new ArrayList<>();
    }

    public String getName() {
        return this.name;
    }

    public List<Tank> getTanks() {
        return tanks;
    }

    public List<Territory> getNeighboringTerritories() {
        return neighboringTerritories;
    }

    @Override
    public String toString() {
        return "Territory: " + this.getName();
    }

    public void setContinent(Continent continent) {
        this.continent = continent;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj != null) {
            if (obj instanceof Territory) {
                Territory otherTerritory = (Territory) obj;
                return this.name.equalsIgnoreCase(otherTerritory.name);
            }
        }
        return false;
    }

    public Player getOwnerPlayer() {
        return ownerPlayer;
    }

    public void setOwnerPlayer(Player ownerPlayer) {
        this.ownerPlayer = ownerPlayer;
    }

}

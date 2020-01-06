package risk;

import java.util.ArrayList;
import java.util.List;

public abstract class Territory {

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

    public Continent getContinent() {
        return this.continent;
    }

    public void setContinent(Continent continent) {
        this.continent = continent;
    }

    public Player getOwnerPlayer() {
        return ownerPlayer;
    }

    public void setOwnerPlayer(Player ownerPlayer) {
        this.ownerPlayer = ownerPlayer;
    }

    @Override
    public abstract boolean equals(Object obj);

}

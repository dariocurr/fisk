package risk;

import java.util.ArrayList;
import java.util.List;

/**
    Classe che modella il concetto di territorio.
*/

public abstract class Territory {

    protected final List<Territory> neighboringTerritories;
    protected final String name;
    protected final List<Tank> tanks;
    protected Continent continent;
    protected Player ownerPlayer;

    /**
        Costruttore che prende a parametro il nome del territorio da istanziare.
        @param territoryName nome del territorio
    */
    public Territory(String territoryName) {
        this.name = territoryName;
        this.neighboringTerritories = new ArrayList<>();
        this.tanks = new ArrayList<>();
    }

    /**
        Restituisce il nome del territorio.
    */
    public String getName() {
        return this.name;
    }

    /**
        Restituisce la lista delle armate poste nel territorio.
        @return lista delle armate
    */
    public List<Tank> getTanks() {
        return tanks;
    }

    /**
        Restituisce la lista dei territori confinanti a questo territorio.
        @return lista dei territori
    */
    public List<Territory> getNeighboringTerritories() {
        return neighboringTerritories;
    }

    @Override
    public String toString() {
        return "Territory: " + this.getName();
    }

    /**
        Restituisce il continente in cui questo territorio è contenuto.
    */
    public Continent getContinent() {
        return this.continent;
    }

    /**
        Setta il continente che contiene questo territorio.
        @param continent continente che contiene questo territorio
    */
    public void setContinent(Continent continent) {
        this.continent = continent;
    }

    /**
        Restituisce il player che possiede questo territorio.
        @return riferimento al player che possiede questo territorio
    */
    public Player getOwnerPlayer() {
        return ownerPlayer;
    }

    /**
        Setta il player che detiene questo territorio.
        @param ownerPlayer player che possiede questo territorio
    */
    public void setOwnerPlayer(Player ownerPlayer) {
        this.ownerPlayer = ownerPlayer;
    }

    @Override
    public abstract boolean equals(Object obj);

}

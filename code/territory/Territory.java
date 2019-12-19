/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package risk;

import java.util.*;

/**
 *
 * @author dario
 */
public class Territory {

    private List<Territory> neighboringTerritories; //public o private o something else?
    private String name;
    private Continent continent;
    private List<Tank> tanks;
    private Player ownerPlayer;

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
        return this.getName();
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

    public void printNeighboringTerritories() {
        int counter = 0;
        for (Territory t : this.neighboringTerritories) {
            System.out.println(t + " " + counter);
            counter++;
        }
    }

}

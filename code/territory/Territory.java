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

    public Territory ( String territoryName ){
	this.name = territoryName;
        this.neighboringTerritories = new ArrayList<>();
    }

    public String getName (){
	return this.name;
    }

    public List<Territory> getNeighboringTerritories() {
        return neighboringTerritories;
    }

    @Override
    public String toString() {
    	return this.getName();
    }
    
    public void setContinent ( Continent continent ){
        this.continent = continent;
    }
    
}

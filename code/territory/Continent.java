package risk;

import java.io.*;
import java.util.*;

public class Continent {

	private String name;
	private List<Territory> territories = new ArrayList<>();

	public Continent ( String continentName ){
		this.name = continentName;
	}

    public String getName() {
        return name;
    }
        
	public List<Territory> getTerritories (){
		return this.territories;
	}

	@Override
	public String toString (){
		return (this.name + ": " + this.territories.toString());
	}

    @Override
    public boolean equals(Object obj) {
        if (obj != null) {
            if (obj instanceof Continent) {
                Continent otherContinent = (Continent) obj;
                return this.name.equalsIgnoreCase(otherContinent.name);
            }
        }
        return false;
    }

}

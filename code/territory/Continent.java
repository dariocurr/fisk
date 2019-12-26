package risk;

import java.util.List;

public class Continent {

    protected final String name;
    protected final List<Territory> territories;

    public Continent(String continentName, List<Territory> territories) {
        this.name = continentName;
        this.territories = territories;
    }

    public String getName() {
        return name;
    }

    public List<Territory> getTerritories() {
        return this.territories;
    }

    @Override
    public String toString() {
        return "Continent: " + this.name;
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

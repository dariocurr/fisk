package risk;

import java.util.List;

public class ConcreteContinent extends Continent {
    
    public ConcreteContinent(String continentName, List<Territory> territories) {
        super(continentName, territories);
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

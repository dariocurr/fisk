package risk;

/**
 * Classe che concretizza il concetto di territorio.
 */
public class ConcreteTerritory extends Territory {

    public ConcreteTerritory(String territoryName) {
        super(territoryName);
    }

    @Override
    public boolean equals(Object obj) {
        if (obj != null) {
            if (obj instanceof ConcreteTerritory) {
                ConcreteTerritory otherTerritory = (ConcreteTerritory) obj;
                return this.name.equalsIgnoreCase(otherTerritory.name);
            }
        }
        return false;
    }

}

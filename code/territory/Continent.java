package risk;

import java.util.List;

/**
    Classe che modella il concetto di continente.
*/

public abstract class Continent {

    protected final String name;
    protected final List<Territory> territories;

    /**
        Costruttore che prende a parametro il nome del continente e la lista dei territori che lo compongono.
        @param continentName nome del continente
        @param territories lista dei territori che compongono il continente
    */
    public Continent(String continentName, List<Territory> territories) {
        this.name = continentName;
        this.territories = territories;
    }

    /**
        Restituisce il nome del continente.
        @return nome del continente
    */
    public String getName() {
        return name;
    }

    /**
        Restituisce la lista dei territori del continente.
        @return lista dei territori
    */
    public List<Territory> getTerritories() {
        return this.territories;
    }

    @Override
    public String toString() {
        return "Continent: " + this.name;
    }

    @Override
    public abstract boolean equals(Object obj);

}

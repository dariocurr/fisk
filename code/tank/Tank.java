package risk;

/**
	Classe che modella un' armata di gioco di un giocatore.
*/

public abstract class Tank {

    protected RiskColor color;

    /**
		Costruttore che istanzia un armata di un certo colore.
		@param color colore dell'armata
    */
    public Tank(RiskColor color) {
        this.color = color;
    }

    /**
    	Restituisce il colore dell'armata.
    */
    public RiskColor getColor() {
        return this.color;
    }

    @Override
    public String toString() {
        return "Tank: " + this.color;
    }

}

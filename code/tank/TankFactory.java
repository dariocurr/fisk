package risk;

/**
 * Classe che modellizza il concetto di Factory per creare le armate.
 */
@FunctionalInterface
public interface TankFactory {

    /**
     * Permette la creazione di un'armata.
     *
     * @param color colore dell'armata
     * @return l'armata creata
     */
    public Tank createTank(RiskColor color);

}

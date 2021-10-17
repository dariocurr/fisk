package fisk.tank;

import fisk.player.RiskColor;

/**
 * Classe che concretizza un'armata di un giocatore.
 */
public class ConcreteTank extends Tank {

    /**
     * Istanzia una armata.
     *
     * @param color colore della armata
     */
    public ConcreteTank(RiskColor color) {
        super(color);
    }

}

package fisk.tank;

import fisk.player.RiskColor;

/**
 * Classe che concretizza il concetto di Factory per creare le armate.
 * Implementa il pattern Singleton
 */
public class ConcreteTankFactory implements TankFactory {

    protected static TankFactory tankFactory;

    protected ConcreteTankFactory() {
        super();
    }

    @Override
    public Tank createTank(RiskColor color) {
        return new ConcreteTank(color);
    }

    /**
     * Restituisce l'unica istanza della classe
     *
     * @return l'unica istanza della classe
     */
    public static TankFactory getTankFactory() {
        if (ConcreteTankFactory.tankFactory == null) {
            ConcreteTankFactory.tankFactory = new ConcreteTankFactory();
        }
        return ConcreteTankFactory.tankFactory;
    }

}

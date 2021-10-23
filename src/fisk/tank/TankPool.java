package fisk.tank;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import fisk.player.RiskColor;

/**
 * Classe che gestisce le armate disponibili attraverso un pool.
 */
public class TankPool implements Iterable<Tank> {

    protected TankFactory tankFactory;
    protected List<Tank> tanks;

    /**
     * Istanzia un pool di armate
     *
     * @param numberOfTanks numero di armate da inserire nel pool
     * @param riskColor     colore delle armate
     */
    public TankPool(Integer numberOfTanks, RiskColor riskColor) {
        this.tankFactory = ConcreteTankFactory.getTankFactory();
        this.tanks = new ArrayList<>();
        for (int i = 0; i < numberOfTanks; i++) {
            this.tanks.add(this.tankFactory.createTank(riskColor));
        }
    }

    /**
     * Consente di ottenere una armata, se ve ne sono ancora disponibili.
     *
     * @return una armata
     */
    public Tank releaseTank() {
        if (this.tanks.size() > 0) {
            return this.tanks.remove(0);
        } else {
            return null;
        }
    }

    /**
     * Consente di reinserire una armata nel pool.
     *
     * @param tank armata da reinserire
     */
    public void acquireTank(Tank tank) {
        this.tanks.add(tank);
    }

    @Override
    public Iterator<Tank> iterator() {
        return this.tanks.iterator();
    }

}

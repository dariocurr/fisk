/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package risk;

import java.util.*;

/**
 *
 * @author dario
 */
public class TankPool {
    
    private List<Tank> tanks;
    
    public TankPool(int number, RiskColor riskColor) {
        this.tanks = new ArrayList<>();
        for(int i = 0; i < number; i++) {
            tanks.add(new Tank(riskColor));
        }
    }
    
    public Tank releaseTank() {
        return this.tanks.remove(0);
    }
    
    public void acquireTank(Tank tank) {
        this.tanks.add(tank);
    }
    
}

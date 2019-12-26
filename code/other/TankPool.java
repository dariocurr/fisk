package risk;

import java.util.ArrayList;
import java.util.List;

public class TankPool {

    protected List<Tank> tanks;

    public TankPool(int number, RiskColor riskColor) {
        this.tanks = new ArrayList<>();
        for (int i = 0; i < number; i++) {
            this.tanks.add(new Tank(riskColor));
        }
    }

    public Tank releaseTank() {
        if (this.tanks.size() > 0) {
            return this.tanks.remove(0);
        } else {
            return null;
        }
    }

    public void acquireTank(Tank tank) {
        this.tanks.add(tank);
    }

}

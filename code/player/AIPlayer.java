package risk;

import java.util.List;
import java.util.Map;

public abstract class  AIPlayer extends Player {

    protected static Map<Tris, Integer> trisBonus;
    protected RiskStrategy strategy;

    public AIPlayer(String name, RiskColor color, RiskStrategy strategy) {
        super(name, color);
        this.strategy = strategy;
    }

    public static void setTrisBonus(Map<Tris, Integer> trisBonus) {
        AIPlayer.trisBonus = trisBonus;
    }

    public void setStrategy(RiskStrategy strategy) {
        this.strategy = strategy;
    }

    public Territory addTank() {
        return this.strategy.addTank(this);
    }
    
    public List<Territory> attack() {
        return this.strategy.attack(this);
    }
    
    public List<Territory> moveTanks() {
        return this.strategy.moveTanks(this);
    }
    
    public Integer getNumberOfTanksToMove(List<Territory> territories) {
        return this.strategy.getNumberOfTanksToMove(territories);
    }

    public abstract Tris exchangeTris();

}

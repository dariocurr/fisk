package risk;

public class BalancedRiskStrategy extends RiskStrategy {

    @Override
    protected Boolean wantToAttack(Integer delta, Integer numberOfTanksToAttack) {
        return delta > numberOfTanksToAttack;
    }
    
    @Override
    public String getStrategyName() {
        return "Balanced";
    }

}

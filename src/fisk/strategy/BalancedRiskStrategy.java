package fisk.strategy;

public class BalancedRiskStrategy extends RiskStrategy {

    public BalancedRiskStrategy() {
        super();
    }

    @Override
    protected Boolean wantToAttack(Integer delta, Integer numberOfTanksToAttack) {
        return delta > numberOfTanksToAttack;
    }

    @Override
    public String getStrategyName() {
        return "Balanced";
    }

}

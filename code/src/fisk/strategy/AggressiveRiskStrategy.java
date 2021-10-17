package fisk.strategy;

public class AggressiveRiskStrategy extends RiskStrategy {

    public AggressiveRiskStrategy() {
        super();
    }

    @Override
    protected Boolean wantToAttack(Integer delta, Integer numberOfTanksToAttack) {
        return delta > numberOfTanksToAttack * 0.5;
    }

    @Override
    public String getStrategyName() {
        return "Aggressive";
    }

}

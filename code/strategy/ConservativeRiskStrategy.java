package risk;

public class ConservativeRiskStrategy extends RiskStrategy {

    public ConservativeRiskStrategy() {
        super();
    }

    @Override
    protected Boolean wantToAttack(Integer delta, Integer numberOfTanksToAttack) {
        return delta > numberOfTanksToAttack * 2;
    }

    @Override
    public String getStrategyName() {
        return "Conservative";
    }

}

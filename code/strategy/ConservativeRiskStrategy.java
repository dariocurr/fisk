package risk;

public class ConservativeRiskStrategy extends RiskStrategy {

    @Override
    protected boolean wantToAttack(Integer delta, Integer numberOfTanksToAttack) {
        return delta > numberOfTanksToAttack * 2;
    }
    
    @Override
    public String getStrategyName() {
        return "Conservative";
    }

}

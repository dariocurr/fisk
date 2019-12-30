package risk;

public interface Main {

    public static void main(String... args) {
        //new FacadeGUI().askMatch();
        //new AIPlayer("Dario", RiskColor.BLUE, new BalancedRiskStrategy()).changeTris();
        new TankPool(45, RiskColor.YELLOW);
    }
    
}

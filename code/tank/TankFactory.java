package risk;

@FunctionalInterface
public interface TankFactory {

    public Tank createTank(RiskColor color);

}

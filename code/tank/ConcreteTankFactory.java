package risk;

public class ConcreteTankFactory implements TankFactory {

    protected static TankFactory tankFactory;

    protected ConcreteTankFactory() {
        super();
    }

    @Override
    public Tank createTank(RiskColor color) {
        return new ConcreteTank(color);
    }

    public static TankFactory getTankFactory() {
        if (ConcreteTankFactory.tankFactory == null) {
            ConcreteTankFactory.tankFactory = new ConcreteTankFactory();
        }
        return ConcreteTankFactory.tankFactory;
    }

}

package risk;

public class ConcreteTankFactory implements TankFactory {
    
    protected static ConcreteTankFactory tankFactory;
    
    protected ConcreteTankFactory() {}
    
    @Override
    public Tank createTank(RiskColor color) {
        return new Tank(color);
    }
    
    public static ConcreteTankFactory getTankFactory() {
        if (ConcreteTankFactory.tankFactory == null) {
            ConcreteTankFactory.tankFactory = new ConcreteTankFactory();
        }
        return ConcreteTankFactory.tankFactory;
    }

}

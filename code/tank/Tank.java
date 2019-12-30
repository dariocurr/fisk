package risk;

public class Tank {

    protected RiskColor color;

    public Tank(RiskColor color) {
        this.color = color;
    }

    public RiskColor getColor() {
        return this.color;
    }

    @Override
    public String toString() {
        return "Tank: " + this.color;
    }

}

package risk;

import java.util.List;

public abstract class Stage {

    protected RiskMediator mediator;

    public Stage(RiskMediator mediator) {
        this.mediator = mediator;
    }

    public abstract void play(List<Territory> clickedTerritories);

    public abstract void setAvailableTerritories();

    @Override
    public String toString() {
        return "stage";
    }

}

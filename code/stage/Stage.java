package risk;

import java.util.List;

public abstract class Stage {

    protected Mediator mediator;
    
    public Stage(Mediator mediator) {
        this.mediator = mediator;
    }
    
    public abstract void play(List<Territory> clickedTerritories);

    public abstract List<Territory> setAvailableTerritories();

    public abstract boolean checkEndStage();

    @Override
    public String toString() {
        return "stage";
    }
    

}

package risk;

public abstract class TwoActionsStage extends Stage {

    public TwoActionsStage(RiskMediator mediator) {
        super(mediator);
    }

    public abstract void setAvailableTerritoriesAfterFirstAction(Territory involvedTerritory);

}

package risk;

public abstract class TwoActionsStage extends Stage {

	/**
		Costruttore della classe con riferimento al mediator.
		@param mediator riferimento al mediator
	*/
    public TwoActionsStage(RiskMediator mediator) {
        super(mediator);
    }
    
    /**
    	Selezionato il primo territorio, rende cliccabili i bottoni ad esso coerenti in questa fase.
    	@param involvedTerritory territorio selezionato
    */
    public abstract void setAvailableTerritoriesAfterFirstAction(Territory involvedTerritory);

}

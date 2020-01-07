package risk;

/**
 * Classe che permette di cominciare una partita di risiko
 */
public class StartRIskGUI implements Startable {

    @Override
    public void start() {
        RiskMediator mediator = new ConcreteRiskMediator();
        RiskFacade facade = new GUIRiskFacade();
        mediator.setFacade(facade);
        facade.setMediator(mediator);
        new StartWindow(facade);
    }

}

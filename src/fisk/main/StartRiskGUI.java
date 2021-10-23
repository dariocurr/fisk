package fisk.main;

import fisk.facade.GUIRiskFacade;
import fisk.facade.RiskFacade;
import fisk.mediator.ConcreteRiskMediator;
import fisk.mediator.RiskMediator;
import fisk.view.gui.StartWindow;

/**
 * Classe che permette di cominciare una partita di risiko
 */
public class StartRiskGUI implements Startable {

    @Override
    public void start() {
        RiskMediator mediator = new ConcreteRiskMediator();
        RiskFacade facade = new GUIRiskFacade();
        mediator.setFacade(facade);
        facade.setMediator(mediator);
        new StartWindow(facade);
    }

}

package risk;

import java.util.List;

/**
 * Classe che astrae il concetto di fase di gioco di un turno.
 */
public abstract class Stage {

    protected RiskMediator mediator;

    /**
     * Costruttore della classe con riferimento al mediator.
     *
     * @param mediator riferimento al mediator di questa partita
     */
    public Stage(RiskMediator mediator) {
        this.mediator = mediator;
    }

    /**
     * Implementa l'operazione che il player ( reale o virtuale ) potrà
     * effettuare in questa fase di gioco.
     *
     * @param involvedTerritories territori coinvolti nell'operazione
     */
    public abstract void play(List<Territory> involvedTerritories);

    /**
     * Rende cliccabili soltanto i bottoni leciti in questo stage. In questo
     * modo l'utente è guidato nella scelta dei territori leciti e vengono
     * implementate le regole del Risiko classico.
     */
    public abstract void setAvailableTerritories();

    @Override
    public String toString() {
        return "";
    }

}

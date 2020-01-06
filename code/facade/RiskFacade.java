package risk;

import java.awt.Color;
import java.util.List;
import java.util.Map;

/**
    Classe che permette la comunicazione tra Mediator e sistema di input.
*/

public interface RiskFacade {

    /**
        Permette di passare al Mediator quali territori sono stati selezionati e di
        effettuare l'operazione propria di questa fase di gioco.
    */
    public void update(Territory territory);

    /**
        Restituisce la lista di tutti i territori della board.
        @return lista dei territiori
    */
    public List<Territory> getTerritories();

    /**
        Restituisce la carta degli obiettivi del player reale.
        @return obiettivo del giocatore reale
    */
    public GoalCard getHumanPlayerGoal();

    /**
        Restituisce la lista delle carte dei simboli del player reale.
        @return carte dei simboli
    */
    public List<SymbolCard> getHumanPlayerCards();

    /**
        Restituisce il colore scelto dal giocatore reale.
        @return colore del giocatore reale
    */
    public Color getHumanPlayerColor();

    /**
        Resituisce il nome del giocatore reale.
        @return nome del giocatore reale.
    */
    public String getHumanPlayerName();

    /**
        Verifica se il tris passato a parametro è lecito.
        @param tris tris selezionato
        @return true se è lecito, false altrimenti
        @see risk.RiskMediator#checkTris
    */
    public boolean checkTris(Tris tris);

    /**
        Scambia il tris passato a parametro con il numero di armate corrispondente.
        @param tris tris selezionato
        @see risk.RiskMediator#exchangeTris
    */
    public void exchangeTris(Tris tris);

    /**
        Visualizza i dadi estratti durante un attacco.
        @param numberOfRolledDice numero di dadi da visualizzare
        @param attackDiceValues valori dei dadi di attacco
        @param defenseDiceValues valori dei dadi di difesa
        @see risk.RollFrame
    */
    public void askDice(int numberOfRolledDice, Dice[] attackDiceValues, Dice[] defenseDiceValues);

    /**
        Permette di specificare in input il numero di armate da spostare dopo un attacco
        in cui si vince il territorio attaccato.
        @param territory territorio conquistato in cui porre le armate
        @param min numero di armate minime da passare nel nuovo territorio: devono essere almeno pari al numero di armate che hanno partecipato all'attacco
        @param max numero massimo di armate che è possibile passare: nel territorio da cui si parte ne deve rimanere almeno una
    */
    public Integer askNumberOfTanksToMove(Territory territory, Integer min, Integer max);

    /**
        Passa i dati in input dalla startWindow al mediator.
        @param humanPlayerName nome scelto dal giocatore reale
        @param humanPlayerColor colore scelto dal giocatore reale
        @param virtualPlayersStrategies strategie scelte dal giocatore reale
        @see risk.RiskMediator#prepareGame 
        @see risk.StartWindow
    */
    public void prepareGame(String humanPlayerName, RiskColor humanPlayerColor, List<RiskStrategy> virtualPlayersStrategies);

    /**
        Aggiorna i dati del player nell' interfaccia.
        @see risk.RiskGUI#updatePlayerPanel
    */
    public void updatePlayerData(Integer numberOfTerritories, Integer numberOfFreeTanks, String currentStage);

    /**
        Aggiorna le label della board di gioco.
        @see risk.RiskGUI#updateLabelsTerritoriesButtons
    */
    public void updateLabelsTerritories(List<Territory> territories);

    /**
        Aggiorna i colori dei territori della board di gioco.
        @see risk.RiskGUI#updateColorsTerritoriesButtons
    */
    public void updateColorsTerritories(List<Territory> territories);

    /**
        Setta il valore del mediator.
        @param mediator riferimento al mediator
    */
    public void setMediator(RiskMediator mediator);

    /** 
        Rende selezionabili i territori passati a parametro.
        @param territories lista dei territori cliccabili
        @see risk.RiskGUI#setClickableTerritories
    */
    public void setAvailableTerritories(List<Territory> territories);

    /**
        Crea una nuova interfaccia.
    */
    public void createRiskInterface();

    /**
        Pulisce la lista dei territori cliccati sinora.
    */
    public void clearInvolvedTerritories();

    /**
        Setta il numero di armate da spostare.
    */
    public void setNumberOfTanksToMove(Integer v);

    /**
        Permette di passare alla prossima fase di gioco, su richiesta del giocatore corrente.
        @see risk.RiskMediator#nextStage
    */
    public void endStage();

    /**
        Restituisce la lista delle corrispondenze Continente - numero di armate.
    */
    public Map<Continent, Integer> getAllContinentsBonus();

    /**
        Disabilita la possibilità di scambiare un tris di carte.
    */
    public void disableCards();

    /**
        Abilita la possibilità di scambiare un tris di carte.
    */
    public void enableCards();

    /**
        Disabilita la possibilità di passare alla prossima fase di gioco.
    */
    public void disableEndStage();

    /**
        Abilita la possibilità di passare alla prossima fase di gioco.
    */
    public void enableEndStage();

    /**
        Mostra un messaggio nell'interfaccia.
        @param message messaggio da visualizzare.
        @see risk.RiskGUI#showMessage
    */
    public void showMessage(String message);

    /** 
        Conclude il gioco.
    */
    public void endGame();

    /**
        Aggiunge un osservatore per il log panel.
        @see risk.LogPanel
        @see risk.Observable
    */
    public void addObserver(Observer observer);

}

package risk;

import java.awt.Color;
import java.util.AbstractMap.SimpleEntry;
import java.util.List;
import java.util.Map;

/**
 * Interfaccia che permette la comunicazione tra Mediator e sistema di input.
 */
public interface RiskFacade {  
    
    /**
     * Permette di passare al Mediator quali territori sono stati selezionati e
     * di effettuare l'operazione propria di questa fase di gioco.
     *
     * @param territory territorio scelto attraverso l'interfaccia di gioco
     */
    public void update(Territory territory);

    /**
     * Restituisce la lista di tutti i territori della board.
     *
     * @return lista dei territiori
     */
    public List<Territory> getTerritories();

    /**
     * Restituisce la carta degli obiettivi del player reale.
     *
     * @return obiettivo del giocatore reale
     */
    public GoalCard getHumanPlayerGoal();

    /**
     * Restituisce la lista delle carte dei simboli del player reale.
     *
     * @return carte dei simboli
     */
    public List<SymbolCard> getHumanPlayerCards();

    /**
     * Verifica se il tris passato a parametro è lecito.
     *
     * @param tris tris selezionato
     * @return true se è lecito, false altrimenti
     * @see risk.RiskMediator#checkTris
     */
    public boolean checkTris(Tris tris);

    /**
     * Scambia il tris passato a parametro con il numero di armate
     * corrispondente.
     *
     * @param tris tris selezionato
     * @see risk.RiskMediator#exchangeTris
     */
    public void exchangeTris(Tris tris);

    /**
     * Richiede o mostra i dadi estratti durante un attacco.
     *
     * @param numberOfRolledDice numero di dadi da visualizzare
     * @param attackDiceValues valori dei dadi di attacco
     * @param defenseDiceValues valori dei dadi di difesa
     * @see risk.RollFrame
     */
    public void askDice(Territory from, Territory to, Integer numberOfRolledDice, Dice[] attackDiceValues, Dice[] defenseDiceValues);

    /**
     * Permette di specificare in input il numero di armate da spostare da un
     * territorio all'altro.
     *
     * @param territory territorio destinazione
     * @param min numero di armate minime da spostare nel nuovo territorio
     * @param max numero massimo di armate che è possibile spostare
     * @return numero di armate da spostare
     */
    public Integer askNumberOfTanksToMove(Territory territory, Integer min, Integer max);

    /**
     * Passa i dati in input dalla startWindow al mediator.
     *
     * @param humanPlayerName nome scelto dal giocatore reale
     * @param humanPlayerColor colore scelto dal giocatore reale
     * @param virtualPlayersStrategies strategie scelte dal giocatore reale
     * @see risk.RiskMediator#prepareGame
     * @see risk.StartWindow
     */
    public void prepareGame(String humanPlayerName, RiskColor humanPlayerColor, List<RiskStrategy> virtualPlayersStrategies);

    /**
     * Aggiorna i dati del player nell' interfaccia.
     *
     * @param numberOfTerritories numero di territori posseduti dal player reale
     * @param currentPlayer nome del giocatore corrente
     * @param currentStage fase corrente
     * @see risk.RiskGUI#updatePlayerPanel
     */
    public void updatePlayerData(Integer numberOfTerritories, String currentPlayer, String currentStage);

    /**
     * Aggiorna le label della board di gioco.
     *
     * @param territories lista di territori da aggiornare
     * @see risk.RiskGUI#updateLabelsTerritoriesButtons
     */
    public void updateLabelsTerritories(List<Territory> territories);

    /**
     * Aggiorna i colori dei territori della board di gioco.
     *
     * @param territories lista di territori da aggiornare
     * @see risk.RiskGUI#updateColorsTerritoriesButtons
     */
    public void updateColorsTerritories(List<Territory> territories);

    /**
     * Setta il mediator.
     *
     * @param mediator mediator selezionato
     */
    public void setMediator(RiskMediator mediator);

    /**
     * Rende selezionabili i territori a parametro.
     *
     * @param territories lista dei territori cliccabili
     * @see risk.RiskGUI#setClickableTerritories
     */
    public void setAvailableTerritories(List<Territory> territories);

    /**
     * Crea una nuova interfaccia di gioco.
     */
    public void createRiskInterface(String humanPlayerName, Color humanPlayerColor, List<SimpleEntry<String, RiskColor>> players, List<String> stages);

    /**
     * Svuota la lista dei territori selezionati.
     */
    public void clearInvolvedTerritories();

    /**
     * Setta il numero di armate da spostare.
     *
     * @param numberOfTanksToMove numero di tanks da spostare
     */
    public void setNumberOfTanksToMove(Integer numberOfTanksToMove);

    /**
     * Permette di passare alla prossima fase di gioco, su richiesta del
     * giocatore corrente.
     *
     * @see risk.RiskMediator#nextStage
     */
    public void endStage();

    /**
     * Restituisce la mappa delle corrispondenze Continente - numero di armate.
     *
     * @return la mappa delle corrispondenze Continente - numero di armate
     * @see risk.BoardPanel#addContinentsBonusLabels
     */
    public Map<Continent, Integer> getAllContinentsBonus();

    /**
     * Disabilita la possibilità di scambiare un tris di carte.
     */
    public void disableCards();

    /**
     * Abilita la possibilità di scambiare un tris di carte.
     */
    public void enableCards();

    /**
     * Disabilita la possibilità di passare alla prossima fase di gioco.
     */
    public void disableEndStage();

    /**
     * Abilita la possibilità di passare alla prossima fase di gioco.
     */
    public void enableEndStage();

    /**
     * Mostra un messaggio nell'interfaccia.
     *
     * @param message messaggio da visualizzare.
     * @see risk.RiskGUI#showMessage
     */
    public void showMessage(String message);

    /**
     * Conclude il gioco.
     */
    public void endGame();

    /**
     * Aggiunge un osservatore per il log panel.
     *
     * @param observer osservatore da aggiungere
     * @see risk.LogPanel
     * @see risk.Observable
     */
    public void addObserver(Observer observer);
    
    /**
     * Permmette di conservare la volontà del giocatore di continuare ad attaccare o meno il territorio precedentemente attaccato
     * @param isContinuous 
     */
    public void setContinuousAttack(Boolean isContinuous);
    
    /**
     * @return true se il giocatore vuole continuare ad attaccare il territorio precedentemente attaccato, false altrimenti
     */
    public Boolean isContinuousAttack();

}

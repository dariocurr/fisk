package risk;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Classe astratta che modella le funzionalità di Mediator. La classe implementa
 * tutte le funzionalità che gestiranno il gioco. Il Mediator ha accesso alle
 * risorse di gioco, preparerà il gioco, gestirà il turno e le fasi di gioco, e
 * svolgerà tutte le attività di servizio per il corretto funzionamento del
 * gioco. La classe rappresenta metaforicamente un ipotetico arbitro o un
 * ipotetico banco che gestisce il gioco.
 */
public abstract class RiskMediator extends Observable {

    protected Game game;
    protected RiskFacade facade;
    protected List<Player> players;
    protected Boolean isEnded;
    protected Player currentPlayer;

    public RiskMediator() {
        this.game = new ConcreteGameBuilder().buildGame();
        AIPlayer.setTrisBonus(this.game.getAllTrisBonus());
        this.players = new ArrayList<>();
        this.isEnded = false;
    }

    /**
     * Restituisce la lista di tutti i territori.
     *
     * @return la lista dei territori della board di gioco
     */
    public List<Territory> getAllTerritories() {
        return this.game.getTerritories();
    }

    /**
     * Restituisce la lista delle corrispondenze Continente - numero di armate.
     *
     * @return la mappa Continente - numero di armate
     */
    public Map<Continent, Integer> getAllContinentsBonus() {
        return this.game.getAllContinentsBonus();
    }

    /**
     * Restituisce Facade.
     *
     * @return un riferimento a Facade
     */
    public RiskFacade getFacade() {
        return this.facade;
    }

    /**
     * Inizializza l'attributo facade.
     *
     * @param facade valore da attribuire alla variabile di classe
     */
    public void setFacade(RiskFacade facade) {
        this.facade = facade;
    }

    /**
     * Restituisce il valore dell'attributo isEnded. isEnded assume valore true
     * se uno dei giocatori ha raggiunto il proprio obiettivo, ovvero se la
     * partita è terminata, false altrimenti.
     *
     * @return valore di isEnded
     */
    public Boolean IsEnded() {
        return this.isEnded;
    }

    /**
     * Restituisce il player che attualmente detiene il turno.
     *
     * @return riferimento a player
     */
    public Player getCurrentPlayer() {
        return this.currentPlayer;
    }

    /**
     * Restituisce un riferimento al player reale.
     *
     * @return riferimento a player
     */
    public abstract Player getHumanPlayer();

    /**
     * Implementa la fase di preparazione (Preparation Stage).
     *
     * @see risk.PreparationStage
     */
    public abstract void startPreparationStage();

    /**
     * Permette di passare al successivo player nella fase di preparazione.
     *
     * @see risk.PreparationStage
     */
    public abstract void nextPlayerPreparationStage();

    /**
     * Permette di passare il turno al successivo giocatore.
     */
    public abstract void nextPlayer();

    /**
     * Permette di passare alla fase successiva di un turno di gioco.
     */
    public abstract void nextStage();

    /**
     * Inizializza il gioco creando il player reale, i player virtuali ed
     * assegnando loro dei colori. Inoltre mischia i giocatori, rilascia gli
     * obiettivi ai singoli giocatori, distribuisce i territori, mette i tank
     * iniziali e chiama il metodo startPreparationStage per avviare la fase di
     * preparazione.
     *
     * @param humanPlayerName nome scelto dal giocatore reale
     * @param humanPlayerColor colore scelto dal giocatore reale
     * @param virtualPlayersStrategies strategie scelte dall'utente per i
     * giocatori virtuali
     */
    public abstract void prepareGame(String humanPlayerName, RiskColor humanPlayerColor, List<RiskStrategy> virtualPlayersStrategies);

    /**
     * Permette di iniziare il gioco dopo la fase di preparazione.
     */
    public abstract void startGame();

    /**
     * Permette al player reale di effettuare le operazioni proprie della fase
     * in cui si trova.
     *
     * @param involvedTerritories territori scelti dal player reale
     */
    public abstract void playHumanPlayer(List<Territory> involvedTerritories);

    /**
     * Permette di scambiare un tris con il corrispondente numero di armate.
     *
     * @param tris oggetto tris che incapsula le tre carte da scambiare
     */
    public abstract void exchangeTris(Tris tris);

    /**
     * Verifica che il tris scelto sia lecito
     *
     * @param tris oggetto tris che incapsula le tre carte da scambiare
     * @return true se il tris è lecito, false altrimenti
     */
    public abstract boolean checkTris(Tris tris);

    /**
     * Permette di posizionare un' armata nel territorio passato a parametro.
     *
     * @param territory territorio in cui posizionare l'armata
     * @see risk.ReinforcementStage
     */
    public abstract void putTank(Territory territory);

    /**
     * Rimuove il numero di armate specificate a parametro dal territorio
     * specificato.
     *
     * @param territory territorio da cui rimuovere le armate
     * @param numberOfTanksToRemove numero di armate da rimuovere
     * @see risk.AttackStage
     * @see risk.ConcreteRiskMediator#handleAttack
     */
    public abstract void removeTanks(Territory territory, Integer numberOfTanksToRemove);

    /**
     * Sposta numberOfTankstoMove armate dal territorio from al territorio to.
     *
     * @param from territorio da cui spostare le armate
     * @param to territorio da cui spostare le armate
     * @param numberOfTanksToMove numero di armate da spostare
     * @see risk.MovingStage
     */
    public abstract void moveTanks(Territory from, Territory to, Integer numberOfTanksToMove);

    /**
     * Gestisce ed esegue tutte le operazioni per implementare un attacco dal
     * territorio from al territorio to.
     *
     * @param from territorio da cui attaccare
     * @param to territorio da attaccare
     * @see risk.AttackStage
     */
    public abstract void handleAttack(Territory from, Territory to);

    /**
     * Cambia il possessore di un territorio.
     *
     * @param territory territorio coinvolto
     */
    public abstract void changeOwnerTerritory(Territory territory);

    /**
     * Mostra ad interfaccia i dadi estratti durante una battaglia.
     *
     * @param numberOfRolledDice numero di dadi tirati
     */
    public void showDice(Territory from, Territory to, int numberOfRolledDice) {
        this.facade.askDice(from, to, numberOfRolledDice, this.game.getAttackDice(), this.game.getDefenseDice());
    }

}

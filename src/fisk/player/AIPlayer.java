package fisk.player;

import java.util.List;
import java.util.Map;

import fisk.card.SymbolCard.Tris;
import fisk.strategy.RiskStrategy;
import fisk.territory.Territory;

/**
 * Classe che modella un player virtuale, dotato di intelligenza artificiale.
 */
public abstract class AIPlayer extends Player {

    protected static Map<Tris, Integer> trisBonus;
    protected RiskStrategy strategy;

    /**
     * Costruttore che istanzia un player virtuale, con nome, colore e stile di
     * gioco.
     *
     * @param name     nome del giocatore virtuale
     * @param color    colore del giocatore virtuale
     * @param strategy stile del giocatore virtuale
     */
    public AIPlayer(String name, RiskColor color, RiskStrategy strategy) {
        super(name, color);
        this.strategy = strategy;
    }

    /**
     * Permette ai giocatori virtuali di conoscore i bonus corrispondenti ai diversi
     * tris.
     *
     * @param trisBonus mappa tris - bonus
     */
    public static void setTrisBonus(Map<Tris, Integer> trisBonus) {
        AIPlayer.trisBonus = trisBonus;
    }

    /**
     * Inizializza la strategia da utilizzare.
     *
     * @param strategy strategia scelta
     */
    public void setStrategy(RiskStrategy strategy) {
        this.strategy = strategy;
    }

    /**
     * Restituisce il territorio in cui aggiungere l'armata. Questo metodo viene
     * richiamato da Mediator durante la fase di rinforzo.
     *
     * @return territorio in cui aggiungere un' armata
     */
    public Territory addTank() {
        return this.strategy.addTank(this);
    }

    /**
     * Restituisce la lista dei territori: da cui attaccare e da attaccare Questo
     * metodo viene richiamato da Mediator durante la fase di attacco.
     *
     * @return lista di territori coinvolti nell'attacco
     */
    public List<Territory> attack() {
        return this.strategy.attack(this);
    }

    /**
     * Restituisce i territorio coinvolti in uno spostamento di armate. Questo
     * metodo viene richiamato da Mediator durante la fase di spostamento.
     *
     * @return i due territori coinvolti nello spostamento
     */
    public List<Territory> moveTanks() {
        return this.strategy.moveTanks(this);
    }

    /**
     * Restituisce il numero di armate da spostare nei territori passati a
     * parametro.Questo metodo viene richiamato da Mediator durante la fase di
     * spostamento.
     *
     * @param territories territori coinvolti
     * @return numero di armate da spostare
     */
    public Integer getNumberOfTanksToMove(List<Territory> territories) {
        return this.strategy.getNumberOfTanksToMove(territories);
    }

    /**
     * Permette di scegliere il tris da scambiare.
     *
     * @return tris da scambiare
     */
    public abstract Tris exchangeTris();

}

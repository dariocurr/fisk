package risk;

import java.util.ArrayList;
import java.util.List;

/**
 * Classe che modella un giocatore.
 */
public abstract class Player {

    protected final String name;
    protected final RiskColor color;
    protected final List<Territory> territories;
    protected final List<Continent> continents;
    protected final List<Tank> freeTanks;
    protected final List<SymbolCard> cards;
    protected GoalCard goal;

    /**
     * Costruttore della classe. Specifica il nome del giocatore e il colore.
     *
     * @param name nome del giocatore
     * @param color colore del giocatore
     */
    public Player(String name, RiskColor color) {
        this.name = name;
        this.color = color;
        this.territories = new ArrayList<>();
        this.continents = new ArrayList<>();
        this.freeTanks = new ArrayList<>();
        this.cards = new ArrayList<>();
    }

    /**
     * Lancia i dadi, prendendo a parametro gli stessi e il numero di lanci da
     * effettuare.
     *
     * @param dices dadi da lanciare
     * @param rolls numero di lanci
     */
    public void rollDice(Dice[] dices, int rolls) {
        for (int i = 0; i < rolls; i++) {
            dices[i].roll();
        }
        for (int i = rolls; i < 3; i++) {
            dices[i].setValue(1);
        }
    }

    /**
     * Restituisce il nome del giocatore.
     *
     * @return nome del giocatore
     */
    public String getName() {
        return this.name;
    }

    /**
     * Restituisce il colore di questo giocatore.
     *
     * @return colore di questo giocatore
     */
    public RiskColor getColor() {
        return this.color;
    }

    /**
     * Restituisce le carte del giocatore.
     *
     * @return lista delle carte
     */
    public List<SymbolCard> getCards() {
        return this.cards;
    }

    /**
     * Restituisce la lista dei territori posseduti da questo giocatore.
     *
     * @return lista dei territori del giocatore
     */
    public List<Territory> getTerritories() {
        return this.territories;
    }

    /**
     * Restituisce la lista dei continenti posseduti da questo giocatore.
     *
     * @return lista dei continenti del giocatore
     */
    public List<Continent> getContinents() {
        return this.continents;
    }

    /**
     * Restituisce la lista delle armate ancora da posizionare possedute da
     * questo giocatore.
     *
     * @return lista delle armate da posizionare del giocatore
     */
    public List<Tank> getFreeTanks() {
        return this.freeTanks;
    }

    /**
     * Setta la carta degli obiettivi di questo giocatore.
     *
     * @param goal carta degli obiettivi per questo giocatore
     */
    public void setGoal(GoalCard goal) {
        this.goal = goal;
    }

    /**
     * Restituisce la carta degli obiettivi di questo giocatore.
     *
     * @return la carta degli obiettivi
     */
    public GoalCard getGoal() {
        return this.goal;
    }

    @Override
    public String toString() {
        return this.name + " (" + this.color + ")";
    }

    @Override
    public boolean equals(Object obj) {
        if (obj != null) {
            if (obj instanceof Player) {
                Player otherPlayer = (Player) obj;
                return this.color.equals(otherPlayer.color);
            }
        }
        return false;
    }

}

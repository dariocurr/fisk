package risk;

import java.util.List;
import java.util.Map;

/**
    Classe che implementa le risorse di gioco.
    Definisce la corrispondeza tra tris di carte e numero di armate.
    Definisce la corrispondeza tra un continente e numero di armate.
    Contiene la lista di tutti i territori e di tutti i continenti.
    Contiene i dadi di attacco e di difesa.
    Contiene il mazzo delle carte dei territori e delle carte degli obiettivi.
    Mantiene la corrispondenza tra un giocatore ed un pool di armate.
*/


public abstract class Game {

    protected final Map<Tris, Integer> trisBonus;
    protected final Map<Continent, Integer> continentsBonus;
    protected final List<Territory> territories;
    protected final List<Continent> continents;
    protected final Dice[] attackDice;
    protected final Dice[] defenseDice;
    protected final GoalsDeck goalsDeck;
    protected final TerritoriesDeck territoriesDeck;
    protected final SymbolsDeck symbolDeck;
    protected final Map<RiskColor, TankPool> tanksPools;

    /**
        Costruttore della classe.
    */
    public Game(Map<Tris, Integer> trisBonus, Map<Continent, Integer> continentsBonus,
            List<Territory> territories, List<Continent> continents,
            Dice[] attackDice, Dice[] defenseDice, GoalsDeck goaslDeck,
            TerritoriesDeck territoriesDeck, SymbolsDeck symbolDeck,
            Map<RiskColor, TankPool> tanksPools) {
        this.trisBonus = trisBonus;
        this.continentsBonus = continentsBonus;
        this.territories = territories;
        this.continents = continents;
        this.attackDice = attackDice;
        this.defenseDice = defenseDice;
        this.goalsDeck = goaslDeck;
        this.territoriesDeck = territoriesDeck;
        this.symbolDeck = symbolDeck;
        this.tanksPools = tanksPools;
    }

    /**
        Restituisce la lista dei continenti.
    */
    public List<Continent> getContinents() {
        return this.continents;
    }

    /**
        Restituisce la lista dei territori.
    */
    public List<Territory> getTerritories() {
        return this.territories;
    }

    /**
        Restituisce il deck dei territori.
    */
    public TerritoriesDeck getTerritoriesDeck() {
        return this.territoriesDeck;
    }

    /**
        Restituisce il deck delle carte degli obiettivi.
    */
    public GoalsDeck getGoalsDeck() {
        return this.goalsDeck;
    }

    /**
        Restituisce la lista delle corrispondenze Tris - numero di armate.
    */
    public Map<Tris, Integer> getAllTrisBonus() {
        return this.trisBonus;
    }

    /**
        Restituisce la lista delle corrispondenze Continente - numero di armate.
        @return mappa Continent - Numero di armate
    */
    public Map<Continent, Integer> getAllContinentsBonus() {
        return this.continentsBonus;
    }

    /**
        Restituisce i dadi di attacco.
        @return array di dimensione 3 di tipo Dice
    */
    public Dice[] getAttackDice() {
        return this.attackDice;
    }

    /**
        Restituisce i dadi di difesa.
        @return array di dimensione 3 di tipo Dice
    */
    public Dice[] getDefenseDice() {
        return this.defenseDice;
    }

    /**
        Restituisce il pool di tank di un certo colore.
        @param riskColor colore delle armate
        @return pool di armate
    */
    public TankPool getTanksPool(RiskColor riskColor) {
        return this.tanksPools.get(riskColor);
    }

    /**
        Restituisce il mazzo dei carte dei simboli
        @return cosa????
    */
    public SymbolsDeck getSymbolDeck() {
        return this.symbolDeck;
    }

    /**
        Dato un tris restituisce il numero di armate da attribuire.
        @return numero di armate
    */
    public abstract Integer getTrisBonus(Tris tris);

    /**
        Dato un continente restituisce il numero di armate da attribuire.
        @return numero di armate
    */
    public abstract Integer getContinentBonus(Continent continent);

}

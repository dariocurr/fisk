package risk;

import java.util.List;
import java.util.Map;

public abstract class Game {

    protected final Map<Tris, Integer> trisBonus;
    protected final Map<Continent, Integer> continentsBonus;
    protected final List<Territory> territories;
    protected final List<Continent> continents;
    protected final Dice[] attackDice;
    protected final Dice[] defenseDice;
    protected final GoalsDeck goalsDeck;
    protected final TerritoriesDeck territoriesDeck;
    protected final SymbolsDeck symbolsDeck;
    protected final Map<RiskColor, TankPool> tanksPools;

    public Game(Map<Tris, Integer> trisBonus, Map<Continent, Integer> continentsBonus,
            List<Territory> territories, List<Continent> continents,
            Dice[] attackDice, Dice[] defenseDice, GoalsDeck goaslDeck,
            TerritoriesDeck territoriesDeck, SymbolsDeck symbolsDeck,
            Map<RiskColor, TankPool> tanksPools) {
        this.trisBonus = trisBonus;
        this.continentsBonus = continentsBonus;
        this.territories = territories;
        this.continents = continents;
        this.attackDice = attackDice;
        this.defenseDice = defenseDice;
        this.goalsDeck = goaslDeck;
        this.territoriesDeck = territoriesDeck;
        this.symbolsDeck = symbolsDeck;
        this.tanksPools = tanksPools;
    }

    public List<Continent> getContinents() {
        return this.continents;
    }

    public List<Territory> getTerritories() {
        return this.territories;
    }

    public TerritoriesDeck getTerritoriesDeck() {
        return this.territoriesDeck;
    }

    public GoalsDeck getGoalsDeck() {
        return this.goalsDeck;
    }

    public Map<Tris, Integer> getAllTrisBonus() {
        return this.trisBonus;
    }

    public Map<Continent, Integer> getAllContinentsBonus() {
        return this.continentsBonus;
    }

    public Dice[] getAttackDice() {
        return this.attackDice;
    }

    public Dice[] getDefenseDice() {
        return this.defenseDice;
    }

    public TankPool getTanksPool(RiskColor riskColor) {
        return this.tanksPools.get(riskColor);
    }

    public SymbolsDeck getSymbolsDeck() {
        return this.symbolsDeck;
    }

    public abstract Integer getTrisBonus(Tris tris);

    public abstract Integer getContinentBonus(Continent continent);

}

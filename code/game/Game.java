package risk;

import java.util.List;
import java.util.Map;

public class Game {

    protected final List<Tris> allTris;
    protected final Map<Tris, Integer> trisBonus;
    protected final Map<Continent, Integer> continentsBonus;
    protected final List<Territory> territories;
    protected final List<Continent> continents;
    protected final ClassicDice[] attackDice;
    protected final ClassicDice[] defenseDice;
    protected final GoalDeck goalsDeck;
    protected final TerritoryDeck territoriesDeck;
    protected final Map<RiskColor, TankPool> tanksPolls;

    public Game(List<Tris> allTris, Map<Tris, Integer> trisBonus,
            Map<Continent, Integer> continentsBonus, List<Territory> territories,
            List<Continent> continents, ClassicDice[] attackDice,
            ClassicDice[] defenseDice, GoalDeck goaslDeck,
            TerritoryDeck territoriesDeck, Map<RiskColor, TankPool> tanksPools) {
        this.allTris = allTris;
        this.trisBonus = trisBonus;
        this.continentsBonus = continentsBonus;
        this.territories = territories;
        this.continents = continents;
        this.attackDice = attackDice;
        this.defenseDice = defenseDice;
        this.goalsDeck = goaslDeck;
        this.territoriesDeck = territoriesDeck;
        this.tanksPolls = tanksPools;
    }

    public Integer getTrisBonus(Tris tris) {
        return this.trisBonus.get(tris);
    }

    public Integer getContinentBonus(Continent continent) {
        return this.continentsBonus.get(continent);
    }

    public List<Territory> getTerritories() {
        return territories;
    }

    public TerritoryDeck getTerritoriesDeck() {
        return this.territoriesDeck;
    }

    public GoalDeck getGoalsDeck() {
        return this.goalsDeck;
    }

    public List<Tris> getAllTris() {
        return this.allTris;
    }
    
    public Map<Continent, Integer> getAllContinentsBonus() {
        return this.continentsBonus;
    }

    public ClassicDice[] getAttackDice() {
        return this.attackDice;
    }

    public ClassicDice[] getDefenseDice() {
        return this.defenseDice;
    }

    public TankPool getTanksPools(RiskColor riskColor) {
        return this.tanksPolls.get(riskColor);
    }

}

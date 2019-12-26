package risk;

import java.util.List;
import java.util.Map;

public class Game {

    protected final List<Tris> ALL_TRIS;
    protected final Map<Tris, Integer> TRIS_BONUS;
    protected final Map<Continent, Integer> CONTINENTS_BONUS;
    protected final List<Territory> TERRITORIES;
    protected final List<Continent> CONTINENTS;
    protected final ClassicDice[] ATTACK_DICE;
    protected final ClassicDice[] DEFENSE_DICE;
    protected final GoalDeck GOAL_DECK;
    protected final TerritoryDeck TERRITORY_DECK;
    protected final Map<RiskColor, TankPool> TANK_POOLS;

    public Game(List<Tris> ALL_TRIS, Map<Tris, Integer> TRIS_BONUS,
            Map<Continent, Integer> CONTINENTS_BONUS, List<Territory> TERRITORIES,
            List<Continent> CONTINENTS, ClassicDice[] ATTACK_DICE,
            ClassicDice[] DEFENSE_DICE, GoalDeck GOAL_DECK,
            TerritoryDeck TERRITORY_DECK, Map<RiskColor, TankPool> TANK_POOLS) {
        this.ALL_TRIS = ALL_TRIS;
        this.TRIS_BONUS = TRIS_BONUS;
        this.CONTINENTS_BONUS = CONTINENTS_BONUS;
        this.TERRITORIES = TERRITORIES;
        this.CONTINENTS = CONTINENTS;
        this.ATTACK_DICE = ATTACK_DICE;
        this.DEFENSE_DICE = DEFENSE_DICE;
        this.GOAL_DECK = GOAL_DECK;
        this.TERRITORY_DECK = TERRITORY_DECK;
        this.TANK_POOLS = TANK_POOLS;
    }

    public Integer getTrisBonus(Tris tris) {
        return this.TRIS_BONUS.get(tris);
    }

    public Integer getContinentBonus(Continent continent) {
        return this.CONTINENTS_BONUS.get(continent);
    }

    public List<Territory> getTerritories() {
        return TERRITORIES;
    }

    public TerritoryDeck getTerritoryDeck() {
        return this.TERRITORY_DECK;
    }

    public GoalDeck getGoalDeck() {
        return this.GOAL_DECK;
    }

    public List<Tris> getAllTris() {
        return this.ALL_TRIS;
    }

    public ClassicDice[] getAttackDice() {
        return ATTACK_DICE;
    }

    public ClassicDice[] getDefenseDice() {
        return DEFENSE_DICE;
    }

    public TankPool getTankPool(RiskColor riskColor) {
        return this.TANK_POOLS.get(riskColor);
    }

}

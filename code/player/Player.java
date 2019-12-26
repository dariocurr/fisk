package risk;

import java.util.ArrayList;
import java.util.List;

public class Player {

    protected final String name;
    protected final RiskColor COLOR;
    protected final List<Territory> territories;
    protected final List<Continent> continents;
    protected final List<Tank> freeTanks;
    protected final List<SymbolCard> cards;
    protected GoalCard goal;

    public Player(String name, RiskColor COLOR) {
        this.name = name;
        this.COLOR = COLOR;
        this.territories = new ArrayList<>();
        this.continents = new ArrayList<>();
        this.freeTanks = new ArrayList<>();
        this.cards = new ArrayList<>();
    }

    public void rollDice(ClassicDice[] dices, int rolls) {
        for (int i = 0; i < rolls; i++) {
            dices[i].roll();
        }
        for (int i = rolls; i < 3; i++) {
            dices[i].setValue(1);
        }
    }

    public String getName() {
        return this.name;
    }

    public List<SymbolCard> getCards() {
        return this.cards;
    }

    public List<Territory> getTerritories() {
        return this.territories;
    }

    public List<Continent> getContinents() {
        return continents;
    }

    public List<Tank> getFreeTanks() {
        return this.freeTanks;
    }

    public boolean setGoal(GoalCard goal) {
        if (this.goal == null) {
            this.goal = goal;
            return true;
        } else {
            return false;
        }
    }

    public GoalCard getGoal() {
        return this.goal;
    }

    @Override
    public String toString() {
        return this.name;
    }

    public RiskColor getColor() {
        return this.COLOR;
    }

}

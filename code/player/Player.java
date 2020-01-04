package risk;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public abstract class Player {

    protected final String name;
    protected final RiskColor color;
    protected final List<Territory> territories;
    protected final List<Continent> continents;
    protected final List<Tank> freeTanks;
    protected final List<SymbolCard> cards;
    protected GoalCard goal;

    public Player(String name, RiskColor color) {
        this.name = name;
        this.color = color;
        this.territories = new ArrayList<>();
        this.continents = new ArrayList<>();
        this.freeTanks = new ArrayList<>();
        this.cards = new ArrayList<>();
    }

    public void rollDice(Dice[] dices, int rolls) {
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
        return this.continents;
    }

    public List<Tank> getFreeTanks() {
        return this.freeTanks;
    }

    public void setGoal(GoalCard goal) {
        this.goal = goal;
    }

    public GoalCard getGoal() {
        return this.goal;
    }

    @Override
    public String toString() {
        return this.name + " (" + this.color + ")";
    }

    public RiskColor getColor() {
        return this.color;
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

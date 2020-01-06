package risk;

import java.util.List;
import java.util.Map;

/**
    Classe che concretizza la classe Game.
*/

public class ConcreteGame extends Game {

    /**
        Costruttore della classe.
    */
    public ConcreteGame(Map<Tris, Integer> trisBonus,
            Map<Continent, Integer> continentsBonus, List<Territory> territories,
            List<Continent> continents, Dice[] attackDice,
            Dice[] defenseDice, GoalsDeck goaslDeck,
            TerritoriesDeck territoriesDeck, SymbolsDeck symbolDeck,
            Map<RiskColor, TankPool> tanksPools) {
        super(trisBonus, continentsBonus, territories, continents, attackDice, defenseDice, goaslDeck, territoriesDeck, symbolDeck, tanksPools);
    }

    /**
        Concretizzazione del metodo abstract definito nella superclasse.
        Implementa le regole del Risiko classico.
    */
    @Override
    public Integer getTrisBonus(Tris tris) {
        for (Tris validTris : this.trisBonus.keySet()) {
            if (validTris.equals(tris)) {
                return this.trisBonus.get(validTris);
            }
        }
        return null;
    }

    /**
        Concretizzazione del metodo abstract definito nella superclasse.
        Implementa le regole del Risiko classico.
    */
    @Override
    public Integer getContinentBonus(Continent continent) {
        for (Continent c : this.continentsBonus.keySet()) {
            if (c.equals(continent)) {
                return this.continentsBonus.get(c);
            }
        }
        return null;
    }

}

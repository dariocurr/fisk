/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package risk;

import java.util.*;

/**
 *
 * @author dario
 */
public class AIPlayer extends Player {
    
    public static final Set<String> NAMES_SET = AIPlayer.initNamesSet();
    public static final RiskStrategy[] ALL_POSSIBLE_STRATEGIES = AIPlayer.initStrategies();
    private RiskStrategy strategy;
    
    public AIPlayer(String name, RiskColor color, RiskStrategy strategy) {
        super(name, color);
        this.strategy = strategy;
    }
    
    private static Set<String> initNamesSet() {
        Set<String> temp = new HashSet<>();
        temp.add("Dario");
        temp.add("Riccardo");
        temp.add("Domenico");
        temp.add("Emanuele");
        temp.add("Salvatore");
        temp.add("Antonino");
        return temp;
    }
    
    private static RiskStrategy[] initStrategies() {
        RiskStrategy[] temp = new RiskStrategy[3];
        temp[0] = new AggressiveRiskStrategy();
        temp[1] = new BalancedRiskStrategy();
        temp[2] = new ConservativeRiskStrategy();
        return temp;
    }
    
}

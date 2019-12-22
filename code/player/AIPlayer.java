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
public class AIPlayer {
    
    public final static Set<String> NAMES_SET = initNamesSet();
    
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
    
}

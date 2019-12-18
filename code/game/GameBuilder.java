/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package risk;

/**
 *
 * @author dario
 */
public interface GameBuilder {
    
    static final String TRIS_BONUS_FILE = "res/tris.txt";
    static final String CONTINENTS_BONUS_FILE = "res/continents_bonus.txt";
    static final String ADJACENCIES_FILE = "res/adjacencies.txt";
    static final String CONTINENTS_FILE = "res/continents.txt";
    
    public Game buildGame();
    
}

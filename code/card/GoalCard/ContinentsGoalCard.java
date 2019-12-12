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
public class ContinentsGoalCard extends GoalCard<List<Continent>> {

    public ContinentsGoalCard(List<Continent> continents) {
        super(continents);
    }
    
    @Override
    public String toString() {
    return "Continents to conquer: " + super.toString();
    }
    
}

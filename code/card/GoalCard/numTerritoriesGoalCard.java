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
public class numTerritoriesGoalCard extends GoalCard<Integer> {

    public numTerritoriesGoalCard(Integer num) {
        super(num);
    }

    @Override
    public String toString() {
        return "Number of territories to conquer: " + super.toString();
    }
    
}

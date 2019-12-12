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
public class KillGoalCard extends GoalCard<Player>{

    public KillGoalCard(Player player) {
        super(player);
    }
    
    @Override
    public String toString() {
        return "Player to destroy: " + super.toString();
    }
    
}

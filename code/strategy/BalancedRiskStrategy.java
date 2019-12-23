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
public class BalancedRiskStrategy extends RiskStrategy {
    
    @Override
    protected boolean wantToAttack(Integer delta, Integer numberOfTanksToAttack) {
        return delta > numberOfTanksToAttack;
    }
    
    @Override
    public String toString() {
        return "Balanced";
    }
    
}

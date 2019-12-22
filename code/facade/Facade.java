/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package risk;

import java.awt.Color;
import java.util.*;

/**
 *
 * @author dario
 */
public interface Facade {
    
    public List<Territory> addClickedTerritory(Territory territory);
    public List<Territory> getTerritories();
    public void update();
    public GoalCard getPlayerGoal();
    public List<SymbolCard> getPlayerCards();  
    public boolean checkTris(Tris tris);
    public boolean changeTris(Tris tris);
    public Color getPlayerColor();
    public String getPlayerName();
    
    public void askMatch();
    public void askDice();
    public void askNumberOfTanks(Territory territory, Integer max);
    public void startMatch();
    
    public boolean setHumanPlayer(Player player);
    public boolean setNumberOfVirtualPlayer(Integer num);
    
    public void updatePlayerData(Integer numberOfTerritories, Integer numberOfFreeTanks, String currentStage);
    public void updateLog(String string);
    public void updateBoard(List<Territory> territories);
    
}

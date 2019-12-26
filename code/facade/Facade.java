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

    public void setHumanPlayer(Player player);

    public void setVirtualPlayersStrategies(List<RiskStrategy> strategies);

    public void updatePlayerData(Integer numberOfTerritories, Integer numberOfFreeTanks, String currentStage);

    public void updateLog(String string);

    public void updateBoard(List<Territory> territories);

    public void setMediator(Mediator mediator);

    public void setPlayer(Player player);

    public void setGui(RiskGUI riskGui);

    public void initPlayerData();

    public Mediator getMediator();

    public void showDice();

    public int getNumberOfTanks();

    public void setClickableTerritories(List<Territory> territories);

    public void showGui();

    public void clearClickedTerritories();

    public void setNumberOfTanksToMove(Integer v);

    public void end();

    public void updateColorTerritoryButton();

    public Player getCurrentPlayer();

    public Integer getNumberOfTanksToMove();
    
    public Map<Continent, Integer> getContinentBonus();

}

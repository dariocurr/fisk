package risk;

import java.awt.Color;
import java.util.*;

public interface Facade {

    public void addClickedTerritory(Territory territory);

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

    public void prepareGame(String humanPlayerName, RiskColor humanPlayerColor, List<RiskStrategy> virtualPlayersStrategies);

    public void updatePlayerData(Integer numberOfTerritories, Integer numberOfFreeTanks, String currentStage);

    public void updateLog(String string);

    public void updateLabelsTerritories(List<Territory> territories);
    
    public void updateColorsTerritories(List<Territory> territories);

    public void setMediator(Mediator mediator);

    public Mediator getMediator();

    public void showDice();

    public int getNumberOfTanks();

    public void setClickableTerritories(List<Territory> territories);

    public void createRiskInterface();

    public void clearClickedTerritories();

    public void setNumberOfTanksToMove(Integer v);

    public void endStage();

    public Player getCurrentPlayer();

    public Integer getNumberOfTanksToMove();
    
    public Map<Continent, Integer> getAllContinentsBonus();
    
    public void disableCards();
    
    public void enableCards();
    
    public void disableEndStage();
    
    public void enableEndStage();

}

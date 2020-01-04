package risk;

import java.awt.Color;
import java.util.List;
import java.util.Map;

public interface Facade {

    public void addClickedTerritory(Territory territory);

    public List<Territory> getTerritories();

    public void update();

    public GoalCard getHumanPlayerGoal();

    public List<SymbolCard> getHumanPlayerCards();
    
    public Color getHumanPlayerColor();

    public String getHumanPlayerName();

    public boolean checkTris(Tris tris);

    public void exchangeTris(Tris tris);

    public void askMatch();

    public void askDice( int numberOfRolledDice, Dice [] attackDiceValues, Dice [] defenseDiceValues );

    public Integer askNumberOfTanks(Territory territory, Integer min, Integer max);

    public void prepareGame(String humanPlayerName, RiskColor humanPlayerColor, List<RiskStrategy> virtualPlayersStrategies);

    public void updatePlayerData(Integer numberOfTerritories, Integer numberOfFreeTanks, String currentStage);

    public void updateLog(String string);

    public void updateLabelsTerritories(List<Territory> territories);
    
    public void updateColorsTerritories(List<Territory> territories);

    public void setMediator(Mediator mediator);

    public void showDice();

    public int getNumberOfTanks();

    public void setAvailableTerritories(List<Territory> territories);

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
    
    public void notifyError(String error);

    public void showMessage ( String message );

    public void endGame ();
    
    public RiskGUI getGui();

}

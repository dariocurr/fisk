package risk;

import java.awt.Color;
import java.util.List;
import java.util.Map;

public interface RiskFacade {

    public void update(Territory territory);

    public List<Territory> getTerritories();

    public GoalCard getHumanPlayerGoal();

    public List<SymbolCard> getHumanPlayerCards();

    public Color getHumanPlayerColor();

    public String getHumanPlayerName();

    public boolean checkTris(Tris tris);

    public void exchangeTris(Tris tris);

    public void askDice(int numberOfRolledDice, Dice[] attackDiceValues, Dice[] defenseDiceValues);

    public Integer askNumberOfTanksToMove(Territory territory, Integer min, Integer max);

    public void prepareGame(String humanPlayerName, RiskColor humanPlayerColor, List<RiskStrategy> virtualPlayersStrategies);

    public void updatePlayerData(Integer numberOfTerritories, Integer numberOfFreeTanks, String currentStage);

    public void updateLabelsTerritories(List<Territory> territories);

    public void updateColorsTerritories(List<Territory> territories);

    public void setMediator(RiskMediator mediator);

    public void setAvailableTerritories(List<Territory> territories);

    public void createRiskInterface();

    public void clearInvolvedTerritories();

    public void setNumberOfTanksToMove(Integer v);

    public void endStage();

    public Map<Continent, Integer> getAllContinentsBonus();

    public void disableCards();

    public void enableCards();

    public void disableEndStage();

    public void enableEndStage();

    public void showMessage(String message);

    public void endGame();

    public void addObserver(Observer observer);

}

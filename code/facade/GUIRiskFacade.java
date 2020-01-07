package risk;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Classe che implementa l'interfaccia RiskFacade per interfacciarsi con una
 * GUI.
 */
public class GUIRiskFacade implements RiskFacade {

    protected final List<Territory> clickedTerritories;
    protected RiskMediator mediator;
    protected RiskGUI gui;
    protected Integer numberOfTanksToMove;

    public GUIRiskFacade() {
        this.clickedTerritories = new ArrayList<>();
    }

    @Override
    public void setMediator(RiskMediator mediator) {
        this.mediator = mediator;
    }

    @Override
    public void addObserver(Observer observer) {
        this.mediator.addObserver(observer);
    }

    @Override
    public void update(Territory territory) {
        this.clickedTerritories.add(territory);
        this.mediator.playHumanPlayer(this.clickedTerritories);
    }

    @Override
    public List<Territory> getTerritories() {
        return this.mediator.getAllTerritories();
    }

    @Override
    public GoalCard getHumanPlayerGoal() {
        return this.mediator.getHumanPlayer().getGoal();
    }

    @Override
    public List<SymbolCard> getHumanPlayerCards() {
        return this.mediator.getHumanPlayer().getCards();
    }

    @Override
    public Color getHumanPlayerColor() {
        return this.mediator.getHumanPlayer().getColor().getColor();
    }

    @Override
    public String getHumanPlayerName() {
        return this.mediator.getHumanPlayer().getName();
    }

    @Override
    public boolean checkTris(Tris tris) {
        return this.mediator.checkTris(tris);
    }

    @Override
    public void exchangeTris(Tris tris) {
        this.mediator.exchangeTris(tris);
    }

    @Override
    public void askDice(Integer numberOfRolledDice, Dice[] attackDiceValues, Dice[] defenseDiceValues) {
        new RollFrame(this, numberOfRolledDice, attackDiceValues, defenseDiceValues);
    }

    @Override
    public Integer askNumberOfTanksToMove(Territory territory, Integer min, Integer max) {
        new NumberOfTanksFrame(this, territory.getName(), min, max);
        Integer temp = this.numberOfTanksToMove;
        return temp;
    }

    @Override
    public void prepareGame(String humanPlayerName, RiskColor humanPlayerColor, List<RiskStrategy> virtualPlayersStrategies) {
        this.mediator.prepareGame(humanPlayerName, humanPlayerColor, virtualPlayersStrategies);
    }

    @Override
    public void updateLabelsTerritories(List<Territory> territories) {
        this.gui.updateLabelsTerritoriesButtons(territories);
    }

    @Override
    public void updatePlayerData(Integer numberOfTerritories, Integer numberOfFreeTanks, String currentStage) {
        this.gui.updatePlayerPanel(numberOfTerritories, numberOfFreeTanks, currentStage);
    }

    @Override
    public void setAvailableTerritories(List<Territory> territories) {
        this.gui.setClickableTerritories(territories);
    }

    @Override
    public void createRiskInterface() {
        this.gui = new RiskGUI(this);
    }

    @Override
    public void clearInvolvedTerritories() {
        this.clickedTerritories.clear();
    }

    @Override
    public void setNumberOfTanksToMove(Integer numberOfTanksToMove) {
        this.numberOfTanksToMove = numberOfTanksToMove;
    }

    @Override
    public void endStage() {
        this.mediator.nextStage();
    }

    @Override
    public void updateColorsTerritories(List<Territory> territories) {
        this.gui.updateColorsTerritoriesButtons(territories);
    }

    @Override
    public Map<Continent, Integer> getAllContinentsBonus() {
        return this.mediator.getAllContinentsBonus();
    }

    @Override
    public void disableCards() {
        this.gui.disableCardsButton();
    }

    @Override
    public void enableCards() {
        this.gui.enableCardsButton();
    }

    @Override
    public void disableEndStage() {
        this.gui.disableEndStageButton();
    }

    @Override
    public void enableEndStage() {
        this.gui.enableEndStageButton();
    }

    @Override
    public void showMessage(String message) {
        this.gui.showMessage(message);
    }

    @Override
    public void endGame() {
        this.gui.dispose();
    }

}

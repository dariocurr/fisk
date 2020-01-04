package risk;

import java.awt.Color;
import java.util.*;

public class FacadeGUI implements Facade {

    protected final List<Territory> clickedTerritories;
    protected Mediator mediator;
    protected RiskGUI gui;
    protected Integer numberOfTanksToMove;

    public FacadeGUI() {
        this.clickedTerritories = new ArrayList<>();
    }

    @Override
    public void setMediator(Mediator mediator) {
        this.mediator = mediator;
    }

    @Override
    public void addClickedTerritory(Territory territory) {
        this.clickedTerritories.add(territory);
        this.update();
    }

    @Override
    public List<Territory> getTerritories() {
        return this.mediator.getTerritories();
    }

    @Override
    public void update() {
        this.mediator.play(this.clickedTerritories);
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
    public boolean checkTris(Tris tris) {
        return this.mediator.checkTris(tris);
    }

    @Override
    public void exchangeTris(Tris tris) {
        this.mediator.exchangeTris(tris);
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
    public void askDice( int numberOfRolledDice, Dice [] attackDiceValues, Dice [] defenseDiceValues ) {
        new RollFrame(this, numberOfRolledDice, attackDiceValues, defenseDiceValues);
    }

    @Override
    public Integer askNumberOfTanks(Territory territory, Integer min, Integer max) {
        new NumberOfTanksFrame(this, "" + territory, min, max);
        return this.numberOfTanksToMove;
    }

    @Override
    public void prepareGame(String humanPlayerName, RiskColor humanPlayerColor, List<RiskStrategy> virtualPlayersStrategies) {
        this.mediator.prepareGame(humanPlayerName, humanPlayerColor, virtualPlayersStrategies);
    }

    @Override
    public void askMatch() {
        new StartWindow(this);
    }

    @Override
    public void updateLog(String string) {
        this.gui.updateLogPanel(string);
    }

    @Override
    public void updateLabelsTerritories(List<Territory> territories) {
        this.gui.updateLabelsTerritoriesButtons(territories);
    }

    @Override
    public void updatePlayerData(Integer numberOfTerritories, Integer numberOfFreeTanks, String currentStage) {
        this.gui.updatePlayerPanel(numberOfTerritories, numberOfFreeTanks, currentStage);
    }

    public void showDice() {
        //System.out.println("Work in progress");
    }

    public int getNumberOfTanks() {
        //System.out.println("Work in progress");
        return 0;
    }

    @Override
    public void setAvailableTerritories(List<Territory> territories) {
        this.gui.setClickableTerritories( territories );
    }

    @Override
    public void createRiskInterface() {
        this.gui = new RiskGUI(this);
    }

    @Override
    public void clearClickedTerritories() {
        this.clickedTerritories.clear();
    }

    public void setNumberOfTanksToMove(Integer num) {
        this.numberOfTanksToMove = num;
    }

    public Integer getNumberOfTanksToMove() {
        return this.numberOfTanksToMove;
    }

    @Override
    public Player getCurrentPlayer() {
        return this.mediator.getCurrentPlayer();
    }

    @Override
    public void endStage() {
        this.mediator.endStage();
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
    public void notifyError(String error) {
        this.gui.showError(error);
    }

    @Override
    public void showMessage ( String message ){
        this.gui.showMessage( message );
    }

    @Override
    public void endGame (){
        this.gui.dispose();
    }

    @Override
    public RiskGUI getGui() {
        return this.gui;
    }
    
}

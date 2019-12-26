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
public class FacadeGUI implements Facade {

    private final List<Territory> clickedTerritories;
    private Mediator mediator;
    private Player humanPlayer;
    private RiskGUI gui;
    private List<RiskStrategy> virtualPlayersStrategies;
    private Integer numberOfTanksToMove;

    public FacadeGUI() {
        this.clickedTerritories = new ArrayList<>();
    }

    @Override
    public void setMediator(Mediator mediator) {
        this.mediator = mediator;
    }

    public Mediator getMediator() {
        return this.mediator;
    }

    @Override
    public void setPlayer(Player player) {
        this.humanPlayer = player;
    }

    @Override
    public void setGui(RiskGUI gui) {
        this.gui = gui;
    }

    @Override
    public List<Territory> addClickedTerritory(Territory territory) {
        this.clickedTerritories.add(territory);
        this.update();
        return null;
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
    public GoalCard getPlayerGoal() {
        return this.humanPlayer.getGoal();
    }

    @Override
    public List<SymbolCard> getPlayerCards() {
        return this.humanPlayer.getCards();
    }

    @Override
    public boolean checkTris(Tris tris) {
        //return this.mediator.checkTris(tris);
        return false;
    }

    @Override
    public boolean changeTris(Tris tris) {
        //return this.mediator.changeTris(null, tris);
        return false;
    }

    @Override
    public Color getPlayerColor() {
        return this.humanPlayer.getColor().getColor();
    }

    @Override
    public String getPlayerName() {
        return this.humanPlayer.getName();
    }

    @Override
    public void askDice() {
        new RollFrame(this);
    }

    @Override
    public void askNumberOfTanks(Territory territory, Integer max) {
        new NumberOfTanksFrame(this, "" + territory, max);
    }

    @Override
    public void setHumanPlayer(Player player) {
        this.humanPlayer = player;
    }

    @Override
    public void setVirtualPlayersStrategies(List<RiskStrategy> strategies) {
        this.virtualPlayersStrategies = strategies;
    }

    @Override
    public void startMatch() {
        /*
        List<Player> players = new ArrayList<>();
        AIPlayer.NAMES_SET.remove(this.humanPlayer.getName());
        List<RiskColor>  freeColors = new ArrayList<>();
        for(RiskColor color: RiskColor.values()) {
            if(!color.equals(this.humanPlayer.getColor())) {
                freeColors.add(color);
            }
        }
        for(int i = 0; i < this.virtualPlayersStrategies.size(); i++) {
            String name = (String) AIPlayer.NAMES_SET.toArray()[i];
            players.add(new AIPlayer(name, freeColors.get(i), this.virtualPlayersStrategies.get(i)));
        }
        players.add(this.humanPlayer);
        Collections.shuffle(players);
        this.mediator = new Mediator(players, new ConcreteGameBuilder().buildGame());
        PreparationStage ps = new PreparationStage( this.mediator );
        ps.init();
        this.gui = new RiskGUI(this);
         */
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
    public void updateBoard(List<Territory> territories) {
        this.gui.updateBoardPanel(territories);
    }

    @Override
    public void updatePlayerData(Integer numberOfTerritories, Integer numberOfFreeTanks, String currentStage) {
        this.gui.updatePlayerPanel(numberOfTerritories, numberOfFreeTanks, currentStage);
    }

    public void showDice() {
        System.out.println("Work in progress");
    }

    public int getNumberOfTanks() {
        System.out.println("Work in progress");
        return 0;
    }

    @Override
    public void setClickableTerritories(List<Territory> territories) {
        //this.gui.setClickableTerritories( territories );
    }

    @Override
    public void showGui() {
        //this.gui.showGui();
    }

    @Override
    public void clearClickedTerritories() {
        this.clickedTerritories.clear();
    }

    public void setNumberOfTanksToMove(Integer v) {
        System.out.println(v);
        this.numberOfTanksToMove = v;
    }

    public Integer getNumberOfTanksToMove() {
        return this.numberOfTanksToMove;
    }

    @Override
    public Player getCurrentPlayer() {
        return this.mediator.getCurrentPlayer();
    }

    public void initPlayerData() {
        this.updatePlayerData(this.mediator.getCurrentPlayer().getTerritories().size(), this.mediator.getCurrentPlayer().getFreeTanks().size(), this.mediator.getCurrentStage().toString());
        this.setClickableTerritories(this.mediator.getCurrentStage().setClickableTerritories());
    }

    @Override
    public void end() {
        this.mediator.end();
    }

    @Override
    public void updateColorTerritoryButton() {
        this.gui.updateColorTerritoryButton();
    }
    
    @Override
    public Map<Continent, Integer> getContinentBonus() {
        //return this.mediator.getContinentBonus();
        return null;
    }

}

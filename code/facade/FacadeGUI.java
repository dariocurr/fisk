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
    private Integer numberOfVirtualPlayer;
    private RiskGUI gui;
    
    public FacadeGUI() {
        this.clickedTerritories = new ArrayList<>();
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
        //this.mediator.play( this.clickedTerritories );
        System.out.println(this.clickedTerritories);
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
    public void askDice (){
    	new RollFrame(this);
    }
    
    @Override
    public void askNumberOfTanks (Territory territory, Integer max) {
        new NumberOfTanksFrame(this, "" + territory, max);
    }
    
    @Override
    public boolean setHumanPlayer(Player player) {
        if (this.humanPlayer == null) {
            this.humanPlayer = player;
            return true;
        } else {
            return false;
        }
        
    }
    
    @Override
    public boolean setNumberOfVirtualPlayer(Integer num) {
        if (this.numberOfVirtualPlayer == null) {
            this.numberOfVirtualPlayer = num;
            return true;
        } else {
            return false;
        }
    }

    @Override
    public void startMatch() {
        List<Player> players = new ArrayList<>();
        AIPlayer.NAMES_SET.remove(this.humanPlayer.getName());
        List<RiskColor>  freeColors = new ArrayList<>();
        for(RiskColor color: RiskColor.values()) {
            if(!color.equals(this.humanPlayer.getColor())) {
                freeColors.add(color);
            }
        }
        for(int i = 0; i < this.numberOfVirtualPlayer; i++) {
            players.add(new Player((String) AIPlayer.NAMES_SET.toArray()[i], freeColors.get(i)));
        }
        players.add(this.humanPlayer);
        Collections.shuffle(players);
        this.mediator = new Mediator(players, new ConcreteGameBuilder().buildGame());
        this.gui = new RiskGUI(this);
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
    
    
}

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
    private Player player;
    private Integer numberOfVirtualPlayer;
    //private final GUI gui;
    
    public FacadeGUI() {
        this.clickedTerritories = new ArrayList<>();
    }
    
    @Override
    public void setMediator(Mediator mediator) {
        this.mediator = mediator;
    }

    @Override
    public List<Territory> addClickedTerritory(Territory territory) {
        this.clickedTerritories.add(territory);
        this.update();
        return null;
    }

    @Override
    public List<Territory> getTerritories() {
        // return this.mediator.getTerritories();
        return null;
    }
    
    @Override
    public void update() {
        //this.mediator.play( this.clickedTerritories );
        System.out.println(this.clickedTerritories);
    }
    
    @Override
    public GoalCard getPlayerGoal() {
        return this.player.getGoal();
    }
    
    @Override
    public List<SymbolCard> getPlayerCards() {
        return this.player.getCards();
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
        return this.player.getColor().getColor();
    }

    @Override
    public String getPlayerName() {
        return this.player.getName();
    }
    
    @Override
    public boolean updatePlayerData(Integer numberOfTerritories, Integer numberOfFreeTanks, String currentStage) {
        //TODO
        return true;
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
        if (this.player == null) {
            this.player = player;
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
        AIPlayer.NAMES_SET.remove(this.player.getName());
        List<RiskColor>  freeColors = new ArrayList<>();
        for(RiskColor color: RiskColor.values()) {
            if(!color.equals(this.player.getColor())) {
                freeColors.add(color);
            }
        }
        for(int i = 0; i < this.numberOfVirtualPlayer; i++) {
            players.add(new Player((String) AIPlayer.NAMES_SET.toArray()[i], freeColors.get(i)));
        }
        players.add(this.player);
        Collections.shuffle(players);
        new Mediator(players, new ConcreteGameBuilder().buildGame());
        new MainWindow(this);
    }

    @Override
    public void askMatch() {
        new StartWindow(this);
    }
    
    
}

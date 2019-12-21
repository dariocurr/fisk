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
public class ConcreteFacade implements Facade {
    
    private final List<Territory> clickedTerritories;
    private final Mediator mediator;
    private final Player player;

    public ConcreteFacade(Mediator mediator, Player player) {
        this.clickedTerritories = new ArrayList<>();
        this.mediator = mediator;
        this.player = player;
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
        return this.mediator.checkTris(tris);
    }
    
    @Override
    public boolean changeTris(Tris tris) {
        return this.mediator.changeTris(null, tris);
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
    public Integer getPlayerNumberOfTerritories() {
        return this.player.getTerritories().size();
    }
    
    @Override
    public Integer getPlayerNumberOfFreeTanks() {
        return this.player.getFreeTanks().size();
    }

    @Override
    public String getCurrentStage() {
        return "TODO";
    }
    
    
    
}

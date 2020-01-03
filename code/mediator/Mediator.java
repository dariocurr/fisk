package risk;

import java.util.*;

public class Mediator {

    private Game game;
    private Player humanPlayer;
    protected Facade facade;
    protected List<Player> players;
    protected List<Stage> stages; // { reinforcementState, attackState, movingState }
    protected Player currentPlayer;
    protected Stage currentStage;
    protected boolean currentPlayerWinsTerritory;

    public Mediator() {
        this.game = new ConcreteGameBuilder().buildGame();
        this.players = new ArrayList<>();
        this.stages = new ArrayList<>();
        this.currentPlayerWinsTerritory = false;
    }

    public void prepareGame(String humanPlayerName, RiskColor humanPlayerColor, List<RiskStrategy> virtualPlayersStrategies) {
        this.createHumanPlayer(humanPlayerName, humanPlayerColor);
        this.createVirtualPlayers(virtualPlayersStrategies);
        this.initStages();
        AIPlayer.setTrisBonus(this.game.getAllTrisBonus());
        PreparationStage ps = new PreparationStage(this);
        ps.init();
        this.facade.createRiskInterface();
        this.facade.updatePlayerData(this.humanPlayer.getTerritories().size(), this.humanPlayer.getFreeTanks().size(), "");
        this.startPreparationStage();
    }
    
    private void startPreparationStage (){
        this.facade.disableEndStage();
        this.stages.add( 0, new PreparationStage( this ) );
        this.currentPlayer = this.players.get( this.players.size() - 1 );
        this.nextPlayerPreparationStage();
    }

    private boolean preparationStageEnd (){
        for ( Player p : this.players )
            if ( p.getFreeTanks().size() > 0 )
                return false;
        return true;
    }

    public void nextPlayerPreparationStage (){
        if ( this.preparationStageEnd() ){
            this.stages.remove(0);
            this.startGame();
        }
        else{
            int indexCurrentPlayer = this.players.indexOf(this.currentPlayer);
            int indexNextPlayer = (indexCurrentPlayer + 1) % this.players.size();
            this.currentPlayer = this.players.get(indexNextPlayer);
            this.currentStage = this.stages.get(0);
            this.facade.setClickableTerritories(this.currentStage.setClickableTerritories());
            this.facade.updatePlayerData(this.currentPlayer.getTerritories().size(), this.currentPlayer.getFreeTanks().size(), this.currentStage.toString());
            this.facade.updateLog( "It's the turn of " + this.currentPlayer.getName() + " (" + this.currentPlayer.getColor() + ")");
            if (this.currentPlayer instanceof AIPlayer) {
                this.playPreparationStageAIPlayer();
            }
        }
    }

    private void playPreparationStageAIPlayer() {
        AIPlayer aiPlayer = (AIPlayer) this.currentPlayer;
        List<Territory> clickedTerritories = new ArrayList<>();
        int counter = 0;
        while (counter < 3 && aiPlayer.getFreeTanks().size() > 0) {
            clickedTerritories.add(aiPlayer.addTank());
            this.currentStage.play(clickedTerritories);
            clickedTerritories.clear();
            counter++;
            this.pause(1);
        }
    }
    
    public void removePlayer ( Player p ){
        this.players.remove( p );
    }

    public void showMessage ( String message ){
        this.facade.showMessage( message );
    }

    public void startGame() {
        this.currentPlayer = this.players.get(this.players.size() - 1);
        this.nextPlayer();
    }

    public void createHumanPlayer(String name, RiskColor color) {
        this.humanPlayer = new Player(name, color);
        this.players.add(this.humanPlayer);
    }

    public Player getHumanPlayer() {
        return this.humanPlayer;
    }

    public void createVirtualPlayers(List<RiskStrategy> virtualPlayersStrategies) {
        AIPlayer.NAMES_SET.remove(this.humanPlayer.getName());
        List<RiskColor> freeColors = new ArrayList<>();
        for (RiskColor color : RiskColor.values()) {
            if (!color.equals(this.humanPlayer.getColor())) {
                freeColors.add(color);
            }
        }
        for (int i = 0; i < virtualPlayersStrategies.size(); i++) {
            String name = (String) AIPlayer.NAMES_SET.toArray()[i];
            this.players.add(new AIPlayer(name, freeColors.get(i), virtualPlayersStrategies.get(i)));
        }
    }

    private void initStages() {
        ReinforcementStage rs = new ReinforcementStage(this);
        AttackStage as = new AttackStage(this);
        MovingStage ms = new MovingStage(this);
        this.stages.add(rs);
        this.stages.add(as);
        this.stages.add(ms);
    }

    public void endStage() {
        if (this.currentStage.checkEndStage()) {
            this.getFacade().clearClickedTerritories();
            this.nextStage();
        }
    }

    public void endGame (){
        this.facade.endGame();
    }

    public List<Territory> getTerritories() {
        return this.game.getTerritories();
    }

    public void setFacade(Facade facade) {
        this.facade = facade;
    }

    public void setPlayers(List<Player> players) {
        this.players = players;
    }

    public SymbolDeck getSymbolDeck() {
        return this.game.getSymbolDeck();
    }

    public Player getCurrentPlayer() {
        return this.currentPlayer;
    }

    public List<Player> getPlayers() {
        return this.players;
    }

    public boolean getCurrentPlayerWinsTerritory() {
        return currentPlayerWinsTerritory;
    }

    public Map<Continent, Integer> getAllContinentsBonus() {
        return this.game.getAllContinentsBonus();
    }

    public void setCurrentPlayerWinsTerritory(boolean value) {
        this.currentPlayerWinsTerritory = value;
    }

    public Stage getCurrentStage() {
        return this.currentStage;
    }

    public List<Stage> getStages() {
        return this.stages;
    }

    public Facade getFacade() {
        return this.facade;
    }
    
    public Game getGame() {
        return this.game;
    }

    public void nextPlayer() {
        int indexCurrentPlayer = this.players.indexOf(this.currentPlayer);
        int indexNextPlayer = (indexCurrentPlayer + 1) % this.players.size();
        this.currentPlayer = this.players.get(indexNextPlayer);
        this.releaseTanksToPlayer();
        this.currentStage = this.stages.get(0);
        this.currentPlayerWinsTerritory = false;
        this.facade.setClickableTerritories(this.currentStage.setClickableTerritories());
        this.facade.updatePlayerData(this.currentPlayer.getTerritories().size(), this.currentPlayer.getFreeTanks().size(), this.currentStage.toString());
        this.facade.updateLog( "It's the turn of " + this.currentPlayer.getName() + " (" + this.currentPlayer.getColor() + ")");
        if (this.currentPlayer instanceof AIPlayer) {
            this.playAIPlayer();
        } else {
            if (this.currentPlayer.getFreeTanks().size() > 0) {
                this.facade.enableCards();
                this.facade.disableEndStage();
            } else {
                this.endStage();
            }
            
        }
    }
    
    protected void releaseTanksToPlayer() {
        Integer numberOfTanks = this.currentPlayer.getTerritories().size() / 3;
        for (Continent continent: this.currentPlayer.getContinents()) {
            numberOfTanks += this.game.getContinentBonus(continent);
        }
        for(int i = 0; i < numberOfTanks; i++) {
            this.currentPlayer.getFreeTanks().add(this.game.getTanksPool(this.currentPlayer.getColor()).releaseTank());
        }
    }

    public void nextStage() {
        this.getFacade().clearClickedTerritories();
        if (this.currentStage instanceof MovingStage) {
            this.nextPlayer();
        } else {
            int indexCurrentStage = this.stages.indexOf(this.currentStage);
            int indexNextStage = indexCurrentStage + 1;
            this.currentStage = this.stages.get(indexNextStage);
            if ((this.currentPlayer.equals(this.humanPlayer)) && (this.currentStage instanceof AttackStage)) {
                this.facade.disableCards();
                this.facade.enableEndStage();
            }
            this.facade.setClickableTerritories(this.currentStage.setClickableTerritories());
            this.facade.updatePlayerData(this.currentPlayer.getTerritories().size(), this.currentPlayer.getFreeTanks().size(), this.currentStage.toString());
        }
    }

    private void playAIPlayer() {
        AIPlayer aiPlayer = (AIPlayer) this.currentPlayer;
        List<Territory> clickedTerritories = new ArrayList<>();
        Tris tris = aiPlayer.exchangeTris();
        if(tris != null) {
            this.exchangeTris(tris);
        }
        while (aiPlayer.getFreeTanks().size() > 0) {
            clickedTerritories.add(aiPlayer.addTank());
            this.currentStage.play(clickedTerritories);
            clickedTerritories.clear();
            this.pause(1);
        }
        clickedTerritories = aiPlayer.attack();
        while (clickedTerritories != null) {
            this.currentStage.play(clickedTerritories);
            clickedTerritories.clear();
            clickedTerritories = aiPlayer.attack();
            this.pause(1);
        }
        this.endStage();
        clickedTerritories = aiPlayer.moveTanks();
        if (clickedTerritories != null) {
            MovingStage movingStage = (MovingStage) this.currentStage;
            movingStage.play(clickedTerritories, aiPlayer.getNumberOfTanksToMove(clickedTerritories));
            clickedTerritories.clear();
            this.pause(1);
        } else {
            this.endStage();
        }
    }
    
    private void pause(Integer seconds) {
        /*
        Long startTime = System.currentTimeMillis();
        Long currentTime = System.currentTimeMillis();
        while (currentTime - startTime < seconds * 1000) {
            currentTime = System.currentTimeMillis();
        }
        */
    }

    public void play(List<Territory> clickedTerritories) {
        this.currentStage.play(clickedTerritories);
    }
    
    public void exchangeTris(Tris tris) {
        if (this.checkTris(tris)) {
            Integer bonus = this.game.getTrisBonus(tris);
            for (SymbolCard symbolCard : tris.getCards()) {
                if (symbolCard instanceof TerritoryCard) {
                    TerritoryCard territoryCard = (TerritoryCard) symbolCard;
                    if (this.currentPlayer.getTerritories().contains(territoryCard.getTerritory())) {
                        bonus += 2;
                    }
                }
            }
            this.facade.updateLog(this.currentPlayer.getName() + " change " + tris + " and obtain " + bonus + " more tanks");
            for(int i = 0; i < bonus; i++) {
                this.currentPlayer.getFreeTanks().add(this.game.getTanksPool(this.currentPlayer.getColor()).releaseTank());
            }
            if(this.currentPlayer.equals(this.humanPlayer)) {
                this.facade.updatePlayerData(this.humanPlayer.getTerritories().size(), this.humanPlayer.freeTanks.size(), this.currentStage.toString());
            }
            for(SymbolCard symbolCard: tris) {
                this.game.getSymbolDeck().addCard(symbolCard);
                this.currentPlayer.getCards().remove(symbolCard);
            }
        }
    }
    
    public boolean checkTris(Tris tris) {
        for (Tris validTris: this.game.getAllTrisBonus().keySet()) {
            if (validTris.equals(tris)) {
                return true;
            }
        }
        return false;
    }
    
    public void removeTanks(Territory territory, Integer num) {
        TankPool pool = this.game.getTanksPool(territory.getOwnerPlayer().getColor());
        for (int i = 0; i < num; i++) {
            pool.acquireTank(territory.getTanks().remove(0));
        }
    }

    public void showDice ( int numberOfRolledDice ){
        this.facade.askDice( numberOfRolledDice, this.game.getAttackDice(), this.game.getDefenseDice() );
    }

}

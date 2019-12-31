package risk;

import java.util.*;
import java.util.Map.Entry;

public class Mediator {

    private Game game;
    private SymbolDeck symbolDeck;
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
        this.createNewSymbolDeck();
        this.initStages();
        PreparationStage ps = new PreparationStage(this);
        ps.init();
        this.facade.createRiskInterface();
        this.facade.updatePlayerData(this.humanPlayer.getTerritories().size(), this.humanPlayer.getFreeTanks().size(), "Preparation");
        this.startGame();
    }
    
    public void startGame() {
        this.currentPlayer = this.players.get(this.players.size() - 1);
        this.nextPlayer();
    }
    
    public void updateColorTerritoryButton() {
        this.facade.updateColorTerritoryButton();
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
            this.nextStage();
        }
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
        return this.symbolDeck;
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

    public void updateLabelTerritoryButton(List<Territory> territories) {
        this.facade.updateBoard(territories);
    }

    public void nextPlayer() {
        int indexCurrentPlayer = this.players.indexOf(this.currentPlayer);
        int indexNextPlayer = (indexCurrentPlayer + 1) % this.players.size();
        this.currentPlayer = this.players.get(indexNextPlayer);
        this.getTanksToPlayer();
        this.currentStage = this.stages.get(0);
        this.currentPlayerWinsTerritory = false;
        this.facade.setClickableTerritories(this.currentStage.setClickableTerritories());
        this.facade.updatePlayerData(this.currentPlayer.getTerritories().size(), this.currentPlayer.getFreeTanks().size(), this.currentStage.toString());
        this.facade.updateLog( "It's the turn of " + this.currentPlayer.getName() + " (" + this.currentPlayer.getColor() + ")");
        if (this.currentPlayer instanceof AIPlayer) {
            this.playAIPlayer();
        }
    }
    
    protected void getTanksToPlayer() {
        Integer numberOfTanks = this.currentPlayer.getTerritories().size() / 3;
        for (Continent continent: this.currentPlayer.getContinents()) {
            numberOfTanks += this.game.getContinentBonus(continent);
        }
        for(int i = 0; i < numberOfTanks; i++) {
            this.currentPlayer.getFreeTanks().add(this.game.getTanksPools(this.currentPlayer.getColor()).releaseTank());
        }
    }

    public void nextStage() {
        this.getFacade().clearClickedTerritories();
        int indexCurrentStage = this.stages.indexOf(this.currentStage);
        if (this.currentStage instanceof MovingStage) {
            this.nextPlayer();
        } else {
            int indexNextStage = indexCurrentStage + 1;
            this.currentStage = this.stages.get(indexNextStage);
            this.facade.setClickableTerritories(this.currentStage.setClickableTerritories());
            this.facade.updatePlayerData(this.currentPlayer.getTerritories().size(), this.currentPlayer.getFreeTanks().size(), this.currentStage.toString());
        }
    }

    private void playAIPlayer() {
        AIPlayer aiPlayer = (AIPlayer) this.currentPlayer;
        List<Territory> clickedTerritories = new ArrayList<>();
        while (aiPlayer.getFreeTanks().size() > 0) {
            clickedTerritories.add(aiPlayer.addTank());
            this.currentStage.play(clickedTerritories);
            clickedTerritories.clear();
            this.pause();
        }
        this.nextStage();
        clickedTerritories = aiPlayer.attack();
        while (clickedTerritories != null) {
            this.currentStage.play(clickedTerritories);
            clickedTerritories.clear();
            clickedTerritories = aiPlayer.attack();
            this.pause();
        }
        this.nextStage();
        Entry<List<Territory>, Integer> movingInformation = aiPlayer.moveTanks();
        if (movingInformation != null) {
            clickedTerritories = movingInformation.getKey();
            Integer numberOfTanksToMove = movingInformation.getValue();
            MovingStage movingStage = (MovingStage) this.currentStage;
            movingStage.play(clickedTerritories, numberOfTanksToMove);
            clickedTerritories.clear();
            this.pause();
        }
        this.nextStage();
    }
    
    private void pause() {
        /*
        Long startTime = System.currentTimeMillis();
        Long currentTime = System.currentTimeMillis();
        while (currentTime - startTime < 2500) {
            currentTime = System.currentTimeMillis();
        }
        */
    }

    public void createNewSymbolDeck() {
        this.symbolDeck = new SymbolDeck(this.game.getTerritoriesDeck());
    }

    public void play(List<Territory> clickedTerritories) {
        this.currentStage.play(clickedTerritories);
    }

}

class PreparationStage {

    private Mediator mediator;

    public PreparationStage(Mediator mediator) {
        this.mediator = mediator;
    }

    public void init() {
        Collections.shuffle(this.mediator.getPlayers());
        this.releaseGoal();
        this.checkGoals();
        this.releaseTerritory();
        this.releaseTanks();
        this.initTerritory();
    }

    private void initTerritory() {
        for (Player p : this.mediator.getPlayers()) {
            this.initTerritoryToPlayer(p);
        }
    }

    private void initTerritoryToPlayer(Player p) {
        for (Territory t : p.getTerritories()) {
            t.getTanks().add(p.getFreeTanks().remove(0));
        }
    }

    private void releaseTanks() {
        Integer numberOfTanks;
        if (this.mediator.getPlayers().size() == 3) {
            numberOfTanks = 35;
        } else if (this.mediator.getPlayers().size() == 4) {
            numberOfTanks = 30;
        } else if (this.mediator.getPlayers().size() == 5) {
            numberOfTanks = 25;
        } else {
            numberOfTanks = 20;
        }
        for (Player p : this.mediator.getPlayers()) {
            this.releaseTanksToPlayer(p, numberOfTanks);
        }
    }

    private void releaseTanksToPlayer(Player p, Integer numberOfTanks) {
        for (int i = 0; i < numberOfTanks; i++) {
            p.getFreeTanks().add(this.mediator.getGame().getTanksPools(p.getColor()).releaseTank());
        }
    }

    private void releaseTerritory() {
        TerritoryCard drawCard;
        boolean ended = false;
        while (!this.mediator.getGame().getTerritoriesDeck().isEmpty()) {
            for (int i = 0; i < this.mediator.getPlayers().size() && !ended; i++) {
                Player p = this.mediator.getPlayers().get(i);
                drawCard = this.mediator.getGame().getTerritoriesDeck().removeCard();
                p.getTerritories().add(drawCard.getTerritory());
                drawCard.getTerritory().setOwnerPlayer(p);
                if(this.mediator.getGame().getTerritoriesDeck().isEmpty()) {
                    ended = true;
                }
            }
        }
    }

    private void releaseGoal() {
        for (Player p : this.mediator.getPlayers()) {
            p.setGoal(this.mediator.getGame().getGoalsDeck().removeCard());
        }
    }

    private void checkGoals() {
        for (Player player : this.mediator.getPlayers()) {
            if (player.getGoal() instanceof KillGoalCard) {
                boolean found = false;
                Player playerToKill = null;
                RiskColor colorToKill = (RiskColor) player.getGoal().getCard();
                for (int i = 0; i < this.mediator.getPlayers().size() && !found; i++) {
                    if (this.mediator.getPlayers().get(i).getColor() == colorToKill) {
                        playerToKill = this.mediator.getPlayers().get(i);
                        found = true;
                    }
                }
                if (found) {
                    if (player.equals(playerToKill)) {
                        player.setGoal(new NumberOfTerritoriesGoalCard(24));
                    }
                } else {
                    player.setGoal(new NumberOfTerritoriesGoalCard(24));
                }
            }
        }
    }

}

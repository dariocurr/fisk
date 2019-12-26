package risk;

import java.util.*;
import java.io.*;

public class Mediator {

    private Game game;
    private SymbolDeck symbolDeck;
    protected Facade facade;
    protected List<Player> players;
    protected List<Stage> stages; // { reinforcementState, attackState, movingState }
    protected Player currentPlayer;
    protected Stage currentStage;
    protected boolean currentPlayerWinsTerritory;

    /*public Mediator(ArrayList<Player> players, Game game, Facade facade) {
        this.players = players;
        this.game = game;
        this.symbolDeck = new SymbolDeck(this.game.getTerritoryDeck());
        this.currentPlayerWinsTerritory = false;
        this.stages = new ArrayList<>();
    }

    public Mediator(List<Player> players, Game game) {
        this.players = players;
        this.game = game;
        this.symbolDeck = new SymbolDeck(this.game.getTerritoryDeck());
        this.currentPlayerWinsTerritory = false;
        this.stages = new ArrayList<>();
    }

    public Mediator ( Game game ){
    	this.game = game;
        this.symbolDeck = new SymbolDeck(this.game.getTerritoryDeck());
        this.currentPlayerWinsTerritory = false;
        this.stages = new ArrayList<>();
    }*/
    public Mediator() {
        this.players = new ArrayList<>();
        this.stages = new ArrayList<>();
        this.currentPlayerWinsTerritory = false;
    }

    public void prepareGame() {
        this.createGame();
        this.createNewSymbolDeck();
        this.initStages();
        PreparationStage ps = new PreparationStage(this);
        ps.init();
        this.setFirstPlayer();
        this.setFirstStage();
    }

    public void updateColorTerritoryButton() {
        this.facade.updateColorTerritoryButton();
    }

    private void initStages() {
        ReinforcementStage rs = new ReinforcementStage(this);
        AttackStage as = new AttackStage(this);
        MovingStage ms = new MovingStage(this);
        this.addStage(rs);
        this.addStage(as);
        this.addStage(ms);
    }

    public void end() {
        if (this.currentStage.checkEndStage()) {
            this.nextStage();
        }
    }

    private void createGame() {
        this.game = new ConcreteGameBuilder().buildGame();
    }

    public void addStage(Stage s) {
        this.stages.add(s);
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

    public Game getGame() {
        return this.game;
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

    public void updateLabelTerritoryButton(List<Territory> territories) {
        this.facade.updateBoard(territories);
    }

    public void nextPlayer() {
        int indexCurrentPlayer = this.players.indexOf(this.currentPlayer);
        int indexNextPlayer = (indexCurrentPlayer + 1) % this.players.size();
        this.currentPlayer = this.players.get(indexNextPlayer);
        this.currentStage = this.stages.get(0);
        this.currentPlayerWinsTerritory = false;
        this.facade.setClickableTerritories(this.currentStage.setClickableTerritories());
        this.facade.updatePlayerData(this.currentPlayer.getTerritories().size(),
                this.currentPlayer.getFreeTanks().size(),
                this.currentStage.toString());
    }

    public void nextStage() {

        this.getFacade().clearClickedTerritories();
        int indexCurrentStage = this.stages.indexOf(this.currentStage);
        if (indexCurrentStage == 2) {
            this.nextPlayer();
        } else {
            int indexNextStage = indexCurrentStage + 1;
            System.out.println(indexNextStage);
            this.currentStage = this.stages.get(indexNextStage);
            this.facade.setClickableTerritories(this.currentStage.setClickableTerritories());
            this.facade.updatePlayerData(this.currentPlayer.getTerritories().size(),
                    this.currentPlayer.getFreeTanks().size(),
                    this.currentStage.toString());
        }
    }

    public void createNewSymbolDeck() {
        this.symbolDeck = new SymbolDeck(this.game.getTerritoryDeck());
    }

    public void setFirstPlayer() {
        this.currentPlayer = this.players.get(0);
    }

    public void setFirstStage() {
        this.currentStage = this.stages.get(0);
    }

    public void play(List<Territory> clickedTerritories) {
        this.currentStage.play(clickedTerritories);
    }

}

class GuiMediator extends Mediator {

    /*public GuiMediator ( ArrayList<Player> players, Game game ) {
		super( players, game );
	}

	//     non sono sicuro sul valore di ritorno
	public void play ( List<Territory> clickedTerritories ){
		super.currentStage.play( clickedTerritories );
	}*/
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
        /*for (Player p : this.players) {
            this.formation(p);
        }*/ //credo che non sia necessaria questa parte, sarà la prima fase di reinforcement per ogni giocatore;

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
        for (Player p : this.mediator.getPlayers()) {
            this.releaseTanksToPlayer(p);
        }
    }

    private void releaseTanksToPlayer(Player p) {
        if (this.mediator.getPlayers().size() == 3) {
            for (int i = 0; i < 35; i++) {
                p.getFreeTanks().add(new Tank(p.getColor()));
            }
        } else if (this.mediator.getPlayers().size() == 4) {
            for (int i = 0; i < 30; i++) {
                p.getFreeTanks().add(new Tank(p.getColor()));
            }
        } else if (this.mediator.getPlayers().size() == 5) {
            for (int i = 0; i < 25; i++) {
                p.getFreeTanks().add(new Tank(p.getColor()));
            }
        } else {
            for (int i = 0; i < 20; i++) {
                p.getFreeTanks().add(new Tank(p.getColor()));
            }
        }
    }

    private void releaseTerritory() {
        TerritoryCard drawCard;
        while (!this.mediator.getGame().getTerritoryDeck().isEmpty()) {
            for (Player p : this.mediator.getPlayers()) {
                drawCard = this.mediator.getGame().getTerritoryDeck().removeCard();
                p.getTerritories().add(drawCard.getTerritory());
                drawCard.getTerritory().setOwnerPlayer(p);
            }
        }
    }

    private void releaseGoal() {
        for (Player p : this.mediator.getPlayers()) {
            p.setGoal(this.mediator.getGame().getGoalDeck().removeCard());
        }
    }

    private void checkGoals() {
        for (Player player : this.mediator.getPlayers()) {
            if (player.getGoal() instanceof KillGoalCard) {
                boolean found = false;
                Player playerToKill = null;
                RiskColor colorToKill = (RiskColor) player.getGoal().card;
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

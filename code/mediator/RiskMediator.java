package risk;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public abstract class RiskMediator extends Observable {

    protected Game game;
    protected RiskFacade facade;
    protected List<Player> players;
    protected Boolean isEnded;
    protected Player currentPlayer;

    public RiskMediator() {
        this.game = new ConcreteGameBuilder().buildGame();
        AIPlayer.setTrisBonus(this.game.getAllTrisBonus());
        this.players = new ArrayList<>();
        this.isEnded = false;
    }

    public List<Territory> getAllTerritories() {
        return this.game.getTerritories();
    }

    public Map<Continent, Integer> getAllContinentsBonus() {
        return this.game.getAllContinentsBonus();
    }

    public RiskFacade getFacade() {
        return this.facade;
    }

    public void setFacade(RiskFacade facade) {
        this.facade = facade;
    }

    public Boolean IsEnded() {
        return this.isEnded;
    }

    public Player getCurrentPlayer() {
        return this.currentPlayer;
    }

    public abstract Player getHumanPlayer();

    public abstract void startPreparationStage();

    public abstract void nextPlayerPreparationStage();

    public abstract void nextPlayer();

    public abstract void nextStage();

    public abstract void prepareGame(String humanPlayerName, RiskColor humanPlayerColor, List<RiskStrategy> virtualPlayersStrategies);

    public abstract void startGame();

    public abstract void playHumanPlayer(List<Territory> involvedTerritories);

    public abstract void exchangeTris(Tris tris);

    public abstract boolean checkTris(Tris tris);

    public abstract void putTank(Territory territory);

    public abstract void removeTanks(Territory territory, Integer num);

    public abstract void moveTanks(Territory from, Territory to, int numberOfTanksToMove);

    public abstract void handleAttack(Territory from, Territory to);

    public abstract void changeOwnerOfTerritory(Territory territory);

    public void showDice(int numberOfRolledDice) {
        this.facade.askDice(numberOfRolledDice, this.game.getAttackDice(), this.game.getDefenseDice());
    }

}

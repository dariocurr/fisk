package risk;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class ConcreteRiskMediator extends RiskMediator {

    protected static final Set<String> VIRTUAL_PLAYER_NAMES_SET = ConcreteRiskMediator.initNamesSet();
    protected Player humanPlayer;
    protected List<Stage> stages;
    protected Stage currentStage;
    protected Boolean currentPlayerWinsTerritory;

    public ConcreteRiskMediator() {
        super();
        this.stages = new ArrayList<>();
        this.currentPlayerWinsTerritory = false;
    }

    protected static Set<String> initNamesSet() {
        Set<String> temp = new HashSet<>();
        temp.add("Dario");
        temp.add("Riccardo");
        temp.add("Lucia");
        temp.add("Emanuele");
        temp.add("Giulia");
        temp.add("Eleonora");
        return temp;
    }

    @Override
    public Player getHumanPlayer() {
        return this.humanPlayer;
    }

    @Override
    public void prepareGame(String humanPlayerName, RiskColor humanPlayerColor, List<RiskStrategy> virtualPlayersStrategies) {
        this.createHumanPlayer(humanPlayerName, humanPlayerColor);
        this.createVirtualPlayers(virtualPlayersStrategies);
        Collections.shuffle(this.players);
        this.initStages();
        this.releaseGoals();
        this.checkGoals();
        this.releaseInitialTanks();
        this.releaseTerritories();
        this.initTerritories();
        this.updateAllContinents();
        this.facade.createRiskInterface();
        this.facade.updatePlayerData(this.humanPlayer.getTerritories().size(), this.humanPlayer.getFreeTanks().size(), "");
        PreparationStage ps = new PreparationStage(this);
        this.startPreparationStage();
    }

    protected void createHumanPlayer(String name, RiskColor color) {
        this.humanPlayer = new ConcretePlayer(name, color);
        this.players.add(this.humanPlayer);
    }

    protected void createVirtualPlayers(List<RiskStrategy> virtualPlayersStrategies) {
        ConcreteRiskMediator.VIRTUAL_PLAYER_NAMES_SET.remove(this.humanPlayer.getName());
        List<RiskColor> freeColors = new ArrayList<>();
        for (RiskColor color : RiskColor.values()) {
            if (!color.equals(this.humanPlayer.getColor())) {
                freeColors.add(color);
            }
        }
        for (int i = 0; i < virtualPlayersStrategies.size(); i++) {
            String name = (String) ConcreteRiskMediator.VIRTUAL_PLAYER_NAMES_SET.toArray()[i];
            this.players.add(new ConcreteAIPlayer(name, freeColors.get(i), virtualPlayersStrategies.get(i)));
        }
    }

    protected void initStages() {
        ReinforcementStage rs = new ReinforcementStage(this);
        AttackStage as = new AttackStage(this);
        MovingStage ms = new MovingStage(this);
        this.stages.add(rs);
        this.stages.add(as);
        this.stages.add(ms);
    }

    protected void releaseGoals() {
        this.players.forEach((p) -> p.setGoal(this.game.getGoalsDeck().removeCard()));
    }

    protected void checkGoals() {
        for (Player player : this.players) {
            if (player.getGoal() instanceof KillGoalCard) {
                boolean found = false;
                Player playerToKill = null;
                RiskColor colorToKill = (RiskColor) player.getGoal().getCard();
                for (int i = 0; i < this.players.size() && !found; i++) {
                    if (this.players.get(i).getColor() == colorToKill) {
                        playerToKill = this.players.get(i);
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

    protected void releaseInitialTanks() {
        Integer numberOfTanks;
        if (this.players.size() == 3) {
            numberOfTanks = 35;
        } else if (this.players.size() == 4) {
            numberOfTanks = 30;
        } else if (this.players.size() == 5) {
            numberOfTanks = 25;
        } else {
            numberOfTanks = 20;
        }
        this.players.forEach((player) -> {
            for (int i = 0; i < numberOfTanks; i++) {
                player.getFreeTanks().add(this.game.getTanksPool(player.getColor()).releaseTank());
            }
        });
    }

    protected void releaseTerritories() {
        TerritoryCard drawCard;
        boolean isDrawEnded = false;
        while (!this.game.getTerritoriesDeck().isEmpty()) {
            for (int i = 0; i < this.players.size() && !isDrawEnded; i++) {
                Player p = this.players.get(i);
                drawCard = this.game.getTerritoriesDeck().removeCard();
                p.getTerritories().add(drawCard.getTerritory());
                drawCard.getTerritory().setOwnerPlayer(p);
                if (this.game.getTerritoriesDeck().isEmpty()) {
                    isDrawEnded = true;
                }
            }
        }
    }

    protected void initTerritories() {
        this.players.forEach((player) -> {
            player.getTerritories().forEach((territory) -> territory.getTanks().add(player.getFreeTanks().remove(0)));
        });
    }

    protected void updateAllContinents() {
        this.game.getContinents().forEach((continent) -> {
            this.players
                    .stream()
                    .filter((player) -> (player.getTerritories().containsAll(continent.getTerritories())))
                    .forEach((player) -> player.getContinents().add(continent));
        });
    }

    @Override
    public void startPreparationStage() {
        this.facade.disableEndStage();
        this.facade.disableCards();
        this.stages.add(0, new PreparationStage(this));
        this.currentPlayer = this.players.get(this.players.size() - 1);
        this.nextPlayerPreparationStage();
    }

    @Override
    public void nextPlayerPreparationStage() {
        if (this.isPreparationStageEnded()) {
            this.stages.remove(0);
            this.startGame();
        } else {
            int indexCurrentPlayer = this.players.indexOf(this.currentPlayer);
            int indexNextPlayer = (indexCurrentPlayer + 1) % this.players.size();
            this.currentPlayer = this.players.get(indexNextPlayer);
            this.currentStage = this.stages.get(0);
            this.currentStage.setAvailableTerritories();
            this.facade.updatePlayerData(this.humanPlayer.getTerritories().size(), this.humanPlayer.getFreeTanks().size(), this.currentStage.toString());
            this.notifyObservers("It's the turn of " + this.currentPlayer.getName() + " (" + this.currentPlayer.getColor() + ")");
            if (this.currentPlayer instanceof AIPlayer) {
                this.playPreparationStageAIPlayer();
            }
        }
    }

    protected boolean isPreparationStageEnded() {
        return this.players
                .stream()
                .noneMatch((p) -> (p.getFreeTanks().size() > 0));
    }

    protected void playPreparationStageAIPlayer() {
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

    @Override
    public void startGame() {
        this.currentPlayer = this.players.get(this.players.size() - 1);
        this.nextPlayer();
    }

    protected void endGame(Player winnerPlayer) {
        this.facade.showMessage("The player " + winnerPlayer.toString() + " wins!");
        this.facade.endGame();
    }

    @Override
    public void nextPlayer() {
        if (!this.isEnded) {
            int indexCurrentPlayer = this.players.indexOf(this.currentPlayer);
            int indexNextPlayer = (indexCurrentPlayer + 1) % this.players.size();
            this.currentPlayer = this.players.get(indexNextPlayer);
            this.releaseTanks();
            this.currentStage = this.stages.get(0);
            this.currentPlayerWinsTerritory = false;
            this.currentStage.setAvailableTerritories();
            this.facade.updatePlayerData(this.humanPlayer.getTerritories().size(), this.humanPlayer.getFreeTanks().size(), this.currentStage.toString());
            this.notifyObservers("It's the turn of " + this.currentPlayer.getName() + " (" + this.currentPlayer.getColor() + ")");
            if (this.currentPlayer instanceof AIPlayer) {
                this.playAIPlayer();
            } else {
                this.facade.enableCards();
                if (this.currentPlayer.getFreeTanks().size() > 0) {
                    this.facade.disableEndStage();
                } else {
                    this.facade.enableEndStage();
                }
            }
        }
    }

    @Override
    public void nextStage() {
        if (!this.isEnded) {
            this.facade.clearInvolvedTerritories();
            this.facade.setAvailableTerritories(new ArrayList<>());
            if (this.currentStage instanceof MovingStage) {
                if (this.currentPlayerWinsTerritory) {
                    if ((!this.game.getSymbolsDeck().isEmpty()) && (this.currentPlayer.getCards().size() < 7)) {
                        this.currentPlayer.getCards().add(this.game.getSymbolsDeck().removeCard());
                    }
                }
                this.nextPlayer();
            } else {
                int indexCurrentStage = this.stages.indexOf(this.currentStage);
                int indexNextStage = indexCurrentStage + 1;
                this.currentStage = this.stages.get(indexNextStage);
                if ((this.currentPlayer.equals(this.humanPlayer)) && (this.currentStage instanceof AttackStage)) {
                    this.facade.disableCards();
                    this.facade.enableEndStage();
                }
                this.currentStage.setAvailableTerritories();
                this.facade.updatePlayerData(this.humanPlayer.getTerritories().size(), this.humanPlayer.getFreeTanks().size(), this.currentStage.toString());
            }
        }
    }

    protected void releaseTanks() {
        Integer numberOfTanks = this.currentPlayer.getTerritories().size() / 3;
        numberOfTanks = this.currentPlayer.getContinents()
                .stream()
                .map((continent) -> this.game.getContinentBonus(continent))
                .reduce(numberOfTanks, Integer::sum);
        for (int i = 0; i < numberOfTanks; i++) {
            Tank tank = this.game.getTanksPool(this.currentPlayer.getColor()).releaseTank();
            if (tank != null) {
                this.currentPlayer.getFreeTanks().add(tank);
            }

        }
    }

    protected void playAIPlayer() {
        AIPlayer aiPlayer = (AIPlayer) this.currentPlayer;
        List<Territory> involvedTerritories = new ArrayList<>();
        Tris tris = aiPlayer.exchangeTris();
        if (tris != null) {
            this.exchangeTris(tris);
        }
        while (aiPlayer.getFreeTanks().size() > 0) {
            involvedTerritories.add(aiPlayer.addTank());
            this.currentStage.play(involvedTerritories);
            involvedTerritories.clear();
            this.pause(1);
        }
        involvedTerritories = aiPlayer.attack();
        while (involvedTerritories != null && !this.isEnded) {
            this.currentStage.play(involvedTerritories);
            involvedTerritories.clear();
            involvedTerritories = aiPlayer.attack();
            this.pause(1);
        }
        this.nextStage();
        if (!this.isEnded) {
            involvedTerritories = aiPlayer.moveTanks();
            if (involvedTerritories != null) {
                this.currentStage.play(involvedTerritories);
                involvedTerritories.clear();
                this.pause(1);
            } else {
                this.nextStage();
            }
        }
    }

    protected void pause(Integer seconds) {
        /*
        Long startTime = System.currentTimeMillis();
        Long currentTime = System.currentTimeMillis();
        while (currentTime - startTime < seconds * 1000) {
            currentTime = System.currentTimeMillis();
        }
         */
    }

    @Override
    public void exchangeTris(Tris tris) {
        if (this.checkTris(tris)) {
            Integer bonus = this.game.getTrisBonus(tris);
            /*
            for (SymbolCard symbolCard : tris.getCards()) {
            if (symbolCard instanceof TerritoryCard) {
            TerritoryCard territoryCard = (TerritoryCard) symbolCard;
            if (this.currentPlayer.getTerritories().contains(territoryCard.getTerritory())) {
            bonus += 2;
            }
            }
            }
             */
            bonus = tris.getCards()
                    .stream()
                    .filter((symbolCard) -> (symbolCard instanceof TerritoryCard))
                    .map((symbolCard) -> (TerritoryCard) symbolCard)
                    .filter((territoryCard) -> (this.currentPlayer.getTerritories().contains(territoryCard.getTerritory())))
                    .map((_item) -> 2)
                    .reduce(bonus, Integer::sum);
            this.notifyObservers(this.currentPlayer.getName() + " change " + tris + " and obtain " + bonus + " more tanks");
            for (int i = 0; i < bonus; i++) {
                this.currentPlayer.getFreeTanks().add(this.game.getTanksPool(this.currentPlayer.getColor()).releaseTank());
            }
            if (this.currentPlayer.equals(this.humanPlayer)) {
                this.facade.updatePlayerData(this.humanPlayer.getTerritories().size(), this.humanPlayer.freeTanks.size(), this.currentStage.toString());
            }
            for (SymbolCard symbolCard : tris) {
                this.game.getSymbolsDeck().addCard(symbolCard);
                this.currentPlayer.getCards().remove(symbolCard);
            }
        }
    }

    @Override
    public boolean checkTris(Tris tris) {
        return this.game.getAllTrisBonus().keySet()
                .stream()
                .anyMatch((validTris) -> (validTris.equals(tris)));
    }

    @Override
    public void putTank(Territory territory) {
        territory.getTanks().add(this.currentPlayer.getFreeTanks().remove(0));
        this.notifyObservers(this.currentPlayer + " places a tank in " + territory.getName()
                + ", " + this.currentPlayer.getFreeTanks().size() + " still to place");
        List<Territory> involvedTerritories = new ArrayList<>();
        involvedTerritories.add(territory);
        this.facade.updateLabelsTerritories(involvedTerritories);
        if (this.currentPlayer.equals(this.humanPlayer)) {
            this.facade.updatePlayerData(this.humanPlayer.getTerritories().size(),
                    this.humanPlayer.getFreeTanks().size(),
                    this.currentStage.toString());
        }
    }

    @Override
    public void removeTanks(Territory territory, Integer numberOfTanksToRemove) {
        TankPool pool = this.game.getTanksPool(territory.getOwnerPlayer().getColor());
        for (int i = 0; i < numberOfTanksToRemove; i++) {
            pool.acquireTank(territory.getTanks().remove(0));
        }
        List<Territory> involvedTerritories = new ArrayList<>();
        involvedTerritories.add(territory);
        this.facade.updateLabelsTerritories(involvedTerritories);
    }

    @Override
    public void moveTanks(Territory from, Territory to, Integer numberOfTanksToMove) {
        if (from.getOwnerPlayer().equals(to.getOwnerPlayer())) {
            for (int i = 0; i < numberOfTanksToMove; i++) {
                to.getTanks().add(from.getTanks().remove(0));
            }
            List<Territory> involvedTerritories = new ArrayList<>();
            involvedTerritories.add(from);
            involvedTerritories.add(to);
            this.facade.updateLabelsTerritories(involvedTerritories);
            this.notifyObservers(this.currentPlayer + " moved " + numberOfTanksToMove + " from " + from.getName() + " to " + to.getName());
        }
    }

    @Override
    public void handleAttack(Territory from, Territory to) {
        Player attackedPlayer = to.getOwnerPlayer();
        this.notifyObservers(this.currentPlayer.getName() + " attack " + attackedPlayer.toString()
                + " from " + from.getName() + " to " + to.getName());
        int numberOfRollAttackingPlayer = Math.min(from.getTanks().size() - 1, 3);
        this.notifyObservers("Number of dice attacking " + numberOfRollAttackingPlayer);
        int numberOfRollAttackedPlayer = Math.min(to.getTanks().size(), 3);
        this.notifyObservers("Number of dice defensing " + numberOfRollAttackedPlayer);
        this.currentPlayer.rollDice(this.game.getAttackDice(), numberOfRollAttackingPlayer);
        attackedPlayer.rollDice(this.game.getDefenseDice(), numberOfRollAttackedPlayer);
        Arrays.sort(this.game.getAttackDice(), Collections.reverseOrder());
        Arrays.sort(this.game.getDefenseDice(), Collections.reverseOrder());
        int numberOfComparisons = Math.min(numberOfRollAttackedPlayer, numberOfRollAttackingPlayer);
        /*
        if ( from.getOwnerPlayer().equals(this.humanPlayer) || to.getOwnerPlayer().equals(this.humanPlayer)) {
            this.showDice(numberOfComparisons);
        }
         */
        int[] result = this.compareDice(numberOfComparisons);
        this.removeTanks(from, result[0]);
        this.removeTanks(to, result[1]);
        List<Territory> involvedTerritories = new ArrayList<>();
        involvedTerritories.add(from);
        involvedTerritories.add(to);
        this.facade.updateLabelsTerritories(involvedTerritories);
        this.notifyObservers("Result of the clash: " + this.currentPlayer.getName() + " lost "
                + result[0] + " tank(s), " + attackedPlayer.toString() + " " + result[1]);
    }

    protected int[] compareDice(int numberOfComparisons) {
        int howManyLoseAttacking = 0;
        int howManyLoseAttacked = 0;
        for (int i = 0; i < numberOfComparisons; i++) {
            if (this.game.getAttackDice()[i].compareTo(this.game.getDefenseDice()[i]) > 0) {
                howManyLoseAttacked++;
            } else {
                howManyLoseAttacking++;
            }
        }
        int[] result = new int[2];
        result[0] = howManyLoseAttacking;
        result[1] = howManyLoseAttacked;
        return result;
    }

    @Override
    public void changeOwnerTerritory(Territory territory) {
        Player attackedPlayer = territory.getOwnerPlayer();
        attackedPlayer.getTerritories().remove(territory);
        this.checkLoosedContinent(territory);
        territory.setOwnerPlayer(this.currentPlayer);
        this.currentPlayer.getTerritories().add(territory);
        this.checkConqueredContinent(territory);
        this.currentPlayerWinsTerritory = true;
        List<Territory> involvedTerritories = new ArrayList<>();
        involvedTerritories.add(territory);
        this.facade.updateColorsTerritories(involvedTerritories);
        this.notifyObservers(this.currentPlayer + " wins " + territory.getName());
        if (this.currentPlayer.equals(this.humanPlayer) || attackedPlayer.equals(this.humanPlayer)) {
            this.facade.updatePlayerData(this.humanPlayer.getTerritories().size(),
                    this.humanPlayer.getFreeTanks().size(),
                    this.currentStage.toString());
        }
        if (!this.checkEnd()) {
            if (attackedPlayer.getTerritories().isEmpty()) {
                this.players.remove(attackedPlayer);
                this.notifyObservers("The player " + attackedPlayer.toString()
                        + " has been killed by " + this.currentPlayer.toString() + "!");
            }
        }
    }

    protected Boolean checkEnd() {
        Player winnerPlayer = null;
        for (Player player : this.players) {
            GoalCard temp = player.getGoal();
            if (temp instanceof ContinentsGoalCard) {
                ContinentsGoalCard goalCard = (ContinentsGoalCard) temp;
                if (player.getContinents().containsAll(goalCard.getCard())) {
                    winnerPlayer = player;
                }
            } else if (temp instanceof NumberOfTerritoriesGoalCard) {
                NumberOfTerritoriesGoalCard goalCard = (NumberOfTerritoriesGoalCard) temp;
                if (player.getTerritories().size() >= goalCard.getCard()) {
                    winnerPlayer = player;
                }
            } else if (temp instanceof KillGoalCard) {
                KillGoalCard goalCard = (KillGoalCard) temp;
                boolean found = false;
                Integer indexPlayerToKill = null;
                for (int i = 0; i < this.players.size() && !found; i++) {
                    if (this.players.get(i).getColor() == goalCard.getCard()) {
                        found = true;
                        indexPlayerToKill = i;
                    }
                }
                if (this.players.get(indexPlayerToKill).getTerritories().isEmpty()) {
                    winnerPlayer = player;
                }
            }
        }
        if (winnerPlayer != null) {
            this.isEnded = true;
            this.endGame(winnerPlayer);
        }
        return winnerPlayer != null;
    }

    protected void checkConqueredContinent(Territory territory) {
        if (this.currentPlayer.getTerritories().containsAll(territory.getContinent().getTerritories())) {
            this.currentPlayer.getContinents().add(territory.getContinent());
        }
    }

    protected void checkLoosedContinent(Territory territory) {
        Player attackedPlayer = territory.getOwnerPlayer();
        if (attackedPlayer.getContinents().contains(territory.getContinent())) {
            attackedPlayer.getContinents().remove(territory.getContinent());
        }
    }

    @Override
    public void playHumanPlayer(List<Territory> involvedTerritories) {
        this.currentStage.play(involvedTerritories);
    }

}

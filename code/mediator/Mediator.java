package risk;

import java.util.*;

public class Mediator {

	protected Facade facade;
	protected final List<Player> players;
	protected final List<Stage> stages; // { reinforcementState, attackState, movingState }
	protected Player currentPlayer;
	protected Stage currentStage;
	protected boolean currentPlayerWinsTerritory;
	private final Game game;
	private SymbolDeck symbolDeck;
	private PreparationStage ps = new PreparationStage(this);

	public Mediator(List<Player> players, Game game) {
            this.players = players;
            this.game = game;
            this.symbolDeck = new SymbolDeck(this.game.getTerritoryDeck());
            this.currentPlayerWinsTerritory = false;
            this.stages = new ArrayList<>();
            this.stages.add(new ReinforcementStage(this));
            this.stages.add(new AttackStage(this));
            this.stages.add(new MovingStage(this));
        }

    public SymbolDeck getSymbolDeck(){
    	return this.symbolDeck;
    }

	public Player getCurrentPlayer (){
		return this.currentPlayer;
	}

	public List<Player> getPlayers (){
		return this.players;
	}

	public boolean getCurrentPlayerWinsTerritory (){
		return currentPlayerWinsTerritory;
	}

	public Game getGame (){
		return this.game;
	}

	public void setCurrentPlayerWinsTerritory ( boolean value ){
		this.currentPlayerWinsTerritory = value;
	}

	public Stage getCurrentStage () {
		return this.currentStage;
	}

	public List<Stage> getStages () {
		return this.stages;
	}

	public Facade getFacade () {
		return this.facade;
	}

	public void nextPlayer (){
		int indexCurrentPlayer = this.players.indexOf( this.currentPlayer );
		int indexNextPlayer = ( indexCurrentPlayer + 1 ) % this.players.size();
		this.currentPlayer = this.players.get( indexNextPlayer );
		this.currentStage = this.stages.get(0);
		this.currentPlayerWinsTerritory = false;
	}

	public void nextStage (){
		int indexCurrentStage = this.stages.indexOf( this.currentStage );
		if ( indexCurrentStage == 2 )
			this.nextPlayer();
		int indexNextStage = indexCurrentStage + 1;
		this.currentStage = this.stages.get( indexNextStage );
	}

	public void createNewSymbolDeck (){
		this.symbolDeck = new SymbolDeck (this.game.getTerritoryDeck());
	}
    
    public void setFacade(Facade facade) {
        this.facade = facade;
    }
    
    public List<Territory> getTerritories() {
        return this.game.getTerritories();
    }

}

class GuiMediator extends Mediator {

	public GuiMediator ( ArrayList<Player> players, Game game ) {
		super( players, game );
	}

	//     non sono sicuro sul valore di ritorno
	public void play ( List<Territory> clickedTerritories ){
		super.currentStage.play( clickedTerritories );
	}

}

class PreparationStage {

	private Mediator mediator;

	public PreparationStage ( Mediator mediator ){
		this.mediator = mediator;
	} 

	public void init (){
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
        for (Player p : this.mediator.getPlayers() ) {
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
                        player.setGoal(new NumberTerritoriesGoalCard(24));
                    }
                } else {
                    player.setGoal(new NumberTerritoriesGoalCard(24));
                }
            }
        }
    }

}

interface Stage {

	public abstract void play ( List<Territory> clickedTerritories );

}

class ReinforcementStage implements Stage {

	private Mediator mediator;

	public ReinforcementStage ( Mediator mediator ){
		this.mediator = mediator;
	} 

	// posizionare un free tank play( List<Territory> )
	public void play ( List<Territory> clickedTerritories ){
		if ( this.mediator.getCurrentPlayer().getFreeTanks().size() > 0 && clickedTerritories.size() == 1 ){
			clickedTerritories.get(0).getTanks().add( this.mediator.getCurrentPlayer().getFreeTanks().remove(0) );
		}
	}

	// scambiare un tris play ( Tris )
	public void play ( Tris t ){
	
	/*

	************************************************************************************************************************************************************
	IMPORTANTE
	Questo metodo permette di scambiare i tris:
	lo devo implementare qui o da facade chiami direttamente il metodo changeTris in mediator?
	************************************************************************************************************************************************************

	*/
		
	}

}

class AttackStage implements Stage {

	private Mediator mediator;

	public AttackStage ( Mediator mediator ){
		this.mediator = mediator;
	} 

	public void play ( List<Territory> clickedTerritories ){
		if ( clickedTerritories.size() == 2 ){
			Territory [] involvedTerritory = new Territory [2];
			involvedTerritory[0] = clickedTerritories.get(0);
			involvedTerritory[1] = clickedTerritories.get(1);
			if ( this.checkTerritoriesForAttack( this.mediator.getCurrentPlayer(), involvedTerritory ) ){
				System.out.println( "Inizio Fase di Attacco" );
				Player attackedPlayer = involvedTerritory[1].getOwnerPlayer();
				int numberOfRollAttackingPlayer = Math.max(Math.min(involvedTerritory[0].getTanks().size()-1, 3), 1);
				System.out.println( "Numero di tank dell' attancante " + numberOfRollAttackingPlayer );
				int numberOfRollAttackedPlayer = Math.min(involvedTerritory[1].getTanks().size(), 3);
				System.out.println( "Numero di tank del difensore " + numberOfRollAttackedPlayer );
				this.mediator.getCurrentPlayer().rollDice( this.mediator.getGame().getAttackDice(), numberOfRollAttackingPlayer );
				attackedPlayer.rollDice( this.mediator.getGame().getDefenseDice(), numberOfRollAttackedPlayer );
				this.mediator.getFacade().askDice();
				Arrays.sort( this.mediator.getGame().getAttackDice(), Collections.reverseOrder() );
				Arrays.sort( this.mediator.getGame().getDefenseDice(), Collections.reverseOrder() );	
				int numberOfComparison = Math.min(numberOfRollAttackedPlayer, numberOfRollAttackingPlayer);	
				System.out.println( "Numero di scontri " + numberOfComparison );
				int [] result = this.diceComparison( numberOfComparison );
				System.out.println( "Esito scontro " + result );
				this.removeTanksFromTerritory( involvedTerritory, result );
				if ( involvedTerritory[1].getTanks().size() == 0 ){
					this.mediator.getCurrentPlayer().getTerritories().add( involvedTerritory[1] );
					involvedTerritory[1].getTanks().add(involvedTerritory[0].getTanks().remove(0));
					involvedTerritory[1].setOwnerPlayer( this.mediator.getCurrentPlayer() );
					this.mediator.setCurrentPlayerWinsTerritory( true );
				}
				if ( this.checkEnd() ){
					System.out.println( "The winner is: " + this.mediator.getCurrentPlayer() );
				}
			}
			else{
				System.out.println( "Territori errati" );
			}
		}
		else {
			System.out.println( "Inserisci un altro territorio" ); // direi di non metterlo
		}

		return;

		/*
	
		************************************************************************************************************************************************************
		IMPORTANTE
		I System.out.println("") vanno sostituiti con dei messageDialog nella gui
		************************************************************************************************************************************************************

		*/
	}

	private boolean checkEnd() {
        for (Player player : this.mediator.getPlayers()) {
            GoalCard temp = player.getGoal();
            if (temp instanceof ContinentsGoalCard) {
                ContinentsGoalCard goalCard = (ContinentsGoalCard) temp;
                return player.getContinents().containsAll(goalCard.card);
            } else if (temp instanceof NumberTerritoriesGoalCard) {
                NumberTerritoriesGoalCard goalCard = (NumberTerritoriesGoalCard) temp;
                return (player.getTerritories().size() >= goalCard.card);
            } else if (temp instanceof KillGoalCard) {
                KillGoalCard goalCard = (KillGoalCard) temp;
                boolean found = false;
                Integer indexPlayerToKill = null;
                for (int i = 0; i < this.mediator.getPlayers().size() && !found; i++) {
                    if (this.mediator.getPlayers().get(i).getColor() == goalCard.card) {
                        found = true;
                        indexPlayerToKill = i;
                    }
                }
                return (this.mediator.getPlayers().get(indexPlayerToKill).getTerritories().isEmpty());
            }
        }
        return false;
    }

	private void removeTanksFromTerritory(Territory[] involvedTerritory, int[] result) {
        for (int i = 0; i < result[0]; i++) {
            involvedTerritory[0].getTanks().remove(0);
        }
        for (int i = 0; i < result[1]; i++) {
            involvedTerritory[1].getTanks().remove(0);
        }
    }

	private int[] diceComparison(int numberOfComparison) {
        int howManyLoseAttacking = 0;
        int howManyLoseAttacked = 0;
        for (int i = 0; i < numberOfComparison; i++) {
            if (this.mediator.getGame().getAttackDice()[i].compareTo(this.mediator.getGame().getDefenseDice()[i]) > 0) {
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

	private boolean checkTerritoriesForAttack(Player p, Territory[] involvedTerritory) {
        if (involvedTerritory == null) {
            return false;
        } else if ((involvedTerritory[0].getNeighboringTerritories().contains(involvedTerritory[1]))
                && (p.getTerritories().contains(involvedTerritory[0]) && !p.getTerritories().contains(involvedTerritory[1]))) {
            return true;
        } else {
            return false;
        }
    }

}

class MovingStage implements Stage {

	private Mediator mediator;

	public MovingStage ( Mediator mediator ){
		this.mediator = mediator;
	} 

	public void play ( List<Territory> clickedTerritories ){
		Territory from = clickedTerritories.get(0);
		Territory to = clickedTerritories.get(1);
		if ( clickedTerritories.size() == 2 ){
			if ( this.mediator.getCurrentPlayer().getTerritories().contains( from ) && from.getNeighboringTerritories().contains( to ) ){
				this.mediator.getFacade().askNumberOfTanks(to, from.getTanks().size());
                                int numberOfTanksToMove = 0;
				if ( numberOfTanksToMove < from.getTanks().size() ){
					for ( int i = 0; i < numberOfTanksToMove; i++ )
						to.getTanks().add( from.getTanks().remove(0) );
				}
				else{
					System.out.println( "Errore: numero di tanks troppo alto" );
					return;
				}
			}
			else{
				System.out.println( "Errore: territori selezionati non leciti" );
				return;
			}
		}
		if ( this.mediator.getCurrentPlayerWinsTerritory() ){
			if ( this.mediator.getCurrentPlayerWinsTerritory() ){
				SymbolCard draw = this.mediator.getSymbolDeck().removeCard();
				this.mediator.getCurrentPlayer().getCards().add( draw );
				System.out.println( "E' stata estratta la carta" + draw );
			}
			if ( this.mediator.getSymbolDeck().isEmpty() ){
				for ( Player s : this.mediator.getPlayers() )
					s.getCards().clear();
			}
			this.mediator.createNewSymbolDeck();
		}
		this.mediator.nextStage();
	}

	/*
	
	************************************************************************************************************************************************************
	IMPORTANTE
	I System.out.println("") vanno sostituiti con dei messageDialog nella gui
	************************************************************************************************************************************************************

	*/

}
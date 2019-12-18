package risk;

import java.io.*;
import java.util.*;
// aggiungere metodo getDeck() in Deck<T>
// aggiungere metodo getTanks() in Territory
// come è possibile che Territory non ha un attributo name?

public class Mediator {

	private List<Player> players;
	private Game game;
        private SymbolDeck symbolDeck;

	public Mediator ( ArrayList<Player> players, Game game ){
		this.players = players;
		this.game = game;
                this.symbolDeck = new SymbolDeck(this.game.getTerritoryDeck());
	} 

	public void play (){
		this.preparationStage();
		/*while ( !this.checkEnd() ){
			for ( Player p : this.players ){
				this.startReinforcementStage( p );
				//this.startAttackStage( p );
				//this.startMovingStage( p );
			}
		}*/
		for ( Player p : this.players ){
			this.startReinforcementStage( p );
			this.startAttackStage( p );
			//this.startMovingStage( p );
		}
	}

	private void startReinforcementStage ( Player p ){ // correggi
		this.formation( p );
		Tris trisRequest;
		if ( p.getCards().size() >= 3 ) {
			do{
				System.out.println( "Mediator says: do you have a tris?" );
				trisRequest = this.askTris( p );
				if ( this.checkTris( trisRequest ) ){
					// cosa devo fare?
					System.out.println( "Scambio carte" );
				}
				else {
					System.out.println( "Invalide Tris" );
				}
			} while ( trisRequest != null );
		}
	}

	private void startAttackStage ( Player p ){ 
		Territory [] involvedTerritory;
		do{
			involvedTerritory = this.getTerritory( p );
			System.out.println( "Territori coinvolti nello scontro: " + involvedTerritory );
			if ( this.checkTerritoriesForAttack( p, involvedTerritory ) ){
				System.out.println( "Inizio Fase di Attacco" );
				Player attackedPlayer = involvedTerritory[1].getOwnerPlayer();
				int numberOfRollAttackingPlayer = Math.max(Math.min(involvedTerritory[0].getTanks().size()-1, 3), 1);
				System.out.println( "Numero di tank dell' attancante " + numberOfRollAttackingPlayer );
				int numberOfRollAttackedPlayer = Math.min(involvedTerritory[1].getTanks().size(), 3);
				System.out.println( "Numero di tank del difensore " + numberOfRollAttackedPlayer );
				p.rollDice( this.game.getAttackDice(), numberOfRollAttackingPlayer );
				attackedPlayer.rollDice( this.game.getDefenseDice(), numberOfRollAttackedPlayer );
				Arrays.sort( this.game.getAttackDice(), Collections.reverseOrder() );
				Arrays.sort( this.game.getDefenseDice(), Collections.reverseOrder() );	
				System.out.println( "Dice attaccante" );
				this.printAttackDices();
				System.out.println( "Dice difensore" );
				this.printDefenseDices();
				int numberOfComparison = Math.min(numberOfRollAttackedPlayer, numberOfRollAttackingPlayer);	
				System.out.println( "Numero di scontri " + numberOfComparison );
				int [] result = this.diceComparison( numberOfComparison );
				System.out.println( "Esito scontro " + result );
				this.removeTanksFromTerritory( involvedTerritory, result );
				if ( involvedTerritory[1].getTanks().size() == 0 ){
					p.getTerritories().add( involvedTerritory[1] );
					involvedTerritory[1].getTanks().add(involvedTerritory[0].getTanks().remove(0));
					involvedTerritory[1].setOwnerPlayer( p );
				}
				System.out.println( "Numero di tank dopo lo scontro nel territorio dell'attaccante " + involvedTerritory[0].getTanks().size() );
				System.out.println( "Numero di tank dopo lo scontro nel territorio del difensore " + involvedTerritory[1].getTanks().size() );
				// controllo continenti
				// controllo se l'obiettivo è stato raggiunto da p
			}
			else if ( involvedTerritory != null )
				System.out.println( "Territori Errati" );
		}while ( involvedTerritory != null );
	}

	private void removeTanksFromTerritory ( Territory[] involvedTerritory, int [] result ){
		for ( int i = 0; i < result[0]; i++ ){
			involvedTerritory[0].getTanks().remove(0);
		}
		for ( int i = 0; i < result[1]; i++ ){
			involvedTerritory[1].getTanks().remove(0);
		}
	}

	private int[] diceComparison ( int numberOfComparison ){
		int howManyLoseAttacking = 0;
		int howManyLoseAttacked = 0;
		for ( int i = 0; i < numberOfComparison; i++ ){
			if ( this.game.getAttackDice()[i].compareTo(this.game.getDefenseDice()[i]) > 0 )
				howManyLoseAttacked++;
			else
				howManyLoseAttacking++;
		}
		int [] result = new int [2];
		result[0] = howManyLoseAttacking;
		result[1] = howManyLoseAttacked;
		return result;
	}

	private Territory [] getTerritory ( Player p ){
		return p.attack();
	}

	// vedere se sono confinanti if (involvedTerritory[0].getNeighboring().contains( involvedTerritory[1] ))
	// vedere se sono di du palyer diversi if ( p.getTerritories().contains( involvedTerritory[0] ) && !p.getTerritories().contains( involvedTerritories[1] ) )
	// se il primo ciccato è del player che ha il turno if (  )
	private boolean checkTerritoriesForAttack ( Player p, Territory [] involvedTerritory ){
		if ( involvedTerritory == null )
			return false;
		else if ( (involvedTerritory[0].getNeighboringTerritories().contains( involvedTerritory[1] )) 
				&& ( p.getTerritories().contains( involvedTerritory[0] ) && !p.getTerritories().contains( involvedTerritory[1] ) ) )
			return true;
		else 
			return false;
	}

	/*private boolean checkTerritoriesForAttack ( Player p, Territory [] territoriesForAttack ){
		if ( territoriesForAttack == null )
			return false;

		if ( territoriesForAttack.length != 2 )
			return false;
	}*/

	private Tris askTris ( Player p ){
		return p.exchangeTris();
	}

	private boolean checkTris ( Tris t ){
		if ( t == null ) return false;
		else return this.game.getAllTris().contains( t ) ;
	}

	private void preparationStage (){
		Collections.shuffle(this.players);
		this.releaseGoal();
		this.releaseTerritory();
		this.releaseTanks();
		this.initTerritory();
		for ( Player p : this.players ){
			this.formation(p);
		}
	}

	private void formation ( Player p ){
		for ( Tank t : p.getFreeTanks() ){
			System.out.println( "\n\n" + p.getName() + " scegli territorio in cui mettere il tank: " );
			Territory territory = p.addTank(); // non è necessario verificare se il territorio è lecito, poichè il territorio viene scelto da quelli posseduti.
			territory.getTanks().add( t ); // occhio al pool di tank
		}
		p.getFreeTanks().clear();
	}

	private boolean checkEnd (){
		return true; // implementare questo metodo
	}

	private void releaseGoal () {
		for ( Player p : this.players ){
			p.setGoal( this.game.getGoalDeck().removeCard() );
		}
	}

	private void releaseTerritory () {
		TerritoryCard drawCard;
		while ( !this.game.getTerritoryDeck().isEmpty() ){
			for ( Player p : this.players ){
				drawCard = this.game.getTerritoryDeck().removeCard();
				p.getTerritories().add( drawCard.getTerritory() );
				drawCard.getTerritory().setOwnerPlayer( p );
			}
		}
	}

	private void releaseTanks () {
		for ( Player p : this.players ){
			this.releaseTanksToPlayer( p );
		}
	}

	private void initTerritory (){
		for ( Player p : this.players ){
			this.initTerritoryToPlayer( p );
		}
	}

	// occhio all'object pool dei tank
	private void releaseTanksToPlayer ( Player p ){
		if ( this.players.size() == 3 ){
			for ( int i = 0; i < 35; i++ ){
				p.getFreeTanks().add( new Tank(p.getColor()) );
			}
		}
		else if ( this.players.size() == 4 ){
			for ( int i = 0; i < 30; i++ ){
				p.getFreeTanks().add( new Tank(p.getColor()) );
			}
		}
		else if ( this.players.size() == 5 ){
			for ( int i = 0; i < 25; i++ ){
				p.getFreeTanks().add( new Tank(p.getColor()) );
			}
		}
		else {
			for ( int i = 0; i < 20; i++ ){
				p.getFreeTanks().add( new Tank(p.getColor()) );
			}
		}
	}

	private void initTerritoryToPlayer ( Player p ){
		for ( Territory t : p.getTerritories() ){
			t.getTanks().add( p.getFreeTanks().remove(0) );
		}
	}
        
        public void printAttackDices (){
        for ( ClassicDice c : this.game.getAttackDice() ){
            System.out.print( c + " " );
            }
        }

        public void printDefenseDices (){
            for ( ClassicDice c : this.game.getDefenseDice() ){
                System.out.print( c + " " );
            }
        }

}
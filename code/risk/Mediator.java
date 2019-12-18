package risk;

import java.io.*;
import java.util.*;
// aggiungere metodo getDeck() in Deck<T>
// aggiungere metodo getTanks() in Territory
// come è possibile che Territory non ha un attributo name?

public class Mediator {

	private List<Player> players;
	private Game game;

	public Mediator ( ArrayList<Player> players, Game game ){
		this.players = players;
		this.game = game;
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
			//this.startAttackStage( p );
			//this.startMovingStage( p );
		}
	}

	private void startReinforcementStage ( Player p ){ // correggi
		for ( Tank t : p.getFreeTanks() ){
			System.out.println( "\n\n" + p.getName() + " scegli territorio in cui mettere il tank: " );
			Territory territory = p.addTank(); // non è necessario verificare se il territorio è lecito, poichè il territorio viene scelto da quelli posseduti.
			territory.getTanks().add( t ); // occhio al pool di tank
		}
		p.getFreeTanks().clear();
		
		Tris trisRequest;
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

        /*
	private void startAttackStage ( Player p ){ 
		Territory [] involvedTerritory;
		do{
			involvedTerritory = this.getTerritory();
			if ( this.checkAttackedTerritory( involvedTerritory ) ){
				this.rollDice();
			}
			else if ( involvedTerritory != null )
				System.out.println( "Territori Errati" );
		}while ( involvedTerritory != null )
	}
        */

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
				p.getCards().add( drawCard );
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

}

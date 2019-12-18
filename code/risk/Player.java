/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package risk;

import java.util.*;

/**
 *
 * @author dario
 */
public class Player {
    
	private String name;
	private final RiskColor COLOR;
	private List<Territory> territories;
	private List<Continent> continents;
	private List<Tank> freeTanks;
	private GoalCard goal; // forse sarebbe meglio final?
	private List<SymbolCard> cards;

	public Player ( String name, RiskColor COLOR ){
		this.name = name;
		this.COLOR = COLOR;
		this.territories = new ArrayList<> ();
		this.continents = new ArrayList<> ();
		this.freeTanks = new ArrayList<> ();
		this.cards = new ArrayList<>();
	}

	public String getName (){
		return this.name;
	}

	public String [] attack (){
		/*Scanner in = new Scanner ( System.in );
		String attackLine = in.nextLine();
		String splittedAttack = attackLine.split(",");
		return splittedAttack;*/
		return null;
	}

	public Tris exchangeTris (){
		this.printGoalCards();

		Scanner in = new Scanner ( System.in );
		String trisLine = in.nextLine();
		
		if ( trisLine.trim().equals( "null" ) ){
			return null;
		}

		String [] trisSplitter = trisLine.split(",");
		// se uno o più dei territori presi in input non appartiene alla lista delle cards allora sarà passato null e il mediator farà il controllo di coerenza del tris
		return new Tris ( this.cards.get(Integer.valueOf( trisSplitter[0] )), this.cards.get(Integer.valueOf( trisSplitter[1] )), this.cards.get(Integer.valueOf( trisSplitter[2] )) );

	}

	private void printGoalCards (){
		int counter = 0;
		for ( SymbolCard c : this.cards ){
			System.out.println( c + " " + counter );
			counter++;
		} 
	}

	public List<SymbolCard> getCards (){
		return this.cards;
	}

	public Territory addTank (){

		this.printTerritories();

		Scanner in = new Scanner ( System.in );
		int territoryIndex = in.nextInt();

		return this.territories.get( territoryIndex );

	}

	private void printTerritories (){
		int counter = 0;
		for ( Territory t : this.territories ){
			System.out.println( t + " : " + counter );
			counter++;
		}
	}

	public List<Territory> getTerritories (){
		return this.territories;
	}

	private Territory fromStringToTerritory ( String territoryName ){
		for ( Territory t : this.territories ){
			if ( t.getName().equals( territoryName ) ){
				return t;
			} 
		}
		return null;
	}

	public List<Tank> getFreeTanks (){
		return this.freeTanks;
	}

	public boolean setGoal ( GoalCard goal ){
		if ( this.goal == null ){
			this.goal = goal;
			return true;
		}
		else 
			return false;
	}

	public GoalCard getGoal(){
		return this.goal;
	}

	@Override
	public String toString (){
		System.out.println( this.name + "\n\n" );
		System.out.println( this.goal + "\n\n" );
		System.out.println( this.territories.size() + "\n\n");
		System.out.println( this.freeTanks.size() + "\n\n" );
		System.out.println( this.cards );
		return "";
	}
        
        public RiskColor getColor() {
            return COLOR;
        }

}

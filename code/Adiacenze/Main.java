import java.util.*;
import java.io.*;

class Territory {
    
	//enum COLOR { RED, BLA, BLA };
	//ArrayList<Territory> neighboringTerritories; //public o private o something else?
	String name;

	public Territory ( String territoryName ){
		this.name = territoryName;
	}

	public String getName (){
		return this.name;
	}

	public String toString (){
		return this.getName();
	}
}

class Mediator {

	ArrayList<Territory> territories;
	ArrayList<Territory>[] adjacensies; //public o private o something else?

	public Mediator (){
		this.initTerritories();
		this.initAdjacencies();
	}

	private void initTerritories (){
		try {
			// apro file lista dei territori
			// per ogni territorio creo un nuovo oggetto
			// aggiungo questo oggetto a this.territories
			this.territories = new ArrayList<>();
			String territoryName = new String();
			String fileName = new String ( "territories.txt" );
			File territoriesFile = new File ( fileName );
			Scanner in = new Scanner ( territoriesFile );
			while ( in.hasNextLine() ){
				territoryName = in.nextLine();
				this.territories.add( new Territory ( territoryName ) );
			} 
		}
		catch( Exception e ){

		}
	}

	private void initAdjacencies(){
		try{
			/*for ( Territory t : territories ){
				File neighboringTerritories = new File ( t.getName() + "_neighboring.txt" );
				Scanner in = new Scanner ( neighboringTerritories );
				String neighboring = in.nextLine();
				//neighboringTerritories.close();
				String [] territories = neighboring.split(",");
				for ( String s : territories ){
					this.adjacensies[ this.fromTerritoryNameToIndex( t.getName() ) ].add( this.territories.get( this.fromTerritoryNameToIndex( s ) ) );
				}
			}*/

			this.adjacensies = new ArrayList[42];

			for ( int i = 0; i < 42; i++ ){
				this.adjacensies[i] = new ArrayList<Territory>();
			}

			File neighboringTerritories = new File ( "adjacensies.txt" );
			Scanner in = new Scanner ( neighboringTerritories );
			String line = new String ();
			String [] territories;
			while ( in.hasNextLine() ){
				line = in.nextLine();
				territories = line.split(",");
				for ( int i = 1; i < territories.length; i++ ){
					this.adjacensies[ this.fromTerritoryNameToIndex( territories[0].trim() ) ].add( this.territories.get( this.fromTerritoryNameToIndex( territories[i].trim() ) ) );
				}
			}
		}
		catch( Exception e ){

		}
	}

	public int fromTerritoryNameToIndex ( String territoryName ){
		switch( territoryName ){
			case "Alaska" : return 0;
			case "Alberta" : return 1;
			case "Central America": return 2;
			case "Eastern United States": return 3;
			case "Greenland": return 4;
			case "Northwest Territory": return 5;
			case "Ontario": return 6;
			case "Quebec": return 7;
			case "Western United States": return 8;
			case "Argentina": return 9;
			case "Brazil": return 10;
			case "Venezuela": return 11;
			case "Great Britain": return 12;
			case "Iceland": return 13;
			case "Northern Europe": return 14;
			case "Scandinavia": return 15;
			case "Southern Europe": return 16;
			case "Ukraine": return 17;
			case "Western Europe": return 18;
			case "Congo": return 19;
			case "East Africa": return 20;
			case "Egypt": return 21;
			case "Madagascar": return 22;
			case "North Africa": return 23;
			case "South Africa": return 24;
			case "Afghanistan": return 25;
			case "China": return 26;
			case "India": return 27;
			case "Irkutsk": return 28;
			case "Japan": return 29;
			case "Kamchatka": return 30;
			case "Middle East": return 31;
			case "Mongolia": return 32;
			case "Siam": return 33;
			case "Siberia": return 34;
			case "Ural": return 35;
			case "Yakutsk": return 36;
			case "Eastern Australia": return 37;
			case "Indonesia": return 38;
			case "LotR": return 39;
			case "New Guinea": return 40;
			case "Western Australia": return 41;
		}
		return -1;
	}

}

public class Main {

	public static void main ( String [] args ){
		Mediator m = new Mediator();
		System.out.println( m.adjacensies[2] );
	}

}
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package risk;

import java.io.*;
import java.util.*;
/**
 *
 * @author dario
 */
public class Game {
    
    private static final String TRIS_BONUS_FILE = "res/tris.txt";
    private static final String TERRITORIES_FILE = "res/countries.txt";
    private static final String ADJACENCIES_FILE = "res/adjacencies.txt";
    private static final String CONTINENTS_FILE = "res/continents.txt";
    private static final String TERRITORIES_BUTTON_FILE = "res/territory_button.txt";
    
    public final List<Tris> ALL_TRIS = new ArrayList<>();
    public final Map<Tris, Integer> TRIS_BONUS = new HashMap<>();
    public final List<Territory> TERRITORIES = new ArrayList<>();
    public final List<Continent> CONTINENTS = new ArrayList<>();
    public final List<TerritoryButton> TERRITORY_BUTTONS = new ArrayList<>();
    public final ClassicDice[] ATTACK_DICE = new ClassicDice[3];
    public final ClassicDice[] DEFENSE_DICE = new ClassicDice[3];
            
    public Game() {
        this.initGame();
    }
    
    public Integer getTrisBonus(Tris tris) {
        for(Tris x: this.ALL_TRIS) {
            if(x.equals(tris)) {
                return this.TRIS_BONUS.get(x);
            }
        }
        return null;
    }
    
    private void initGame() {
        this.initTris();
        this.initTerritories();
        this.initAdjacencies();
        this.initContinents();
        this.initTerritoryButton();
        for(Territory t: this.TERRITORIES) {
            System.out.println(t.getName() + ": " + t.getNeighboringTerritories());
        }
        for(Tris t: this.TRIS_BONUS.keySet()) {
            System.out.println(t + " " + this.TRIS_BONUS.get(t));
        } 
        for ( Continent c : this.CONTINENTS ){
            System.out.println( c );
        }
        for ( TerritoryButton t : TERRITORY_BUTTONS ){
            System.out.println( t );
        }
    }
    
    private void initTris() {
        try {
            BufferedReader reader = new BufferedReader(new FileReader(Game.TRIS_BONUS_FILE));
            String line = reader.readLine();
            while (line != null) {
                String[] splitted_line = line.split(",");
                List<SymbolCard> symbolCards = new ArrayList<>(3);  
                symbolCards.add(fromStringToSymbolCard(splitted_line[0]));
                symbolCards.add(fromStringToSymbolCard(splitted_line[1]));
                symbolCards.add(fromStringToSymbolCard(splitted_line[2]));
                Tris tris = new Tris(symbolCards.get(0), symbolCards.get(1), symbolCards.get(2));
                this.TRIS_BONUS.put(tris, Integer.valueOf(splitted_line[3]));
                line = reader.readLine();
            }
            this.ALL_TRIS.addAll(this.TRIS_BONUS.keySet());
        } catch (FileNotFoundException ex) {
            System.out.println("File " + Game.TRIS_BONUS_FILE + " not found!");
        } catch (IOException ex) {
            System.out.println("IO error!");
        }
    }
    
    private SymbolCard fromStringToSymbolCard(String string) {
        if (string.equalsIgnoreCase("cannon")) {
            return new SymbolCard(Symbol.CANNON);
        } else if (string.equalsIgnoreCase("bishop")) {
            return new SymbolCard(Symbol.BISHOP);
        } else if (string.equalsIgnoreCase("knight")) {
            return new SymbolCard(Symbol.KNIGHT);
        } else if (string.equalsIgnoreCase("jolly")) {
            return new SymbolCard(Symbol.JOLLY);
        }
        return null;
    }
    
    private void initTerritories (){
        try {
            // apro file lista dei territori
            // per ogni territorio creo un nuovo oggetto
            // aggiungo questo oggetto a this.territories
            File territoriesFile = new File (Game.TERRITORIES_FILE);
            Scanner in = new Scanner ( territoriesFile );
            while ( in.hasNextLine() ){
                    String territoryName = in.nextLine();
                    this.TERRITORIES.add( new Territory ( territoryName ) );
            } 
        } catch( FileNotFoundException ex ){
            System.out.println("File " + Game.TERRITORIES_FILE + " not found!");
        }
    }
    
    private void initAdjacencies() {
        try{
            File neighboringTerritories = new File (Game.ADJACENCIES_FILE);
            Scanner in = new Scanner ( neighboringTerritories );
            while ( in.hasNextLine() ){
                    String line = in.nextLine();
                    String[] splitted_line = line.split(",");
                    String territoryName = splitted_line[0];
                    Territory t = this.fromStringToTerritory( territoryName.trim() );
                    this.initNeighboringTerritories(t, splitted_line );
            }
        } catch( FileNotFoundException ex ) {
            System.out.println("File " + Game.ADJACENCIES_FILE + " not found!");
        }
    }
    
    private void initTerritoryButton (){ //rp
        // apro file lista dei territoryButton
        // leggo una linea e la splitto
        // creo un nuovo territoryButton 
        try {
            Scanner in = new Scanner ( new File( Game.TERRITORIES_BUTTON_FILE ) );
            while ( in.hasNextLine() ){
                String line = in.nextLine();
                String [] splitted_line = line.split(",");
                TerritoryButton t = new TerritoryButton( 
                    this.fromStringToTerritory(splitted_line[0].trim()), 
                    Integer.parseInt(splitted_line[1].trim()), 
                    Integer.parseInt(splitted_line[2].trim()), 
                    Integer.parseInt(splitted_line[3].trim()), 
                    Integer.parseInt(splitted_line[4].trim()) ); // builder
                this.TERRITORY_BUTTONS.add( t );
            }
        }
        catch( FileNotFoundException e ){
            System.out.println("File " + Game.TERRITORIES_BUTTON_FILE + " not found!");
        }
    }
    
    private void initContinents (){ //rp
        // apro file continents.txt
        // leggo una linea
        // creo un continent 
        // aggiungo i territori alla lista territories
        try {
            File continentsFile = new File( Game.CONTINENTS_FILE );
            Scanner in = new Scanner ( continentsFile );
            while ( in.hasNextLine() ){
                String line = in.nextLine();
                String [] splitted_line = line.split(",");
                String continentName = splitted_line[0];
                Continent c = new Continent( continentName );
                this.initTerritoriesOfContinent( c, splitted_line );
                this.CONTINENTS.add( c );
            } 
        }
        catch( FileNotFoundException e ){
            System.out.println("File " + Game.CONTINENTS_FILE + " not found!");
        } 
    }

    private void initTerritoriesOfContinent ( Continent c, String [] territories ){ //rp
        for ( int i = 1; i < territories.length; i++ ){
            Territory t = this.fromStringToTerritory( territories[i].trim() );
            System.out.println(territories[i].trim());
            t.setContinent( c );
            c.getTerritories().add( t );
        }
    }
    
    private Territory fromStringToTerritory ( String territoryName ){
        for (Territory territory: this.TERRITORIES) {
            if (territory.getName().equalsIgnoreCase(territoryName)) {
                return territory;
            }
        }
        return null;
    }
    
    private void initNeighboringTerritories ( Territory t, String[] neighboring ){
        for ( int i = 1; i < neighboring.length; i++ ){
            t.getNeighboringTerritories().add( this.fromStringToTerritory(neighboring[i].trim()) );
        }
    }
    
}

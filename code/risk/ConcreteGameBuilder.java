/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package risk;

import java.util.*;
import java.io.*;

/**
 *
 * @author dario
 */
public class ConcreteGameBuilder implements GameBuilder {
    
    private final List<Tris> ALL_TRIS;
    private final Map<Tris, Integer> TRIS_BONUS;
    private final Map<Continent, Integer> CONTINENTS_BONUS;
    private final List<Territory> TERRITORIES;
    private final List<Continent> CONTINENTS;
    private final List<TerritoryButton> TERRITORY_BUTTONS;
    private final ClassicDice[] ATTACK_DICE;
    private final ClassicDice[] DEFENSE_DICE;
    private final GoalDeck GOAL_DECK;
    private final TerritoryDeck TERRITORY_DECK;
    
    public ConcreteGameBuilder() {
        this.ALL_TRIS = new ArrayList<>();
        this.TRIS_BONUS = new HashMap<>();
        this.CONTINENTS_BONUS = new HashMap<>();
        this.TERRITORIES = new ArrayList<>();
        this.CONTINENTS = new ArrayList<>();
        this.TERRITORY_BUTTONS = new ArrayList<>();
        this.ATTACK_DICE = new ClassicDice[3];
        this.DEFENSE_DICE = new ClassicDice[3];
        this.initGame();
        this.GOAL_DECK = new GoalDeck(this.CONTINENTS, this.TERRITORIES);
        this.TERRITORY_DECK = new TerritoryDeck(this.TERRITORIES);
    }
     
    @Override
    public Game buildGame() {
        return new Game(this.ALL_TRIS, this.TRIS_BONUS, this.CONTINENTS_BONUS,
                        this.TERRITORIES, this.CONTINENTS, this.TERRITORY_BUTTONS,
                        this.ATTACK_DICE, this.DEFENSE_DICE, this.GOAL_DECK,
                        this.TERRITORY_DECK);
    }
    
    private void initGame() {
        this.addTrisAndBonus();
        this.addContinentsAndTerritories();
        this.addAdjacencies();
        this.addTerritoryButton();
        this.addContinentsBonus();
    }
    
    private void addTrisAndBonus() {
        try {
            BufferedReader reader = new BufferedReader(new FileReader(GameBuilder.TRIS_BONUS_FILE));
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
            System.out.println("File " + GameBuilder.TRIS_BONUS_FILE + " not found!");
        } catch (IOException ex) {
            System.out.println("IO error!");
        }
    }
    
    private void addAdjacencies() {
        try{
            File neighboringTerritories = new File (GameBuilder.ADJACENCIES_FILE);
            Scanner in = new Scanner ( neighboringTerritories );
            while ( in.hasNextLine() ){
                    String line = in.nextLine();
                    String[] splitted_line = line.split(",");
                    String territoryName = splitted_line[0];
                    Territory t = this.fromStringToTerritory( territoryName.trim() );
                    this.addNeighboringTerritories(t, splitted_line );
            }
        } catch( FileNotFoundException ex ) {
            System.out.println("File " + GameBuilder.ADJACENCIES_FILE + " not found!");
        }
    }
    
    private void addNeighboringTerritories ( Territory t, String[] neighboring ){
        for ( int i = 1; i < neighboring.length; i++ ){
            t.getNeighboringTerritories().add( this.fromStringToTerritory(neighboring[i].trim()) );
        }
    }
    
    private void addTerritoryButton (){
        try {
            Scanner in = new Scanner ( new File( GameBuilder.TERRITORIES_BUTTON_FILE ) );
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
            System.out.println("File " + GameBuilder.TERRITORIES_BUTTON_FILE + " not found!");
        }
    }
    
    private void addContinentsAndTerritories () {
        try {
            File continentsFile = new File( GameBuilder.CONTINENTS_FILE );
            Scanner in = new Scanner ( continentsFile );
            while ( in.hasNextLine() ){
                String line = in.nextLine();
                String [] splitted_line = line.split(",");
                String continentName = splitted_line[0];
                Continent continent = new Continent(continentName);
                this.CONTINENTS.add(continent);
                for(int i = 1; i < splitted_line.length; i++) {
                    Territory territory = new Territory(splitted_line[i].trim());
                    territory.setContinent(continent);
                    this.TERRITORIES.add(territory);
                    continent.getTerritories().add(territory);
                }
            } 
        }
        catch( FileNotFoundException e ){
            System.out.println("File " + GameBuilder.CONTINENTS_FILE + " not found!");
        } 
    }
    
    private void addContinentsBonus() {
        try {
            BufferedReader reader = new BufferedReader(new FileReader(GameBuilder.CONTINENTS_BONUS_FILE));
            String line = reader.readLine();
            while (line != null) {
                System.out.println(line);
                String[] splitted_line = line.split(","); 
                this.CONTINENTS_BONUS.put(this.fromStringToContinent(splitted_line[0]), Integer.valueOf(splitted_line[1]));
                line = reader.readLine();
            }
        } catch (FileNotFoundException ex) {
            System.out.println("File " + GameBuilder.CONTINENTS_BONUS_FILE + " not found!");
        } catch (IOException ex) {
            System.out.println("IO error!");
        }
    }
    
    private Territory fromStringToTerritory ( String territoryName) {
        for (Territory territory: this.TERRITORIES) {
            if (territory.getName().equalsIgnoreCase(territoryName.trim())) {
                return territory;
            }
        }
        return null;
    }
    
    private SymbolCard fromStringToSymbolCard(String symbol) {
        if (symbol.equalsIgnoreCase("cannon")) {
            return new SymbolCard(Symbol.CANNON);
        } else if (symbol.equalsIgnoreCase("bishop")) {
            return new SymbolCard(Symbol.BISHOP);
        } else if (symbol.equalsIgnoreCase("knight")) {
            return new SymbolCard(Symbol.KNIGHT);
        } else if (symbol.equalsIgnoreCase("jolly")) {
            return new SymbolCard(Symbol.JOKER);
        }
        return null;
    }
    
    private Continent fromStringToContinent(String continentName) {
        for (Continent continent: this.CONTINENTS) {
            if (continent.getName().equalsIgnoreCase(continentName.trim())) {
                return continent;
            }
        }
        return null;
    }
    
}

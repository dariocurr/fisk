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
    
    public final List<Tris> ALL_TRIS = new ArrayList<>();
    public final Map<Tris, Integer> TRIS_BONUS = new HashMap<>();
    public final List<Territory> territories = new ArrayList<>();
    
    public void initGame() {
        this.initTris();
        this.initTerritories();
        this.initAdjacencies();
        for(Territory t: this.territories) {
            System.out.println(t.getName() + ": " + t.getNeighboringTerritories());
        }
        for(Tris t: this.TRIS_BONUS.keySet()) {
            System.out.println(t + " " + this.TRIS_BONUS.get(t));
        }
        Tris tris = new Tris(new SymbolCard(Symbol.BISHOP), new SymbolCard(Symbol.BISHOP), new SymbolCard(Symbol.BISHOP));
        System.out.println(this.getTrisBonus(tris));
    }
    
    public Integer getTrisBonus(Tris tris) {
        for(Tris x: this.ALL_TRIS) {
            if(x.equals(tris)) {
                return this.TRIS_BONUS.get(x);
            }
        }
        return null;
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
                    this.territories.add( new Territory ( territoryName ) );
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
    
    private Territory fromStringToTerritory ( String territoryName ){
        for (Territory territory: this.territories) {
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

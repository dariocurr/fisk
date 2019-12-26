package risk;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ConcreteGameBuilder implements GameBuilder {

    protected final List<Tris> ALL_TRIS;
    protected final Map<Tris, Integer> TRIS_BONUS;
    protected final Map<Continent, Integer> CONTINENTS_BONUS;
    protected final List<Territory> TERRITORIES;
    protected final List<Continent> CONTINENTS;
    protected final ClassicDice[] ATTACK_DICE;
    protected final ClassicDice[] DEFENSE_DICE;
    protected final GoalDeck GOAL_DECK;
    protected final TerritoryDeck TERRITORY_DECK;
    protected final Map<RiskColor, TankPool> TANK_POOLS;

    public ConcreteGameBuilder() {
        this.ALL_TRIS = new ArrayList<>();
        this.TRIS_BONUS = new HashMap<>();
        this.CONTINENTS_BONUS = new HashMap<>();
        this.TERRITORIES = new ArrayList<>();
        this.CONTINENTS = new ArrayList<>();
        this.ATTACK_DICE = new ClassicDice[3];
        this.DEFENSE_DICE = new ClassicDice[3];
        for (int i = 0; i < 3; i++) {
            this.ATTACK_DICE[i] = new ClassicDice();
            this.DEFENSE_DICE[i] = new ClassicDice();
        }
        this.initGame();
        this.GOAL_DECK = new GoalDeck(this.CONTINENTS, this.TERRITORIES);
        this.TERRITORY_DECK = new TerritoryDeck(this.TERRITORIES);
        this.TANK_POOLS = new HashMap<>();
        this.initTankPools();
    }

    @Override
    public Game buildGame() {
        return new Game(this.ALL_TRIS, this.TRIS_BONUS, this.CONTINENTS_BONUS,
                this.TERRITORIES, this.CONTINENTS, this.ATTACK_DICE,
                this.DEFENSE_DICE, this.GOAL_DECK, this.TERRITORY_DECK,
                this.TANK_POOLS);
    }

    protected void initGame() {
        this.addTrisAndBonus();
        this.addContinentsAndTerritories();
        this.addAdjacencies();
        this.addContinentsBonus();
    }

    protected void addTrisAndBonus() {
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

    protected void addAdjacencies() {
        try {
            BufferedReader reader = new BufferedReader(new FileReader(GameBuilder.ADJACENCIES_FILE));
            String line = reader.readLine();
            while (line != null) {
                String[] splitted_line = line.split(",");
                String territoryName = splitted_line[0];
                Territory t = this.fromStringToTerritory(territoryName.trim());
                this.addNeighboringTerritories(t, splitted_line);
                line = reader.readLine();
            }
        } catch (FileNotFoundException ex) {
            System.out.println("File " + GameBuilder.ADJACENCIES_FILE + " not found!");
        } catch (IOException ex) {
            System.out.println("IO error!");
        }
    }

    protected void addNeighboringTerritories(Territory t, String[] neighboring) {
        for (int i = 1; i < neighboring.length; i++) {
            t.getNeighboringTerritories().add(this.fromStringToTerritory(neighboring[i].trim()));
        }
    }

    protected void addContinentsAndTerritories() {
        try {
            BufferedReader reader = new BufferedReader(new FileReader(GameBuilder.CONTINENTS_FILE));
            String line = reader.readLine();
            while (line != null) {
                String[] splitted_line = line.split(",");
                String continentName = splitted_line[0];
                List<Territory> continentTerritories = new ArrayList<>();
                for (int i = 1; i < splitted_line.length; i++) {
                    Territory territory = new Territory(splitted_line[i].trim());
                    continentTerritories.add(territory);
                    this.TERRITORIES.add(territory);
                }
                Continent continent = new Continent(continentName, continentTerritories);
                for (Territory territory: continentTerritories) {
                    territory.setContinent(continent);
                }
                this.CONTINENTS.add(continent);
                line = reader.readLine();
            }
        } catch (FileNotFoundException e) {
            System.out.println("File " + GameBuilder.CONTINENTS_FILE + " not found!");
        } catch (IOException ex) {
            System.out.println("IO error!");
        }
    }

    protected void addContinentsBonus() {
        try {
            BufferedReader reader = new BufferedReader(new FileReader(GameBuilder.CONTINENTS_BONUS_FILE));
            String line = reader.readLine();
            while (line != null) {
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

    protected Territory fromStringToTerritory(String territoryName) {
        for (Territory territory : this.TERRITORIES) {
            if (territory.getName().equalsIgnoreCase(territoryName.trim())) {
                return territory;
            }
        }
        return null;
    }

    protected SymbolCard fromStringToSymbolCard(String symbol) {
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

    protected Continent fromStringToContinent(String continentName) {
        for (Continent continent : this.CONTINENTS) {
            if (continent.getName().equalsIgnoreCase(continentName.trim())) {
                return continent;
            }
        }
        return null;
    }

    protected void initTankPools() {
        for (RiskColor riskColor : RiskColor.values()) {
            this.TANK_POOLS.put(riskColor, new TankPool(140, riskColor));
        }
    }

}

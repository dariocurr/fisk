package fisk.game;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import fisk.Resource;
import fisk.card.SymbolCard.ConcreteSymbolCard;
import fisk.card.SymbolCard.ConcreteTris;
import fisk.card.SymbolCard.Symbol;
import fisk.card.SymbolCard.SymbolCard;
import fisk.card.SymbolCard.Tris;
import fisk.deck.ConcreteGoalsDeck;
import fisk.deck.ConcreteSymbolsDeck;
import fisk.deck.ConcreteTerritoriesDeck;
import fisk.deck.GoalsDeck;
import fisk.deck.SymbolsDeck;
import fisk.deck.TerritoriesDeck;
import fisk.dice.ConcreteClassicDice;
import fisk.dice.Dice;
import fisk.player.RiskColor;
import fisk.tank.TankPool;
import fisk.territory.ConcreteContinent;
import fisk.territory.ConcreteTerritory;
import fisk.territory.Continent;
import fisk.territory.Territory;

/**
 * Classe che implementa l'interfaccia GameBuilder, quindi capace di creare
 * un'istanza della classe game.
 */
public class ConcreteGameBuilder implements GameBuilder {

    protected final List<Tris> allTris;
    protected final Map<Tris, Integer> trisBonus;
    protected final Map<Continent, Integer> continentsBonus;
    protected final List<Territory> territories;
    protected final List<Continent> continents;
    protected final Dice[] attackDice;
    protected final Dice[] defenseDice;
    protected final GoalsDeck goalsDeck;
    protected final TerritoriesDeck territoriesDeck;
    protected final SymbolsDeck symbolDeck;
    protected final Map<RiskColor, TankPool> tanksPolls;

    public ConcreteGameBuilder() {
        this.allTris = new ArrayList<>();
        this.trisBonus = new HashMap<>();
        this.continentsBonus = new HashMap<>();
        this.territories = new ArrayList<>();
        this.continents = new ArrayList<>();
        this.attackDice = new ConcreteClassicDice[3];
        this.defenseDice = new ConcreteClassicDice[3];
        for (int i = 0; i < 3; i++) {
            this.attackDice[i] = new ConcreteClassicDice();
            this.defenseDice[i] = new ConcreteClassicDice();
        }
        this.initGame();
        this.goalsDeck = new ConcreteGoalsDeck(this.continents, this.territories.size());
        this.territoriesDeck = new ConcreteTerritoriesDeck(this.territories);
        this.symbolDeck = new ConcreteSymbolsDeck(this.territoriesDeck);
        this.tanksPolls = new HashMap<>();
        this.initTankPools();
    }

    @Override
    public Game buildGame() {
        return new ConcreteGame(this.trisBonus, this.continentsBonus, this.territories, this.continents,
                this.attackDice, this.defenseDice, this.goalsDeck, this.territoriesDeck, this.symbolDeck,
                this.tanksPolls);
    }

    protected void initGame() {
        this.addTrisAndBonus();
        this.addContinentsAndTerritories();
        this.addAdjacencies();
        this.addContinentsBonus();
    }

    protected void addTrisAndBonus() {
        try {
            BufferedReader reader = new BufferedReader(new FileReader(Resource.TRIS_BONUS_FILE));
            String line = reader.readLine();
            while (line != null) {
                String[] splitted_line = line.split(",");
                List<SymbolCard> symbolCards = new ArrayList<>(3);
                symbolCards.add(fromStringToSymbolCard(splitted_line[0]));
                symbolCards.add(fromStringToSymbolCard(splitted_line[1]));
                symbolCards.add(fromStringToSymbolCard(splitted_line[2]));
                Tris tris = new ConcreteTris(symbolCards.get(0), symbolCards.get(1), symbolCards.get(2));
                this.trisBonus.put(tris, Integer.valueOf(splitted_line[3]));
                line = reader.readLine();
            }
            this.allTris.addAll(this.trisBonus.keySet());
            reader.close();
        } catch (FileNotFoundException ex) {
            System.out.println("File " + Resource.TRIS_BONUS_FILE + " not found!");
        } catch (IOException ex) {
            System.out.println("IO error!");
        }
    }

    protected void addAdjacencies() {
        try {
            BufferedReader reader = new BufferedReader(new FileReader(Resource.ADJACENCIES_FILE));
            String line = reader.readLine();
            while (line != null) {
                String[] splitted_line = line.split(",");
                String territoryName = splitted_line[0];
                Territory territory = this.fromStringToTerritory(territoryName.trim());
                this.addNeighboringTerritories(territory, splitted_line);
                line = reader.readLine();
            }
            reader.close();
        } catch (FileNotFoundException ex) {
            System.out.println("File " + Resource.ADJACENCIES_FILE + " not found!");
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
            BufferedReader reader = new BufferedReader(new FileReader(Resource.CONTINENTS_FILE));
            String line = reader.readLine();
            while (line != null) {
                String[] splitted_line = line.split(",");
                String continentName = splitted_line[0];
                List<Territory> continentTerritories = new ArrayList<>();
                for (int i = 1; i < splitted_line.length; i++) {
                    Territory territory = new ConcreteTerritory(splitted_line[i].trim());
                    continentTerritories.add(territory);
                    this.territories.add(territory);
                }
                Continent continent = new ConcreteContinent(continentName, continentTerritories);
                continentTerritories.forEach((territory) -> territory.setContinent(continent));
                this.continents.add(continent);
                line = reader.readLine();
            }
            reader.close();
        } catch (FileNotFoundException e) {
            System.out.println("File " + Resource.CONTINENTS_FILE + " not found!");
        } catch (IOException ex) {
            System.out.println("IO error!");
        }
    }

    protected void addContinentsBonus() {
        try {
            BufferedReader reader = new BufferedReader(new FileReader(Resource.CONTINENTS_BONUS_FILE));
            String line = reader.readLine();
            while (line != null) {
                String[] splitted_line = line.split(",");
                this.continentsBonus.put(this.fromStringToContinent(splitted_line[0]),
                        Integer.valueOf(splitted_line[1]));
                line = reader.readLine();
            }
            reader.close();
        } catch (FileNotFoundException ex) {
            System.out.println("File " + Resource.CONTINENTS_BONUS_FILE + " not found!");
        } catch (IOException ex) {
            System.out.println("IO error!");
        }
    }

    protected Territory fromStringToTerritory(String territoryName) {
        for (Territory territory : this.territories) {
            if (territory.getName().equalsIgnoreCase(territoryName.trim())) {
                return territory;
            }
        }
        return null;
    }

    protected SymbolCard fromStringToSymbolCard(String symbol) {
        if (symbol.equalsIgnoreCase("cannon")) {
            return new ConcreteSymbolCard(Symbol.CANNON);
        } else if (symbol.equalsIgnoreCase("bishop")) {
            return new ConcreteSymbolCard(Symbol.BISHOP);
        } else if (symbol.equalsIgnoreCase("knight")) {
            return new ConcreteSymbolCard(Symbol.KNIGHT);
        } else if (symbol.equalsIgnoreCase("jolly")) {
            return new ConcreteSymbolCard(Symbol.JOKER);
        }
        return null;
    }

    protected Continent fromStringToContinent(String continentName) {
        for (Continent continent : this.continents) {
            if (continent.getName().equalsIgnoreCase(continentName.trim())) {
                return continent;
            }
        }
        return null;
    }

    protected void initTankPools() {
        for (RiskColor riskColor : RiskColor.values()) {
            this.tanksPolls.put(riskColor, new TankPool(140, riskColor));
        }
    }

}

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
public class Tris {
    
    private static final Map<String, Tris> TRIS = new TreeMap<>();
    private final List<SymbolCard> cards;

    public Tris(SymbolCard firstCard, SymbolCard secondCard, SymbolCard thirdCard) {
        this.cards = new ArrayList<>(3);
        this.cards.add(firstCard);
        this.cards.add(secondCard);
        this.cards.add(thirdCard);
        Collections.sort(this.cards);
    }
    
    public static Tris getTris(String id) {
        return TRIS.get(id);
    }
    
    public static void initTris() {
        try {
            BufferedReader reader = new BufferedReader(new FileReader("res/tris.txt"));
            String line = reader.readLine();
            while (line != null) {
                List<SymbolCard> symbolCards = new ArrayList<>(3);
                String id = "";
                String[] splitted_line = line.split(",");
                for (String symbol: splitted_line) {
                    if (symbol.equalsIgnoreCase("cannon")) {
                        symbolCards.add(new SymbolCard(Symbol.CANNON));
                        id += "c";
                    } else if (symbol.equalsIgnoreCase("bishop")) {
                        symbolCards.add(new SymbolCard(Symbol.BISHOP));
                        id += "b";
                    } else if (symbol.equalsIgnoreCase("knight")) {
                        symbolCards.add(new SymbolCard(Symbol.KNIGHT));
                        id += "k";
                    } else if (symbol.equalsIgnoreCase("jolly")) {
                        symbolCards.add(new SymbolCard(Symbol.JOLLY));
                        id += "j";
                    }
                }
                Tris tris = new Tris(symbolCards.get(0), symbolCards.get(1), symbolCards.get(2));
                TRIS.put(id, tris);
                line = reader.readLine();
            }
        } catch (FileNotFoundException ex) {
            System.out.println("File tris.txt not found!");
        } catch (IOException ex) {
            System.out.println("IO error!");
        }
    }

    @Override
    public String toString() {
        return "Tris: " + this.cards;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj != null) {
            if (obj instanceof Tris) {
                Tris otherTris = (Tris) obj;
                return this.cards.equals(otherTris.cards);
            }
        }
        return false;
    }
    
    
    
}

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
public abstract class Deck {
    
    protected List<Card> deck;

    public Deck() {
        this.deck = new ArrayList<>();
    }

    @Override
    public String toString() {
        String tmp = "Deck:\n";
        for(Card x: this.deck) {
            tmp += x + "\n";
        }
        return tmp;
    }
    
    
 
}
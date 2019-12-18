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
public abstract class Deck<T> {
    
    protected List<T> deck;

    public Deck() {
        this.deck = new ArrayList<>();
    }

    @Override
    public String toString() {
        return this.deck.toString();
    }
    
    public T removeCard() {
        return this.deck.remove(0);
    }
    
    public void addCard(T t) {
        this.deck.add(t);
    }
    
    public boolean isEmpty() {
        return this.deck.isEmpty();
    }
    
}

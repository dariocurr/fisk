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
    
    protected List<? super T> deck;

    public Deck() {
        this.deck = new ArrayList<>();
    } 
 
}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package risk;

/**
 *
 * @author dario
 */
public abstract class Card<T> {
    
    protected T card;

    public Card(T card) {
        this.card = card;
    }
    
    public T getCard() {
        return this.card;
    }
    
    @Override
    public String toString() {
        return this.card.toString();
    }
    
}

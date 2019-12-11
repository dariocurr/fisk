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
public class SymbolCard extends Card implements Comparable<SymbolCard>{
    
    private Symbol symbol;

    public SymbolCard(Symbol symbol) {
        super();
        this.symbol = symbol;
    }

    @Override
    public String toString() {
        return this.symbol.toString();
    }

    @Override
    public boolean equals(Object obj) {
        if (obj != null) {
            if (obj instanceof SymbolCard) {
                SymbolCard otherSymbolCard = (SymbolCard) obj;
                return this.symbol.equals(otherSymbolCard.symbol);
            }
        }
        return false;
    }

    @Override
    public int compareTo(SymbolCard otherSymbolCard) {
        return this.symbol.compareTo(otherSymbolCard.symbol);
    } 
    
}

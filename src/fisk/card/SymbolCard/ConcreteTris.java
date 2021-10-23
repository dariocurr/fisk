package fisk.card.SymbolCard;

public class ConcreteTris extends Tris {

    public ConcreteTris(SymbolCard firstCard, SymbolCard secondCard, SymbolCard thirdCard) {
        super(firstCard, secondCard, thirdCard);
    }

    @Override
    public boolean equals(Object obj) {
        if (obj != null) {
            if (obj instanceof ConcreteTris) {
                ConcreteTris otherTris = (ConcreteTris) obj;
                return this.cards.equals(otherTris.cards);
            }
        }
        return false;
    }

}

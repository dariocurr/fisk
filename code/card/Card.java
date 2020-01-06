package risk;

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

package risk;

import java.util.ArrayList;
import java.util.List;

/**
 * Classe astratta che definisce i metodi che deve implementare un'entità
 * osservabile
 */
public abstract class Observable {

    protected List<Observer> observers;

    /**
     * Istanzia l'entità osservabile.
     */
    public Observable() {
        this.observers = new ArrayList<>();
    }

    /**
     * Permette l'aggiunzione di un osservatore.
     *
     * @param observer osservatore da aggiungere
     */
    public void addObserver(Observer observer) {
        this.observers.add(observer);
    }

    /**
     * Permette la rimozione di un osservatore.
     *
     * @param observer osservatore da rimuovere
     */
    public void removeObserver(Observer observer) {
        this.observers.remove(observer);
    }

    /**
     * Notifica gli osservatori dell'aggiornamento avvenuto.
     *
     * @param update aggiornamento avvenuto.
     */
    public void notifyObservers(Object update) {
        this.observers.forEach((observer) -> observer.update(update));
    }

}

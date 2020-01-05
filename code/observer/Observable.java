package risk;

import java.util.ArrayList;
import java.util.List;

public abstract class Observable {

    protected List<Observer> observers;

    public Observable() {
        this.observers = new ArrayList<>();
    }

    public void addObserver(Observer observer) {
        this.observers.add(observer);
    }

    public void removeObserver(Observer observer) {
        this.observers.remove(observer);
    }

    public void notifyObservers(Object update) {
        this.observers.forEach((observer) -> observer.update(update));
    }

}

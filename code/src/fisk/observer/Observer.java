package fisk.observer;

/**
 * Interfaccia funzionale che definisce i metodi di un oseervatore
 */
@FunctionalInterface
public interface Observer {

    /**
     * Permette all'osservatore di agire di conseguenza ad un cambiamento di stato
     *
     * @param update aggiornamento avvenuto
     */
    public void update(Object update);

}

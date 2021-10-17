package fisk.game;

/**
 * Interfaccia funzionale del design pattern Builder, per la classe Game.
 */
@FunctionalInterface
public interface GameBuilder {

    /**
     * Costruisce un oggetto di tipo Game e lo restituisce.
     *
     * @return un istanza della classe Game
     */
    public Game buildGame();

}

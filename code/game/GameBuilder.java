package risk;

public interface GameBuilder {

    public static final String TRIS_BONUS_FILE = "res/tris.txt";
    public static final String CONTINENTS_BONUS_FILE = "res/continents_bonus.txt";
    public static final String ADJACENCIES_FILE = "res/adjacencies.txt";
    public static final String CONTINENTS_FILE = "res/continents.txt";

    public Game buildGame();

}

package fisk.card.GoalCard;

/**
 * Classe che modellizza il concetto di Factory per creare una carta obiettivo.
 */
@FunctionalInterface
public interface GoalCardFactory {

    /**
     * Permette la creazione di una carta obiettiva.
     *
     * @param goal obiettivo
     * @return carta corrispondente all'obiettivo da raggiungere
     */
    public GoalCard<?> createGoal(Object goal);

}

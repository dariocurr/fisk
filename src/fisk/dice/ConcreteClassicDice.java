package fisk.dice;

import java.util.Random;

/**
 * Concretizzazione della classe Dice. Essendo un dado classico a sei facce,
 * verrà generato un intero tra uno e sei.
 *
 * @see Dice
 */
public class ConcreteClassicDice extends Dice {

    /**
     * Istanzia un dado classico a 6 facce
     */
    public ConcreteClassicDice() {
        super();
    }

    /**
     * Lancia in dado, generando randomicamente il valore estratto.
     *
     * @return intero generato casualmente
     */
    @Override
    public Integer roll() {
        Random random = new Random();
        this.value = random.nextInt(6);
        return ++this.value;
    }

}

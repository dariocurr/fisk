package risk;

import java.util.Random;

/**
	Concretizzazione della classe Dice.
	Essendo un dado classico a sei faccie, verrà generato un intero tra uno e sei.
	@see Dice
*/

public class ConcreteClassicDice extends Dice {

    public ConcreteClassicDice() {
        super();
    }

    /**
		Lancia un dado, generando randomicamente il valore estratto.
		@return intero generato casualmente
    */
    @Override
    public Integer roll() {
        Random random = new Random();
        this.value = random.nextInt(6);
        return ++this.value;
    }

}

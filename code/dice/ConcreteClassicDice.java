package risk;

import java.util.Random;

public class ConcreteClassicDice extends Dice {

    public ConcreteClassicDice() {
        super();
    }
    
    
    public Integer roll() {
        Random random = new Random();
        this.value = random.nextInt(6);
        return ++this.value;
    }
    
}

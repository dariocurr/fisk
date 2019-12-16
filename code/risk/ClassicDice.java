/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package risk;

import java.util.*;

/**
 *
 * @author dario
 */
public class ClassicDice implements Comparable<ClassicDice> {
    
    private final static TreeSet<Integer> POSSIBLE_VALUES = initPossibleValues();
    
    private Integer value;

    public ClassicDice() {
        this.value = null;
    }

    public Integer getValue() {
        return value;
    }

    public void setValue(Integer value) {
        if(ClassicDice.POSSIBLE_VALUES.contains(value)) {
            this.value = value;
        }
    }
    
    private static TreeSet<Integer> initPossibleValues() {
        TreeSet set = new TreeSet();
        set.add(1);
        set.add(2);
        set.add(3);
        set.add(4);
        set.add(5);
        set.add(6);
        return set;
    }

    @Override
    public int compareTo(ClassicDice otherClassicDice) {
        return this.value.compareTo(otherClassicDice.value);
    }
    
}

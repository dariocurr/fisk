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
public class MediatorGUI {

    private final List<Territory> clicked;

    public MediatorGUI() {
        this.clicked = new ArrayList<>();
    }

    public void addClickedTerritory(Territory territory) {
        clicked.add(territory);
    }

    public void update() {
        System.out.println(this.clicked);
    }

}

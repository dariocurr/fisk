/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package risk;

import java.awt.Color;

/**
 *
 * @author dario
 */
public enum RiskColor {

    RED, GREEN, BLUE, YELLOW, PURPLE, BLACK;
    
    public Color getColor() {
        if (this == null) {
            return null;
        } else {
            switch (this) {
                case RED:
                    return Color.RED;
                case GREEN:
                    return Color.GREEN;
                case BLUE:
                    return Color.GREEN;
                case YELLOW:
                    return Color.GREEN;
                case PURPLE:
                    return Color.GREEN;
                case BLACK:
                    return Color.GREEN;
                default:
                    return null;
            }
        }
    }
    
}

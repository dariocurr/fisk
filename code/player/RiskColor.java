package risk;

import java.awt.Color;

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
                    return Color.BLUE;
                case YELLOW:
                    return Color.YELLOW;
                case PURPLE:
                    return Color.MAGENTA;
                case BLACK:
                    return Color.BLACK;
                default:
                    return null;
            }
        }
    }

}

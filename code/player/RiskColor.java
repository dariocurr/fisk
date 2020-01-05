package risk;

import java.awt.Color;

public enum RiskColor {

    RED, GREEN, BLUE, PURPLE, BLACK, YELLOW;

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
                case PURPLE:
                    return Color.MAGENTA;
                case BLACK:
                    return Color.BLACK;
		case YELLOW:
                    return Color.YELLOW;
                default:
                    return null;
            }
        }
    }

}

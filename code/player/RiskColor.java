package risk;

import java.awt.Color;

/**
 * Enumerazione contenente tutti i possibili colori dei giocatori.
 */
public enum RiskColor {

    RED, GREEN, BLUE, PURPLE, BLACK, YELLOW;

    /**
     * Restituisce il colore corrispondente.
     *
     * @return il colore corrispondente
     */
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

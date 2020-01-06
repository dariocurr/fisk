package risk;

import java.awt.*;
import javax.swing.*;

/**
    Classe che realizza un bottone per ogni territorio della board di gioco.
*/

public class TerritoryButton extends JButton {

    protected static final String TANK_IMAGE_FILE = "res/tank.png";
    protected final static Image TANK_IMAGE = ImageLoader.loadImage(TANK_IMAGE_FILE);
    protected final int positionX;
    protected final int positionY;
    protected final int width;
    protected final int height;
    protected final Territory territory;
    protected final JLabel numberTanks;

    public TerritoryButton(Territory territory, int x, int y, int width, int height) {
        super();
        double resize = 1.35;
        this.territory = territory;
        this.positionX = (int) (x * resize);
        this.positionY = (int) (y * resize);
        this.width = (int) (width * resize);
        this.height = (int) (height * resize);
        this.setPreferredSize(new Dimension(this.width, this.height));
        this.setForeground(Color.WHITE);
        Image scaledImage = TerritoryButton.TANK_IMAGE.getScaledInstance(15, 15, 0);
        this.setLayout(new BorderLayout());
        this.numberTanks = new JLabel(new ImageIcon(scaledImage));
        this.numberTanks.setText(" " + this.territory.getTanks().size());
        this.numberTanks.setForeground(Color.WHITE);
        this.numberTanks.setFont(new Font("Dialog", Font.BOLD, 12));
        this.setToolTipText(this.territory.getName());
        this.add(this.numberTanks, BorderLayout.CENTER);
    }

    public int getPositionX() {
        return positionX;
    }

    public int getPositionY() {
        return positionY;
    }

    @Override
    public int getWidth() {
        return width;
    }

    @Override
    public int getHeight() {
        return height;
    }

    /**
        Restituisce il territorio associato a questo bottone.
    */
    public Territory getTerritory() {
        return this.territory;
    }

    @Override
    public String toString() {
        return this.territory.getName() + " " + this.positionX + " " + this.positionY;
    }

    /**
        Aggiorna il numero di armate poste in questo territorio.
    */
    public void updateNumberTanksLabel() {
        this.numberTanks.setText(" " + this.territory.getTanks().size());
    }

    /**
        Aggiorna il colore di questo territorio.
    */
    public void updateColor() {
        this.setBackground(this.territory.getOwnerPlayer().getColor().getColor());
    }

}

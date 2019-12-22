package risk;

import java.awt.*;
import javax.swing.*;

public class TerritoryButton extends JButton {

    private static final String TANK_IMAGE_FILE = "res/tank.png";
    private final static Image TANK_IMAGE = ImageLoader.loadImage(TANK_IMAGE_FILE);
    private final int positionX;
    private final int positionY;
    private final int width;
    private final int height;
    private final Territory territory;
    private final JLabel numberTanks;
    
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
        this.numberTanks.setForeground(Color.WHITE);
        this.numberTanks.setFont(new Font("Dialog", Font.BOLD, 12));
        this.setToolTipText(this.territory.getName());
        this.add(this.numberTanks, BorderLayout.CENTER);
        this.updateNumberTanksLabel();
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

    public Territory getTerritory() {
        return this.territory;
    }

    @Override
    public String toString() {
        return this.territory.getName() + " " + this.positionX + " " + this.positionY;
    }
    
    public void updateNumberTanksLabel() {
        this.numberTanks.setText(" " + this.territory.getTanks().size());
    }

}

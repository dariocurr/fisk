package risk;

import java.awt.*;
import javax.swing.*;

public class TerritoryButton extends JButton {

    private static final String TANK_IMAGE_FILE = "res/tank.png";
    private final static Image TANK_IMAGE = ImageLoader.loadImage(TANK_IMAGE_FILE);
    private int positionX;
    private int positionY;
    private int width;
    private int height;
    private Territory territory;

    public TerritoryButton ( Territory territory, int x, int y, int width, int height ){
        super();
        double resize = 1.35;
        this.territory = territory;
        this.positionX = (int)(x * resize);
        this.positionY = (int)(y * resize);
        this.width = (int)(width * resize);
        this.height = (int)(height * resize);
        this.setPreferredSize(new Dimension(this.width, this.height));
        Font defaultFont = new Font("Calibri", Font.BOLD, 10);
        this.setFont(defaultFont);
        this.setForeground(Color.WHITE);
        Image scaledImage = TerritoryButton.TANK_IMAGE.getScaledInstance(15, 15, 0);
        this.setLayout(new FlowLayout(FlowLayout.CENTER));
        JLabel tank = new JLabel(new ImageIcon(scaledImage));
        JLabel numberTanks = new JLabel(" " + this.territory.getTanks().size());
        numberTanks.setForeground(Color.WHITE);
        numberTanks.setFont(defaultFont);
        this.setToolTipText(this.territory.getName());
        this.add(tank);
        this.add(numberTanks);
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
    
    public Territory getTerritory (){
            return this.territory;
    }

    @Override
    public String toString (){
            return this.territory.getName() + " " + this.positionX + " " + this.positionY;
    }

}

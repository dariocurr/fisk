package risk;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Image;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;

/**
 * Classe che realizza un bottone per ogni territorio della board di gioco.
 */
public class TerritoryButton extends JButton {

    protected static final Image TANK_IMAGE = ImageLoader.loadImage(Resource.TANK_IMAGE_FILE);
    protected final Integer positionX;
    protected final Integer positionY;
    protected final Integer width;
    protected final Integer height;
    protected final Territory territory;
    protected final JLabel numberTanks;

    public TerritoryButton(Territory territory, Integer x, Integer y, Integer width, Integer height) {
        super();
        double resize = 1.35;
        this.territory = territory;
        this.positionX = (int) (x * resize);
        this.positionY = (int) (y * resize);
        this.width = (int) (width * resize);
        this.height = (int) (height * resize);
        this.setPreferredSize(new Dimension(this.width, this.height));
        this.setForeground(Color.WHITE);
        Image scaledImage = this.TANK_IMAGE.getScaledInstance(15, 15, 0);
        this.setLayout(new BorderLayout());
        this.numberTanks = new JLabel(new ImageIcon(scaledImage));
        this.numberTanks.setText(" " + this.territory.getTanks().size());
        this.numberTanks.setForeground(Color.WHITE);
        this.numberTanks.setFont(new Font("Dialog", Font.BOLD, 12));
        this.setToolTipText(this.territory.getName());
        this.add(this.numberTanks, BorderLayout.CENTER);
    }

    public Integer getPositionX() {
        return this.positionX;
    }

    public Integer getPositionY() {
        return this.positionY;
    }

    @Override
    public int getWidth() {
        return this.width;
    }

    @Override
    public int getHeight() {
        return this.height;
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

    public void updateColor() {
        this.setBackground(this.territory.getOwnerPlayer().getColor().getColor());
    }

}

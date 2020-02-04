package risk;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class BoardPanel extends JPanel {

    protected static final Image BACKGROUND_IMAGE = ImageLoader.loadImage(Resource.BACKGROUND_IMAGE_FILE);
    protected final Image scaledImage;
    protected final List<TerritoryButton> territoriesButtons;
    protected final Integer width;
    protected final Integer height;
    protected final List<Territory> territories;
    protected final Map<Continent, Integer> continentsBonus;
    protected final RiskFacade facade;

    public BoardPanel(RiskFacade facade, int width, int height, List<Territory> territories, Map<Continent, Integer> continentsBonus) {
        super();
        this.width = width;
        this.height = height;
        this.continentsBonus = continentsBonus;
        this.facade = facade;
        this.setPreferredSize(new Dimension(this.width, this.height));
        this.scaledImage = BoardPanel.BACKGROUND_IMAGE.getScaledInstance(this.width, this.height, 0);
        this.territories = territories;
        this.territoriesButtons = new ArrayList<>();
        this.initTerritoryButtons();
        this.setLayout(null);
        for (TerritoryButton territoryButton : this.territoriesButtons) {
            territoryButton.setBounds(territoryButton.getPositionX(),
                    territoryButton.getPositionY(),
                    territoryButton.getWidth(),
                    territoryButton.getHeight());
            this.add(territoryButton);
        }
        this.addContinentsBonusLabels();
        this.addListeners();
    }

    protected void initTerritoryButtons() {
        try {
            Scanner in = new Scanner(new File(Resource.TERRITORIES_BUTTON_FILE));
            while (in.hasNextLine()) {
                String line = in.nextLine();
                String[] splitted_line = line.split(",");
                TerritoryButton t = new TerritoryButton(
                        this.fromStringToTerritory(splitted_line[0].trim()),
                        Integer.parseInt(splitted_line[1].trim()),
                        Integer.parseInt(splitted_line[2].trim()),
                        Integer.parseInt(splitted_line[3].trim()),
                        Integer.parseInt(splitted_line[4].trim()));
                this.territoriesButtons.add(t);
            }
        } catch (FileNotFoundException e) {
            System.out.println("File " + Resource.TERRITORIES_BUTTON_FILE + " not found!");
        }
    }

    protected void drawLinesBetweenTerritories(Graphics g) {
        try {
            Scanner in = new Scanner(new File(Resource.LINES_BETWEEN_TERRITORIES_FILE));
            while (in.hasNextLine()) {
                String line = in.nextLine();
                String[] splitted_line = line.split(",");
                splitted_line[0] = splitted_line[0].trim();
                splitted_line[1] = splitted_line[1].trim();
                Integer x1, y1, x2, y2;
                TerritoryButton firstTerritoryButton = this.fromTerritoryToTerritoryButton(this.fromStringToTerritory(splitted_line[0]));
                x1 = firstTerritoryButton.getPositionX() + (firstTerritoryButton.getWidth() / 2);
                y1 = firstTerritoryButton.getPositionY() + (firstTerritoryButton.getHeight() / 2);
                if ((splitted_line[1].equalsIgnoreCase("LEFT")) || (splitted_line[1].equalsIgnoreCase("RIGHT"))) {
                    if (splitted_line[1].equalsIgnoreCase("LEFT")) {
                        x2 = 0;
                    } else {
                        x2 = this.width;
                    }
                    y2 = y1;
                } else {
                    TerritoryButton secondTerritoryButton = this.fromTerritoryToTerritoryButton(this.fromStringToTerritory(splitted_line[1]));
                    x2 = secondTerritoryButton.getPositionX() + (secondTerritoryButton.getWidth() / 2);
                    y2 = secondTerritoryButton.getPositionY() + (secondTerritoryButton.getHeight() / 2);
                }
                g.drawLine(x1, y1, x2, y2);
            }
        } catch (FileNotFoundException e) {
            System.out.println("File " + Resource.LINES_BETWEEN_TERRITORIES_FILE + " not found!");
        }
    }

    protected Territory fromStringToTerritory(String territoryName) {
        for (Territory territory : this.territories) {
            if (territory.getName().equalsIgnoreCase(territoryName.trim())) {
                return territory;
            }
        }
        return null;
    }

    protected TerritoryButton fromTerritoryToTerritoryButton(Territory territory) {
        for (TerritoryButton territoryButton : this.territoriesButtons) {
            if (territoryButton.getTerritory().equals(territory)) {
                return territoryButton;
            }
        }
        return null;
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(this.scaledImage, 0, 0, null);
        drawLinesBetweenTerritories(g);
    }

    protected void addContinentsBonusLabels() {
        Integer counter = 0;
        Integer x = this.width - (this.width * 5 / 16);
        Integer y = (this.height * 73 / 72) - this.height / 5;
        Font font = new Font("", Font.ITALIC + Font.BOLD, 14);
        Color brownColor = new Color(68, 48, 34);
        for (Continent continent : this.continentsBonus.keySet()) {
            JLabel nameContinentLabel = new JLabel(continent.getName());
            nameContinentLabel.setFont(font);
            nameContinentLabel.setForeground(brownColor);
            nameContinentLabel.setBounds(x, y + 20 * counter, 150, 20);
            JLabel bonusContinentLabel = new JLabel(this.continentsBonus.get(continent).toString());
            bonusContinentLabel.setFont(font);
            bonusContinentLabel.setForeground(brownColor);
            bonusContinentLabel.setBounds(x + 150, y + 20 * counter, 30, 20);
            this.add(nameContinentLabel);
            this.add(bonusContinentLabel);
            counter++;
        }
    }

    protected void addListeners() {
        this.territoriesButtons.forEach((territoryButton) -> {
            territoryButton.addActionListener((ActionEvent ae) -> {
                Territory clickedTerritory = territoryButton.getTerritory();
                facade.update(clickedTerritory);
            });
        });
    }

    public void updateLabels(List<Territory> territories) {
        this.territoriesButtons
                .stream()
                .filter((territoryButton) -> (territories.contains(territoryButton.getTerritory())))
                .forEach((territoryButton) -> territoryButton.updateNumberTanksLabel());
    }

    public void setClickableTerritories(List<Territory> territories) {
        this.territoriesButtons.forEach((territoryButton) -> {
            if (territories.contains(territoryButton.getTerritory())) {
                territoryButton.setEnabled(true);
                territoryButton.setBackground(territoryButton.getTerritory().getOwnerPlayer().getColor().getColor());
            } else {
                territoryButton.setEnabled(false);
                territoryButton.setBackground(territoryButton.getTerritory().getOwnerPlayer().getColor().getColor().darker());
            }
        });
    }

    public void updateColors(List<Territory> territories) {
        this.territoriesButtons
                .stream()
                .filter((territoryButton) -> (territories.contains(territoryButton.getTerritory())))
                .forEach(TerritoryButton::updateColor);
    }

}

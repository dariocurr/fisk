package risk;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.*;
import javax.swing.*;
import java.io.*;
import java.util.*;

public class BoardPanel extends JPanel {

    protected static final String TERRITORIES_BUTTON_FILE = "res/territory_button.txt";
    protected static final String BACKGROUND_IMAGE_FILE = "res/background.jpg";
    protected static final Image BACKGROUND_IMAGE = ImageLoader.loadImage(BACKGROUND_IMAGE_FILE);
    protected final Image SCALED_IMAGE;
    protected final List<TerritoryButton> TERRITORY_BUTTONS;
    protected final Integer WIDTH;
    protected final Integer HEIGHT;
    protected final List<Territory> TERRITORIES;
    protected final Map<Continent, Integer> CONTINENT_BONUS;
    protected final RiskFacade facade;

    public BoardPanel(RiskFacade facade, int width, int height, List<Territory> territories, Map<Continent, Integer> CONTINENT_BONUS) {
        super();
        this.WIDTH = width;
        this.HEIGHT = height;
        this.CONTINENT_BONUS = CONTINENT_BONUS;
        this.facade = facade;
        this.setPreferredSize(new Dimension(this.WIDTH, this.HEIGHT));
        this.SCALED_IMAGE = BoardPanel.BACKGROUND_IMAGE.getScaledInstance(this.WIDTH, this.HEIGHT, 0);
        this.TERRITORIES = territories;
        this.TERRITORY_BUTTONS = new ArrayList<>();
        this.initTerritoryButtons();
        this.setLayout(null);
        for (TerritoryButton territoryButton : this.TERRITORY_BUTTONS) {
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
            Scanner in = new Scanner(new File(TERRITORIES_BUTTON_FILE));
            while (in.hasNextLine()) {
                String line = in.nextLine();
                String[] splitted_line = line.split(",");
                TerritoryButton t = new TerritoryButton(
                        this.fromStringToTerritory(splitted_line[0].trim()),
                        Integer.parseInt(splitted_line[1].trim()),
                        Integer.parseInt(splitted_line[2].trim()),
                        Integer.parseInt(splitted_line[3].trim()),
                        Integer.parseInt(splitted_line[4].trim()));
                this.TERRITORY_BUTTONS.add(t);
            }
        } catch (FileNotFoundException e) {
            System.out.println("File " + TERRITORIES_BUTTON_FILE + " not found!");
        }
    }

    protected Territory fromStringToTerritory(String territoryName) {
        for (Territory territory : this.TERRITORIES) {
            if (territory.getName().equalsIgnoreCase(territoryName.trim())) {
                return territory;
            }
        }
        return null;
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(this.SCALED_IMAGE, 0, 0, null);
    }

    protected void addContinentsBonusLabels() {
        Integer counter = 0;
        Integer x = this.WIDTH - (this.WIDTH * 5 / 16);
        Integer y = (this.HEIGHT * 73 / 72) - this.HEIGHT / 5;
        Integer numberOfContinents = this.CONTINENT_BONUS.keySet().size();
        Font font = new Font("", Font.ITALIC + Font.BOLD, 14);
        Color brownColor = new Color(68, 48, 34);
        for (Continent continent : this.CONTINENT_BONUS.keySet()) {
            JLabel nameContinentLabel = new JLabel(continent.getName());
            nameContinentLabel.setFont(font);
            nameContinentLabel.setForeground(brownColor);
            nameContinentLabel.setBounds(x, y + 20 * counter, 150, 20);
            JLabel bonusContinentLabel = new JLabel(this.CONTINENT_BONUS.get(continent).toString());
            bonusContinentLabel.setFont(font);
            bonusContinentLabel.setForeground(brownColor);
            bonusContinentLabel.setBounds(x + 150, y + 20 * counter, 30, 20);
            this.add(nameContinentLabel);
            this.add(bonusContinentLabel);
            counter++;
        }
    }

    protected void addListeners() {
        for (TerritoryButton territoryButton : this.TERRITORY_BUTTONS) {
            territoryButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent ae) {
                    Territory clickedTerritory = territoryButton.getTerritory();
                    facade.update(clickedTerritory);
                }
            });
        }
    }

    public void updateLabels(List<Territory> territories) {
        for (TerritoryButton territoryButton : this.TERRITORY_BUTTONS) {
            if (territories.contains(territoryButton.getTerritory())) {
                territoryButton.updateNumberTanksLabel();
            }
        }
    }

    public void setClickableTerritories(List<Territory> territories) {
        for (TerritoryButton territoryButton : this.TERRITORY_BUTTONS) {
            if (territories.contains(territoryButton.getTerritory())) {
                territoryButton.setEnabled(true);
            } else {
                territoryButton.setEnabled(false);
            }
        }
    }

    public void updateColors(List<Territory> territories) {
        for (TerritoryButton territoryButton : this.TERRITORY_BUTTONS) {
            if (territories.contains(territoryButton.getTerritory())) {
                territoryButton.updateColor();
            }
        }
    }

}

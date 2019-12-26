/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package risk;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.event.*;
import javax.swing.*;
import java.io.*;
import java.util.*;

/**
 *
 * @author dario
 */
public class BoardPanel extends JPanel {

    private static final String TERRITORIES_BUTTON_FILE = "res/territory_button.txt";
    private static final String BACKGROUND_IMAGE_FILE = "res/background.jpg";
    private static final Image BACKGROUND_IMAGE = ImageLoader.loadImage(BACKGROUND_IMAGE_FILE);
    private final Image SCALED_IMAGE;
    private final List<TerritoryButton> TERRITORY_BUTTONS;
    private final Integer WIDTH;
    private final Integer HEIGHT;
    private final List<Territory> TERRITORIES;
    private final Map<Continent, Integer> CONTINENT_BONUS;
    private final Facade facade;

    public BoardPanel(int width, int height, List<Territory> territories, Map<Continent, Integer> CONTINENT_BONUS, Facade facade) {
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
        this.add(this.getContinentBonusPanel());
        this.addListeners();
    }

    private void initTerritoryButtons() {
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

    private Territory fromStringToTerritory(String territoryName) {
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
    
    protected JPanel getContinentBonusPanel() {
        JPanel continentBonusPanel = new JPanel();
        Integer numberOfContinents = this.CONTINENT_BONUS.keySet().size();
        if (numberOfContinents % 2 == 0) {
            continentBonusPanel.setLayout(new GridLayout(numberOfContinents / 2, 2));
        } else {
            continentBonusPanel.setLayout(new GridLayout((numberOfContinents + 1) / 2, 2));
        }
        for (Continent continent: this.CONTINENT_BONUS.keySet()) {
            continentBonusPanel.add(new JLabel(continent.getName() + "\t" + this.CONTINENT_BONUS.get(continent)));
        }
        return continentBonusPanel;
    }

    private void addListeners() {
        for (TerritoryButton territoryButton : this.TERRITORY_BUTTONS) {
            territoryButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent ae) {
                    Territory clickedTerritory = territoryButton.getTerritory();
                    List<Territory> involvedTerritories = facade.addClickedTerritory(clickedTerritory);
                    setClickableTerritories(involvedTerritories);

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

    public void updateColorTerritoryButton() {
        for (TerritoryButton t : this.TERRITORY_BUTTONS) {
            t.updateColor();
        }
    }

}

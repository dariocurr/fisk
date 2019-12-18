/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package risk;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
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
    
    public BoardPanel(int width, int height, List<Territory> territories) {
        super();
        this.WIDTH = width;
        this.HEIGHT = height;
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
    }
    
    private void initTerritoryButtons (){
        try {
            Scanner in = new Scanner ( new File( TERRITORIES_BUTTON_FILE ) );
            while ( in.hasNextLine() ){
                String line = in.nextLine();
                String [] splitted_line = line.split(",");
                TerritoryButton t = new TerritoryButton( 
                    this.fromStringToTerritory(splitted_line[0].trim()), 
                    Integer.parseInt(splitted_line[1].trim()), 
                    Integer.parseInt(splitted_line[2].trim()), 
                    Integer.parseInt(splitted_line[3].trim()), 
                    Integer.parseInt(splitted_line[4].trim()));
                this.TERRITORY_BUTTONS.add( t );
            }
        }
        catch( FileNotFoundException e ){
            System.out.println("File " + TERRITORIES_BUTTON_FILE + " not found!");
        }
    }
    
    private Territory fromStringToTerritory (String territoryName) {
        for (Territory territory: this.TERRITORIES) {
            if (territory.getName().equalsIgnoreCase(territoryName.trim())) {
                return territory;
            }
        }
        return null;
    }
    
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(SCALED_IMAGE, 0, 0, null);
    }
    
}

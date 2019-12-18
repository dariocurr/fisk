/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package risk;

import java.awt.*;
import javax.swing.*;

/**
 *
 * @author dario
 */
public class PlayerPanel extends JPanel {

    private final Integer WIDTH;
    private final Integer HEIGHT;
    
    public PlayerPanel(int width, int height) {
        this.WIDTH = width;
        this.HEIGHT = height;
        this.setPreferredSize(new Dimension(this.WIDTH, this.HEIGHT));
    }
    
}

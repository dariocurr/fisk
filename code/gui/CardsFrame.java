/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package risk;

import javax.swing.*;

/**
 *
 * @author dario
 */
public class CardsFrame extends JFrame {

    private static final Integer WIDTH = 200;
    private static final Integer HEIGHT = 400;
    
    public CardsFrame() {
        this.pack();
        this.defaultOperations();
    }
    
    
    
    private void defaultOperations (){
        this.setLocationRelativeTo(null);
        this.setResizable(false);
        this.setDefaultCloseOperation( JFrame.DISPOSE_ON_CLOSE );
        this.setVisible(true); 
    }
    
}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package risk;

import java.awt.GridLayout;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.*;
import javax.swing.*;

/**
 *
 * @author dario
 */
public class CardsFrame extends JFrame {

    private static final Integer WIDTH = 200;
    private final Map<JCheckBox, SymbolCard> checkBoxes;
    private final JButton changeTris;
    private final JLabel status;
    private final Facade facade;
    private Tris currentTris;

    public CardsFrame(Facade facade) {
        this.facade = facade;
        List<SymbolCard> cards = facade.getPlayerCards();
        this.checkBoxes = new HashMap<>();
        this.setLayout(new GridLayout(cards.size() + 3, 0));
        for (SymbolCard symbolCard : cards) {
            JCheckBox cb = new JCheckBox(symbolCard.toString() + ", ");
            this.checkBoxes.put(cb, symbolCard);
            this.add(cb);
        }
        System.out.println("sono qui");
        this.addListenersToCheckBoxes();
        this.status = new JLabel("You must select 3 cards", SwingConstants.CENTER);
        this.status.setForeground(Color.RED);
        this.add(this.status);
        this.changeTris = new JButton("Change tris");
        this.changeTris.setEnabled(false);
        this.add(this.changeTris);
        this.setSize(CardsFrame.WIDTH, 100 + this.checkBoxes.size() * 50);
        this.defaultOperations();
    }

    private void defaultOperations() {
        this.setLocationRelativeTo(null);
        this.setResizable(false);
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        this.setVisible(true);
    }

    private void addListenersToCheckBoxes() {
        for (JCheckBox checkBox : this.checkBoxes.keySet()) {
            checkBox.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent ae) {
                    int counter = 0;
                    currentTris = null;
                    for (JCheckBox cb : checkBoxes.keySet()) {
                        if (cb.isSelected()) {
                            counter++;
                        }
                    }
                    if (counter == 3) {
                        ArrayList<SymbolCard> temp = new ArrayList<>();
                        for (JCheckBox cb : checkBoxes.keySet()) {
                            if (cb.isSelected()) {
                                temp.add(checkBoxes.get(cb));
                            }
                        }
                        Tris tris = new Tris(temp.get(0), temp.get(1), temp.get(2));
                        if(facade.checkTris(tris)) {
                            changeTris.setEnabled(true);
                            status.setText("Press to change Tris!");
                            status.setForeground(Color.GREEN);
                            currentTris = tris;
                        }
                    } else {
                        changeTris.setEnabled(false);
                        status.setText("You must select 3 cards");
                        status.setForeground(Color.RED);
                    }
                }
            });

        }
    }
    
    private void addListenerToButton() {
        this.changeTris.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                if (currentTris != null ) {
                    if (!facade.changeTris(currentTris)) {
                        JOptionPane.showMessageDialog(null, "You reached the maximum number of tanks");
                    }
                } 
            }
        });
    }

}

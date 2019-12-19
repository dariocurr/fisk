/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package risk;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.*;
import javax.swing.*;
import javax.swing.border.EmptyBorder;

/**
 *
 * @author dario
 */
public class CardsFrame extends JFrame {

    private static final Integer WIDTH = 200;
    private static final Integer HEIGHT = 400;
    private final ArrayList<JCheckBox> checkBoxes;
    private final JButton changeTris;
    private final JLabel status;

    public CardsFrame() {
        ArrayList<SymbolCard> cards = new ArrayList<>();
        this.checkBoxes = new ArrayList<>();
        cards.add(new SymbolCard(Symbol.BISHOP));
        cards.add(new SymbolCard(Symbol.JOKER));
        cards.add(new SymbolCard(Symbol.CANNON));
        cards.add(new SymbolCard(Symbol.KNIGHT));
        cards.add(new SymbolCard(Symbol.CANNON));
        cards.add(new SymbolCard(Symbol.KNIGHT));
        this.setLayout(new GridLayout(cards.size() + 3, 0));
        for (SymbolCard territoryCard : cards) {
            System.out.println(territoryCard);
            JCheckBox cb = new JCheckBox(territoryCard.toString() + ", ");
            this.checkBoxes.add(cb);
            this.add(cb);
        }
        this.addListenersToCheckBoxes();
        this.status = new JLabel("You must select 3 cards", SwingConstants.CENTER);
        this.status.setForeground(Color.RED);
        this.add(this.status);
        this.changeTris = new JButton("Change tris");
        this.changeTris.setEnabled(false);
        this.add(this.changeTris);
        this.setSize(200, this.checkBoxes.size() * 75);
        this.defaultOperations();
    }

    private void defaultOperations() {
        this.setLocationRelativeTo(null);
        this.setResizable(false);
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        this.setVisible(true);
    }

    private void addListenersToCheckBoxes() {
        for (JCheckBox checkBox : this.checkBoxes) {
            checkBox.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent ae) {
                    int counter = 0;
                    for (JCheckBox cb : checkBoxes) {
                        if (cb.isSelected()) {
                            counter++;
                        }
                    }
                    if (counter == 3) {
                        changeTris.setEnabled(true);
                        status.setText("Press to change Tris!");
                        status.setForeground(Color.GREEN);
                    } else {
                        changeTris.setEnabled(false);
                        status.setText("You must select 3 cards");
                        status.setForeground(Color.RED);
                    }
                }
            });

        }
    }

}

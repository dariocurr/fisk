/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package risk;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.util.*;

/**
 *
 * @author dario
 */
public class NumberOfTanksFrame extends JFrame {

    private static final Integer HEIGHT = 220;
    private final JButton moveButton;
    private final JSlider numberOfTanksSlider;
    private final Facade facade;

    public NumberOfTanksFrame(Facade facade, String territory, Integer max) {
        this.setTitle("Moving");
        this.facade = facade;
        JLabel request = new JLabel("How many tanks do you want to move to: " + territory, SwingConstants.CENTER);
        this.numberOfTanksSlider = new JSlider(JSlider.HORIZONTAL, 1, max, max / 2);
        Dictionary<Integer, JLabel> labels = new Hashtable<>();
        for (int i = 1; i <= max; i++) {
            labels.put(i, new JLabel("" + i));
        }
        Integer componentMaxWidth = Math.max(request.getText().length() * 8, max * 20);
        this.setLayout(new FlowLayout(FlowLayout.CENTER, componentMaxWidth, 30));
        this.numberOfTanksSlider.setLabelTable(labels);
        this.numberOfTanksSlider.setPaintLabels(true);
        this.numberOfTanksSlider.setPreferredSize(new Dimension(max * 20, 30));
        this.moveButton = new JButton("Move");
        this.add(request);
        this.add(this.numberOfTanksSlider);
        this.add(this.moveButton);
        this.setSize(componentMaxWidth + 50, NumberOfTanksFrame.HEIGHT);
        this.defaultOperations();
        this.addButtonListener();
    }

    private void defaultOperations() {
        this.setLocationRelativeTo(null);
        this.setResizable(false);
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        this.setVisible(true);
    }

    private void addButtonListener() {
        this.moveButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                facade.setNumberOfTanksToMove(numberOfTanksSlider.getValue());
                facade.update();
                dispose();
            }
        });
    }

}

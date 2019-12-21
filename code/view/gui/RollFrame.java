/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package risk;

import java.awt.*;
import java.awt.event.*;
import java.util.*;
import javax.swing.*;

/**
 *
 * @author dario
 */
public class RollFrame extends JFrame {

    private static final Integer WIDTH = 300;
    private static final Integer HEIGHT = 300;
    private final JButton rollButton;
    private final JButton exitButton;
    private final JLabel[] ATTACK_LABEL;
    private final JLabel[] DEFENSE_LABEL;
    private final Facade facade;

    public RollFrame(Facade facade) {
        this.facade = facade;
        this.setLayout(new GridLayout(3, 1));
        int hgap = RollFrame.WIDTH / 10;
        int vgap = RollFrame.HEIGHT / 15;
        JPanel topPanel = new JPanel();
        topPanel.setLayout(new FlowLayout(FlowLayout.CENTER, hgap, vgap));
        JPanel centerPanel = new JPanel();
        centerPanel.setLayout(new FlowLayout(FlowLayout.CENTER, hgap, vgap));
        JPanel downPanel = new JPanel();
        downPanel.setLayout(new FlowLayout(FlowLayout.CENTER, hgap * 2, vgap * 2));
        this.ATTACK_LABEL = new JLabel[3];
        this.DEFENSE_LABEL = new JLabel[3];
        for (int i = 0; i < this.ATTACK_LABEL.length; i++) {
            this.ATTACK_LABEL[i] = new JLabel();
            topPanel.add(this.ATTACK_LABEL[i]);
        }
        for (int i = 0; i < this.DEFENSE_LABEL.length; i++) {
            this.DEFENSE_LABEL[i] = new JLabel();
            centerPanel.add(this.DEFENSE_LABEL[i]);
        }
        this.rollButton = new JButton("Roll");
        this.exitButton = new JButton("Exit");
        downPanel.add(this.rollButton);
        downPanel.add(this.exitButton);
        this.add(topPanel);
        this.add(centerPanel);
        this.add(downPanel);
        this.setSize(RollFrame.WIDTH, RollFrame.HEIGHT);
        this.defaultOperations();
        this.addListeners();
    }

    private void defaultOperations() {
        this.setLocationRelativeTo(null);
        this.setResizable(false);
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        this.setVisible(true);
    }

    private void addListeners() {
        this.rollButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
            }
        });
    }

    private void updateFrame(Integer[] diceValues) {
        for (int i = 0; i < diceValues.length; i++) {
            this.ATTACK_LABEL[i].setIcon(new ImageIcon(this.getAttackDice(diceValues[i])));
        }
        /*
        for (int i = 0; i < this.DEFENSE_DICE.length; i++) {
            this.DEFENSE_LABEL[i].setIcon(new ImageIcon(this.getDefenseDice(this.DEFENSE_DICE[i].getValue())));
        }
        */
    }

    private Image getAttackDice(Integer value) {
        String path = "res/dice/red_dice_";
        path += String.valueOf(value);
        path += ".png";
        return ImageLoader.loadImage(path);
    }

    private Image getDefenseDice(Integer value) {
        String path = "res/dice/white_dice_";
        path += String.valueOf(value);
        path += ".png";
        return ImageLoader.loadImage(path);
    }

}

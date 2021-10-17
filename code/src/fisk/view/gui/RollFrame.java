package fisk.view.gui;

import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.event.ActionEvent;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import fisk.Resource;
import fisk.dice.Dice;
import fisk.facade.RiskFacade;
import fisk.player.AIPlayer;
import fisk.territory.Territory;
import fisk.view.gui.tool.ImageLoader;

/**
 * Classe che implementa l'interfaccia grafica per la visualizzazione dei dadi
 * estratti durante una battaglia.
 */
public class RollFrame extends JDialog {

    protected final Integer width;
    protected final Integer height = 300;
    protected final JButton rollButton;
    protected final JButton continueButton;
    protected final JButton exitButton;
    protected final JLabel[] attackLabels;
    protected final JLabel[] defenseLabels;
    protected final RiskFacade facade;
    protected final Dice[] attackDiceValues;
    protected final Dice[] defenseDiceValues;
    protected final Integer numberOfRolledDice;
    protected final Territory from;
    protected final Territory to;
    // protected Boolean isAttackDiceUpdated;
    // protected Boolean isDefenseDiceUpdated;

    public RollFrame(RiskFacade facade, Territory from, Territory to, int numberOfRolledDice, Dice[] attackDiceValues,
            Dice[] defenseDiceValues) {
        super();
        this.setModal(true);
        this.from = from;
        this.to = to;
        this.setTitle(this.from.getName() + " ( " + this.from.getOwnerPlayer().getName() + " ) attacks "
                + this.to.getName() + " ( " + this.to.getOwnerPlayer().getName() + " )");
        this.width = this.getTitle().length() * 10;
        this.facade = facade;
        this.numberOfRolledDice = numberOfRolledDice;
        this.attackDiceValues = attackDiceValues;
        this.defenseDiceValues = defenseDiceValues;
        this.setLayout(new GridLayout(3, 1));
        int hgap = this.width / 10;
        int vgap = this.height / 15;
        JPanel topPanel = new JPanel();
        topPanel.setLayout(new FlowLayout(FlowLayout.CENTER, hgap, vgap));
        JPanel centerPanel = new JPanel();
        centerPanel.setLayout(new FlowLayout(FlowLayout.CENTER, hgap, vgap));
        JPanel downPanel = new JPanel();
        downPanel.setLayout(new FlowLayout(FlowLayout.CENTER, hgap, vgap * 2));
        this.attackLabels = new JLabel[this.numberOfRolledDice];
        this.defenseLabels = new JLabel[this.numberOfRolledDice];
        for (int i = 0; i < this.numberOfRolledDice; i++) {
            this.attackLabels[i] = new JLabel();
            topPanel.add(this.attackLabels[i]);
        }
        for (int i = 0; i < this.numberOfRolledDice; i++) {
            this.defenseLabels[i] = new JLabel();
            centerPanel.add(this.defenseLabels[i]);
        }
        // this.isAttackDiceUpdated = false;
        // this.isDefenseDiceUpdated = false;
        this.rollButton = new JButton("Roll");
        this.continueButton = new JButton("Continue");
        this.continueButton.setEnabled(false);
        this.exitButton = new JButton("Exit");
        downPanel.add(this.rollButton);
        downPanel.add(this.continueButton);
        downPanel.add(this.exitButton);
        this.add(topPanel);
        this.add(centerPanel);
        this.add(downPanel);
        this.setSize(this.width, this.height);
        this.addListeners();
        this.defaultOperations();
    }

    protected void defaultOperations() {
        this.setLocationRelativeTo(null);
        this.setResizable(false);
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        this.setVisible(true);
    }

    protected void addListeners() {
        this.rollButton.addActionListener((ActionEvent ae) -> {
            /*
             * if (isAttackDiceUpdated == false) { updateAttackDiceFrame(); } else if
             * (isDefenseDiceUpdated == false) { updateDefenseDiceFrame(); }
             */
            Integer[] result = new Integer[numberOfRolledDice];
            Integer tanksLostAttack = 0;
            Integer tanksLostDefense = 0;
            for (int i = 0; i < numberOfRolledDice; i++) {
                if (attackDiceValues[i].compareTo(defenseDiceValues[i]) > 0) {
                    result[i] = 1;
                    tanksLostDefense++;
                } else {
                    result[i] = 0;
                    tanksLostAttack++;
                }
            }
            updateAttackDiceFrame(result);
            updateDefenseDiceFrame(result);
            rollButton.setEnabled(false);
            if (!(from.getOwnerPlayer() instanceof AIPlayer)) {
                if ((from.getTanks().size() - tanksLostAttack > 1) && (to.getTanks().size() - tanksLostDefense > 0)) {
                    continueButton.setEnabled(true);
                }
            }
        });
        this.continueButton.addActionListener((ActionEvent ae) -> {
            facade.setContinuousAttack(true);
            dispose();
        });
        this.exitButton.addActionListener((ActionEvent ae) -> dispose());
    }

    protected void updateAttackDiceFrame(Integer[] result) {
        // this.isAttackDiceUpdated = true;
        for (int i = 0; i < this.numberOfRolledDice; i++) {
            if (result[i] == 1) {
                this.attackLabels[i]
                        .setIcon(new ImageIcon(this.getAttackDice(this.attackDiceValues[i].getValue(), true)));
            } else {
                this.attackLabels[i]
                        .setIcon(new ImageIcon(this.getAttackDice(this.attackDiceValues[i].getValue(), false)));
            }
        }
    }

    protected void updateDefenseDiceFrame(Integer[] result) {
        // this.isDefenseDiceUpdated = true;
        for (int i = 0; i < this.numberOfRolledDice; i++) {
            if (result[i] == 1) {
                this.defenseLabels[i]
                        .setIcon(new ImageIcon(this.getDefenseDice(this.defenseDiceValues[i].getValue(), false)));
            } else {
                this.defenseLabels[i]
                        .setIcon(new ImageIcon(this.getDefenseDice(this.defenseDiceValues[i].getValue(), true)));
            }
        }
    }

    protected Image getAttackDice(Integer value, Boolean win) {
        String path = Resource.RED_DICE_PATH;
        path += String.valueOf(value);
        if (!win) {
            path += "_opaque";
        }
        path += Resource.DICE_IMAGES_EXTENSION;
        return ImageLoader.loadImage(path);
    }

    protected Image getDefenseDice(Integer value, Boolean win) {
        String path = Resource.WHITE_DICE_PATH;
        path += String.valueOf(value);
        if (!win) {
            path += "_opaque";
        }
        path += Resource.DICE_IMAGES_EXTENSION;
        return ImageLoader.loadImage(path);
    }

}

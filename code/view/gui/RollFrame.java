package risk;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class RollFrame extends JDialog {

    protected static final Integer WIDTH = 300;
    protected static final Integer HEIGHT = 300;
    protected final JButton rollButton;
    protected final JButton exitButton;
    protected final JLabel[] ATTACK_LABEL;
    protected final JLabel[] DEFENSE_LABEL;
    protected final RiskFacade facade;
    protected Dice[] attackDiceValues;
    protected Dice[] defenseDiceValues;
    protected boolean isAttackDiceUpdated = false;
    protected boolean isDefenseDiceUpdated = false;
    protected Integer numberOfRolledDice;

    public RollFrame(RiskFacade facade, int numberOfRolledDice, Dice[] attackDiceValues, Dice[] defenseDiceValues) {
        super();
        this.setModal(true);
        this.setTitle("Rolled Dice");
        this.facade = facade;
        this.numberOfRolledDice = numberOfRolledDice;
        this.attackDiceValues = attackDiceValues;
        this.defenseDiceValues = defenseDiceValues;
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
        this.rollButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                if (isAttackDiceUpdated == false) {
                    updateAttackDiceFrame();
                } else if (isDefenseDiceUpdated == false) {
                    updateDefenseDiceFrame();
                }
            }
        });

        this.exitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                dispose();
            }
        });
    }

    /*protected void updateFrame(Integer[] diceValues) {
        for (int i = 0; i < diceValues.length; i++) {
            this.ATTACK_LABEL[i].setIcon(new ImageIcon(this.getAttackDice(diceValues[i])));
        }
        
        for (int i = 0; i < this.DEFENSE_DICE.length; i++) {
            this.DEFENSE_LABEL[i].setIcon(new ImageIcon(this.getDefenseDice(this.DEFENSE_DICE[i].getValue())));
        }
         
    }*/
    protected void updateAttackDiceFrame() {
        this.isAttackDiceUpdated = true;
        for (int i = 0; i < this.numberOfRolledDice; i++) {
            this.ATTACK_LABEL[i].setIcon(new ImageIcon(this.getAttackDice(this.attackDiceValues[i].getValue())));
        }
    }

    protected void updateDefenseDiceFrame() {
        this.isDefenseDiceUpdated = true;
        for (int i = 0; i < this.numberOfRolledDice; i++) {
            this.DEFENSE_LABEL[i].setIcon(new ImageIcon(this.getDefenseDice(this.defenseDiceValues[i].getValue())));
        }
    }

    protected Image getAttackDice(Integer value) {
        String path = "res/dice/red_dice_";
        path += String.valueOf(value);
        path += ".png";
        return ImageLoader.loadImage(path);
    }

    protected Image getDefenseDice(Integer value) {
        String path = "res/dice/white_dice_";
        path += String.valueOf(value);
        path += ".png";
        return ImageLoader.loadImage(path);
    }

}

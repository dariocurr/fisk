package risk;

import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class RollFrame extends JDialog {

    protected final Integer width = 300;
    protected final Integer height = 300;
    protected final JButton rollButton;
    protected final JButton exitButton;
    protected final JLabel[] attackLabels;
    protected final JLabel[] defenseLabels;
    protected final RiskFacade facade;
    protected final Dice[] attackDiceValues;
    protected final Dice[] defenseDiceValues;
    protected final Integer numberOfRolledDice;
    protected Boolean isAttackDiceUpdated;
    protected Boolean isDefenseDiceUpdated;

    public RollFrame(RiskFacade facade, int numberOfRolledDice, Dice[] attackDiceValues, Dice[] defenseDiceValues) {
        super();
        this.setModal(true);
        this.setTitle("Rolled Dice");
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
        downPanel.setLayout(new FlowLayout(FlowLayout.CENTER, hgap * 2, vgap * 2));
        this.attackLabels = new JLabel[3];
        this.defenseLabels = new JLabel[3];
        for (int i = 0; i < this.attackLabels.length; i++) {
            this.attackLabels[i] = new JLabel();
            topPanel.add(this.attackLabels[i]);
        }
        for (int i = 0; i < this.defenseLabels.length; i++) {
            this.defenseLabels[i] = new JLabel();
            centerPanel.add(this.defenseLabels[i]);
        }
        this.isAttackDiceUpdated = false;
        this.isDefenseDiceUpdated = false;
        this.rollButton = new JButton("Roll");
        this.exitButton = new JButton("Exit");
        downPanel.add(this.rollButton);
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

    protected void updateAttackDiceFrame() {
        this.isAttackDiceUpdated = true;
        for (int i = 0; i < this.numberOfRolledDice; i++) {
            this.attackLabels[i].setIcon(new ImageIcon(this.getAttackDice(this.attackDiceValues[i].getValue())));
        }
    }

    protected void updateDefenseDiceFrame() {
        this.isDefenseDiceUpdated = true;
        for (int i = 0; i < this.numberOfRolledDice; i++) {
            this.defenseLabels[i].setIcon(new ImageIcon(this.getDefenseDice(this.defenseDiceValues[i].getValue())));
        }
    }

    protected Image getAttackDice(Integer value) {
        String path = Resource.RED_DICE_PATH;
        path += String.valueOf(value);
        path += Resource.DICE_IMAGES_EXTENSION;
        return ImageLoader.loadImage(path);
    }

    protected Image getDefenseDice(Integer value) {
        String path = Resource.WHITE_DICE_PATH;
        path += String.valueOf(value);
        path += Resource.DICE_IMAGES_EXTENSION;
        return ImageLoader.loadImage(path);
    }

}

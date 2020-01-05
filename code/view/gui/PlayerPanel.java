package risk;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class PlayerPanel extends JPanel {

    protected final Integer WIDTH;
    protected final Integer HEIGHT;
    protected JLabel nameColorLabel;
    protected JLabel numberTerritoriesLabel;
    protected JLabel freeTanksLabel;
    protected JButton cardsButton;
    protected JButton goalButton;
    protected JLabel stageLabel;
    protected JButton endButton;
    protected final RiskFacade facade;

    public PlayerPanel(RiskFacade facade, int width, int height) {
        this.facade = facade;
        this.WIDTH = width;
        this.HEIGHT = height;
        this.setPreferredSize(new Dimension(this.WIDTH, this.HEIGHT));
        this.buildGUI();
        this.addListeners();
    }

    protected void buildGUI() {
        this.setLayout(new GridBagLayout());
        GridBagConstraints innerGBC = new GridBagConstraints();
        innerGBC.gridx = GridBagConstraints.CENTER;
        innerGBC.gridy = GridBagConstraints.RELATIVE;
        innerGBC.weighty = 0.1;
        GridBagConstraints outerGBC = new GridBagConstraints();
        outerGBC.gridx = GridBagConstraints.CENTER;
        outerGBC.gridy = GridBagConstraints.RELATIVE;
        outerGBC.weighty = 2;
        JPanel northPanel = new JPanel();
        northPanel.setPreferredSize(new Dimension(this.WIDTH, this.HEIGHT / 5));
        northPanel.setLayout(new GridBagLayout());
        JPanel centerPanel = new JPanel();
        centerPanel.setPreferredSize(new Dimension(this.WIDTH, this.HEIGHT / 5));
        centerPanel.setLayout(new GridBagLayout());
        JPanel southPanel = new JPanel();
        southPanel.setPreferredSize(new Dimension(this.WIDTH, this.HEIGHT / 5));
        southPanel.setLayout(new GridBagLayout());
        this.nameColorLabel = new JLabel(this.facade.getHumanPlayerName(), SwingConstants.CENTER);
        this.nameColorLabel.setForeground(this.facade.getHumanPlayerColor());
        this.nameColorLabel.setFont(new Font("Dialog", Font.BOLD, 14));
        this.numberTerritoriesLabel = new JLabel("Number of territories: ", SwingConstants.CENTER);
        this.freeTanksLabel = new JLabel("Tanks to locate: ", SwingConstants.CENTER);
        this.cardsButton = new JButton("Cards");
        this.goalButton = new JButton("Goal");
        this.stageLabel = new JLabel("Stage: ", SwingConstants.CENTER);
        this.endButton = new JButton("End stage");
        northPanel.add(this.nameColorLabel, innerGBC);
        northPanel.add(this.numberTerritoriesLabel, innerGBC);
        northPanel.add(this.freeTanksLabel, innerGBC);
        centerPanel.add(this.cardsButton, innerGBC);
        centerPanel.add(this.goalButton, innerGBC);
        southPanel.add(this.stageLabel, innerGBC);
        southPanel.add(this.endButton, innerGBC);
        this.add(northPanel, outerGBC);
        this.add(centerPanel, outerGBC);
        this.add(southPanel, outerGBC);
    }

    protected void addListeners() {
        this.goalButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                JOptionPane.showMessageDialog(null, facade.getHumanPlayerGoal(), "Your goal", JOptionPane.INFORMATION_MESSAGE);
            }
        });
        this.cardsButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                new CardsFrame(facade);
            }
        });
        this.endButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                facade.endStage();
            }
        });
    }

    public void updateLabels(Integer numberOfTerritories, Integer numberOfFreeTanks, String currentStage) {
        this.numberTerritoriesLabel.setText("Number of territories: " + numberOfTerritories);
        this.freeTanksLabel.setText("Tanks to locate: " + numberOfFreeTanks);
        this.stageLabel.setText("Stage: " + currentStage);
    }

    public void enableCardsButton() {
        this.cardsButton.setEnabled(true);
    }

    public void enableEndStageButton() {
        this.endButton.setEnabled(true);
    }

    public void disableCardsButton() {
        this.cardsButton.setEnabled(false);
    }

    public void disableEndStageButton() {
        this.endButton.setEnabled(false);
    }

}

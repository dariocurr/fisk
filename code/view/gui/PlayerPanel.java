/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package risk;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

/**
 *
 * @author dario
 */
public class PlayerPanel extends JPanel {

    private final Integer WIDTH;
    private final Integer HEIGHT;
    private JLabel nameColorLabel;
    private JLabel numberTerritoriesLabel;
    private JLabel freeTanksLabel;
    private JButton cardsButton;
    private JButton goalButton;
    private JLabel stageLabel;
    private JButton endButton;
    private final Facade facade;

    public PlayerPanel(int width, int height, Facade facade) {
        this.facade = facade;
        this.WIDTH = width;
        this.HEIGHT = height;
        this.setPreferredSize(new Dimension(this.WIDTH, this.HEIGHT));
        this.buildGUI();
        this.addListenersToButtons();
    }

    private void buildGUI() {
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
        this.nameColorLabel = new JLabel(this.facade.getPlayerName(), SwingConstants.CENTER);
        this.nameColorLabel.setForeground(this.facade.getPlayerColor());
        this.nameColorLabel.setFont(new Font("Dialog", Font.BOLD, 14));
        this.numberTerritoriesLabel = new JLabel("", SwingConstants.CENTER);
        this.freeTanksLabel = new JLabel("", SwingConstants.CENTER);
        this.cardsButton = new JButton("Cards");
        this.goalButton = new JButton("Goal");
        this.stageLabel = new JLabel("Stage: " , SwingConstants.CENTER);
        this.endButton = new JButton("End stage");
        northPanel.add(nameColorLabel, innerGBC);
        northPanel.add(numberTerritoriesLabel, innerGBC);
        northPanel.add(freeTanksLabel, innerGBC);
        centerPanel.add(cardsButton, innerGBC);
        centerPanel.add(goalButton, innerGBC);
        southPanel.add(stageLabel, innerGBC);
        southPanel.add(endButton, innerGBC);
        this.add(northPanel, outerGBC);
        this.add(centerPanel, outerGBC);
        this.add(southPanel, outerGBC);
    }

    private void addListenersToButtons() {
        this.goalButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                JOptionPane.showMessageDialog(null, "Obiettivo: " + facade.getPlayerGoal());
            }
        });
        this.cardsButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                new CardsFrame(facade);
            }
        });
    }
    
    public void updateLabels(Integer numberOfTerritories, Integer numberOfFreeTanks, String currentStage) {
        this.numberTerritoriesLabel.setText("Number of territories: " + numberOfTerritories);
        this.freeTanksLabel.setText("Tanks to locate: " + numberOfFreeTanks);
        this.stageLabel.setText("Stage: " + currentStage);
    }

}

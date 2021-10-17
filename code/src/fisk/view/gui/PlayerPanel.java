package fisk.view.gui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.AbstractMap.SimpleEntry;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;

import fisk.facade.RiskFacade;
import fisk.player.RiskColor;

/**
 * Classe che implementa il pannello del giocatore reale.
 */
public class PlayerPanel extends JPanel {

    protected final Integer width;
    protected final Integer height;
    protected final JLabel nameColorLabel;
    protected final JLabel numberTerritoriesLabel;
    // protected final JLabel freeTanksLabel;
    protected final JButton cardsButton;
    protected final JButton goalButton;
    protected final JButton endButton;
    protected final RiskFacade facade;
    protected final List<JLabel> stagesLabel;
    protected final List<JLabel> playersLabel;

    public PlayerPanel(RiskFacade facade, int width, int height, String humanPlayerName, Color humanPlayerColor,
            List<SimpleEntry<String, RiskColor>> players, List<String> stages) {
        this.facade = facade;
        this.width = width;
        this.height = height;
        this.setPreferredSize(new Dimension(this.width, this.height));
        this.nameColorLabel = new JLabel(humanPlayerName, SwingConstants.CENTER);
        this.nameColorLabel.setForeground(humanPlayerColor);
        this.numberTerritoriesLabel = new JLabel("Number of territories: ", SwingConstants.CENTER);
        // this.freeTanksLabel = new JLabel("Tanks to locate: ", SwingConstants.CENTER);
        this.cardsButton = new JButton("Cards");
        this.goalButton = new JButton("Goal");
        this.stagesLabel = new ArrayList<>();
        stages.forEach((stage) -> {
            this.stagesLabel.add(new JLabel(stage, SwingConstants.CENTER));
        });
        this.playersLabel = new ArrayList<>();
        for (SimpleEntry<String, RiskColor> entry : players) {
            JLabel playerLabel = new JLabel(entry.getKey(), SwingConstants.CENTER);
            playerLabel.setForeground(entry.getValue().getColor());
            this.playersLabel.add(playerLabel);
        }
        this.endButton = new JButton("End stage");
        this.buildGUI();
        this.addListeners();
    }

    protected void buildGUI() {
        /*
         * this.setLayout(new GridBagLayout()); GridBagConstraints innerGBC = new
         * GridBagConstraints(); innerGBC.gridx = GridBagConstraints.CENTER;
         * innerGBC.gridy = GridBagConstraints.RELATIVE; innerGBC.weighty = 0.1;
         * GridBagConstraints outerGBC = new GridBagConstraints(); outerGBC.gridx =
         * GridBagConstraints.CENTER; outerGBC.gridy = GridBagConstraints.RELATIVE;
         * outerGBC.weighty = 2; JPanel northPanel = new JPanel();
         * northPanel.setPreferredSize(new Dimension(this.width, this.height / 5));
         * northPanel.setLayout(new GridBagLayout()); JPanel centerPanel = new JPanel();
         * centerPanel.setPreferredSize(new Dimension(this.width, this.height / 5));
         * centerPanel.setLayout(new GridBagLayout()); JPanel southPanel = new JPanel();
         * southPanel.setPreferredSize(new Dimension(this.width, this.height / 5));
         * southPanel.setLayout(new GridBagLayout()); this.nameColorLabel.setFont(new
         * Font("Dialog", Font.BOLD, 14)); northPanel.add(this.nameColorLabel,
         * innerGBC); northPanel.add(this.numberTerritoriesLabel, innerGBC);
         * northPanel.add(this.cardsButton, innerGBC); northPanel.add(this.goalButton,
         * innerGBC); this.playersLabel.forEach((playerLabel) -> {
         * centerPanel.add(playerLabel, innerGBC); });
         * //southPanel.add(this.freeTanksLabel, innerGBC);
         * this.stagesLabel.forEach((stage) -> { southPanel.add(stage, innerGBC); });
         * //southPanel.add(this.stageLabel, innerGBC); southPanel.add(this.endButton,
         * innerGBC); this.add(northPanel, outerGBC); this.add(centerPanel, outerGBC);
         * this.add(southPanel, outerGBC);
         */
        JPanel northPanel = new JPanel();
        northPanel.setLayout(new FlowLayout(FlowLayout.CENTER, this.width, 35));
        northPanel.setPreferredSize(new Dimension(this.width, this.height / 3));
        JPanel centerPanel = new JPanel();
        centerPanel.setBorder(new EmptyBorder((6 - this.playersLabel.size()) * 25, 0, 0, 0));
        centerPanel.setLayout(new FlowLayout(FlowLayout.CENTER, this.width, 25));
        centerPanel.setPreferredSize(new Dimension(this.width, this.height / 3));
        JPanel southPanel = new JPanel();
        southPanel.setLayout(new FlowLayout(FlowLayout.CENTER, this.width, 25));
        southPanel.setPreferredSize(new Dimension(this.width, this.height / 3));
        northPanel.add(this.nameColorLabel);
        northPanel.add(this.numberTerritoriesLabel);
        northPanel.add(this.cardsButton);
        northPanel.add(this.goalButton);
        this.playersLabel.forEach(centerPanel::add);
        this.stagesLabel.forEach(southPanel::add);
        southPanel.add(this.endButton);
        this.add(northPanel);
        this.add(centerPanel);
        this.add(southPanel);
    }

    protected void addListeners() {
        this.goalButton.addActionListener((ActionEvent ae) -> {
            JOptionPane.showMessageDialog(null, facade.getHumanPlayerGoal(), "Your goal",
                    JOptionPane.INFORMATION_MESSAGE);
        });
        this.cardsButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                new CardsFrame(facade);
            }
        });
        this.endButton.addActionListener((ActionEvent ae) -> facade.endStage());
    }

    /**
     * Aggiorna il pannello del giocatore modificando il numero di territori da esso
     * posseduti, il numero di armate ancora da posizionare e la fase corrente.
     *
     * @param numberOfTerritories numero di territori posseduti dal giocatore reale
     * @param currentPlayer       nome del giocatore che sta giocando
     * @param currentStage        fase corrente
     */
    public void updateLabels(Integer numberOfTerritories, String currentPlayer, String currentStage) {
        this.numberTerritoriesLabel.setText("Number of territories: " + numberOfTerritories);
        // this.freeTanksLabel.setText("Tanks to locate: " + numberOfFreeTanks);
        this.stagesLabel.forEach((stage) -> {
            if (stage.getText().equalsIgnoreCase(currentStage)) {
                stage.setEnabled(true);
            } else {
                stage.setEnabled(false);
            }
        });
        this.playersLabel.forEach((player) -> {
            if (player.getText().equalsIgnoreCase(currentPlayer)) {
                player.setEnabled(true);
            } else {
                player.setEnabled(false);
            }
        });
        this.endButton.setText("End " + currentStage);
    }

    /**
     * Abilita il bottone delle carte del giocatore.
     */
    public void enableCardsButton() {
        this.cardsButton.setEnabled(true);
    }

    /**
     * Abilita il bottone di fine fase del turno corrente.
     */
    public void enableEndStageButton() {
        this.endButton.setEnabled(true);
    }

    /**
     * Disabilita il bottone delle carte del giocatore.
     */
    public void disableCardsButton() {
        this.cardsButton.setEnabled(false);
    }

    /**
     * Disabilita il bottone di fine fase del turno corrente.
     */
    public void disableEndStageButton() {
        this.endButton.setEnabled(false);
    }

}

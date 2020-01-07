package risk;

import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

/**
 * Classe che implementa il pannello del giocatore reale.
 */
public class PlayerPanel extends JPanel {

    protected final Integer width;
    protected final Integer height;
    protected final JLabel nameColorLabel;
    protected final JLabel numberTerritoriesLabel;
    protected final JLabel freeTanksLabel;
    protected final JButton cardsButton;
    protected final JButton goalButton;
    protected final JLabel stageLabel;
    protected final JButton endButton;
    protected final RiskFacade facade;

    public PlayerPanel(RiskFacade facade, int width, int height) {
        this.facade = facade;
        this.width = width;
        this.height = height;
        this.setPreferredSize(new Dimension(this.width, this.height));
        this.nameColorLabel = new JLabel(this.facade.getHumanPlayerName(), SwingConstants.CENTER);
        this.numberTerritoriesLabel = new JLabel("Number of territories: ", SwingConstants.CENTER);
        this.freeTanksLabel = new JLabel("Tanks to locate: ", SwingConstants.CENTER);
        this.cardsButton = new JButton("Cards");
        this.goalButton = new JButton("Goal");
        this.stageLabel = new JLabel("Stage: ", SwingConstants.CENTER);
        this.endButton = new JButton("End stage");
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
        northPanel.setPreferredSize(new Dimension(this.width, this.height / 5));
        northPanel.setLayout(new GridBagLayout());
        JPanel centerPanel = new JPanel();
        centerPanel.setPreferredSize(new Dimension(this.width, this.height / 5));
        centerPanel.setLayout(new GridBagLayout());
        JPanel southPanel = new JPanel();
        southPanel.setPreferredSize(new Dimension(this.width, this.height / 5));
        southPanel.setLayout(new GridBagLayout());
        this.nameColorLabel.setForeground(this.facade.getHumanPlayerColor());
        this.nameColorLabel.setFont(new Font("Dialog", Font.BOLD, 14));
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

    /**
     * Aggiorna il pannello del giocatore modificando il numero di territori da
     * esso posseduti, il numero di armate ancora da posizionare e la fase
     * corrente.
     *
     * @param numberOfTerritories numero di territori posseduti dal giocatore
     * reale
     * @param numberOfFreeTanks numero di armate ancora da posizionare
     * @param currentStage fase corrente
     */
    public void updateLabels(Integer numberOfTerritories, Integer numberOfFreeTanks, String currentStage) {
        this.numberTerritoriesLabel.setText("Number of territories: " + numberOfTerritories);
        this.freeTanksLabel.setText("Tanks to locate: " + numberOfFreeTanks);
        this.stageLabel.setText("Stage: " + currentStage);
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

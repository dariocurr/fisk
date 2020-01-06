package risk;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.Dimension;
import java.util.*;
import javax.swing.*;

/**
    Classe che implementa l'interfccia grafica.
*/

public class RiskGUI extends JFrame {

    private static final Integer WIDTH = 1500;
    private static final Integer HEIGHT = 900;
    private final RiskFacade facade;
    private final BoardPanel boardPanel;
    private final LogPanel logPanel;
    private final PlayerPanel playerPanel;

    /**
        Costruttore della classe con rifierimento a RiskFacade
    */
    public RiskGUI(RiskFacade facade) {
        super();
        this.setTitle("Risk");
        this.facade = facade;
        this.playerPanel = new PlayerPanel(this.facade, RiskGUI.WIDTH / 5, RiskGUI.HEIGHT);
        this.boardPanel = new BoardPanel(this.facade,
                RiskGUI.WIDTH - RiskGUI.WIDTH / 5,
                RiskGUI.HEIGHT - RiskGUI.HEIGHT / 6,
                this.facade.getTerritories(),
                this.facade.getAllContinentsBonus());
        this.boardPanel.updateColors(this.facade.getTerritories());
        this.logPanel = new LogPanel(this.facade, RiskGUI.WIDTH - RiskGUI.WIDTH / 5, RiskGUI.HEIGHT / 6);
        Container contentPane = this.getContentPane();
        contentPane.setLayout(new BorderLayout(0, 0));
        contentPane.add(this.playerPanel, BorderLayout.WEST);
        JPanel centerJPanel = new JPanel();
        centerJPanel.setLayout(new BorderLayout(0, 0));
        centerJPanel.setPreferredSize(new Dimension(RiskGUI.WIDTH - RiskGUI.WIDTH / 5, RiskGUI.HEIGHT));
        contentPane.add(centerJPanel, BorderLayout.CENTER);
        centerJPanel.add(this.boardPanel, BorderLayout.NORTH);
        centerJPanel.add(this.logPanel, BorderLayout.CENTER);
        this.setSize(RiskGUI.WIDTH, RiskGUI.HEIGHT);
        this.defaultOperations();
    }

    private void defaultOperations() {
        this.setLocationRelativeTo(null);
        this.setResizable(false);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setVisible(true);
    }

    /**
        Aggiorna il log della GUI.
        @param string messaggio da visualizzare
    */
    public void updateLogPanel(String string) {
        this.logPanel.append(string);
    }

    /**
        Aggiorna il pannello del giocatore.
        @see risk.PlayerPanel#updateLabels
    */
    public void updatePlayerPanel(Integer numberOfTerritories, Integer numberOfFreeTanks, String currentStage) {
        this.playerPanel.updateLabels(numberOfTerritories, numberOfFreeTanks, currentStage);
    }

    /**
        Aggiorna le label dei territori passati a parametro.
        @param territories territori di cui aggiornare il numero di armate
        @see risk.BoardPanel#updateLabels
    */
    public void updateLabelsTerritoriesButtons(List<Territory> territories) {
        this.boardPanel.updateLabels(territories);
    }

    /**
        Rende cliccabili i territori passsati a parametro.
        @param territories territori cliccabili
        @see risk.BoardPanel#setClickableTerritories
    */
    public void setClickableTerritories(List<Territory> territories) {
        this.boardPanel.setClickableTerritories(territories);
    }

    /**
        Aggiorna i colori dei territori passati a parametro.
        @param territories territori di cui aggiornare il numero di armate
        @see risk.BoardPanel#updateColors
    */
    public void updateColorsTerritoriesButtons(List<Territory> territories) {
        this.boardPanel.updateColors(territories);
    }

    /**
        Abilita il bottone delle carte del giocatore.
        @see risk.PlayerPanel#enableCardsButton
    */
    public void enableCardsButton() {
        this.playerPanel.enableCardsButton();
    }

    /**
        Abilita il bottone di fine fase del turno corrente.
        @see risk.PlayerPanel#enableCardsButton
    */
    public void enableEndStageButton() {
        this.playerPanel.enableEndStageButton();
    }

    /**
        Disabilita il bottone delle carte del giocatore.
        @see risk.PlayerPanel#disableCardsButton
    */
    public void disableCardsButton() {
        this.playerPanel.disableCardsButton();
    }

    /**
        Disabilita il bottone di fine fase del turno corrente.
        @see risk.PlayerPanel#disableCardsButton
    */
    public void disableEndStageButton() {
        this.playerPanel.disableEndStageButton();
    }

    /**
        Mostra un messaggio a GUI.
        @param message messaggio da visualizzare
    */
    public void showMessage(String message) {
        JOptionPane.showMessageDialog(this, message);
    }

}

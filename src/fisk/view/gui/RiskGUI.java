package fisk.view.gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.util.AbstractMap.SimpleEntry;
import java.util.List;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import fisk.facade.RiskFacade;
import fisk.player.RiskColor;
import fisk.territory.Territory;

/**
 * Classe che implementa l'interfccia grafica.
 */
public class RiskGUI extends JFrame {

    protected final Integer width = 1500;
    protected final Integer height = 900;
    protected final RiskFacade facade;
    protected final BoardPanel boardPanel;
    protected final LogPanel logPanel;
    protected final PlayerPanel playerPanel;

    /**
     * Costruttore della classe con rifierimento a RiskFacade
     */
    public RiskGUI(RiskFacade facade, String humanPlayerName, Color humanPlayerColor,
            List<SimpleEntry<String, RiskColor>> players, List<String> stages) {
        super();
        this.setTitle("Risk");
        this.facade = facade;
        this.playerPanel = new PlayerPanel(this.facade, this.width / 5, this.height, humanPlayerName, humanPlayerColor,
                players, stages);
        this.boardPanel = new BoardPanel(this.facade, this.width - this.width / 5, this.height - this.height / 6,
                this.facade.getTerritories(), this.facade.getAllContinentsBonus());
        this.boardPanel.updateColors(this.facade.getTerritories());
        this.logPanel = new LogPanel(this.facade, this.width - this.width / 5, this.height / 6);
        Container contentPane = this.getContentPane();
        contentPane.setLayout(new BorderLayout(0, 0));
        contentPane.add(this.playerPanel, BorderLayout.WEST);
        JPanel centerJPanel = new JPanel();
        centerJPanel.setLayout(new BorderLayout(0, 0));
        centerJPanel.setPreferredSize(new Dimension(this.width - this.width / 5, this.height));
        contentPane.add(centerJPanel, BorderLayout.CENTER);
        centerJPanel.add(this.boardPanel, BorderLayout.NORTH);
        centerJPanel.add(this.logPanel, BorderLayout.CENTER);
        this.setSize(this.width, this.height);
        this.defaultOperations();
    }

    protected void defaultOperations() {
        this.setLocationRelativeTo(null);
        this.setResizable(false);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setVisible(true);
    }

    /**
     * Aggiorna il log della GUI.
     *
     * @param string messaggio da visualizzare
     */
    public void updateLogPanel(String string) {
        this.logPanel.append(string);
    }

    /**
     * Aggiorna il pannello del giocatore.
     *
     * @param numberOfTerritories numero di territori del player reale
     * @param currentPlayer       giocatore corrente
     * @param currentStage        fase corrente di gioco
     * @see fisk.PlayerPanel#updateLabels
     */
    public void updatePlayerPanel(Integer numberOfTerritories, String currentPlayer, String currentStage) {
        this.playerPanel.updateLabels(numberOfTerritories, currentPlayer, currentStage);
    }

    /**
     * Aggiorna le label dei territori passati a parametro.
     *
     * @param territories territori di cui aggiornare il numero di armate
     * @see fisk.BoardPanel#updateLabels
     */
    public void updateLabelsTerritoriesButtons(List<Territory> territories) {
        this.boardPanel.updateLabels(territories);
    }

    /**
     * Rende cliccabili i territori passsati a parametro.
     *
     * @param territories territori cliccabili
     * @see fisk.BoardPanel#setClickableTerritories
     */
    public void setClickableTerritories(List<Territory> territories) {
        this.boardPanel.setClickableTerritories(territories);
    }

    /**
     * Aggiorna i colori dei territori passati a parametro.
     *
     * @param territories territori di cui aggiornare il numero di armate
     * @see fisk.BoardPanel#updateColors
     */
    public void updateColorsTerritoriesButtons(List<Territory> territories) {
        this.boardPanel.updateColors(territories);
    }

    /**
     * Abilita il bottone delle carte del giocatore.
     *
     * @see fisk.PlayerPanel#enableCardsButton
     */
    public void enableCardsButton() {
        this.playerPanel.enableCardsButton();
    }

    /**
     * Abilita il bottone di fine fase del turno corrente.
     *
     * @see fisk.PlayerPanel#enableCardsButton
     */
    public void enableEndStageButton() {
        this.playerPanel.enableEndStageButton();
    }

    /**
     * Disabilita il bottone delle carte del giocatore.
     *
     * @see fisk.PlayerPanel#disableCardsButton
     */
    public void disableCardsButton() {
        this.playerPanel.disableCardsButton();
    }

    /**
     * Disabilita il bottone di fine fase del turno corrente.
     *
     * @see fisk.PlayerPanel#disableCardsButton
     */
    public void disableEndStageButton() {
        this.playerPanel.disableEndStageButton();
    }

    /**
     * Mostra un messaggio a GUI.
     *
     * @param message messaggio da visualizzare
     */
    public void showMessage(String message) {
        JOptionPane.showMessageDialog(this, message);
    }

}

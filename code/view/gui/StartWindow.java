package risk;

import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

/**
 * Classe che implementa l'interfaccia grafica per la scelta del nome del
 * giocatore reale, per la scelta del numero di giocatori e per la scelta dello
 * stile di gioco dei giocatori virtuali.
 */
public class StartWindow extends JFrame {

    public static final RiskStrategy[] ALL_POSSIBLE_STRATEGIES = StartWindow.initStrategies();
    protected final Integer width = 350;
    protected final Integer height = 220;
    protected final JTextField nameTextField;
    protected final JComboBox<RiskColor> playerColorList;
    protected final JComboBox<Integer> numberOfVirtualPlayersList;
    protected final Map<JLabel, JComboBox<RiskStrategy>> virtualPlayers;
    protected final JButton startButton;
    protected final RiskFacade facade;

    public StartWindow(RiskFacade facade) {
        this.setTitle("Let's play risk!");
        this.facade = facade;
        JLabel playerNameLabel = new JLabel("Choice your name:");
        this.nameTextField = new JTextField(10);
        JLabel playerColorLabel = new JLabel("Choice your color:");
        this.playerColorList = new JComboBox<>(RiskColor.values());
        Integer[] numberOfPlayers = new Integer[4];
        for (int i = 0; i < 4; i++) {
            numberOfPlayers[i] = i + 2;
        }
        JLabel playersListLabel = new JLabel("How many virtual players?");
        this.numberOfVirtualPlayersList = new JComboBox<>(numberOfPlayers);
        this.startButton = new JButton("Start");
        this.setLayout(new FlowLayout(FlowLayout.CENTER, 20, 20));
        this.add(playerNameLabel);
        this.add(this.nameTextField);
        this.add(playerColorLabel);
        this.add(this.playerColorList);
        this.add(playersListLabel);
        this.add(this.numberOfVirtualPlayersList);
        this.add(this.startButton);
        this.startButton.setEnabled(false);
        this.addListeners();
        this.virtualPlayers = new TreeMap<>((JLabel label1, JLabel label2) -> label1.getText().compareToIgnoreCase(label2.getText()));
        this.updateFrame((Integer) this.numberOfVirtualPlayersList.getSelectedItem());
    }

    protected void defaultOperations() {
        this.setLocationRelativeTo(null);
        this.setResizable(false);
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        this.setVisible(true);
    }

    protected void addListeners() {
        this.nameTextField.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent de) {
                if (nameTextField.getText().length() == 0) {
                    startButton.setEnabled(false);
                } else {
                    startButton.setEnabled(true);
                }
            }

            @Override
            public void removeUpdate(DocumentEvent de) {
                if (nameTextField.getText().length() == 0) {
                    startButton.setEnabled(false);
                } else {
                    startButton.setEnabled(true);
                }
            }

            @Override
            public void changedUpdate(DocumentEvent de) {
            }
        });
        this.startButton.addActionListener((ActionEvent ae) -> {
            List<RiskStrategy> virtualPlayersStrategies = new ArrayList<>();
            virtualPlayers.keySet().forEach((label) -> {
                virtualPlayersStrategies.add((RiskStrategy) virtualPlayers.get(label).getSelectedItem());
            });
            dispose();
            facade.prepareGame(nameTextField.getText(), (RiskColor) playerColorList.getSelectedItem(), virtualPlayersStrategies);
        });
        this.numberOfVirtualPlayersList.addActionListener((ActionEvent ae) -> {
            updateFrame((Integer) numberOfVirtualPlayersList.getSelectedItem());
        });
    }

    protected void updateFrame(Integer numberOfVirtualPlayers) {
        this.remove(this.startButton);
        this.virtualPlayers.keySet().stream().map((label) -> {
            this.remove(label);
            return label;
        }).forEachOrdered((label) -> {
            this.remove(this.virtualPlayers.get(label));
        });
        this.virtualPlayers.clear();
        for (int i = 0; i < numberOfVirtualPlayers; i++) {
            JLabel virtualPlayerLabel = new JLabel((i + 1) + "° virtual player");
            JComboBox<RiskStrategy> virtualPlayerStrategiesList = this.getRiskStrategyComboBox();
            this.virtualPlayers.put(virtualPlayerLabel, virtualPlayerStrategiesList);
        }
        this.virtualPlayers.keySet().stream().map((label) -> {
            this.add(label);
            return label;
        }).forEachOrdered((label) -> {
            this.add(this.virtualPlayers.get(label));
        });
        this.add(this.startButton);
        this.setSize(this.width, this.height + numberOfVirtualPlayers * 45);
        this.defaultOperations();
    }

    protected JComboBox<RiskStrategy> getRiskStrategyComboBox() {
        JComboBox<RiskStrategy> temp = new JComboBox<>(StartWindow.ALL_POSSIBLE_STRATEGIES);
        temp.setSelectedIndex(1);
        return temp;
    }

    protected static RiskStrategy[] initStrategies() {
        RiskStrategy[] temp = new RiskStrategy[3];
        temp[0] = new AggressiveRiskStrategy();
        temp[1] = new BalancedRiskStrategy();
        temp[2] = new ConservativeRiskStrategy();
        return temp;
    }

}

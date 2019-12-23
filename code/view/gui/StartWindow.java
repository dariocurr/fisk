/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package risk;

import java.awt.FlowLayout;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.event.*;
import java.util.*;

/**
 *
 * @author dario
 */
public class StartWindow extends JFrame {
    
    private static final Integer WIDTH = 300;
    private static final Integer HEIGHT = 220;
    private final JTextField nameTextField;
    private final JComboBox<RiskColor> playerColorList;
    private final JComboBox<Integer> numberOfVirtualPlayersList;
    private final Map<JLabel, JComboBox<RiskStrategy>> virtualPlayers;
    private final JButton startButton;
    private final Facade facade;

    public StartWindow(Facade facade) {
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
        JLabel playersListLabel = new JLabel("How many virtual player?");
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
        this.defaultOperations();
        this.addListeners();
        this.virtualPlayers = new TreeMap<>(new Comparator<JLabel>() {
            @Override
            public int compare(JLabel label1, JLabel label2) {
                return label1.getText().compareToIgnoreCase(label2.getText());
            }
        });
        this.updateFrame((Integer)this.numberOfVirtualPlayersList.getSelectedItem());
    }
    
    private void defaultOperations() {
        this.setLocationRelativeTo(null);
        this.setResizable(false);
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        this.setVisible(true);
    }
    
    private void addListeners() {
        this.nameTextField.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent de) {
                if(nameTextField.getText().length() == 0) {
                    startButton.setEnabled(false);
                } else {
                    startButton.setEnabled(true);
                }
            }
            @Override
            public void removeUpdate(DocumentEvent de) {
                if(nameTextField.getText().length() == 0) {
                    startButton.setEnabled(false);
                } else {
                    startButton.setEnabled(true);
                }
            }
            @Override
            public void changedUpdate(DocumentEvent de) {}
        });
        this.startButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                facade.setHumanPlayer(new Player(nameTextField.getText(), (RiskColor) playerColorList.getSelectedItem()));
                List<RiskStrategy> virtualPlayersStrategies = new ArrayList<>();
                for(JLabel label: virtualPlayers.keySet()) {
                    virtualPlayersStrategies.add((RiskStrategy) virtualPlayers.get(label).getSelectedItem());
                }
                facade.setVirtualPlayersStrategies(virtualPlayersStrategies);
                facade.startMatch();
                dispose();
            }
        });
        this.numberOfVirtualPlayersList.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                updateFrame((Integer)numberOfVirtualPlayersList.getSelectedItem());
            }
        });
    }
    
    private void updateFrame(Integer numberOfVirtualPlayers) {
        this.remove(this.startButton);
        for(JLabel label: this.virtualPlayers.keySet()) {
            this.remove(label);
            this.remove(this.virtualPlayers.get(label));
        }
        this.virtualPlayers.clear();
        for(int i = 0; i < numberOfVirtualPlayers; i++) {
            JLabel virtualPlayerLabel = new JLabel((i + 1) + "° virtual player");
            JComboBox<RiskStrategy> virtualPlayerStrategiesList = this.getRiskStrategyComboBox();
            this.virtualPlayers.put(virtualPlayerLabel, virtualPlayerStrategiesList);
        }
        for(JLabel label: this.virtualPlayers.keySet()) {
            this.add(label);
            this.add(this.virtualPlayers.get(label));
        }
        this.add(this.startButton);
        this.setSize(StartWindow.WIDTH, StartWindow.HEIGHT + numberOfVirtualPlayers * 45);
    }
    
    
    private JComboBox<RiskStrategy> getRiskStrategyComboBox() {
        JComboBox<RiskStrategy> temp = new JComboBox<>(AIPlayer.ALL_POSSIBLE_STRATEGIES);
        temp.setSelectedIndex(1);
        return temp;
    }
            
}

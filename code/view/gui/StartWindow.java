/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package risk;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

/**
 *
 * @author dario
 */
public class StartWindow extends JFrame {
    
    private static final Integer WIDTH = 300;
    private static final Integer HEIGHT = 220;
    private final JTextField nameTextField;
    private final JComboBox<RiskColor> playerColorList;
    private final JComboBox<Integer> numberOfPlayersList;
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
        this.numberOfPlayersList = new JComboBox<>(numberOfPlayers);
        this.startButton = new JButton("Start");
        this.setLayout(new FlowLayout(FlowLayout.CENTER, 20, 20));
        this.add(playerNameLabel);
        this.add(this.nameTextField);
        this.add(playerColorLabel);
        this.add(this.playerColorList);
        this.add(playersListLabel);
        this.add(this.numberOfPlayersList);
        this.add(this.startButton);
        this.startButton.setEnabled(false);
        this.setSize(StartWindow.WIDTH, StartWindow.HEIGHT);
        this.defaultOperations();
        this.addListeners();
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
                facade.setNumberOfVirtualPlayer((Integer) numberOfPlayersList.getSelectedItem());
                facade.startMatch();
                dispose();
            }
        });
    }
    
}

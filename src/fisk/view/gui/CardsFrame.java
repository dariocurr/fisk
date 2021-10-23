package fisk.view.gui;

import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.SwingConstants;

import fisk.card.SymbolCard.ConcreteTris;
import fisk.card.SymbolCard.SymbolCard;
import fisk.card.SymbolCard.Tris;
import fisk.facade.RiskFacade;

/**
 * Classe che implementa l'interfaccia grafica per la scelta e la
 * visualizzazione delle carte dei territori possedute dal giocatore reale.
 */
public class CardsFrame extends JFrame {

    protected final Integer width = 400;
    protected final Map<JCheckBox, SymbolCard> checkBoxes;
    protected final JButton changeTrisButton;
    protected final JLabel status;
    protected final RiskFacade facade;
    protected Tris currentTris;

    public CardsFrame(RiskFacade facade) {
        this.setTitle("Your cards");
        this.facade = facade;
        this.checkBoxes = new HashMap<>();
        this.setLayout(new FlowLayout(FlowLayout.CENTER, 200, 30));
        for (SymbolCard symbolCard : this.facade.getHumanPlayerCards()) {
            JCheckBox cb = new JCheckBox(symbolCard.toString());
            this.checkBoxes.put(cb, symbolCard);
            this.add(cb);
        }
        this.status = new JLabel("You must select 3 cards", SwingConstants.CENTER);
        this.status.setForeground(Color.RED);
        this.add(this.status);
        this.changeTrisButton = new JButton("Exchange tris");
        this.changeTrisButton.setEnabled(false);
        this.add(this.changeTrisButton);
        this.addListeners();
        this.setSize(this.width, 150 + this.checkBoxes.size() * 55);
        this.defaultOperations();
    }

    protected void defaultOperations() {
        this.setLocationRelativeTo(null);
        this.setResizable(false);
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        this.setVisible(true);
    }

    protected void addListeners() {
        this.checkBoxes.keySet().forEach((checkBox) -> {
            checkBox.addActionListener((ActionEvent ae) -> {
                int counter = 0;
                currentTris = null;
                counter = checkBoxes.keySet().stream().filter((cb) -> (cb.isSelected())).map((_item) -> 1)
                        .reduce(counter, Integer::sum);
                if (counter == 3) {
                    List<SymbolCard> temp = new ArrayList<>();
                    checkBoxes.keySet().stream().filter((cb) -> (cb.isSelected()))
                            .forEachOrdered((cb) -> temp.add(checkBoxes.get(cb)));
                    Tris tris = new ConcreteTris(temp.get(0), temp.get(1), temp.get(2));
                    if (facade.checkTris(tris)) {
                        changeTrisButton.setEnabled(true);
                        status.setText("Press to change Tris!");
                        status.setForeground(Color.GREEN);
                        currentTris = tris;
                    }
                } else {
                    changeTrisButton.setEnabled(false);
                    status.setText("You must select 3 cards");
                    status.setForeground(Color.RED);
                }
            });
        });
        this.changeTrisButton.addActionListener((ActionEvent ae) -> {
            if (currentTris != null) {
                facade.exchangeTris(currentTris);
                dispose();
            }
        });
    }
}

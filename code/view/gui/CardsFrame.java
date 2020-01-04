package risk;

import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.*;
import javax.swing.*;

public class CardsFrame extends JFrame {

    protected static final Integer WIDTH = 400;
    protected final Map<JCheckBox, SymbolCard> checkBoxes;
    protected final JButton changeTrisButton;
    protected final JLabel status;
    protected final Facade facade;
    protected Tris currentTris;

    public CardsFrame(Facade facade) {
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
        this.changeTrisButton = new JButton("Change tris");
        this.changeTrisButton.setEnabled(false);
        this.add(this.changeTrisButton);
        this.addListeners();
        this.setSize(CardsFrame.WIDTH, 150 + this.checkBoxes.size() * 55);
        this.defaultOperations();
    }

    protected void defaultOperations() {
        this.setLocationRelativeTo(null);
        this.setResizable(false);
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        this.setVisible(true);
    }

    protected void addListeners() {
        for (JCheckBox checkBox : this.checkBoxes.keySet()) {
            checkBox.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent ae) {
                    int counter = 0;
                    currentTris = null;
                    for (JCheckBox cb : checkBoxes.keySet()) {
                        if (cb.isSelected()) {
                            counter++;
                        }
                    }
                    if (counter == 3) {
                        ArrayList<SymbolCard> temp = new ArrayList<>();
                        for (JCheckBox cb : checkBoxes.keySet()) {
                            if (cb.isSelected()) {
                                temp.add(checkBoxes.get(cb));
                            }
                        }
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
                }
            });
        }
        this.changeTrisButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                if (currentTris != null) {
                    facade.exchangeTris(currentTris);
                    dispose();
                }
            }
        });
    }
}

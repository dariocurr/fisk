package risk;

import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.util.Dictionary;
import java.util.Hashtable;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JSlider;
import javax.swing.SwingConstants;

/**
 * Classe che implementa l'interfaccia grafica per la scelta del numero di
 * armate da spostare in un territorio appena conquistato.
 */
public class NumberOfTanksFrame extends JDialog {

    protected final Integer height = 220;
    protected final JButton moveButton;
    protected final JSlider numberOfTanksSlider;
    protected final RiskFacade facade;

    public NumberOfTanksFrame(RiskFacade facade, String territory, Integer min, Integer max) {
        super();
        this.setModal(true);
        this.setTitle("Moving");
        this.facade = facade;
        JLabel request = new JLabel("How many tanks do you want to move to: " + territory, SwingConstants.CENTER);
        this.numberOfTanksSlider = new JSlider(JSlider.HORIZONTAL, min, max, (min + max) / 2);
        Dictionary<Integer, JLabel> labels = new Hashtable<>();
        for (int i = 1; i <= max; i++) {
            labels.put(i, new JLabel("" + i));
        }
        Integer componentMaxWidth = Math.max(request.getText().length() * 8, max * 20);
        this.setLayout(new FlowLayout(FlowLayout.CENTER, componentMaxWidth, 30));
        this.numberOfTanksSlider.setLabelTable(labels);
        this.numberOfTanksSlider.setPaintLabels(true);
        this.numberOfTanksSlider.setPreferredSize(new Dimension(max * 20, 30));
        this.moveButton = new JButton("Move");
        this.add(request);
        this.add(this.numberOfTanksSlider);
        this.add(this.moveButton);
        this.setSize(componentMaxWidth + 50, this.height);
        this.addButtonListener();
        this.defaultOperations();
    }

    protected void defaultOperations() {
        this.setLocationRelativeTo(null);
        this.setResizable(false);
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        this.setVisible(true);
    }

    protected void addButtonListener() {
        this.moveButton.addActionListener((ActionEvent ae) -> {
            facade.setNumberOfTanksToMove(numberOfTanksSlider.getValue());
            dispose();
        });
    }

}

package fisk.view.gui;

import java.awt.Dimension;
import java.awt.Font;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

import fisk.facade.RiskFacade;
import fisk.observer.Observer;

/**
 * Classe che implementa il pannello di log.
 */
public class LogPanel extends JScrollPane implements Observer {

    protected static final JTextArea TEXT_AREA = new JTextArea();
    protected final Integer width;
    protected final Integer height;

    public LogPanel(RiskFacade facade, int width, int height) {
        super(LogPanel.TEXT_AREA, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        facade.addObserver(this);
        LogPanel.TEXT_AREA.setEditable(false);
        this.width = width;
        this.height = height;
        this.setPreferredSize(new Dimension(this.width, this.height));
        LogPanel.TEXT_AREA.setFont(new Font("Calibri", Font.BOLD, 14));
    }

    /**
     * Appenda una stringa nel pannelo di log.
     *
     * @param string messaggio da scrivere
     */
    public void append(String string) {
        LogPanel.TEXT_AREA.append(string + "\n");
    }

    /**
     * Metodo per implementare l'interfaccia Observer.
     *
     * @param update oggetto da notificare.
     */
    @Override
    public void update(Object update) {
        if (update instanceof String) {
            this.append((String) update);
        }
    }

}

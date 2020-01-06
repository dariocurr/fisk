package risk;

import java.awt.*;
import javax.swing.*;

/**
    Classe che implementa il pannello di log.
*/

public class LogPanel extends JScrollPane implements Observer {

    protected final Integer WIDTH;
    protected final Integer HEIGHT;
    protected static final JTextArea TEXT_AREA = new JTextArea();

    public LogPanel(RiskFacade facade, int width, int height) {
        super(LogPanel.TEXT_AREA, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        facade.addObserver(this);
        LogPanel.TEXT_AREA.setEditable(false);
        this.WIDTH = width;
        this.HEIGHT = height;
        this.setPreferredSize(new Dimension(this.WIDTH, this.HEIGHT));
    }

    /**
        Appenda una stringa nel pannelo di log.
        @param string messaggio da scrivere
    */
    public void append(String string) {
        LogPanel.TEXT_AREA.append(string + "\n");
    }

    /**
        Metodo per implementare l'interfaccia Observer.
        @param update oggetto da notificare.
    */
    @Override
    public void update(Object update) {
        if (update instanceof String) {
            this.append((String) update);
        }
    }

}

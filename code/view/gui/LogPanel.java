package risk;

import java.awt.Dimension;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

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
    }

    public void append(String string) {
        LogPanel.TEXT_AREA.append(string + "\n");
    }

    @Override
    public void update(Object update) {
        if (update instanceof String) {
            this.append((String) update);
        }
    }

}

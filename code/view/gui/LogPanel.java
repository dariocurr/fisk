package risk;

import java.awt.*;
import javax.swing.*;

public class LogPanel extends JScrollPane {

    protected final Integer WIDTH;
    protected final Integer HEIGHT;
    protected static final JTextArea TEXT_AREA = new JTextArea();

    public LogPanel(int width, int height) {
        super(LogPanel.TEXT_AREA, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        LogPanel.TEXT_AREA.setEditable(false);
        this.WIDTH = width;
        this.HEIGHT = height;
        this.setPreferredSize(new Dimension(this.WIDTH, this.HEIGHT));
    }

    public void append(String string) {
        LogPanel.TEXT_AREA.append(string + "\n");
    }

}

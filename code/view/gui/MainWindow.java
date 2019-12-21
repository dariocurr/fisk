package risk;

import java.awt.*;
import javax.swing.*;

public class MainWindow extends JFrame {

    private static final Integer WIDTH = 1500;
    private static final Integer HEIGHT = 900;
    private final Facade facade;

    public MainWindow(Facade facade) {
        super();
        this.facade = facade;
        Container contentPane = this.getContentPane();
        contentPane.setLayout(new BorderLayout(0, 0));
        PlayerPanel playerPanel = new PlayerPanel(MainWindow.WIDTH / 5, MainWindow.HEIGHT, facade);
        contentPane.add(playerPanel, BorderLayout.WEST);
        JPanel centerJPanel = new JPanel();
        centerJPanel.setLayout(new BorderLayout(0, 0));
        centerJPanel.setPreferredSize(new Dimension(MainWindow.WIDTH - MainWindow.WIDTH / 5, MainWindow.HEIGHT));
        contentPane.add(centerJPanel, BorderLayout.CENTER);
        BoardPanel boardPanel = new BoardPanel(MainWindow.WIDTH - MainWindow.WIDTH / 5, MainWindow.HEIGHT - MainWindow.HEIGHT / 6, this.facade.getTerritories(), facade);
        centerJPanel.add(boardPanel, BorderLayout.NORTH);
        LogPanel logPanel = new LogPanel(MainWindow.WIDTH - MainWindow.WIDTH / 5, MainWindow.HEIGHT / 6);
        centerJPanel.add(logPanel, BorderLayout.CENTER);
        this.setSize(MainWindow.WIDTH, MainWindow.HEIGHT);
        this.defaultOperations();
    }

    private void defaultOperations() {
        this.setLocationRelativeTo(null);
        this.setResizable(false);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setVisible(true);
    }

}

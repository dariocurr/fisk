package risk;

import java.awt.*;
import javax.swing.*;

public class MainWindow extends JFrame {

    private static final Integer WIDTH = 1500;
    private static final Integer HEIGHT = 900;
    private final Game game;

    public MainWindow(Game game) {
        super();
        this.game = game;
        Container contentPane = this.getContentPane();
        contentPane.setLayout(new BorderLayout(0, 0));
        contentPane.add(new PlayerPanel(MainWindow.WIDTH / 5, MainWindow.HEIGHT), BorderLayout.WEST);
        JPanel centerJPanel = new JPanel();
        centerJPanel.setLayout(new BorderLayout(0, 0));
        centerJPanel.setPreferredSize(new Dimension(MainWindow.WIDTH - MainWindow.WIDTH / 5, MainWindow.HEIGHT));
        contentPane.add(centerJPanel, BorderLayout.CENTER);
        centerJPanel.add(new BoardPanel(MainWindow.WIDTH - MainWindow.WIDTH / 5, MainWindow.HEIGHT - MainWindow.HEIGHT / 6, this.game.getTerritories()), BorderLayout.NORTH);
        centerJPanel.add(new LogPanel(MainWindow.WIDTH - MainWindow.WIDTH / 5, MainWindow.HEIGHT / 6), BorderLayout.CENTER);
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

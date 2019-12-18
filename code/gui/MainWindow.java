package risk;

import java.awt.*;
import javax.swing.*;


public class MainWindow extends JFrame {
    
    private final Integer WIDTH = 1500;
    private final Integer HEIGHT = 900;
    private final Game game;

    public MainWindow (Game game) {
        super();
        this.game = game;
        Container contentPane = this.getContentPane();
        contentPane.setLayout(new BorderLayout(0, 0));
        contentPane.add(new PlayerPanel(this.WIDTH / 5, this.HEIGHT), BorderLayout.WEST);
        JPanel centerJPanel = new JPanel();
        centerJPanel.setLayout(new BorderLayout(0, 0));
        centerJPanel.setPreferredSize(new Dimension(this.WIDTH - this.WIDTH / 5, this.HEIGHT));
        contentPane.add(centerJPanel, BorderLayout.CENTER);
        centerJPanel.add(new BoardPanel(this.WIDTH - this.WIDTH / 5, this.HEIGHT - this.HEIGHT / 6, this.game.getTERRITORIES()), BorderLayout.NORTH);
        centerJPanel.add(new LogPanel(this.WIDTH - this.WIDTH / 5, this.HEIGHT / 6), BorderLayout.CENTER);
        this.setSize(this.WIDTH, this.HEIGHT);
        this.defaultOperations();
    }

    private void defaultOperations (){
        this.setLocationRelativeTo(null);
        this.setResizable(false);
        this.setDefaultCloseOperation( JFrame.EXIT_ON_CLOSE );
        this.setVisible(true); 
    }

}	
package risk;

import java.util.*;
import java.io.*;

public class Main2 {

    public static void main(String[] args) {
        Player p1 = new Player("Riccardo", RiskColor.RED);
        Player p2 = new Player("Dario", RiskColor.BLUE);
        Player p3 = new Player("Giovanni", RiskColor.GREEN);
        ArrayList<Player> players = new ArrayList<>();
        players.add(p1);
        players.add(p2);
        players.add(p3);
        Game game = new ConcreteGameBuilder().buildGame();
        Mediator mediator = new Mediator(players, game);
        mediator.play();
        for (Player p : players) {
            System.out.println(p + "\n\n\n\n\n\n\n");
        }
    }

}

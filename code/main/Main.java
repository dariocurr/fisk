/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package risk;

import java.util.*;

/**
 *
 * @author dario
 */
public interface Main {

    public static void main(String... args) {
        ArrayList<Player> players = new ArrayList<>();
        Player p1 = new Player("Dario", RiskColor.BLUE);
        Player p2 = new Player("Eleonora", RiskColor.RED);
        players.add(p1);
        players.add(p2);
        Game game = new ConcreteGameBuilder().buildGame();
        Mediator mediator = new Mediator(players, game);
        Facade facade = new ConcreteFacade(mediator, p1);
        new MainWindow(facade);
        //new RollFrame(new ConcreteFacade(null));
    }

}

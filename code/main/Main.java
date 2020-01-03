package risk;

public interface Main {

    public static void main(String... args) {
        //new FacadeGUI().askMatch();
        //new AIPlayer("Dario", RiskColor.BLUE, new BalancedRiskStrategy()).changeTris();
        //new TankPool(45, RiskColor.YELLOW);
        /*
        Player p1 = new Player("riccardo", RiskColor.RED);
        Player p2 = new Player("dario", RiskColor.GREEN);
        Player p3 = new Player("ciccio", RiskColor.YELLOW);
        List<Player> players = new ArrayList<>();
        players.add(p1);
        players.add(p2);
        players.add(p3);
        Mediator mediator = new Mediator();
        mediator.setPlayers(players);
        Facade facade = new FacadeGUI();
        mediator.setFacade(facade);
        mediator.prepareGame();
        facade.setPlayer(p1);
        facade.setMediator(mediator);
        RiskGUI riskGui = new RiskGUI(facade);
        facade.setGui(riskGui);
        facade.initPlayerData();
        facade.showGui();
        */
        Mediator mediator = new Mediator();
        Facade facade = new FacadeGUI();
        mediator.setFacade(facade);
        facade.setMediator(mediator);
        new StartWindow(facade);
    }
}

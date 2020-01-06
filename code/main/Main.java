package risk;

public interface Main {

    public static void main(String... args) {
        RiskMediator mediator = new ConcreteRiskMediator();
        RiskFacade facade = new GUIRiskFacade();
        mediator.setFacade(facade);
        facade.setMediator(mediator);
        new StartWindow(facade);
    }
}

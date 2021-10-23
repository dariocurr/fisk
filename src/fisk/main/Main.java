package fisk.main;

public interface Main {

    public static void main(String... args) {
        Startable riskGUI = new StartRiskGUI();
        riskGUI.start();
    }
}

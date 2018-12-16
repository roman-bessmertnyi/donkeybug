package donkeybug.service.car;

public interface ConsoleManager {
    void startApp();

    String sendCommand(String command);

    void closeApp();
}

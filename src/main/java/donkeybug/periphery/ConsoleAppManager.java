package donkeybug.periphery;

public interface ConsoleAppManager {
    void startApp();

    String sendCommand(String command);

    void closeApp();
}

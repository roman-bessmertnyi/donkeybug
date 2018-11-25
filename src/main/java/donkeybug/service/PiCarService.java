package donkeybug.service;

import donkeybug.periphery.ConsoleAppManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

@Service("piCarService")
public class PiCarService implements CarService {
    @Autowired
    @Qualifier("carAppManager")
    private ConsoleAppManager consoleAppManager;

    @PostConstruct
    public void initIt() throws Exception {
        consoleAppManager.startApp();
    }

    @Override
    public void goForward() {
        consoleAppManager.sendCommand("w");
    }

    @Override
    public void goBackward() {
        consoleAppManager.sendCommand("s");
    }

    @Override
    public void turnLeft() {
        consoleAppManager.sendCommand("a");
    }

    @Override
    public void turnRight() {
        consoleAppManager.sendCommand("d");
    }

    @Override
    public void stop() {
        consoleAppManager.sendCommand("q");
    }

    @PreDestroy
    public void cleanUp() throws Exception {
        consoleAppManager.closeApp();
    }
}

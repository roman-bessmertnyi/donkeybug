package donkeybug.service;

import donkeybug.periphery.ConsoleAppManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import java.util.Observable;

@Service
public class PiCarService extends Observable implements CarService {
    @Autowired
    private ConsoleAppManager consoleAppManager;

    private String command;
    private boolean receivedCommand;
    private CommandSender commandSender;

    @PostConstruct
    public void initIt() throws Exception {
        consoleAppManager.startApp();

        command = "q";
        receivedCommand = false;
        consoleAppManager.sendCommand("q");

        commandSender = new CommandSender();
        Thread CommandThread = new Thread(commandSender);
        CommandThread.start();
    }

    @Override
    public synchronized void goForward() {
        receiveCommand("w");
    }

    @Override
    public synchronized void goBackward() {
        receiveCommand("s");
    }

    @Override
    public synchronized void turnLeft() {
        receiveCommand("a");
    }

    @Override
    public synchronized void turnRight() {
        receiveCommand("d");
    }

    @Override
    public synchronized void stop() {
        receiveCommand("q");
    }

    @PreDestroy
    public void cleanUp() throws Exception {
        consoleAppManager.closeApp();
    }

    private void receiveCommand(String command) {
        this.command = command;
        receivedCommand = true;
        if (commandSender != null) {
            synchronized (commandSender) {
                commandSender.notify();
            }
        }
    }

    private class CommandSender implements Runnable {
        String previousCommand;

        @Override
        public synchronized void run() {
            while (true) {
                previousCommand = command;
                receivedCommand = false;

                try {
                    this.wait(200);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                if (!receivedCommand) {
                    command = "q";
                    if (!previousCommand.equals(command)) {
                        consoleAppManager.sendCommand(command);
                    }
                    previousCommand = command;
                } else if (!previousCommand.equals(command)) {
                    consoleAppManager.sendCommand(command);
                }
            }
        }
    }
}
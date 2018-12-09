package donkeybug.service;

import donkeybug.periphery.ConsoleAppManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import java.util.Observable;

@Service("piCarService")
public class PiCarService extends Observable implements CarService {
    @Autowired
    @Qualifier("carAppManager")
    private ConsoleAppManager consoleAppManager;

    String command;
    boolean recievedCommand;
    CommandSender commandSender;

    @PostConstruct
    public void initIt() throws Exception {
        consoleAppManager.startApp();

        command = "q";
        recievedCommand = false;
        consoleAppManager.sendCommand("q");

        commandSender = new CommandSender();
        Thread CommandThread = new Thread(commandSender);
        CommandThread.start();
    }

    @Override
    public synchronized void goForward() {
        recieveCommand("w");
    }

    @Override
    public synchronized void goBackward() {
        recieveCommand("s");
    }

    @Override
    public synchronized void turnLeft() {
        recieveCommand("a");
    }

    @Override
    public synchronized void turnRight() {
        recieveCommand("d");
    }

    @Override
    public synchronized void stop() {
        recieveCommand("q");
    }

    @PreDestroy
    public void cleanUp() throws Exception {
        consoleAppManager.closeApp();
    }

    private void recieveCommand(String command) {
        this.command = command;
        recievedCommand = true;
        if(commandSender!=null) {
            synchronized(commandSender) {
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
                recievedCommand = false;

                //consoleAppManager.sendCommand("q");

                try {
                    this.wait(200);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                if (!recievedCommand) {
                    command = "q";
                    if (!previousCommand.equals(command)) {
                        consoleAppManager.sendCommand(command);
                    }
                    previousCommand = command;
                }
                else if (!previousCommand.equals(command)) {
                    consoleAppManager.sendCommand(command);
                }
            }
        }
    }
}

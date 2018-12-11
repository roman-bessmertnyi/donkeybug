package donkeybug.periphery;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.*;
import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;

@Service
public class CarAppManager implements ConsoleAppManager {
    @Value("${OS}")
    private String OS;

    private CyclicBarrier responseBarrier;

    private Process process;

    private PrintWriter writer;

    private StreamGobbler sgInput;
    private StreamGobbler sgError;

    private String response;

    @Override
    public void startApp() {
        try {
            ProcessBuilder builder;
            switch (OS) {
                case "Linux":
                    builder = new ProcessBuilder("sudo", "periphery/car");
                    break;
                case "Windows":
                    builder = new ProcessBuilder("periphery/car.exe");
                    break;
                default:
                    builder = new ProcessBuilder("periphery/car.exe");
            }

            Process process = builder.start();

            // create the stream gobblers, one for the input stream and one for the
            // error stream. these gobblers will consume these streams.
            sgInput = new StreamGobbler(
                    process.getInputStream(), "input");
            sgError = new StreamGobbler(
                    process.getErrorStream(), "error");

            responseBarrier = new CyclicBarrier(2);

            Thread inputThread = new Thread(sgInput);
            inputThread.start();
            Thread errorThread = new Thread(sgError);
            errorThread.start();

            writer = new PrintWriter(process.getOutputStream());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public String sendCommand(String command) {
        responseBarrier = new CyclicBarrier(2);

        // sends the command to the process
        // simulating an user input (note the \n)
        writer.write(command);
        writer.write("\n");
        writer.flush();

        try {
            responseBarrier.await();
        } catch (InterruptedException ex) {
            System.out.println(ex.toString());
            return ex.toString();
        } catch (BrokenBarrierException ex) {
            System.out.println(ex.toString());
            return ex.toString();
        }
        return response;
    }

    @Override
    public void closeApp() {
        sendCommand("end");
        process.destroy();
    }

    private class StreamGobbler implements Runnable {

        private InputStream is;
        private String type;
        private FileWriter fw;

        public StreamGobbler(InputStream is, String type) {
            this.is = is;
            this.type = type;
        }

        public StreamGobbler(InputStream is, String type, File file)
                throws IOException {
            this.is = is;
            this.type = type;
            this.fw = new FileWriter(file);
        }

        @Override
        public void run() {
            try {
                InputStreamReader isr = new InputStreamReader(is);
                BufferedReader br = new BufferedReader(isr);
                String line = null;
                while ((line = br.readLine()) != null) {
                    if (fw != null) {
                        fw.write(line + "\n");
                    } else {
                        response = type + ">" + line;
                        System.out.println(response);
                    }

                    try {
                        responseBarrier.await();
                    } catch (InterruptedException ex) {
                        return;
                    } catch (BrokenBarrierException ex) {
                        return;
                    }
                }
                if (fw != null) {
                    fw.close();
                }
            } catch (IOException ioe) {
                ioe.printStackTrace();
            }
        }
    }
}
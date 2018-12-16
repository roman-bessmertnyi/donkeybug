package donkeybug.service;

import com.github.sarxos.webcam.Webcam;
import com.github.sarxos.webcam.ds.v4l4j.V4l4jDriver;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import java.awt.*;
import java.awt.image.BufferedImage;

@Service
public class V4l4jWebcamService implements WebcamService {
    @Value("${OS}")
    private String OS;

    private Webcam webcam;

    private BufferedImage image;

    @PostConstruct
    public void initIt() throws Exception {
        switch (OS) {
            case "Linux":
                Webcam.setDriver(new V4l4jDriver()); // this is important (not really)
                break;
        }
        // get default webcam and open it
        webcam = Webcam.getDefault();
        if (webcam != null) {
            webcam.setViewSize(new Dimension(320, 240));
            webcam.open();
        }
        Thread viewThread = new Thread(this::view);
        viewThread.start();
    }

    @Override
    public BufferedImage GetPicture(){
        return image;
    }

    @PreDestroy
    public void cleanUp() throws Exception {
        webcam.close();
    }

    private void view() {
        while (true) {
            if (webcam != null) {
                // get image
                image = webcam.getImage();
            }
        }
    }
}

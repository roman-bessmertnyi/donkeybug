package donkeybug.controller;

import com.github.sarxos.webcam.Webcam;
import com.github.sarxos.webcam.ds.v4l4j.V4l4jDriver;
import donkeybug.model.WebcamDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Controller;

import javax.annotation.PostConstruct;
import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;

@EnableScheduling
@Controller
public class WebcamController {
    @Value("${OS}")
    private String OS;

    Webcam webcam;

    @Autowired
    private SimpMessagingTemplate template;

    @PostConstruct
    public void initIt() throws Exception {
        switch (OS){
            case "Linux":
                Webcam.setDriver(new V4l4jDriver()); // this is important
                break;
        }
        // get default webcam and open it
        webcam = Webcam.getDefault();
        if(webcam != null) {
            webcam.setViewSize(new Dimension(640, 480));
            webcam.open();
        }
    }

    @Scheduled(fixedRate = 80)
    public void sendImage()  throws Exception {
        if(webcam != null) {
            // get image
            BufferedImage image = webcam.getImage();

            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            ImageIO.write(image, "jpg", baos);
            byte[] byteArray = baos.toByteArray();

            this.template.convertAndSend("/topic/webcam", new WebcamDTO(byteArray));
        }
    }
}

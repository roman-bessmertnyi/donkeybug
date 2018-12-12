package donkeybug.controller;

import donkeybug.model.Odometry;
import donkeybug.model.WebcamDTO;
import donkeybug.service.webcam.VisualOdometryService;
import donkeybug.service.webcam.WebcamService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Controller;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;

@EnableScheduling
@Controller
public class WebcamController {
    @Autowired
    private WebcamService webcamService;
    @Autowired
    private VisualOdometryService visualOdometryService;

    @Autowired
    private SimpMessagingTemplate template;

    private boolean clientIsReady = false;

    @MessageMapping("/webcam")
    public void ready(String message) {
        if (message.equals("ready")) {
            clientIsReady = true;
        }
    }

    @Scheduled(fixedRate = 67)
    public void sendImage()  throws Exception {
        if (true)/*(clientIsReady)*/ {
            BufferedImage image = webcamService.GetPicture();

            if (image != null) {
                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                ImageIO.write(image, "jpg", baos);
                byte[] byteArray = baos.toByteArray();

                Odometry odometry = visualOdometryService.getOdometry(image);
                template.convertAndSend("/topic/odometry", odometry);
                template.convertAndSend("/topic/webcam", new WebcamDTO(byteArray));
            }
            clientIsReady = false;
        }
    }
}

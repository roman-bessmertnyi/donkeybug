package donkeybug.controller;

import com.github.sarxos.webcam.Webcam;
import donkeybug.model.WebcamDTO;
import org.springframework.beans.factory.annotation.Autowired;
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
    private SimpMessagingTemplate template;

    @Scheduled(fixedRate = 80)
    public void sendImage()  throws Exception {

        // get default webcam and open it
        Webcam webcam = Webcam.getDefault();
        webcam.open();

        // get image
        BufferedImage image = webcam.getImage();


        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ImageIO.write(image, "jpg", baos);
        byte[] byteArray = baos.toByteArray();

        this.template.convertAndSend("/topic/webcam", new WebcamDTO(byteArray));
    }
}

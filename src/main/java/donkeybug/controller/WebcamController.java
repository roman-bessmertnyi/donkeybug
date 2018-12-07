package donkeybug.controller;

import donkeybug.model.WebcamDTO;
import donkeybug.service.WebcamService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Controller;

@EnableScheduling
@Controller
public class WebcamController {
    @Autowired
    @Qualifier("v4l4jWebcamService")
    WebcamService webcamService;

    @Autowired
    private SimpMessagingTemplate template;

    boolean clientIsReady = false;

    @MessageMapping("/webcam/ready")
    public void ready(String message) {
        if (message == "ready") {
            clientIsReady = true;
        }
    }

    @Scheduled(fixedRate = 33)
    public void sendImage()  throws Exception {
        if (clientIsReady) {
            byte[] byteArray = webcamService.GetPicture();

            if (byteArray != null) {
                this.template.convertAndSend("/topic/webcam", new WebcamDTO(byteArray));
            }

            clientIsReady = false;
        }
    }
}

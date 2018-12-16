package donkeybug.controller;

import donkeybug.model.Odometry;
import donkeybug.model.WebcamDTO;
<<<<<<< HEAD
import donkeybug.service.WebcamService;
import org.apache.commons.compress.utils.IOUtils;
=======
import donkeybug.service.webcam.VisualOdometryService;
import donkeybug.service.webcam.WebcamService;
>>>>>>> odometry
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;

@EnableScheduling
@Controller
public class WebcamController {
<<<<<<< HEAD
	@Autowired
	private WebcamService webcamService;

	@Autowired
	private SimpMessagingTemplate template;

	private boolean clientIsReady = false;

	@GetMapping(value = "/webcam", produces = MediaType.IMAGE_JPEG_VALUE, consumes = "*/*")
	public @ResponseBody byte[] getFeed() {
		try {
			BufferedImage image = webcamService.GetPicture();

			ByteArrayOutputStream baos = new ByteArrayOutputStream();

            ImageIO.write(image, "jpeg", baos);
            byte[] byteArray = baos.toByteArray();

			return byteArray;
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}
	}

	@Scheduled(fixedRate = 200)
	public void sendImage() throws Exception {
		if (clientIsReady) {
			BufferedImage image = webcamService.GetPicture();

			if (image != null) {
				//template.convertAndSend("/topic/webcam", new WebcamDTO(byteArray));
			}
			clientIsReady = false;
		}
	}
=======
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

    @Scheduled(fixedDelay = 67)
    public void sendImage()  throws Exception {
        BufferedImage image = webcamService.GetPicture();
        if (image != null) {
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            ImageIO.write(image, "jpg", baos);
            byte[] byteArray = baos.toByteArray();

            Odometry odometry = visualOdometryService.getOdometry(image);
            template.convertAndSend("/topic/odometry", odometry);

            if (clientIsReady) {
                template.convertAndSend("/topic/webcam", new WebcamDTO(byteArray));
                clientIsReady = false;
            }
        }
    }
>>>>>>> odometry
}

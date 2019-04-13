package donkeybug.controller;

import donkeybug.service.webcam.WebcamService;
import donkeybug.service.webcam.WebcamViewer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.MediaType;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.PostConstruct;
import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Optional;

@EnableScheduling
@Controller
public class WebcamController {
	@Autowired
	private WebcamViewer webcamViewer;

    @Autowired
    private SimpMessagingTemplate template;

	@GetMapping(value = "/webcam", produces = MediaType.IMAGE_JPEG_VALUE)
	public @ResponseBody byte[] getFeed() {
		byte[] byteArray = webcamViewer.getRawView();
        return byteArray;
	}

	@Scheduled(fixedRate = 1000)
	public void sendFPS()  throws Exception {
		template.convertAndSend("/topic/fps", (int) Math.round(webcamViewer.getFPS()));
	}
}

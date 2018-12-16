package donkeybug.controller;

import donkeybug.service.webcam.WebcamService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.PostConstruct;
import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

@EnableScheduling
@Controller
public class WebcamController {
	@Autowired
	private WebcamService webcamService;
	private BufferedImage image;

    @Autowired
    private SimpMessagingTemplate template;

    private void view() {
        while (true) {
            image = webcamService.GetPicture();
        }
    }

    @PostConstruct
    public void initIt() throws Exception {
        Thread viewThread = new Thread(this::view);
        viewThread.start();
    }

	@GetMapping(value = "/webcam", produces = MediaType.IMAGE_JPEG_VALUE)
	public @ResponseBody byte[] getFeed() {
		try {
			ByteArrayOutputStream baos = new ByteArrayOutputStream();
			if (image != null) {
                ImageIO.write(image, "jpeg", baos);
                byte[] byteArray = baos.toByteArray();

                return byteArray;
            } else {
                System.out.println("image is null");
                return null;
            }
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}
	}
}

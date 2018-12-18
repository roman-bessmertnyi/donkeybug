package donkeybug.service.webcam;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Optional;

@Service
public class LoopWebcamViewer implements WebcamViewer {
	@Autowired
	private WebcamService webcamService;

	private Optional<BufferedImage> image;

	private void view() {
		while (true) {
			image = webcamService.getPicture();
		}
	}

	private byte[] toByte(BufferedImage bufferedImage) {
		try {
			ByteArrayOutputStream baos = new ByteArrayOutputStream();
			ImageIO.write(bufferedImage, "jpeg", baos);
			byte[] byteArray = baos.toByteArray();

			return byteArray;
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}
	}

	@PostConstruct
	public void initIt() throws Exception {
		Thread viewThread = new Thread(this::view);
		viewThread.start();
	}

	public byte[] getRawView() {
		return toByte(image.orElse(new BufferedImage(1,1, BufferedImage.TYPE_INT_RGB)));
	};
}

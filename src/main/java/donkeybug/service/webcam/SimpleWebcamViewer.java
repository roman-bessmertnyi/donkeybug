package donkeybug.service.webcam;

import com.github.sarxos.webcam.Webcam;
import com.github.sarxos.webcam.ds.v4l4j.V4l4jDriver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Optional;

@Service
public class SimpleWebcamViewer implements WebcamViewer {
	@Autowired
	WebcamService webcamService;

	@Value("${OS}")
	String OS;

	byte[] toByte(BufferedImage bufferedImage) {
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

	@Override
	public BufferedImage getImage() {
		Optional<Webcam> webcam = webcamService.getWebcam();
		BufferedImage result = webcam.map(Webcam::getImage)
				.orElse(new BufferedImage(1,1, BufferedImage.TYPE_INT_RGB));
		return result;
	}

	@Override
	public double getFPS() {
		Optional<Webcam> webcam = webcamService.getWebcam();
		double result = webcam.map(Webcam::getFPS)
				.orElse(0.0);
		return result;
	}

	@Override
	public byte[] getRawView() {
		return toByte(getImage());
	}
}

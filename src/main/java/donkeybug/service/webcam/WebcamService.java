package donkeybug.service.webcam;

import com.github.sarxos.webcam.Webcam;

import java.awt.image.BufferedImage;
import java.util.Optional;

public interface WebcamService {
    Optional<Webcam> getWebcam();
}

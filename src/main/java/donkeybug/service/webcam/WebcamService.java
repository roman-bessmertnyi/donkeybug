package donkeybug.service.webcam;

import java.awt.image.BufferedImage;
import java.util.Optional;

public interface WebcamService {
    Optional<BufferedImage> getPicture();
}

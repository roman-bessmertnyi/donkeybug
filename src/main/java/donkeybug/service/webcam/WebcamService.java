package donkeybug.service.webcam;

import java.awt.image.BufferedImage;
import java.io.IOException;

public interface WebcamService {
    BufferedImage GetPicture() throws IOException;
}

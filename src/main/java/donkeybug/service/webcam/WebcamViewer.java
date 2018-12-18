package donkeybug.service.webcam;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Optional;

public interface WebcamViewer {
	byte[] getRawView();
}

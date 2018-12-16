package donkeybug.service;

import java.awt.image.BufferedImage;
import java.io.IOException;

public interface WebcamService {
    BufferedImage GetPicture() throws IOException;
}

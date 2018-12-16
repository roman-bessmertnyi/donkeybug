package donkeybug.service.webcam;

import donkeybug.model.Odometry;

import java.awt.image.BufferedImage;

public interface VisualOdometryService {
	Odometry getOdometry(BufferedImage image);
}

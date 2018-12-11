package donkeybug.service.webcam;

import com.github.sarxos.webcam.Webcam;
import com.github.sarxos.webcam.ds.v4l4j.V4l4jDriver;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

@Service
public class WebcamCaptureWebcamService implements WebcamService {
    @Value("${OS}")
    private String OS;

    private Webcam webcam;

    @PostConstruct
    public void initIt() throws Exception {
        switch (OS) {
            case "Linux":
                Webcam.setDriver(new V4l4jDriver()); // this is important (not really)
                break;
        }
        // get default webcam and open it
        webcam = Webcam.getDefault();
        if (webcam != null) {
            webcam.setViewSize(new Dimension(320, 240));
            webcam.open();
        }
    }

    @Override
    public BufferedImage GetPicture() throws IOException {
        if (webcam != null) {
            // get image
            BufferedImage image = webcam.getImage();

            /*ByteArrayOutputStream baos = new ByteArrayOutputStream();

            ImageIO.write(image, "jpg", baos);
            byte[] byteArray = baos.toByteArray();*/
            return image;
        }
        return null;
    }

    @PreDestroy
    public void cleanUp() throws Exception {
        webcam.close();
    }
}

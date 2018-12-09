package donkeybug.service;

import com.github.sarxos.webcam.Webcam;
import com.github.sarxos.webcam.ds.v4l4j.V4l4jDriver;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.imageio.IIOImage;
import javax.imageio.ImageIO;
import javax.imageio.ImageWriteParam;
import javax.imageio.ImageWriter;
import javax.imageio.plugins.jpeg.JPEGImageWriteParam;
import javax.imageio.stream.ImageOutputStream;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Locale;

@Service("v4l4jWebcamService")
public class V4l4jWebcamService implements WebcamService{
    @Value("${OS}")
    private String OS;

    @Value("${imageQuality}")
    private double quality;

    Webcam webcam;

    @PostConstruct
    public void initIt() throws Exception {
        switch (OS){
            case "Linux":
                Webcam.setDriver(new V4l4jDriver()); // this is important (not really)
                break;
        }
        // get default webcam and open it
        webcam = Webcam.getDefault();
        if(webcam != null) {
            webcam.setViewSize(new Dimension(320, 240));
            webcam.open();
        }
    }

    @Override
    public byte[] GetPicture() throws IOException{
        if(webcam != null) {
            // get image
            BufferedImage image = webcam.getImage();

            ByteArrayOutputStream baos = new ByteArrayOutputStream();

            ImageWriter imgWriter = ImageIO.getImageWritersByFormatName("jpg").next();
            ImageOutputStream ioStream = ImageIO.createImageOutputStream(baos);
            imgWriter.setOutput(ioStream);

            JPEGImageWriteParam jpegParams = new JPEGImageWriteParam(
                    Locale.getDefault());
            jpegParams.setCompressionMode(ImageWriteParam.MODE_EXPLICIT);
            jpegParams.setCompressionQuality((float)quality);

            imgWriter.write(null, new IIOImage(image, null, null), jpegParams);


            //ImageIO.write(image, "jpg", baos);
            byte[] byteArray = baos.toByteArray();
            return byteArray;
        }
        return null;
    }

    @PreDestroy
    public void cleanUp() throws Exception {
        webcam.close();
    }
}

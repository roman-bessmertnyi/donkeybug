package donkeybug.service.webcam;

import boofcv.abst.feature.detect.interest.ConfigGeneralDetector;
import boofcv.abst.feature.tracker.PointTracker;
import boofcv.abst.sfm.d3.MonocularPlaneVisualOdometry;
import boofcv.alg.tracker.klt.PkltConfig;
import boofcv.factory.feature.tracker.FactoryPointTracker;
import boofcv.factory.sfm.FactoryVisualOdometry;
import boofcv.io.MediaManager;
import boofcv.io.calibration.CalibrationIO;
import boofcv.io.image.ConvertBufferedImage;
import boofcv.io.wrapper.DefaultMediaManager;
import boofcv.struct.calib.MonoPlaneParameters;
import boofcv.struct.image.GrayU8;
import boofcv.struct.image.ImageType;
import donkeybug.model.Odometry;
import georegression.struct.point.Vector3D_F64;
import georegression.struct.se.Se3_F64;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.awt.image.BufferedImage;

@Service
public class BoofcvVisualOdometryService implements VisualOdometryService {
	private GrayU8 grayImage;

	private MonocularPlaneVisualOdometry<GrayU8> visualOdometry;

	@PostConstruct
	public void initIt(){

		MediaManager media = DefaultMediaManager.INSTANCE;

		// load camera description
		MonoPlaneParameters calibration = CalibrationIO.load(media.openFile("mono_plane.yaml"));

		// specify how the image features are going to be tracked
		PkltConfig configKlt = new PkltConfig();
		configKlt.pyramidScaling = new int[]{1, 2, 4, 8};
		configKlt.templateRadius = 3;
		ConfigGeneralDetector configDetector = new ConfigGeneralDetector(600,3,1);

		PointTracker<GrayU8> tracker = FactoryPointTracker.klt(configKlt, configDetector, GrayU8.class, null);

		// declares the algorithm
		visualOdometry =
				FactoryVisualOdometry.monoPlaneInfinity(75, 2, 1.5, 200, tracker, ImageType.single(GrayU8.class));

		// Pass in intrinsic/extrinsic calibration.  This can be changed in the future.
		visualOdometry.setCalibration(calibration);
	}

	@Override
	public Odometry getOdometry(BufferedImage image) {
		grayImage = ConvertBufferedImage.convertFrom(image, grayImage);

		if( !visualOdometry.process(grayImage) ) {
			System.out.println("Fault!");
			visualOdometry.reset();
		}

		Se3_F64 leftToWorld = visualOdometry.getCameraToWorld();
		Vector3D_F64 T = leftToWorld.getT();

		return new Odometry(T.x, T.y, T.z);
	}
}

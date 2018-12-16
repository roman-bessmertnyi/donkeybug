package donkeybug.model;

public class Odometry {
	private double x;
	private double y;
	private double z;

	public Odometry(double x, double y, double z) {
		this.x = x;
		this.y = y;
		this.z = z;
	}

	public double getX() {
		return x;
	}

	public double getY() {
		return y;
	}

	public double getZ() {
		return z;
	}
}

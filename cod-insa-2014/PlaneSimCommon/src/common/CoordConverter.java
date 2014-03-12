package common;

import model.Coord;

/**
 * This class allow the convertion between degrees and cartesian coords
 * 
 */
public class CoordConverter {

	private double min_long; // min x
	private double max_long; // max x

	private double min_lat; // min y
	private double max_lat; // max y

	public CoordConverter(double minla, double maxla, double minlo, double maxlo) {
		min_lat = minla;
		max_lat = maxla;
		min_long = minlo;
		max_long = maxlo;
	}

	public double getWidth() {
		return max_long - min_long;
	}

	public double getHeight() {
		return max_lat - min_lat;
	}

	public Coord toDegrees(Coord cartesianCoord) {
		return new Coord(cartesianCoord.x + min_long, cartesianCoord.y - min_lat);
	}

	public Coord toCartesian(Coord degreesCoord) {
		return new Coord(degreesCoord.x - min_long, degreesCoord.y - min_lat);
	}
	public Coord.View toDegrees(Coord.View cartesianCoord) {
		return new Coord(cartesianCoord.x() + min_long, cartesianCoord.y() - min_lat).view();
	}

	public Coord.View toCartesian(Coord.View degreesCoord) {
		return new Coord(degreesCoord.x() - min_long, degreesCoord.y() - min_lat).view();
	}
}

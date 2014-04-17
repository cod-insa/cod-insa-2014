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

	private double latAmplitude;
	private double longAmplitude;
	
	private double widthWorld;
	private double heightWorld;
	
	public CoordConverter(double minla, double maxla, double minlo, double maxlo, double zoom) {
		min_lat = minla;
		max_lat = maxla;
		min_long = minlo;
		max_long = maxlo;
		
		latAmplitude = max_lat - min_lat;
		longAmplitude = max_long - min_long;

		widthWorld = longAmplitude * zoom;
		heightWorld = latAmplitude * zoom;
	}
	
	public double getWidth() {
		return widthWorld;
	}

	public double getHeight() {
		return heightWorld;
	}

	/*public Coord toDegrees(double longitude, double latitude) {
		return new Coord(longitude + min_long, latitude + min_lat);
	}

	public Coord toDegrees(Coord cartesianCoord) {
		return new Coord(cartesianCoord.x + min_long, cartesianCoord.y
				+ min_lat);
	}

	public Coord.View toDegrees(Coord.View cartesianCoord) {
		return new Coord(cartesianCoord.x() + min_long, cartesianCoord.y()
				+ min_lat).view();
	}

	public Coord toCartesian(double longitude, double latitude) {
		return new Coord(longitude - min_long, latitude - min_lat);
	}*/
	
	public Coord.Unique toCartesianUnique(double longitude, double latitude) {
		
		return new Coord.Unique(((longitude - min_long)/longAmplitude)*widthWorld, heightWorld-(heightWorld*((latitude - min_lat)/latAmplitude)));
	}
	
	public double getLatFromUnique(Coord.Unique coord) {
		return (((heightWorld - coord.y()) / heightWorld) * latAmplitude)+min_lat;
	}
	
	public double getLatFromY(double y) {
		return (((heightWorld - y) / heightWorld) * latAmplitude)+min_lat;
	}
	
	public double getLongFromUnique(Coord.Unique coord) {
		return ((coord.x() / widthWorld) * longAmplitude)+min_long;
	}
	
	public double getLongFromX(double x) {
		return ((x / widthWorld) * longAmplitude)+min_long;
	}

	/*public Coord toCartesian(Coord degreesCoord) {
		return new Coord(degreesCoord.x - min_long, degreesCoord.y - min_lat);
	}

	public Coord.View toCartesian(Coord.View degreesCoord) {
		return new Coord(degreesCoord.x() - min_long, degreesCoord.y()
				- min_lat).view();
	}*/
	
}

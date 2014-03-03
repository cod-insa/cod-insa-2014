package display;


import model.Coord;

public class ViewTransform {
	
	/*
	 * Maps doubles in [O,1] to pixels in [shif,shift+size]
	 * 
	 * 
	 */
	
	final Coord _shift = new Coord(0,0);
	final Coord.View shift = _shift.view;
	
	final Coord _scale = new Coord(1,1);
	final Coord.View scale = _scale.view;
	
	//double rotation; // Not implemented
	
	private int viewSize;
	
	public ViewTransform (int viewSize) {
		this.viewSize = viewSize;
	}
	
	public void setViewSize (int viewSize) {
		this.viewSize = viewSize;
	}

	public void zoomReset () {
		_scale.set(Coord.unit);
	}
	
	public void zoomIn (double scaleIncreaseRatio) {
		_scale.mult(scaleIncreaseRatio);
	}
	
	public void zoomIn (Pixel p, double scaleIncreaseRatio) {
		
		Coord sp = getCoord(p); sp.sub(shift);
		zoomIn(scaleIncreaseRatio);
		Coord sp2 = getCoord(p); sp2.sub(shift);
		
		sp2.sub(sp.view);
		_shift.sub(sp2.view);
		
	}
	
	
	public Pixel getViewPos (Coord.View cv) {
		return new Pixel(
				(int)Math.round((cv.x()-_shift.x)*_scale.x*viewSize),
				(int)Math.round((cv.y()-_shift.y)*_scale.y*viewSize)
			);
		
	}

	public Coord getCoord (Pixel p) {
		return new Coord(((double)p.x)/viewSize/_scale.x+_shift.x, ((double)p.y)/viewSize/_scale.y+_shift.y);
	}
	
	
}











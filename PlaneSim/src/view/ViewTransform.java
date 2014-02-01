package view;

import model.Coord;

public class ViewTransform {
	
	/*
	 * Maps doubles in [O,1] to pixels in [shif,shift+size]
	 * 
	 * 
	 */
	
	final Coord shift = new Coord(0,0);
	final Coord.View vShift = shift.view;
	
	//int shiftX = 0, shiftY = 0;
	
	//double scale = 1;
	
	final Coord scale = new Coord(1,1);
	final Coord.View vScale = scale.view;
	
	//public double viewSize = 1;
	private int viewSize;
	
	
	//double rotation; // Not implemented
	//int width, height;
	/*
	public ViewTransform (int width, int height) {
		this.width = width;
		this.height = height;
	}
	
	public void setViewSize (int width, int height) {
		this.width = width;
		this.height = height;
	}*/
	public ViewTransform(int viewSize) {
		this.viewSize = viewSize;
	}
	
	public void setViewSize(int viewSize) {
		this.viewSize = viewSize;
	}
	

	//public void zoomIn(int x, int y, double scaleIncreaseRatio) {
	public void zoomIn(Pixel p, double scaleIncreaseRatio) {
		
		//System.out.println(scaleIncreaseRatio);
		
		/*
		//Coord p = getCoord(new pixel());
		Coord pc = getCoord(p);
		//p.x-(p.x-shift.x)/scale.x
		
		//pc.mult(1/scaleIncreaseRatio);
		
		//shift.add(new Coord((pc.x-shift.x)/scale.x, (pc.y-shift.y)/scale.y));
		shift.add(new Coord((pc.x-shift.x)/scaleIncreaseRatio, (pc.y-shift.y)/scaleIncreaseRatio));
		//shift.add(new Coord(-(pc.x-shift.x)/scaleIncreaseRatio, -(pc.y-shift.y)/scaleIncreaseRatio));
		//shift.add(new Coord(-(pc.x-shift.x)*scaleIncreaseRatio, -(pc.y-shift.y)*scaleIncreaseRatio));
		
		//pc.sub(shift);
		*/

		/*
		Coord op = getCoord(p);

		scale.mult(scaleIncreaseRatio);

		Coord op2 = getCoord(p);
		
		op2.sub(new Coord((op.x-shift.x)/scaleIncreaseRatio, (op.y-shift.y)/scaleIncreaseRatio));
		
		shift.set(op2);
		*/

		Coord sp = getCoord(p); sp.sub(vShift);
		scale.mult(scaleIncreaseRatio);
		Coord sp2 = getCoord(p); sp2.sub(vShift);
		
		//sp.mult(scaleIncreaseRatio);
		
		sp2.sub(sp.view);
		shift.sub(sp2.view);
		
	}
	
	
	public Pixel getViewPos (Coord.View cv) {
		//return new Pixel(shiftX + Math.round(c.getX()*scale), shiftY + c.getY());
		//System.out.println("> "+Math.round((cv.x()-shift.x)*scale.x));
		//System.out.println(cv.x()+" "+Math.round((cv.x()-shift.x)*scale.x));
		return new Pixel(
				(int)Math.round((cv.x()-shift.x)*scale.x*viewSize),
				(int)Math.round((cv.y()-shift.y)*scale.y*viewSize)
			);
		//return new Pixel((int)Math.round(cv.x()*scale.x-shift.x), (int)Math.round(cv.y()*scale.y-shift.y));
		
	}

	public Coord getCoord (Pixel p) {
		//System.out.println(">> "+((double)p.x)/((double)viewSize)/scale.x+shift.x);
		//System.out.println(">> "+(((double)p.x)/viewSize/scale.x+shift.x));
		//System.out.println(">> "+(.5/2));
		return new Coord(((double)p.x)/viewSize/scale.x+shift.x, ((double)p.y)/viewSize/scale.y+shift.y);
		//return new Coord(((double)p.x)/((double)viewSize)/scale.x+shift.x, ((double)p.y)/((double)viewSize)/scale.y+shift.y);
	}
	
	
	
	
}











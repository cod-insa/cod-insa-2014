package model;

import common.Copyable;

/**
 * Created by LP on 27/03/2014.
 */
public abstract class MaterialEntity extends Entity{

	private static final double DEFAULT_ENTITY_RADAR_RANGE = 0;
	
	
	private double rotation = 0;

	public double radarRange;

	public abstract Coord.View position();
	
	public MaterialEntity(int id) {
		super(id);
		radarRange = DEFAULT_ENTITY_RADAR_RANGE;
	}
	public MaterialEntity(MaterialEntity.View src) {
		super(src);
//		ownerId = src.ownerId();
		rotation = src.rotation();
		radarRange = src.radarRange();
	}
	
	public class View extends Entity.View {
		
		public final Coord.View position = MaterialEntity.this.position();
		public Coord.View position() { return position; }
		
		public double rotation() { return rotation; }
		public double radarRange() { return radarRange; }

		public boolean canSee(MaterialEntity.View e) {
			return false;
		}
	}
	
	public double rotation () {
		return rotation;
	}
	public void rotation (double angle) {
		rotation = angle;
		rotation %= Math.PI*2;
		if (rotation > Math.PI)
			rotation -= Math.PI*2;
	}
	public void rotate (double angle_delta) {
		rotation(rotation + angle_delta);
	}
	
	@Override
	public View view() {
		return new View();
	}
	
	@Override
	public Object copy(Context context) {
		return null;
	}
}

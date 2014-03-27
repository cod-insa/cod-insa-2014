package game;

import model.Coord;
import model.Entity;
import model.MaterialEntity;
import model.MovingEntity;

/**
 * Created by LP on 27/03/2014.
 */
public abstract class MaterialGameEntity extends GameEntity {
	
	private final Coord _lastPosition; // = new Coord(0,0);
	public final Coord.View lastPosition; // = _lastPosition.view();
	
	protected double radius = 0;
	
	public MaterialGameEntity(MaterialEntity model, Game sim, Altitude alt) {
		super(model, sim, alt);
		_lastPosition = model.position().copied();
		lastPosition = _lastPosition.view();
	}
	
	public final double radius() {
		return radius;
	}
	
	@Override
	public MaterialEntity.View modelView() { return (MaterialEntity.View) model.view(); }
	
	@Override
	MaterialEntity model() { return model(); }
	
	@Override
	public void updateSpecialized(double period) {
		
		if (model instanceof MaterialEntity)
			_lastPosition.set(((MaterialEntity)model).position());
		
		if (model instanceof MovingEntity) {

			MovingEntity model = (MovingEntity) this.model;

//			model.position.x += Math.cos(model.rotation())*model.speed;
//			model.position.y += Math.sin(model.rotation())*model.speed;

			Coord speedVec = model.speedVector().take();
			model.position.x += speedVec.x;
			model.position.y += speedVec.y;


			if (World.WORLD_WRAP)
			{
				World w = sim.getWorld();
				if (model.position.x < 0) {
					model.position.x += w.width; // World.WIDTH;
					_lastPosition.x += w.width;
				}
				else if (model.position.x > w.width) {
					model.position.x -= w.width;
					_lastPosition.x -= w.width;
				}
				if (model.position.y < 0) {
					model.position.y += w.height; // World.HEIGHT;
					_lastPosition.y += w.height;
				}
				else if (model.position.y > w.height) {
					model.position.y -= w.height;
					_lastPosition.y -= w.height;
				}
			}

		}
		
	}

}

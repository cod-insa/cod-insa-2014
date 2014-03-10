package model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import common.Immutable;
import common.ListView;
import common.Unique;
import common.Util;
import common.Viewable;

public class BaseModel extends EntityModel
	implements
		Serializable,
		Viewable<BaseModel.View>
{

	private static final long serialVersionUID = 1L;

	public final Immutable<Coord.View> position;

	public final List<PlaneModel> planes;

	public class View extends EntityModel.View
	{
		public Immutable<Coord.View> getPosition() {
			return BaseModel.this.position;
		}

		public ListView<PlaneModel.View> getPlanes() {
			return Util.view(planes);
		}
		
//		public BaseModel copied() {
//			return new BaseModel(BaseModel.this);
//		}
	}

	@Override
	public View view() {
		return new View();
	}

	public BaseModel(int id, Unique<Coord> pos) {
		// super(new View(),id,pos);
		// super(id, pos);
		super(id);
		planes = new ArrayList<PlaneModel>();
		position = new Immutable<>(pos);
	}

	public BaseModel(BaseModel.View src, Set<Object> context) {
		super(src.id());
//		planes = Util.copy(src.copied().planes);
//		planes = Util.copy(src.getPlanes());
		planes = new ArrayList<PlaneModel>();
		for (PlaneModel.View p : src.getPlanes())
//			planes.add(Util.copy(p, context));
			planes.add(p.copied(context));
		position = src.getPosition(); // Immutable state can be shared
	}
	
//	private BaseModel(BaseModel src)
//	{
//		super(src.id);
//		planes = Util.copy(src.planes);
//		position = src.position;
//	}

	@Override
	public BaseModel copy (Set<Object> context) {
		if (context.contains(this)) return this;
		BaseModel ret = new BaseModel(view(), context);
		context.add(ret);
		return ret;
	}

	@Override
	public model.Coord.View position() {
		return position.view();
	}

}










package model;

import common.Unique;

public class Country extends AbstractBase {

	public class View extends AbstractBase.View {

		@Override
		public Country copied(Context context) {
			return copy(context);
		}
		
	}
	
	public Country(int id, Unique<Coord> pos) {
		super(id, pos);
	}

	public Country(View src, Context context) {
		super(src, context);
	}

	@Override
	public Country copy(Context context) {
		if (context.containsKey(this))
			return context.getSafe(this);
		Country c = new Country(view(),context);
		return c;
	}

	@Override
	public View view() {
		return new View();
	}

}

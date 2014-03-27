package model;

import java.io.Serializable;
import java.util.ArrayList;

import common.Immutable;
import common.Unique;
import common.Viewable;
import common.Copyable.Context;

public class ProgressAxe implements Serializable , Viewable<ProgressAxe.View> {

	private static final long serialVersionUID = 1L;

	public final Base.View base1;
	public final Base.View base2;
	
	public ProgressAxe(Base b1, Base b2) {
		base1 = b1.view();
		base2 = b2.view();
	}
	
	public ProgressAxe(Base.View b1, Base.View b2) {
		base1 = b1;
		base2 = b2;
	}
	
	public class View implements Viewable.View {
		public Base.View base1() { return base1; }
		public Base.View base2() { return base2; }
		public ProgressAxe getModel() { return ProgressAxe.this; }
	}
	
	@Override
	public model.ProgressAxe.View view() {
		// TODO Auto-generated method stub
		return new View();
	}
	
	public ProgressAxe(ProgressAxe.View src, Context context) {
		context.putSafe(src.getModel(), this);
		base1 = src.base1();
		base2 = src.base2();
	}

	public ProgressAxe copy(Context context) {
		if (context.containsKey(this))
			return (ProgressAxe) context.get(this);
		ProgressAxe ret = new ProgressAxe(view(), context);
		return ret;
	}
}

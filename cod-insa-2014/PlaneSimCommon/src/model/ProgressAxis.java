package model;

import java.io.Serializable;

import common.Viewable;
import common.Copyable.Context;

public class ProgressAxis implements Serializable, Viewable<ProgressAxis.View> {

	private static final long serialVersionUID = 1L;

	public final Base.View base1;
	public final Base.View base2;
	
	public ProgressAxis(Base b1, Base b2) {
		base1 = b1.view();
		base2 = b2.view();
	}
	
	public ProgressAxis(Base.View b1, Base.View b2) {
		base1 = b1;
		base2 = b2;
	}
	
	public class View implements Viewable.View {
		public Base.View base1() { return base1; }
		public Base.View base2() { return base2; }
		public ProgressAxis getModel() { return ProgressAxis.this; }
	}
	
	@Override
	public ProgressAxis.View view() {
		// TODO Auto-generated method stub
		return new View();
	}
	
	public ProgressAxis(ProgressAxis.View src, Context context) {
		context.putSafe(src.getModel(), this);
		base1 = src.base1();
		base2 = src.base2();
	}

	public ProgressAxis copy(Context context) {
		if (context.containsKey(this))
			return (ProgressAxis) context.get(this);
		ProgressAxis ret = new ProgressAxis(view(), context);
		return ret;
	}
}

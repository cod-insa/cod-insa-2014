package model;

import java.io.Serializable;

import common.Copyable;
import common.Viewable;

public class ProgressAxis extends Entity implements Serializable, Viewable<ProgressAxis.View> {

	private static final long serialVersionUID = 1L;

	public final Base.FullView base1;
	public final Base.FullView base2;
	
	public double ratio1 = .3, ratio2 = .3; // TODO handle these correctly
	
//	public ProgressAxis(Base b1, Base b2) {
//		base1 = b1.view();
//		base2 = b2.view();
//	}
	
	public ProgressAxis(int id, Base b1, Base b2) {
		super(id);
		base1 = b1.view();
		base2 = b2.view();
//		setBases();
		b1.axes.add(new Oriented(b2));
		b2.axes.add(new Oriented(b1));
	}

	public ProgressAxis(ProgressAxis.View src, Context context) {
		super(src.id());
		context.putSafe(src.model(), this);
		base1 = src.base1();
		base2 = src.base2();
		
		/**
		 * The Base object, when copied, should copy its axes set too!
		 * This is not supposed to be done by ProgressAxis because it is data
		 * owned by Base, not ProgressAxis.
		 */
		// setBases();
	}
	
//	public setBases {
//	}
	
	public ProgressAxis copy(Context context) {
		if (context.containsKey(this))
			return context.getSafe(this);
		ProgressAxis ret = new ProgressAxis(view(), context);
		return ret;
	}
	
	private Oriented mkOriented(Base next) {
		return new Oriented(next);
	}
	
	public class Oriented implements Copyable {
		
		public final Base next;
		
		public Oriented(Base next) {
			this.next = next;
		}

		public final Base.FullView next() { return next.view(); };
		
		public ProgressAxis.View axis() { return view(); }

		@Override
		public Oriented copy(Context context) {
			if (context.containsKey(this))
				return context.getSafe(this);
			//ProgressAxis ret = new ProgressAxis.this.copy(context).ProgressAxis(view(), context);

//			ProgressAxis that = ProgressAxis.this.copy(context);
//			that.mkOriented(next.copy(context));

			Oriented ret = ProgressAxis.this.copy(context).mkOriented(next.copy(context));
			
			context.putSafe(this, ret);
			
			return ret;
		}
	}
	
	public class View extends Entity.View {
		public Base.FullView base1() { return base1; }
		public Base.FullView base2() { return base2; }
//		private ProgressAxis model() { return ProgressAxis.this; }

		public double ratio1() { return ratio1; }
		public double ratio2() { return ratio2; }
		
		public ProgressAxis copied(Context context) {
			return copy(context);
		}
		
	}
	
	@Override
	public ProgressAxis.View view() {
		return new View();
	}
	
}

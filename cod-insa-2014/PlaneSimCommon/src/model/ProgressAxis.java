package model;

import java.io.Serializable;

import common.Copyable;
import common.Viewable;

public class ProgressAxis extends Entity implements Serializable, Viewable<ProgressAxis.View> {

	private static final long serialVersionUID = 1L;

	//	public final Base.FullView base1;
//	public final Base.FullView base2;
	public final Base base1;
	public final Base base2;
	public final Oriented toBase1, toBase2;

	/**
	 * Between 0 and one. For both, 0 represents no advancement from the base.
	 */
	public double ratio1 = .3, ratio2 = .3; // TODO handle these correctly
	
//	public ProgressAxis(Base b1, Base b2) {
//		base1 = b1.view();
//		base2 = b2.view();
//	}
	
	public ProgressAxis(int id, Base b1, Base b2) {
		super(id);
		base1 = b1;
		base2 = b2;
//		setBases();
		toBase1 = new Oriented(b1);
		toBase2 = new Oriented(b2);
		b1.axes.add(toBase2);
		b2.axes.add(toBase1);
	}

	public ProgressAxis(ProgressAxis.View src, Context context) {
		super(src.id());
		context.putSafe(src.model(), this);
		base1 = src.base1().copied(context);
		base2 = src.base2().copied(context);
//		toBase1 = src.model().toBase1.copied(context);
		toBase1 = context.getSafe(src.model().toBase1);
		toBase2 = context.getSafe(src.model().toBase2);
		
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
	
	public Oriented arcTo(Base b) {
		if (b == base1)
			return toBase1;
		if (b == base2)
			return toBase2;
		throw new IllegalArgumentException();
	}
	
	private Oriented mkOriented(Base next) {
		return new Oriented(next);
	}
	
	public class Oriented implements Copyable {
		
		final Base next;
		
		public Oriented(Base next) {
			this.next = next;
		}

		public final Base.FullView next() { return next.view(); };

		public ProgressAxis.View axis() { return view(); }
		
		public double ratio() {
			if (next == base2)
				return ratio1;
			assert next == base1;
			return ratio2;
		}
		void ratio(double val) {
			if (next == base2)
				ratio1 = val;
			else {
				assert next == base1;
				ratio2 = val;
			}
		}
 
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
	
	public interface Nothing {
		
	}
	
	public final class Occidented implements Nothing {
		
		// Nothing
		
	}
	
	public class View extends Entity.View {
		public Base.FullView base1() { return base1.view(); }
		public Base.FullView base2() { return base2.view(); }
//		private ProgressAxis model() { return ProgressAxis.this; }

		public double ratio1() { return ratio1; }
		public double ratio2() { return ratio2; }
		
		public ProgressAxis copied(Context context) {
			return copy(context);
		}

		protected ProgressAxis model() { return ProgressAxis.this; }
	}
	
	@Override
	public ProgressAxis.View view() {
		return new View();
	}
	
}

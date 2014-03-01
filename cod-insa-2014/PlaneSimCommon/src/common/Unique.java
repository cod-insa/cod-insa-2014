package common;

public class Unique<T> {
	
	public static class Copy<T extends Copyable<T>> extends Unique<T> {
		
		public Copy(T original) {
			//this.object = original.copy();
			super(original.copy());
		}
		/*
		//protected Copy() { }
		protected Copy(T original, boolean makeCopy) {
			super(makeCopy? original.copy(): original);
		}
		*/
	}
	
	public static class ExpiredCopyException extends RuntimeException {
		private static final long serialVersionUID = 1L;
		//public ExpiredCopyException() { super("Cannot take a unique object twice."); }
		public ExpiredCopyException() { super("Cannot take or view a unique object after it has been taken."); }
	}
	
	//protected T object = null;
	private T object;
	
	//private Unique() { }
	protected Unique(T object) { this.object = object; }
	
	public final T take() {
		if (object != null) {
			T ret = object;
			object = null;
			return ret;
		}
		throw new ExpiredCopyException();
	}
	/*
	final T peek() {
		return object;
	}
	*/
	public static<V extends Viewable.View, T extends Viewable<V>> V view(Unique<T> src) {
		if (src.object == null)
			throw new ExpiredCopyException();
		return src.object.getView();
	}
	
	public final boolean taken() { return object == null; }
	
}

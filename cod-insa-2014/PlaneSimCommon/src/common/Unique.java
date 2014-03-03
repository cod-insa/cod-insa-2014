package common;


public class Unique<T> {
	
//	public static class Copy<T extends Copyable<T>> extends Unique<T> {
//		
//		public Copy(T original) {
//			//this.object = original.copy();
//			super(original.copy());
//		}
//		/*
//		//protected Copy() { }
//		protected Copy(T original, boolean makeCopy) {
//			super(makeCopy? original.copy(): original);
//		}
//		*/
//	}
	
	
	
	//protected T object = null;
	//private T object;
	T object;
	
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
	public static <V extends Viewable.View, T extends Viewable<V>>
	V view(Unique<T> src) {
		if (src.object == null)
			throw new ExpiredCopyException();
		return src.object.getView();
	}
	
	public final boolean taken() { return object == null; }
	
	

	public static class ExpiredCopyException extends RuntimeException {
		private static final long serialVersionUID = 1L;
		//public ExpiredCopyException() { super("Cannot take a unique object twice."); }
		public ExpiredCopyException() { super("Cannot take or view a unique object after it has been taken."); }
	}
	
	//public static class Copy<T extends Copyable> extends Unique<T> {
	public static class Copy<T> extends Unique<T> {
		/*
		public<U extends Copyable> Copy(U original) {
			super(original.copy());
		}
		*/
		
		private Copy(T copy) {
			super(copy);
		}
		public static<T extends Copyable> Copy<T> make (T src) {
			return new Copy<>(src);
		}

//		public Copy(T original) {
//			super(Util.copy(original));
//		}
		
		public Copy (T original, Copyable.Copier<T> copier) {
			super(copier.copy(original));
		}
		
		/*
		public<U> Copy(U original, Converter<U,Copyable<T>> copier) {
			//this.object = original.copy();
			super(copier.convert(original).copy());
		}
		public<U> Copy(T src, Converter<T,Copyable> copier) {
			super((T)Util.copy(copier.convert(src)));
		}
		*/
		
	}
	
	public static class Collection<T, CT extends java.util.Collection<T>> extends Unique<CT> {
		
		/*
		 * Must be provided with a collection class that can be instantiated using no argument.
		 * This new instance must be empty for the uniqueness invariant to hold.
		 * 
		 * This takes a Class<?> instead of the expected Class<CT>, because due to a weird quirk,
		 * it is otherwise not possible to instantiate: new Collection<X, ArrayList<X>> (ArrayList.class)
		 * With this ?, it is even psosible to write: new Collection<X, List<X>> (ArrayList.class)
		 * 
		 */
		//protected Collection(Class<CT> c) {
		@SuppressWarnings("unchecked")
		public Collection(Class<?> c) {
//			try {
//			super(c.newInstance());
//			} catch(Exception e){}
			super((CT) helper(c));
		}
		
		static<CT> CT helper(Class<CT> c){
			try {
				return c.newInstance();
			} catch (InstantiationException | IllegalAccessException e) {
				throw new IllegalArgumentException("The collection class passed to Unique.Collection" +
						"must be instantiable with no argument ("+c+")", e);
			}
		}
		
		public boolean add(Unique<T> elt) {
			return this.object.add(elt.take());
		}
		
	}
	
	
}












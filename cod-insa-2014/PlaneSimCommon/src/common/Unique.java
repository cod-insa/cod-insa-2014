package common;

/*
 * A class implementing the Unique concept, which is a "deep" concept (sub-objects of Unique object are also Unique).
 * This is basically a wrapper to hide the unique mutable reference to an object (and its sub-objects).
 * The only way to get the object is to take() it, and this can only be done once.
 * It is possible to view the object without taking it if it is Viewable, using Util.view(u) or Unique.view(u).
 * The fact that an object that has been taken can no more be viewed can avoid memory leak problems
 * (keeping an empty Unique object that still has a view to the object would prevent the object to be reclaimed).
 * 
 */
public class Unique<T> {
	
	T object;
	
	/*
	 * Caution: for the whole invariant to hold, this constructor must only be called with an argument
	 * to which no one else has a reference. Eg: super(new X(...))
	 * 
	 */
	protected Unique(T object) { this.object = object; }
	
	public final T take() {
		if (object != null) {
			T ret = object;
			object = null;
			return ret;
		}
		throw new ExpiredCopyException();
	}
	
	public final boolean taken() { return object == null; }
	
	/*
	 * Permits viewing the unique element if it is viewable
	 * 
	 */
	public static <V extends Viewable.View, T extends Viewable<V>>
	V view(Unique<T> src) {
		if (src.object == null)
			throw new ExpiredCopyException();
		return src.object.view();
	}
	
	
	public static class ExpiredCopyException extends RuntimeException {
		private static final long serialVersionUID = 1L;
		public ExpiredCopyException() { super("Cannot take or view a unique object after it has been taken."); }
	}
	
	
	public static class Copy<T> extends Unique<T> {
		
		/*
		 * Provide any object and a deep Copier to copy it and we'll make a unique copy of it
		 * 
		 */
		public Copy (T original, Copyable.Copier<T> copier) {
			super(copier.copy(original));
		}
		
		/*
		 * Java doesn't allow this parameter to be declared: <U extends T & Copyable>
		 * 
		 *	public<U extends T & Copyable> Copy(U original) {
		 *		super(original.copy());
		 *	}
		 * 
		 * So we resort to a static factory method Unique.Copy.make,
		 * for when your type T is Copyable so you don't need to provide a Copier for it
		 * 
		 */
		public static<T extends Copyable> Copy<T> make (T src) {
			return new Copy<>(src);
		}
		
		private Copy(T copy) {
			super(copy);
		}
		
	}
	
	/*
	 * A class allowing to define, fill and view a collection without actually having a mutating
	 * reference to it or to its elements, so it is (deeply) Unique.
	 * 
	 */
	public static class Collection<T, CT extends java.util.Collection<T>> extends Unique<CT> {
		
		/*
		 * This constructor must be provided with a collection class that can be instantiated using no argument.
		 * This new instance must be empty for the uniqueness invariant to hold.
		 * 
		 * This takes a Class<?> instead of the expected Class<CT>, because due to a weird quirk,
		 * it is otherwise not possible to instantiate: new Collection<X, ArrayList<X>> (ArrayList.class)
		 * With this ?, it is even psosible to write: new Collection<X, List<X>> (ArrayList.class)
		 * 
		 */
		// public Collection(Class<CT> c) {
		@SuppressWarnings("unchecked")
		public Collection(Class<?> c) {
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












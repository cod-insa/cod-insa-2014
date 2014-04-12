package common;

import java.util.IdentityHashMap;



/**
 * Contract:
 * - ALL CLASSES IMPLEMENTING THIS MUST PERFORM DEEP COPIES
 * - Copies must be castable to the actual object type; if A is Copyable, "(A)a.copy()" should not fail
 * 		this means classes extending a Copyable class must override copy
 * 
 * A typical design is to always add a copy constructor to classes implementing Copyable, and defer
 * copying to this constructor in copy(), that must be overridden for all classes inheriting
 * a class implementing Copyable.
 * 
 */
public interface Copyable {
	/*
	public final class Unique<T extends Copyable<T>> {
		public common.Unique<T> make(T original) {
			return new common.Unique.Copy<T>(original);
		}
	}
	*/
	
	/**
	 * Invariant: this should only contain key/value pairs of the same type, or so that the value's type can be casted
	 * to the key's type.
	 */
	public static class Context extends IdentityHashMap<Object,Object> {
		@Override
		@Deprecated
		public Object put(Object key, Object value) {
			if (!key.getClass().isInstance(key))
				throw new Error("Value must be an instance of the key's class.");
			return super.put(key, value);
		}
		@Override
		@Deprecated
		public Object get(Object key) {
			return super.get(key);
		}
		public <K, V extends K> void putSafe(K key, V value) {
			super.put(key, value);
		}
		@SuppressWarnings("unchecked")
		public <K> K getSafe(K t) {
			return (K) super.get(t);
		}
	}
	
	public interface Copier<T> {
		public T copy (T src, Context context);
	}
	
	/**
	 * Returns an independent object sharing NO mutable data with the original
	 * Note that sharing an Immutable<T> objects is fine (or any other immutable object).
	 * 
	 * In order to avoid duplicating data or falling into infinite copy recurion
	 * in case of cycles in the ownership graph, a set is to be passed containing
	 * all objects that have been copied to this point. It should never be null.
	 * 
	 * This function is supposed to update the set with the newly copied objects only
	 * as necessary, ie: if those objects are shared and should not get copied again
	 * later during the deep copy of another object that has a reference to it.
	 * 
	 * Caution: for distinct mutable elements with the same hash and so that a.equals(b),
	 * it will usually not make sense to share a single representation in the copied
	 * structure, because they were distinct in the original.
	 * When dealing with such objects, it is better to always copy them, and if sharing
	 * them is wanted, to wrap them into shared handlers that can be distinguished one
	 * another (the handlers don't override equals and hashCode).
	 * 
	 */
	Object copy (Context context);
	
	
//	//public interface Collection<T extends Copyable> extends java.util.Collection<T>, Copyable { }
//	public class Collection<T extends Copyable> extends CollectionWrapper<T> implements Copyable {
//		
//		public Collection(java.util.Collection<T> src) {
//			super(src);
//		}
//		
//		@Override
//		public Object copy() {
//			return new Collection<T>(Util.copy(getModel()));
//		}
//		
//	}
//	
//	//public interface List<T extends Copyable> extends java.util.List<T>, Copyable { }
//	public class List<T extends Copyable> extends ListWrapper<T,List<T>> implements Copyable {
//	//public class List<T extends Copyable> extends ForwardingList<T> implements Copyable {
//		
//		public final List<T> model;
//		
//		public List(java.util.List<T> src) {
//			super(src);
//		}
//
//		@Override
//		protected java.util.List<T> getModel() {
//			return (java.util.List<T>) model;
//		}
//		
//		@Override
//		public Object copy() {
//			return new List<T>(Util.copy(getModel()));
//		}
//		
//	}
	
	
}


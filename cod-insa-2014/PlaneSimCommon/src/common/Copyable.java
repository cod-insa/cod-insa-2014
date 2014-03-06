package common;



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
	
	public interface Copier<T> {
		public T copy (T src);
	}
	
	/*
	 * Returns an independent object sharing NO mutable data with the original
	 * Note that sharing Immutable<T> objects is fine (or any other immutable object)
	 */
	Object copy();
	
	
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


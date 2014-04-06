package common;

import common.Accessors.Access;

public class Nullable<T> implements Access<T>, Viewable<Nullable.View> {
	// Immutable NULL value
	public final static Nullable<?>.View NULL = new Nullable<Object>().view();
	
	private T obj = null;
	
	public Nullable(T obj) {
		set(obj);
	}
	public Nullable() {
	}
	
	public boolean isNone () { return obj == null; }
	public boolean hasSome () { return !isNone(); }
	
	@Override
	public T get () {
		if (obj == null)
			throw new NullPointerException("Cannot get a null object from a Nullable");
		return obj;
	}
	
	@Override
	public void set (T obj) {
		this.obj = obj;
	}
	
//	public static <T> Nullable<T> NULL() { return new Nullable<T>(); }
	@SuppressWarnings("unchecked")
	public static <T> Nullable<T>.View NULL() { return (Nullable<T>.View) NULL; }
	
	public class View implements Viewable.ViewOf<Nullable<T>> {
		public boolean isNone () { return Nullable.this.isNone(); }
		public boolean hasSome () { return Nullable.this.hasSome(); }
		public T get () { return Nullable.this.get(); }
	}
	
	@Override
	public View view() {
		return new View();
	}
	
}

//public class Nullable<T> implements Access<T> {
//	private T obj;
//	
//	public Nullable(T obj) {
//		set(obj);
//	}
//	
//	@Override
//	public final T get () {
//		onGet();
//		return obj;
//	}
//	
//	@Override
//	public final void set (T obj) {
//		if (canSet(obj)) {
//			this.obj = obj;
//			onSet();
//		}
//	}
//	
//	public void onGet () {}
//	public void onSet () {}
//
//	public boolean canSet (T obj) { return true; }
//	
//}



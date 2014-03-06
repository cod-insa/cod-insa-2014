package common;

import common.Accessors.Access;

public class Nullable<T> implements Access<T> {
	private T obj = null;
	
	public Nullable(T obj) {
		set(obj);
	}
	public Nullable() {
	}
	
	public boolean none () { return obj == null; }
	public boolean hasSome () { return !none(); }
	
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



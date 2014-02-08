package common;

import common.Accessors.Access;

class Nullable<T> implements Access<T> {
	private T obj;
	
	public Nullable(T obj) {
		set(obj);
	}
	
	@Override
	public final T get () {
		onGet();
		return obj;
	}
	
	@Override
	public final void set (T obj) {
		if (canSet(obj)) {
			this.obj = obj;
			onSet();
		}
	}
	
	public void onGet () {}
	public void onSet () {}

	public boolean canSet (T obj) { return true; }
}

package common;

import common.Accessors.Access;

class BasicAccess<T> implements Access<T> {
	private T obj;
	
	public BasicAccess(T obj) {
		set(obj);
	}
	
	@Override
	public final T get () {
		onGet();
		return obj;
	}
	
	@Override
	public final void set (T obj) {
		if (obj == null)
			throw new NullPointerException("BasicAccess does not allow null values.");
		if (canSet(obj)) {
			this.obj = obj;
			onSet();
		}
	}
	
	public void onGet () {}
	public void onSet () {}

	public boolean canSet (T obj) { return true; }
}

package common;

public final class Immutable<T extends Viewable<V>, V extends Viewable.View> {
	
	public final V view;
	
	public Immutable(Unique<T> uniqueObject) {
		this.view = uniqueObject.take().getView();
	}
	
}

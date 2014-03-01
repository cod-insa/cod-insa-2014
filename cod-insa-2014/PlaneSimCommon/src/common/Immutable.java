package common;

//public final class Immutable<T extends Viewable<V>, V extends Viewable.View> {
public class Immutable<V extends Viewable.View> {
	
	public final V view;
	
	public<T extends Viewable<V>> Immutable(Unique<T> uniqueObject) {
		this.view = uniqueObject.take().getView();
	}
	
	public static class List<T> extends Immutable<ListView<T>> {

		public List(Unique<T> uniqueObject) {
			super(null);
			// TODO Auto-generated constructor stub
		}
		/*
		public List(java.util.List<T> ls) {
			class ViewableList extends Viewable<V> {}
			super(new Unique<ViewableList>(){});
		}
		*/
	}
}

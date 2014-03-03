package common;

/*
 * If it is overridden, a view's equals method should return true for two
 * views exposing the same properties, even when their model objects might
 * not be equal
 * 
 */

public interface Viewable<V extends Viewable.View> {
	
	public static interface View {
		
	}
	
	public interface ViewOf<T> extends Viewable.View {
		
		//public <T> ViewOf<T> getFrom(T src);
		//public Converter<T,ViewOf<T>> getViewer();
		
	}
	
	public V getView();

}

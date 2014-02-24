package common;

public interface Viewable<V extends Viewable.View> {
	
	public static interface View {
		
	}
	
	public V getView();

}

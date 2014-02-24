package common;

public interface ShallowViewable<V extends Viewable.View, SV extends Viewable.View> extends Viewable<V> {
	
	public static interface ShallowView {
		
	}
	
	public SV getShallowView();

}

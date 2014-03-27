package common;

//public interface ShallowViewable<V extends ShallowViewable.View, SV extends Viewable.View> extends Viewable<V> {
public interface ShallowViewable <SV extends ShallowViewable.ShallowView> {
	
	public static interface ShallowView {
		
	}
	
	public SV shallowView();
	
}

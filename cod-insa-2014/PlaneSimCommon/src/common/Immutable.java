package common;

import java.util.Set;

import common.Util.Converter;

/*
 * Must be completely, deeply immutable
 * "copy" does no copy at all
 * 
 */

//public final class Immutable<T extends Viewable<V>, V extends Viewable.View> {
public class Immutable<V extends Viewable.View>
implements
	Viewable<V>,
	Copyable,
	ShallowCopyable
{
	
	public final V view;

	public<T extends Viewable<V>> Immutable(Unique<T> uniqueObject) {
		this.view = uniqueObject.take().view();
	}

	public<T> Immutable(Unique<T> uniqueObject, Converter<T,V> viewer) {
		this.view = viewer.convert(uniqueObject.take());
	}
	
	/*
	private<T extends Viewable<V>> Immutable(V view) {
		this.view = view;
	}
	
	public static <T, V extends ViewOf<T>>
	Immutable<V> make(Unique<T> uniqueObject) {
		return new Immutable<V>(uniqueObject.take().getView());
	}
	*/
	
	@Override
	public V view() {
		return view;
	}
	
	@Override
	public Immutable<V> copy (Context context) {
		return this;
	}
	
	@Override
	public Immutable<V> shallowCopy() {
		return this;
	}

//	public static <T extends Viewable<V>, V extends Viewable.View>
//	Converter<T, V>
//		getViewer(Unique<T> src)
//	{
//		return new Converter<T, V>() {
//			public V convert(T src) {
//				return Unique.view(src);
//			}
//		};
//	}
	
//	public static class List<T> extends Immutable<ListView<T>> {
//
//		public List(Unique<T> uniqueObject) {
//			super(null);
//			// TODO Auto-generated constructor stub
//		}
//		/*
//		public List(java.util.List<T> ls) {
//			class ViewableList extends Viewable<V> {}
//			super(new Unique<ViewableList>(){});
//		}
//		*/
//	}
	
}










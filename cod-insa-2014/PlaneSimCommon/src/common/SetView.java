package common;

import java.util.Set;

import common.Util.Converter;

public interface SetView<T>
	extends CollectionView<T>
{
	
	public Set<T> asUnmodifiableSet();
	
	
	public static class ShallowOf<T> extends CollectionView.ShallowOf<T> implements SetView<T> {
		
		public ShallowOf(Set<T> src) {
			super(src);
		}
		
		@Override
		public Set<T> asUnmodifiableSet() {
			return (Set<T>) delegate();
		}
		
	}
	
	public static class Transform<U> extends CollectionView.Transform<U> implements SetView<U>, Set<U>, CollectionWrapper
	{
		
		public <T> Transform (Set<T> src, Converter<T,U> transformer) {
			super(src, transformer);
		}
		
		@Override public Set<U> asUnmodifiableSet() { return this; }
		
//		@Override public Set<?> delegate() {
//			return (Set<?>) delegate;
//		}
		
	}
	
	//public static class Of <V extends Viewable.View> extends CollectionView.Of<V> implements SetView<V>
	public static class Of <V extends Viewable.View> extends Transform<V> implements SetView<V>
	{

		public <T extends Viewable<V>> Of (Set<T> src) {
			//super(src, Util.getViewer(src));
//			super(src, new Converter<T,V>(){
//				public V convert(T src) {
//					return src.view();
//				}
//			});
			super(src, Util.<T,V>getViewer());
			//super(src, Util.getViewer(new DummyArg<T>()));
		}
		
		
//		final List<Viewable<V>> delegate;
//		
//		@SuppressWarnings("unchecked")
//		public <T extends Viewable<V>> Of(List<T> src) {
//			//super(new CollectionView.OfViews(src));
//			
//			// This is a safe cast because we're never inserting elements in src:
//			this.delegate = (List<Viewable<V>>) src;
//		}
		
		
		
	}
	
}












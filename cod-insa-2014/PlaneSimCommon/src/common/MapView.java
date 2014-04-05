package common;

import java.util.AbstractMap;
import java.util.Collections;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import common.MapView.EntryView;
import common.Util.Converter;

class Helper {
	static<K,T> EntryView<K,T> makeEntryView(final Entry<K,T> src) {
		return new EntryView<K,T>() {
			public K getKey() { return src.getKey(); }
			public T getValue() { return src.getValue(); }
		};
	}
}

public interface MapView<K,T> extends Viewable.View {

	//public class EntryView<K,T> implements Map.Entry<K,T> {
	
	public interface EntryView<K,T> {
		
		public K getKey();
		
		public T getValue();
		
	}
	
//	class EntryViewImpl<K,T> implements EntryView<K,T> {
//
//		@Override
//		public K getKey() {
//			return null;
//		}
//
//		@Override
//		public T getValue() {
//			return null;
//		}
//		
//		public EntryViewImpl(EntryView<K,T> src) {
//			
//		}
//		
//	}
	
	
	public Map<K,T> asUnmodifiableMap();
	/*
	public boolean contains(Object arg0);
	public boolean containsAll(Collection<?> arg0);
	public boolean isEmpty();

	public Iterator<T> iterator();
	
	public int size();
	public Object[] toArray();
	public <U> U[] toArray(U[] arg);
	*/	
	
	
	public boolean containsKey(Object arg0);
	
	public boolean containsValue(Object arg0);

	//public Set<java.util.Map.Entry<K, T>> entrySet();
	public SetView<EntryView<K, T>> entrySetView();
	
	public T get(Object arg0);
	
	public boolean isEmpty();

	//public Set<K> keySet();
	public SetView<K> keySetView();
	
	public int size();

	//public Collection<T> values();
	public CollectionView<T> valuesView();
	
	
	
	
	public static class ShallowOf<K,T> extends AbstractMap<K,T> implements MapView<K,T> {
		
		final Map<K,T> delegate;
		
		public ShallowOf(Map<K,T> src){
			delegate = src;
		}
		protected Map<K,T> delegate() {
			return delegate;
		}
		@Override
		public Map<K, T> asUnmodifiableMap() {
			return Collections.unmodifiableMap(delegate);
		}
		@Override
		public Set<java.util.Map.Entry<K, T>> entrySet() {
			//return Collections.unmodifiableSet(delegate.entrySet());
			return asUnmodifiableMap().entrySet();
		}
		@Override
		public SetView<EntryView<K,T>> entrySetView() {
			return new SetView.Transform<>(delegate.entrySet(), new Converter<Entry<K,T>, EntryView<K,T>>() {
				public EntryView<K,T> convert(Entry<K,T> src) {
					return Helper.makeEntryView(src);
				}
			});
		}
		@Override
		public SetView<K> keySetView() {
			return new SetView.ShallowOf<K>(delegate.keySet());
		}
		@Override
		public CollectionView<T> valuesView() {
			return new CollectionView.ShallowOf<T>(delegate.values());
		}
		
	}
	
	
	
	
	public static class Transform<K,U> extends AbstractMap<K,U> implements MapView<K,U>
	{
		final Map<K,Object> delegate;
		final Converter<Object,U> transformer;
		
		@SuppressWarnings("unchecked")
		public <T> Transform (Map<K,T> src, Converter<T,U> transformer) {
			// This is a safe cast because we're never inserting elements in src:
			this.delegate = (Map<K,Object>) src;
			this.transformer = (Converter<Object, U>) transformer;
		}
		
		@Override
		public Map<K,U> asUnmodifiableMap() {
			return this;
		}

		@Override
		public Set<java.util.Map.Entry<K, U>> entrySet() {
			return asUnmodifiableMap().entrySet();
		}

		@Override
		public SetView<common.MapView.EntryView<K,U>> entrySetView() {
			//return new SetView.Transform<Map.Entry<K,U>>(delegate.entrySet(), new );
			return new SetView.Transform<>(delegate.entrySet(), new Converter<Entry<K,Object>, EntryView<K,U>>() {
				public EntryView<K,U> convert(final Entry<K,Object> src) {
					return new EntryView<K,U>() {
						public K getKey() {
							return src.getKey();
						}
						public U getValue() {
							return transformer.convert(src.getValue());
						}
					};
				}
			});
		}

		@Override
		public SetView<K> keySetView() {
			return new SetView.ShallowOf<>(keySet());
		}
		
		@Override
		public CollectionView<U> valuesView() {
			return new CollectionView.ShallowOf<>(values());
		}
		
	}
	
	
	
	
	public static class Of <K, V extends Viewable.View> extends Transform<K,V> //implements MapView<K,V>
	{
		
		public <T extends Viewable<V>> Of (Map<K,T> src) {
			super(src, Util.<T,V>getViewer());
		}
		
	}
	
}

//class S<T> implements Set<T> {}
//class M<K,T> implements Map<K,T> {}





















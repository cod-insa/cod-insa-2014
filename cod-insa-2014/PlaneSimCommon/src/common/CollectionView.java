package common;

import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;

public interface CollectionView<T> extends Viewable.View { // extends ShallowCopyable<Collection<T>> {
	
	public Collection<T> asUnmodifiableCollection();
	
	public boolean contains(Object arg0);
	public boolean containsAll(Collection<?> arg0);
	public boolean isEmpty();

	public Iterator<T> iterator();
	
	public int size();
	public Object[] toArray();
	public <U> U[] toArray(U[] arg);
	
	
	//public static CollectionView of(){return null;}
	public static class Of<T> implements CollectionView<T>, Collection<T> {
		
		protected Collection<T> model;
		
		public Of(Collection<T> src){
			//new CollectionView.Of<>(new ArrayList<String>());
			model = src;
		}
		
		@Override
		public Collection<T> asUnmodifiableCollection() {
			return Collections.unmodifiableCollection(this);
		}
		
		@Override public boolean add(T arg0) { throw new UnsupportedOperationException(); }
		@Override public boolean addAll(Collection<? extends T> arg0) { throw new UnsupportedOperationException(); }
		@Override public void clear() { throw new UnsupportedOperationException(); }
		
		@Override public boolean contains(Object arg0) { return model.contains(arg0); }
		@Override public boolean containsAll(Collection<?> arg0) { return model.containsAll(arg0); }
		@Override public boolean isEmpty() { return model.isEmpty(); }

		@Override public Iterator<T> iterator() {
			final Iterator<T> ite = model.iterator();
			return new Iterator<T>(){
				@Override public boolean hasNext() {
					return ite.hasNext();
				}
				@Override public T next() {
					return ite.next();
				}
				@Override public void remove() {
					throw new UnsupportedOperationException();
				}};
		}
		
		@Override public boolean remove(Object arg) { throw new UnsupportedOperationException(); }
		@Override public boolean removeAll(Collection<?> arg) { throw new UnsupportedOperationException(); }
		@Override public boolean retainAll(Collection<?> arg) { throw new UnsupportedOperationException(); }
		
		@Override public int size() { return model.size(); }
		
		@Override public Object[] toArray() {
			return model.toArray();
		}
		
		@Override
		public <U> U[] toArray(U[] arg) {
			return model.toArray(arg);
		}
		
	}
	
}

package common;

import java.util.AbstractCollection;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;

import common.Util.Converter;

public interface CollectionView<T> extends Viewable.View, Iterable<T> { // extends ShallowCopyable<Collection<T>> {
	
	public Collection<T> asUnmodifiableCollection();
	
	public boolean contains(Object arg0);
	public boolean containsAll(Collection<?> arg0);
	public boolean isEmpty();

	public Iterator<T> iterator();
	
	public int size();
	public Object[] toArray();
	public <U> U[] toArray(U[] arg);
	
	
	//public static CollectionView of(){return null;}
	public static class ShallowOf<T> extends AbstractCollection<T> implements CollectionView<T> {//, Collection<T> {
		
		final Collection<T> delegate;
		
		public ShallowOf(Collection<T> src){
			//new CollectionView.Of<>(new ArrayList<String>());
			delegate = src;
		}
		
		protected Collection<T> delegate() {
			return delegate;
		}

		@Override public int size() { return delegate.size(); }
		
		@Override
		public Collection<T> asUnmodifiableCollection() {
			//return Collections.unmodifiableCollection(delegate);
			return this;
		}
		
		/*
//		@Override public boolean add(T arg0) { throw new UnsupportedOperationException(); }
//		@Override public boolean addAll(Collection<? extends T> arg0) { throw new UnsupportedOperationException(); }
//		@Override public void clear() { throw new UnsupportedOperationException(); }
		
		@Override public boolean contains(Object arg0) { return delegate.contains(arg0); }
		@Override public boolean containsAll(Collection<?> arg0) { return delegate.containsAll(arg0); }
		@Override public boolean isEmpty() { return delegate.isEmpty(); }
		*/
		
		@Override public Iterator<T> iterator() {
//			return asUnmodifiableCollection().iterator(); // FIXEDME test
//			return Collections.unmodifiableCollection(delegate).iterator(); // FIXME test
			return delegate.iterator(); // FIXME test
			/*
			final Iterator<T> ite = delegate.iterator();
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
			*/
		}
		
		/*
//		@Override public boolean remove(Object arg) { throw new UnsupportedOperationException(); }
//		@Override public boolean removeAll(Collection<?> arg) { throw new UnsupportedOperationException(); }
//		@Override public boolean retainAll(Collection<?> arg) { throw new UnsupportedOperationException(); }
		
		@Override public int size() { return delegate.size(); }
		
		@Override public Object[] toArray() {
			return delegate.toArray();
		}
		
		@Override
		public <U> U[] toArray(U[] arg) {
			return delegate.toArray(arg);
		}
		*/
	}
	
	
	

	public static class Transform<U> extends AbstractCollection<U> implements CollectionView<U>, CollectionWrapper
	{
		final Collection<Object> delegate;
		final Converter<Object,U> transformer;
		
		@SuppressWarnings("unchecked")
		public <T> Transform (Collection<T> src, Converter<T,U> transformer) {
			// This is a safe cast because we're never inserting elements in src:
			this.delegate = (Collection<Object>) src;
			this.transformer = (Converter<Object, U>) transformer;
		}
		
		@Override
		public Collection<?> delegate() {
			return delegate;
		}
		
		@Override public int size() { return delegate.size(); }
		
		@Override
		public Collection<U> asUnmodifiableCollection() {
			return this;
		}

		@Override public Iterator<U> iterator() {
			final Iterator<Object> ite = delegate.iterator();
			return new Iterator<U>(){
				@Override public boolean hasNext() {
					return ite.hasNext();
				}
				@Override public U next() {
					//return transformer.convert((T)ite.next());
					return transformer.convert(ite.next());
				}
				@Override public void remove() {
					throw new UnsupportedOperationException();
				}
			};
		}
		
	}
	
	
	
	
	/*
	 * "contains" and "containsAll" use the underlying View's equality, // FIXME no more true..?
	 * so it is up to the View object to decide how to handle equality
	 * with their model type
	 * 
	 */
	public static class Of <V extends Viewable.View> extends AbstractCollection<V>
	/*extends CollectionView.Of<V>*/ implements CollectionView<V>
	{
		
		final Collection<Viewable<V>> delegate;
		
		@SuppressWarnings("unchecked")
		public <T extends Viewable<V>> Of (Collection<T> src) {
			// This is a safe cast because we're never inserting elements in src:
			this.delegate = (Collection<Viewable<V>>) src;
		}
		/*
		protected Collection<Viewable<V>> delegate() {
			return delegate;
		}
		*/
		@Override public int size() { return delegate.size(); }
		
		@Override
		public Collection<V> asUnmodifiableCollection() {
			return this;
		}
		
		
		/*
		
		@Override public boolean contains(Object arg0) {
			for (Iterator<V> ite = iterator(); ite.hasNext();) {
				if (arg0.equals(ite.next()))
					return true;
			}
			return false;
		}
		
		@Override public boolean containsAll(Collection<?> arg0) {
			for (Object arg: arg0)
				if (!contains(arg))
					return false;
			return true;
		}
		
		@Override public boolean isEmpty() { return delegate.isEmpty(); }
		*/
		
		@Override public Iterator<V> iterator() {
			final Iterator<Viewable<V>> ite = delegate.iterator();
			return new Iterator<V>(){
				@Override public boolean hasNext() {
					return ite.hasNext();
				}
				@Override public V next() {
					return ite.next().view();
				}
				@Override public void remove() {
					throw new UnsupportedOperationException();
				}};
		}
		
		/*
		@Override public int size() { return delegate.size(); }
		

		@Override public Object[] toArray() {
			Object[] ret = new Object[size()];
			int i = 0;
			for (V v: this)
				ret[i++] = v;
			return ret;
		}
		
		@Override
		public <U> U[] toArray(U[] arg) {
			if (arg.length < size()) {
				U[] ret = new U[size()];
			} else {
				int i = 0;
				for (V v: this)
					arg[i++] = v;
				return arg;
			}
		}
		*/
		
		
	}
	
}

























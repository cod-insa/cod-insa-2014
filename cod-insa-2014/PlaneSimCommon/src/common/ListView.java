package common;

import java.util.AbstractList;
import java.util.Collection;
import java.util.List;
import java.util.ListIterator;

import common.Util.Converter;

public interface ListView<T>
	extends CollectionView<T> //, ShallowCopyable<List<T>>
{
	public List<T> asUnmodifiableList();
	
	// From java.util.List interface:
	public T get(int arg0);
	public int indexOf(Object arg0);
	
	public int lastIndexOf(Object arg0);
	public ListIterator<T> listIterator();
	public ListIterator<T> listIterator(int arg);
	
	public List<T> subList(int arg0, int arg1);
	
	//public class Of<T, LT extends List<T>> extends CollectionView.Of<T> /*implements ListView<T>*/ {
	//public static class Of<T> extends CollectionView.Of<T> implements ListView<T> {
	//public static class Of<T> extends ForwardingList<T> implements ListView<T> {
	public static class ShallowOf<T> extends AbstractList<T> implements ListView<T> {
		// LT extends List<T>
		
		final List<T> delegate;
		
		//final List<T> model;
		
		public ShallowOf(List<T> src) {
			//super(src);
			//this.model = src;
			this.delegate = src;
		}
		/*
		@Override
		protected List<T> delegate() {
			return (List<T>)delegate;
		}
		*/
		/*
		@Override
		public List<T> shallowCopy() {
			// TODO Auto-generated method stub
			return null;
		}
		*/
		@Override
		public List<T> asUnmodifiableList() {
			//return Collections.unmodifiableList(delegate());
			return this;
		}
		
		@Override
		public Collection<T> asUnmodifiableCollection() {
			//return Collections.unmodifiableCollection(delegate);
			return this;
		}
		
		
		@Override public T get(int arg0) { return delegate.get(arg0); }
		
		@Override public int size() { return delegate.size(); }
		
//		@Override public int indexOf(Object arg0) { return delegate().indexOf(arg0); }
//		
//		@Override public int lastIndexOf(Object arg0) { return delegate().lastIndexOf(arg0); }
//		
//		@Override
//		public ListIterator<T> listIterator() {
//			return listIterator(0);
//		}
//		
//		@Override public ListIterator<T> listIterator(int arg) {
//			return asUnmodifiableList().listIterator(arg); // crashes?
//		}
//
//		@Override public List<T> subList(int arg0, int arg1) {
//			//return new ListViewWrapper<>(delegate().subList(arg0, arg1));
//			return asUnmodifiableList().subList(arg0, arg1);
//		}
		
		

		@Override
		public boolean containsTypeSafe(T arg) {
			return contains(arg);
		}

		@Override
		public <U extends T> boolean containsTypeSafe(Collection<U> arg) {
			return containsAll(arg);
		}
		
		
	}
	
	
	
	
	

	public static class Transform<U> extends AbstractList<U> implements ListView<U>, CollectionWrapper
	{
		final List<?> delegate;
		final Converter<Object,U> transformer;
		
		@SuppressWarnings("unchecked")
		public <T> Transform (List<T> src, Converter<T,U> transformer) {
			// This is a safe cast because we're never inserting elements in src:
			this.delegate = (List<?>) src;
			this.transformer = (Converter<Object, U>) transformer;
		}

		@Override public U get(int arg0) { return transformer.convert(delegate.get(arg0)); }

		@Override public int size() { return delegate.size(); }

		@Override public Collection<U> asUnmodifiableCollection() { return this; }

		@Override public List<U> asUnmodifiableList() { return this; }

		@Override public List<?> delegate() {
			return delegate;
		}
		/*
		@SuppressWarnings("unchecked")
		@Override public List<U> newWrapperInstance(Collection<?> src) {
			return new Transform<U>((List<Object>)src, transformer);
		}
		*/

		@Override
		public boolean containsTypeSafe(U arg) {
			return contains(arg);
		}

		@Override
		public <W extends U> boolean containsTypeSafe(Collection<W> arg) {
			return containsAll(arg);
		}
		
	}
	
	
	
	
	
	
	
	//public static class OfViews <V extends Viewable.View, T extends Viewable<V>> extends ListView.Of<V> implements ListView<V>
	public static class Of <V extends Viewable.View> extends AbstractList<V> implements ListView<V>
	{

		final List<Viewable<V>> delegate;
		
		@SuppressWarnings("unchecked")
		public <T extends Viewable<V>> Of(List<T> src) {
			//super(new CollectionView.OfViews(src));
			
			// This is a safe cast because we're never inserting elements in src:
			this.delegate = (List<Viewable<V>>) src;
		}
		
		@Override public V get(int arg0) { return delegate.get(arg0).view(); }

		@Override public int size() { return delegate.size(); }

		@Override public Collection<V> asUnmodifiableCollection() { return this; }

		@Override public List<V> asUnmodifiableList() { return this; }

		@Override
		public boolean containsTypeSafe(V arg) {
			return contains(arg);
		}

		@Override
		public <U extends V> boolean containsTypeSafe(Collection<U> arg) {
			return containsAll(arg);
		}
		
		
//		
//		@Override
//		public ListIterator<T> listIterator() {
//			return listIterator(0);
//		}
//		
//		@Override public ListIterator<T> listIterator(int arg) {
//			//return Collections.unmodifiableList(this).listIterator(arg); // would probably crash
//			final ListIterator<VT> ite = delegate().listIterator();
//			return new ListIterator<T>(){
//				@Override public void add(T e) {
//					throw new UnsupportedOperationException();
//				}
//				@Override public boolean hasNext() {
//					return ite.hasNext();
//				}
//				@Override public boolean hasPrevious() {
//					return ite.hasPrevious();
//				}
//				@Override public T next() {
//					return ite.next().getView();
//				}
//				@Override public int nextIndex() {
//					return ite.nextIndex();
//				}
//				@Override public T previous() {
//					return ite.previous().getView();
//				}
//				@Override public int previousIndex() {
//					return ite.previousIndex();
//				}
//				@Override public void remove() {
//					throw new UnsupportedOperationException();
//				}
//				@Override public void set(T e) {
//					throw new UnsupportedOperationException();
//				}};
//		}
//
//		@Override public List<T> subList(int arg0, int arg1) {
//			return new ListViewWrapper<>(delegate().subList(arg0, arg1));
//		}
//		
		
		
	}
	
}
/*
class A<T> implements Collection<T>{

	@Override
	public boolean add(T arg0) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean addAll(Collection<? extends T> arg0) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void clear() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean contains(Object arg0) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean containsAll(Collection<?> arg0) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean isEmpty() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public Iterator<T> iterator() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean remove(Object arg0) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean removeAll(Collection<?> arg0) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean retainAll(Collection<?> arg0) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public int size() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public Object[] toArray() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public <T> T[] toArray(T[] arg0) {
		// TODO Auto-generated method stub
		return null;
	}}
*/

package common;

import java.util.List;
import java.util.ListIterator;

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
	public static class Of<T> extends CollectionView.Of<T> implements ListView<T> {
		// LT extends List<T>
		
		public Of(List<T> src) {
			super(src);
			// TODO Auto-generated constructor stub
		}
		
		protected List<T> getModel() {
			return (List<T>) model;
		}
		/*
		@Override
		public List<T> shallowCopy() {
			// TODO Auto-generated method stub
			return null;
		}
		*/
		@Override
		public List<T> asUnmodifiableList() {
			// TODO Auto-generated method stub
			return null;
		}

		
		
		
		
		
		
		
		
		
		@Override
		public T get(int arg0) {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public int indexOf(Object arg0) {
			// TODO Auto-generated method stub
			return 0;
		}

		@Override
		public int lastIndexOf(Object arg0) {
			// TODO Auto-generated method stub
			return 0;
		}

		@Override
		public ListIterator<T> listIterator() {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public ListIterator<T> listIterator(int arg) {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public List<T> subList(int arg0, int arg1) {
			// TODO Auto-generated method stub
			return null;
		}
		
		
		
	}
	
	public static class OfViews <V extends Viewable.View, T extends Viewable<V>>
		extends ListView.Of<V> implements ListView<V>
	{
		
		public OfViews(List<T> src) {
			super(new CollectionView.OfViews(src));
			// TODO Auto-generated constructor stub
		}
		
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

package common;


import java.lang.reflect.InvocationTargetException;
import java.util.Collection;
import java.util.List;
import java.util.Random;

import model.Coord;

import common.Copyable.Copier;


public class Util {
	
	public static final Random rand = new Random();
	
	/*
	private static final Coord _unit = new Coord(1,1);
	public static final Coord.View unit = _unit.view;
	
	private static final Coord _origin = new Coord(0,0);
	public static final Coord.View origin = _origin.view;
	
	//public static final Immutable<Coord> test = new Immutable<Coord>(new Coord.Copy(0,0));
	//public static final Unique<Coord> c = new Unique<Coord>();
	*/

	public static final Coord.View
		origin = new Coord(0,0).view,
		unit   = new Coord(1,1).view
	;
	
	
	public static interface Converter<T,U> {
		public U convert(T src);
	}
	
	
	@SuppressWarnings("unchecked")
	public static<T extends Copyable>
	T copy(T src) {
		//Object copy = src.copy();
		return (T)src.copy();
	}
	

//	public static<V extends Viewable.View, T extends Viewable<V>> void foo(T t) {}
//	public static void bar(Viewable<?> v) { foo(v); }

//	public static<V extends Viewable.View, T extends Viewable<V>> V foo(T t) { return null; }
//	public static int bar(Viewable<?> v) { return foo(v); }

//	public static<V extends Viewable.View & Copyable<Integer>, T extends Viewable<V>> void foo(T t) {}
//	public static void bar(Viewable<? extends Copyable<Integer>> v) { foo(v); }

//	interface A { public Object foo(); }
//	interface B extends A { @Override public Integer foo(); }
//	class C implements A { @Override public Integer foo() { return null; } }
	
	
	
	// List utilities:
	

//	public static <T extends Copyable>
//	Copyable.List<T> asCopyable(final List<T> model) {
//		/*return new Copyable.List<T>() {
//			
//		};*/
//		return new Copyable.List<T>(model);
//	}
	
	
//	public static <T extends Copyable>
//	ArrayList<T> copy(List<T> src) {
//		ArrayList<T> ret = new ArrayList<>(src.size());
//		for (T elt: src)
//			ret.add(copy(elt));
//		return ret;
//	}
//	
//	public static <T>
//	ArrayList<T> shallowCopy(List<T> src) {
//		return new ArrayList<>(src);
//	}
	
	/*
	public final Copyable.Copier<List<? extends Copyable>> listCopier = new Copyable.Copier<List<?>>(){
		@Override
		public List<?> copy(List<?> src) {
			return Util.copy(src);
		}
	};
	*/

	public static <T extends Copyable>
	Copyable.Copier<List<T>> getListCopier() {
		return new Copyable.Copier<List<T>>(){
			@Override
			public List<T> copy(List<T> src) {
				return Util.copy(src);
			}
		};
	}
	public static <T>
	Copyable.Copier<List<T>> getListCopier(final Copier<T> elementCopier) {
		return new Copyable.Copier<List<T>>(){
			@Override
			public List<T> copy(List<T> src) {
				return Util.copy(src, elementCopier);
			}
		};
	}
	
	
	
	public static <V extends Viewable.View, T extends Viewable<V>>
	ListView<V> view(List<T> src) {
		return new ListView.OfViews<>(src);
	}
	
	public static <V extends Viewable.View, T extends Viewable<V>>
	V view(Unique<T> src) {
		return Unique.view(src);
	}
	
	
	public static <T>
	CollectionView<T> shallowView(Collection<T> src) {
		return new CollectionView.Of<>(src);
	}
	public static <T>
	ListView<T> shallowView(List<T> src) {
		return new ListView.Of<>(src);
	}
	
	
/*
	public static <V extends Viewable.View, T extends Viewable<V>>
	CollectionView<V> view(Collection<T> src) {
		return new CollectionView.Of<>(src);
	}
*/
	
	
	/*
	static class A{}
	
	public static<T extends A> int bar(T t) {
		return 42;
	}
	public static<T> int bar(T t) {
		return 36;
	}
	
	public static<T> int foo(T t) {
		return bar(t);
	}
	
	public static void main(String[] args) {
		System.out.println(foo(new A()));
	}
	*/
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	@SuppressWarnings("unchecked")
	public static<T, CT extends Collection<T>>
	//CT copy (CT src) {
	CT copy (CT src, Copier<T> elemeCopier) {
		Class<?> c = src.getClass();
		try {
			//Constructor<?> ctor = c.getConstructor();
			CT ret = (CT) c.newInstance();
			//ret.addAll(src);
			for (T elt: src)
				//ret.add(copy(elt));
				ret.add(elemeCopier.copy(elt));
			return ret;
		} catch (
			//  NoSuchMethodException
			  SecurityException
			| InstantiationException
			| IllegalAccessException
			| IllegalArgumentException
			//| InvocationTargetException
			e
		) {
			throw new Error("Cannot deeply copy this collection: the class is impossible to construct with 0 argument");
		}
	}

	public static<T extends Copyable, CT extends Collection<T>>
	CT copy (CT src) {
		return copy(src, new Copier<T>(){
			@Override
			public T copy(T src) {
				return copy(src);
			}
		});
	}
	
	
	
	
	
	
	
	@SuppressWarnings("unchecked")
	public static<T, CT extends Collection<T>>
	CT shallowCopy (CT src) {
		
		try {
			/*Method cloneMethod = src.getClass().getDeclaredMethod("clone");
				if (cloneMethod.isAccessible())
					return (CT) cloneMethod.invoke(src);*/
			return (CT) src.getClass().getDeclaredMethod("clone").invoke(src);
		} catch (
			NoSuchMethodException | IllegalAccessException | IllegalArgumentException | InvocationTargetException e
		) {
			try {
				CT ret = (CT) src.getClass().newInstance();
				ret.addAll(src);
				return ret;
			} catch (
				  SecurityException
				| InstantiationException
				| IllegalAccessException
				| IllegalArgumentException
				f
			) {
				throw new Error(
						"Cannot shallowCopy this collection: the class has no public clone method" +
						"and is impossible to construct with 0 argument"
					);
			}
		}
		
		//if (src instanceof Cloneable)
		//	return ((Cloneable)src).clone();
	}
	
	
	/*
	@SuppressWarnings("unchecked")
	public static<T> Collection<T> makeCopy(Collection<T> src) {
		
		Collection<T> ret;
		
		//Class<? extends Collection<T>> c = getModel().getClass();
		Class<?> c = src.getClass();
		try {
			Constructor<?> ctor = c.getConstructor();
			ret = (Collection<T>) ctor.newInstance();
			ret.addAll(src);
			return ret;
		} catch (
			  NoSuchMethodException
			| SecurityException
			| InstantiationException
			| IllegalAccessException
			| IllegalArgumentException
			| InvocationTargetException
			e
		) {
			try {
				Constructor<?> ctor = c.getConstructor(Collection.class);
				return (Collection<T>) ctor.newInstance(src);
			} catch (
			  NoSuchMethodException
				| SecurityException
				| InstantiationException
				| IllegalAccessException
				| IllegalArgumentException
				| InvocationTargetException
				f
			) {
				throw new Error("Cannot copy this collection: the class is impossible to construct with 0 argument or 1 collection argument");
			}
		}
		
	}
	*/
	
	
	
}












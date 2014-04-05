package common;


import java.lang.reflect.InvocationTargetException;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Set;

import common.Copyable.Context;
import common.Copyable.Copier;


public class Util {
	
	public static final Random rand = new Random();
	

	///////////////////////////////////////////////////////////////////////////
	// MISC
	///////////////////////////////////////////////////////////////////////////
	
	
	public static interface Converter<T,U> {
		public U convert(T src);
	}
	
	public static interface ConditionalConverter<T,U> extends Converter<T,U> {
		public boolean canConvert(T src);
	}
	
	public final static class Dummy<T> {
		public Dummy() { };
	}
	
	
	///////////////////////////////////////////////////////////////////////////
	// COPYING
	///////////////////////////////////////////////////////////////////////////

	static Context getContext(Context context) {
		return context == null? new Context(): context;
	}
	static Context getContext() { return getContext(null); }
	
	/**
	 * A copy() utility based on the requirement that any Copyable's copy() result
	 * should be castable to the object class
	 * 
	 */
	@SuppressWarnings("unchecked")
	public static<T extends Copyable>
	T
		copy(T src, Context context)
	{
		return (T) src.copy(context);
	}
	
	public static<T extends Copyable>
	T
		copy(T src)
	{
		return copy(src, getContext());
	}
	
	
	public static<T extends CopyableAs<R>, R>
	R
		copy(T src)
	{
		return src.copyAs(new Dummy<R>());
	}
	
	
	/*
	 * Method for generating a deep list copier if the element type is Copyable
	 */
	public static <T extends Copyable>
	Copyable.Copier<List<T>>
		getListCopier ()
	{
		return new Copyable.Copier<List<T>>(){
			@Override
			public List<T> copy(List<T> src, Context context) {
				return Util.copy(src, context);
			}
		};
	}
	
	/*
	 * Method for generating a deep list copier based on an element Copier
	 */
	public static <T>
	Copyable.Copier<List<T>>
		getListCopier (final Copier<T> elementCopier)
	{
		return new Copyable.Copier<List<T>>(){
			@Override
			public List<T> copy(List<T> src, Context context) {
				return Util.copy(src, elementCopier, context);
			}
		};
	}
	

	/*
	 * Dirty methods for deep/shallow-copying collections:
	 * 
	 */

	@SuppressWarnings("unchecked")
	public static<T, CT extends Collection<T>>
	CT
		copy (CT src, Copier<T> elemeCopier, Context context)
	{
		
		Collection<?> realCollectionSrc = src;
		
		while (realCollectionSrc instanceof CollectionWrapper)
			realCollectionSrc = ((CollectionWrapper)src).delegate();
		
		Class<?> c = realCollectionSrc.getClass();
		
		try {
			CT ret = (CT) c.newInstance();
			for (T elt: src)
				ret.add(elemeCopier.copy(elt, context));
			return ret;
		} catch (
			  SecurityException
			| InstantiationException
			| IllegalAccessException
			| IllegalArgumentException
			e
		) {
			throw new IllegalArgumentException("Cannot deeply copy this collection:" +
					"the class is impossible to construct with 0 argument" +
					"and is not an instance of CollectionWrapper ("+c+")");
		}
	}
	public static<T, CT extends Collection<T>>
	CT
		copy (CT src, Copier<T> elemeCopier)
	{
		return copy(src, elemeCopier, getContext());
	}

	public static<T extends Copyable, CT extends Collection<T>>
	CT
		copy (CT src, Context context)
	{
		return copy(src, new Copier<T>() {
			@Override
			public T copy(T src, Context context) {
				return Util.copy(src, context);
			}
		}, context);
	}
	public static<T extends Copyable, CT extends Collection<T>>
	CT
		copy (CT src)
	{
		return copy(src, getContext());
	}

	/**
	 * 
	 * Warning: deep copying collection wrappers that do not implement the
	 * marker interface CollectionWrapper is unsafe and will not perform a deep
	 * copy. // TODO use instead a predefined list of legally copyable collections
	 * 
	 */
	@SuppressWarnings("unchecked")
	public static<T, CT extends Collection<T>>
	CT
		shallowCopy (CT src)
	{
		
		try {
			return (CT) src.getClass().getDeclaredMethod("clone").invoke(src);
		} catch (
			NoSuchMethodException | IllegalAccessException | IllegalArgumentException | InvocationTargetException e
		) {
			Collection<?> realCollectionSrc = src;
			
			while (realCollectionSrc instanceof CollectionWrapper)
				realCollectionSrc = ((CollectionWrapper)src).delegate();
			
			Class<?> c = realCollectionSrc.getClass();
			
			try {
				CT ret = (CT) c.newInstance();
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
		
	}
	
	
	
	///////////////////////////////////////////////////////////////////////////
	// VIEWING
	///////////////////////////////////////////////////////////////////////////

	/*
	 * Method for generating a list viewer if the element type is Viewable
	 */
	public static <T extends Viewable<V>, V extends Viewable.View>
	Converter<List<T>, ListView<V>>
		getListViewer()
	{
		return new Converter<List<T>, ListView<V>>(){
			@Override
			public ListView<V> convert(List<T> src) {
				return view(src);
			}
		};
	}

	public static <T extends Viewable<V>, V extends Viewable.View>
	Converter<Collection<T>, CollectionView<V>>
		getCollectionViewer()
	{
		return new Converter<Collection<T>, CollectionView<V>>(){
			@Override
			public CollectionView<V> convert(Collection<T> src) {
				return view(src);
			}
		};
	}
	

	public static <T extends Viewable<V>, V extends Viewable.View>
	Converter<List<T>, ListView<V>>
		getViewer(List<T> src)
	{
		return getListViewer();
	}
	
	public static <T extends Viewable<V>, V extends Viewable.View>
	Converter<Collection<T>, CollectionView<V>>
		getViewer(Collection<T> src)
	{
		return getCollectionViewer();
	}
	
	public static <T extends Viewable<V>, V extends Viewable.View>
	Converter<T, V>
		getViewer()
	{
		return new Converter<T, V>(){
			public V convert(T src) {
				return src.view();
			}
		};
	}
	
	public static <T extends Viewable<V>, V extends Viewable.View>
	Converter<T, V>
		getViewer(Dummy<T> arg)
	{
		return new Converter<T, V>(){
			public V convert(T src) {
				return src.view();
			}
		};
	}
	
	
	

	public static <T extends Viewable<V>, V extends Viewable.View>
	Converter<T, V>
		getViewer(T src)
	{
		return new Converter<T, V>() {
			public V convert(T src) {
				return src.view();
			}
		};
	}
	
	public static <T extends Viewable<V>, V extends Viewable.View>
	Converter<Unique<T>, V>
		getViewer(Unique<T> src)
	{
		return new Converter<Unique<T>, V>() {
			public V convert(Unique<T> src) {
				return view(src);
			}
		};
	}
	
//	public static <T extends Viewable<V>, V extends Viewable.View>
//	Converter<T, V>
//		getInternalViewer(Unique<T> src)
//	{
//		return new Converter<T, V>() {
//			public V convert(T src) {
//				return src.view();
//			}
//		};
//	}
	
	

	public static <V extends Viewable.View, T extends Viewable<V>>
	CollectionView<V>
		view (Collection<T> src)
	{
		return new CollectionView.Of<>(src);
	}
	
	public static <V extends Viewable.View, T extends Viewable<V>>
	SetView<V>
		view (Set<T> src)
	{
		return new SetView.Of<>(src);
	}
	
	public static <K, V extends Viewable.View, T extends Viewable<V>>
	MapView<K,V>
		view(Map<K,T> src)
	{
		return new MapView.Of<>(src);
	}
	
	public static <V extends Viewable.View, T extends Viewable<V>>
	ListView<V>
		view (List<T> src)
	{
		return new ListView.Of<>(src);
	}
	
	public static <V extends Viewable.View, T extends Viewable<V>>
	V
		view(Unique<T> src)
	{
		return Unique.view(src);
	}
	
	public static <V extends Viewable.View, T extends Viewable<V>>
	V
		view(T src)
	{
		return src.view();
	}
	
	
	public static <T>
	CollectionView<T>
		shallowView(Collection<T> src)
	{
		return new CollectionView.ShallowOf<>(src);
	}

	public static <T>
	SetView<T>
		shallowView(Set<T> src)
	{
		return new SetView.ShallowOf<>(src);
	}

	public static <T>
	ListView<T>
		shallowView(List<T> src)
	{
		return new ListView.ShallowOf<>(src);
	}
	
	
	public static <T, U>
	CollectionView<U>
		transform (Collection<T> src, Converter<T,U> eltTransformer)
	{
		return new CollectionView.Transform<U>(src, eltTransformer);
	}
	public static <T, U>
	ListView<U>
		transformView (List<T> src, Converter<T,U> eltTransformer)
	{
		return new ListView.Transform<U>(src, eltTransformer);
	}
	
	
	
	
	
	
}












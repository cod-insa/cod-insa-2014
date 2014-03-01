package common;


import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Random;

import model.Coord;


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
	

	public static <T extends Copyable<T>>
	ArrayList<T> copy(List<T> src) {
		ArrayList<T> ret = new ArrayList<>();
		return ret;
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
	
	
	
}












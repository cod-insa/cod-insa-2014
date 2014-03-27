package common;

/**
 * Created by LP on 27/03/2014.
 */
public class Pair<T> extends Couple<T,T> {
	
	public Pair (T first, T second) {
		super(first, second);
	}
	
	public Pair (Pair<T>.View src) {
		super(src.first(), src.second());
	}
	
//	public class View extends Couple<T,T>.View { }
	
}

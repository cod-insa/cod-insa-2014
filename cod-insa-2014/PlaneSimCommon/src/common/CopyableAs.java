package common;

import common.Util.Dummy;

/**
 * 
 * Copyable doesn't take a type parameter because we felt it was just overhead,
 * and not in Copyable's semantic that copy() could produce an object of a
 * different class. It also didn't fit well with inhertiance at all.
 * 
 * However, it is useful to be able to say something can be copied as something else.
 * For example, a view of an object of type T could be CopyableAs<T>, where the copy
 * would create a new T independent from the original model (which is within a
 * view's allowable behavior because it doesn't modify the model).
 * 
 * The Dummy<T> parameter is used to avoid conflicts, so that an object can be
 * CopyableAs different types. This is useful in the context of inheritance.
 * It also makes type inference easier.
 * 
 */
public interface CopyableAs<T> {
	
	public T copyAs(Dummy<T> type);
	
}


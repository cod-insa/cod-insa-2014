package common;

import java.util.Collection;
/*
public interface CollectionWrapper {
	public Collection<?> delegate();
	public Collection<?> newWrapperInstance(Collection<?> src);
}
*/

/*
 * A collection wrapper that wants to be copyable through Util.copy() must implement this interface,
 * so that Util.copy() can create an object of the actual class of the wrapped collection.
 * 
 */
public interface CollectionWrapper {
	
	public Collection<?> delegate();
	
	// Must be able to wrap a new collection of the same type as returned by delegate()
	//public Collection<T> newWrapperInstance(Collection<?> src);
	
}

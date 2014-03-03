package common;

import java.util.Collection;
/*
public interface CollectionWrapper {
	public Collection<?> delegate();
	public Collection<?> newWrapperInstance(Collection<?> src);
}
*/
public interface CollectionWrapper {
	
	public Collection<?> delegate();
	
	// Must be able to wrap a new collection of the same type as returned by delegate()
	//public Collection<T> newWrapperInstance(Collection<?> src);
	
}

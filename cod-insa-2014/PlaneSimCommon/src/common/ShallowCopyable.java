package common;


public interface ShallowCopyable<T> {
	
	/* Returns an independent object possibly sharing data with the original
	 * A shallow copy of a list, even in the case of a list that uses internally
	 * another list for implementation, should yield a list that is just deep enough
	 * to rest unaffected by structural modifications of the first list.
	 */
	T shallowCopy();
	
}
